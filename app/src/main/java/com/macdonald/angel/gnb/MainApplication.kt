package com.macdonald.angel.gnb

import android.app.Application
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.di.initDI

class MainApplication : Application() {

    companion object {
        lateinit var database: GNBLocalDatabase
    }

    override fun onCreate() {
        super.onCreate()

        initDI()
    }

}