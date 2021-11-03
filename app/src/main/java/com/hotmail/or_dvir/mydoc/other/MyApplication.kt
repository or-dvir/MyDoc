package com.hotmail.or_dvir.mydoc.other

import android.app.Application
import com.hotmail.or_dvir.database.AppDatabase
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepositoryImpl
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel
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
        viewModel { DoctorDetailsViewModel(androidApplication()) }
        viewModel { NewEditDoctorViewModel(androidApplication()) }

        //database
        single { AppDatabase.getDatabase(androidContext()) }
        single { get<AppDatabase>().doctorsDao() }

        //repositories
        single<DoctorsRepository> { DoctorsRepositoryImpl() }
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