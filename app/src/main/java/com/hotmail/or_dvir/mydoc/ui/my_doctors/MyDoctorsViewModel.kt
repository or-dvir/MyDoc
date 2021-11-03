package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel.MyDoctorsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MyDoctorsViewModel(app: Application) : BaseViewModel<MyDoctorsUiState>(app)
{
    private companion object
    {
        private const val TAG = "MyDoctorsViewModel"
    }

    private val doctorsRepo: DoctorsRepository by inject()

    init
    {
        viewModelScope.launch {
            try
            {
                doctorsRepo.getAllDoctors().collectLatest {
                    updateUiState(
                        uiState.value!!.copy(
                            error = "", //reset any errors
                            isLoading = false,
                            doctors = it
                        )
                    )
                }
            } catch (e: Exception)
            {
                Log.e(TAG, e.message, e)
                updateUiState(
                    uiState.value!!.copy(
                        error = getString(R.string.error_loadingDoctorList),
                        isLoading = false
                    )
                )
            }
        }
    }

    //we start loading the doctors in the init block, so set loading to true
    override fun initUiState() = MyDoctorsUiState(isLoading = true)

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class MyDoctorsUiState(
        //todo add error state
        val doctors: List<Doctor> = listOf(),
        val isLoading: Boolean = false,
        val error: String = ""
    )
}