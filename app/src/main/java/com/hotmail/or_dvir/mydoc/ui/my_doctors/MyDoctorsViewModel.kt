package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyDoctorsViewModel(private val app: Application) : AndroidViewModel(app), KoinComponent
{
    private val mainDispatcher = Dispatchers.Main
    private val doctorsRepo: DoctorsRepository by inject()

    private val _uiState = MutableLiveData(MyDoctorsUiState())
    val uiState: LiveData<MyDoctorsUiState> = _uiState

////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class MyDoctorsUiState(
        val doctors: List<Doctor> = listOf(),
        val isLoading: Boolean = false
    )
}