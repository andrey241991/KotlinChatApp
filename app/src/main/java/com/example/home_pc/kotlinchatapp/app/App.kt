package com.example.home_pc.kotlinchatapp.app

import android.app.Application
import androidx.room.Room
import com.example.home_pc.kotlinchatapp.dagger.component.AppComponent
import com.example.home_pc.kotlinchatapp.dagger.component.DaggerAppComponent
import com.example.home_pc.kotlinchatapp.db.MyDataBase

class App : Application() {

    lateinit var myDataBase: MyDataBase

    companion object {
        var instance: App? = null

        fun getApplicationInstance(): App {
            if (instance == null) {
                instance = App()
            }
            return instance as App
        }

        lateinit var appComponent: AppComponent

        fun getDaggerAppComponent(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
        initDataBase()
    }

    fun getDataBase(): MyDataBase {
        return myDataBase
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder().build()
    }

    fun initDataBase(){
        myDataBase = Room.databaseBuilder(
            this,
            MyDataBase::class.java,
            MyDataBase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}


