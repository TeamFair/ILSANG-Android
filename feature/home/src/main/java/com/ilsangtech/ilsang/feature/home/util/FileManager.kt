package com.ilsangtech.ilsang.feature.home.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

object FileManager {
    fun createCacheFile(context: Context): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            File.createTempFile("cache", ".jpg", context.cacheDir)
        )
    }

    fun getBytesFromUri(context: Context, uri: Uri): ByteArray {
        var bitmap = rotateBitmapIfRequired(context, uri)

        val maxSizeBytes = 1 * 1024 * 100 // 100KB
        var quality = 100
        val outputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        while (outputStream.toByteArray().size > maxSizeBytes) {
            if (quality > 10) {
                quality -= 5
            } else {
                bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * 0.8f).toInt(),
                    (bitmap.height * 0.8f).toInt(),
                    true
                )
                quality = 100
            }
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        return outputStream.toByteArray()
    }

    private fun rotateBitmapIfRequired(context: Context, uri: Uri): Bitmap {
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

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}