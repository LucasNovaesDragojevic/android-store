package com.store

import android.app.Application
import com.store.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                listOf(
                    testeDatabaseModule,
                    preferencesModule,
                    daoModule,
                    uiModule,
                    viewModelModule
                )
            )
        }
    }
}