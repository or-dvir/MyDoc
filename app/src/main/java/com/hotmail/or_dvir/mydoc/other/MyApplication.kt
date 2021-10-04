package com.hotmail.or_dvir.mydoc.other

import android.app.Application
import com.hotmail.or_dvir.mydoc.repositories.ClinicsRepository
import com.hotmail.or_dvir.mydoc.repositories.ClinicsRepositoryImpl
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepositoryImpl
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@Suppress("unused")
class MyApplication : Application()
{
    private val appModule = module {
        viewModel { MyDoctorsViewModel(androidApplication()) }
        single<DoctorsRepository> { DoctorsRepositoryImpl() }
        single<ClinicsRepository> { ClinicsRepositoryImpl() }
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