package com.hraj9258.fastfood;

import android.app.Application;
import com.hraj9258.fastfood.di.initKoin
import org.koin.android.ext.koin.androidContext

class FastFoodApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@FastFoodApp)
        }
    }
}
