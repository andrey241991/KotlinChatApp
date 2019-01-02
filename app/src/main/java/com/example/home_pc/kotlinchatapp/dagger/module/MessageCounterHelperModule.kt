package com.example.home_pc.kotlinchatapp.dagger.module

import com.example.home_pc.kotlinchatapp.utils.MessageCounterHelper
import dagger.Module
import dagger.Provides

@Module
class MessageCounterHelperModule {
    @Provides
    internal fun provideMessageCounterHelper(): MessageCounterHelper {
        return MessageCounterHelper()
    }
}