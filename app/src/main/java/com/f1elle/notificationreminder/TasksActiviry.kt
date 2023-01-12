package com.f1elle.notificationreminder

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.f1elle.notificationreminder.data.UserPreferencesSerializer

private const val USER_PREFERENCES_NAME = "UserPreferences"
private const val DATA_STORE_FILE_NAME = "UserPreferences.proto"
private const val SORT_ORDER_KEY = "sort_order"

private val Context.userPreferencesStore: DataStore<Note> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserPreferencesSerializer
)

