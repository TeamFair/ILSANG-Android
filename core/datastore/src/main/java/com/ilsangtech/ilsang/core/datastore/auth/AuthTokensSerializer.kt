package com.ilsangtech.ilsang.core.datastore.auth

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.ilsangtech.ilsang.core.datastore.AuthTokens
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AuthTokensSerializer @Inject constructor() : Serializer<AuthTokens> {
    override val defaultValue: AuthTokens
        get() = AuthTokens.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AuthTokens {
        return try {
            AuthTokens.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AuthTokens, output: OutputStream) {
        t.writeTo(output)
    }
}