package com.ilsangtech.ilsang.feature.home.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val maxSizeBytes = 1 * 1024 * 1024 // 1MB
        var quality = 100
        val outputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        while (outputStream.toByteArray().size > maxSizeBytes && quality > 10) {
            quality -= 5
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        return outputStream.toByteArray()
    }
}