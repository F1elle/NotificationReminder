package com.f1elle.notificationreminder.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.f1elle.notificationreminder.Note
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<Note>{
    override val defaultValue: Note = Note.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Note {
        try{
            return Note.parseFrom(input)}
        catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Note, output: OutputStream) {
        t.writeTo(output)
    }
}