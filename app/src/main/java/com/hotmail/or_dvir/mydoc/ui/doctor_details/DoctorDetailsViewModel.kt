package com.hotmail.or_dvir.mydoc.ui.doctor_details

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.DoctorFactory
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import java.util.UUID

class DoctorDetailsViewModel(app: Application) : BaseViewModel<DoctorDetailsUiState>(app)
{
    private val doctorsRepo: DoctorsRepository by inject()

    override fun initUiState() = DoctorDetailsUiState()

    //todo instead of loading the doctor by request, observe the doctor as a Flow
    // because this screen can directly lead to the edit doctor screen!
    fun loadDoctor(doctorId: UUID)
    {
        //todo replace this function with realtime listener for firebase.
        // the user can edit the doctor and then return to this screen - data should automatically update

        viewModelScope.launch(mainDispatcher) {
            updateUiState(
                uiState.value!!.copy(
                    error = "", //reset any errors
                    isLoading = true
                )
            )

            val doctor = doctorsRepo.getDoctor(doctorId)
            if (doctor == null)
            {
                updateUiState(
                    uiState.value!!.copy(
                        isLoading = false,
                        error = getString(R.string.error_loadingDoctor)
                    )
                )
            } else
            {
                //successfully loaded doctor
                updateUiState(
                    uiState.value!!.copy(
                        isLoading = false,
                        doctor = doctor
                    )
                )
            }
        }
    }

    fun deleteDoctor()
    {
        viewModelScope.launch(mainDispatcher) {
            uiState.value?.apply {
                updateUiState(
                    copy(isLoading = true)
                )

                val success = doctorsRepo.deleteDoctor(doctor)

                if (success)
                {
                    navigateBack()
                } else
                {
                    //todo handle errors
                    updateUiState(
                        copy(isLoading = false)
                    )
                }
            }
        }
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class DoctorDetailsUiState(
        //todo just for initialization. should i keep this or find another way?
        val doctor: Doctor = DoctorFactory.createDefault(),
        val error: String = "",
        val isLoading: Boolean = false
    )
}