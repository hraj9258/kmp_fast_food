package com.hraj9258.fastfood.core.data

import com.hraj9258.fastfood.BuildConfig
import com.jamshedalamqaderi.kmp.appwrite.Client

object AppwriteConfig {
    const val DATABASE_ID = BuildConfig.APPWRITE_DATABASE_ID

    const val PROJECT_ID = BuildConfig.APPWRITE_PROJECT_ID

    const val ENDPOINT = BuildConfig.APPWRITE_ENDPOINT
    const val MENU_COLLECTION_ID = BuildConfig.APPWRITE_MENU_COLLECTION_ID
    const val CATEGORIES_COLLECTION_ID = BuildConfig.APPWRITE_CATEGORIES_COLLECTION_ID
}

object AppWriteFactory{
    fun create()=Client()
        .setEndpoint(AppwriteConfig.ENDPOINT)
        .setProject(AppwriteConfig.PROJECT_ID)
        .setSelfSigned(true)
}


