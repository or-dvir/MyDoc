package com.hotmail.or_dvir.mydoc.other

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@Suppress("unused")
class MyApplication : Application()
{
    private val appModule = module {
//        viewModel { LoginViewModel(androidApplication()) }
//        viewModel { RegisterViewModel(androidApplication()) }
//        single<MoviesRepository> { MoviesRepositoryImpl() }
//        single<UsersRepository> { UsersRepositoryImpl() }
    }

    override fun onCreate()
    {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}