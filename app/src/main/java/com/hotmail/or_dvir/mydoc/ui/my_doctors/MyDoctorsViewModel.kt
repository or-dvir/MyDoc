package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel.MyDoctorsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MyDoctorsViewModel(app: Application) : BaseViewModel<MyDoctorsUiState>(app)
{
    private val doctorsRepo: DoctorsRepository by inject()

    init
    {
        loadAllDoctors()
    }

    override fun initUiState() = MyDoctorsUiState()

    private fun loadAllDoctors()
    {
        viewModelScope.launch(mainDispatcher) {
            updateUiState(
                uiState.value!!.copy(isLoading = true)
            )

            //todo handle errors
            val allDoctors = doctorsRepo.getAll()

            updateUiState(
                uiState.value!!.copy(
                    isLoading = false,
                    doctors = allDoctors
                )
            )
        }
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class MyDoctorsUiState(
        //todo add error state
        val doctors: List<Doctor> = listOf(),
        val isLoading: Boolean = false
    )
}