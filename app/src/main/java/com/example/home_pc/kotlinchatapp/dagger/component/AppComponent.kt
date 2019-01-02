package com.example.home_pc.kotlinchatapp.dagger.component

import com.example.home_pc.kotlinchatapp.dagger.module.MessageCounterHelperModule
import com.example.home_pc.kotlinchatapp.mainactivity.viewmodel.MainViewModel
import dagger.Component

@Component(modules = arrayOf(MessageCounterHelperModule::class))
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}