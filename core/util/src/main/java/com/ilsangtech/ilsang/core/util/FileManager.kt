package com.ilsangtech.ilsang.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import androidx.core.graphics.scale

object FileManager {
    fun createCacheFile(context: Context): File {
        return File.createTempFile("cache", ".jpg", context.cacheDir)
    }

    fun getUriForFile(tempFile: File, context: Context): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tempFile
        )
    }

    suspend fun getBytesFromUri(context: Context, uri: Uri): ByteArray {
        return withContext(Dispatchers.IO) {
            var bitmap = rotateBitmapIfRequired(context, uri)

            val maxSizeBytes = 1 * 1024 * 100 // 100KB
            var quality = 95
            val minQuality = 60
            val outputStream = ByteArrayOutputStream()

            // 1. 품질만 낮추면서 압축
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            while (outputStream.toByteArray().size > maxSizeBytes && quality > minQuality) {
                quality -= 5
                outputStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            }

            // 2. 그래도 크면 이미지 크기를 줄여서 재시도 (점진적 다운스케일)
            while (outputStream.toByteArray().size > maxSizeBytes) {
                bitmap = bitmap.scale((bitmap.width * 0.9f).toInt(), (bitmap.height * 0.9f).toInt())
                quality = 90 // 다시 적절한 품질로 리셋
                outputStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            }

            outputStream.toByteArray()
        }
    }

    private suspend fun rotateBitmapIfRequired(context: Context, uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uri)!!
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            val inputStreamForExif = context.contentResolver.openInputStream(uri)!!
            val exif = ExifInterface(inputStreamForExif)
            inputStreamForExif.close()

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }

            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }
}