package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import java.util.UUID

class NewEditDoctorViewModel(app: Application) : BaseViewModel<DoctorDetailsUiState>(app)
{
    //todo all copied from other file!!!
//    private val doctorsRepo: DoctorsRepository by inject()
//
//    override fun initUiState() = DoctorDetailsUiState()
//
//    fun loadDoctor(doctorId: UUID)
//    {
//        viewModelScope.launch(mainDispatcher) {
//            updateUiState(
//                uiState.value!!.copy(isLoading = true)
//            )
//
//            //todo handle errors
//            val doctor = doctorsRepo.getDoctor(doctorId)
//
//            updateUiState(
//                uiState.value!!.copy(
//                    isLoading = false,
//                    doctor = doctor
//                )
//            )
//        }
//    }
//
//    fun deleteDoctor()
//    {
//        viewModelScope.launch(mainDispatcher) {
//            uiState.value?.apply {
//                updateUiState(
//                    copy(isLoading = true)
//                )
//
//                val success = doctorsRepo.delete(doctor)
//
//                if (success)
//                {
//                    navigateBack()
//                } else
//                {
//                    //todo handle errors
//                    updateUiState(
//                        copy(isLoading = false)
//                    )
//                }
//            }
//        }
//    }
//
//    ////////////////////////////////
//    ////////////////////////////////
//    ////////////////////////////////
//    ////////////////////////////////
//
//    data class DoctorDetailsUiState(
//        //todo just for initialization. should i keep this or find another way?
//        val doctor: Doctor = Doctor(UUID.randomUUID(), "", ""),
//        val error: String = "",
//        val isLoading: Boolean = false
//    )
}