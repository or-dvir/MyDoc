package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Address
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel.NewEditDoctorUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import java.util.UUID

class NewEditDoctorViewModel(app: Application) : BaseViewModel<NewEditDoctorUiState>(app)
{
    private val doctorsRepo: DoctorsRepository by inject()
    private var isEditing = false
    private var firstTime = true

    override fun initUiState() = NewEditDoctorUiState()

    fun getTitle(): String
    {
        return if (isEditing)
        {
            getString(R.string.title_editDoctor)
        } else
        {
            getString(R.string.title_newDoctor)
        }
    }

    fun loadDoctor(doctorId: UUID?)
    {
        //only load the doctor the first time.
        //otherwise it would load again on every orientation change
        if (!firstTime)
        {
            return
        }

        firstTime = false

        //creating a new doctor
        if (doctorId == null)
        {
            isEditing = false
            return
        }

        //doctorId is not null -> editing existing doctor
        isEditing = true

        viewModelScope.launch(mainDispatcher) {
            updateUiState(
                uiState.value!!.copy(isLoading = true)
            )

            //todo handle errors
            val doctor = doctorsRepo.getDoctor(doctorId)

            updateUiState(
                uiState.value!!.copy(
                    isLoading = false,
                    doctor = doctor
                )
            )
        }
    }

    fun createOrUpdateDoctor()
    {
        //todo handle invalid input

        viewModelScope.launch(mainDispatcher) {
            uiState.value?.apply {
                updateUiState(
                    copy(isLoading = true)
                )

                val success =
                    if (isEditing)
                    {
                        doctorsRepo.update(doctor)
                    } else
                    {
                        doctorsRepo.add(doctor)
                    }

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

    fun onDoctorNameInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(name = newInput)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onDoctorSpecialityInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(specialty = newInput)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class NewEditDoctorUiState(
        //todo just for initialization. should i use something else?
        val doctor: Doctor = Doctor(UUID.randomUUID(), ""),
        val generalError: String = "",
        val isLoading: Boolean = false
    )
    {
        @StringRes
        val doctorNameError =
            if (doctor.name.isBlank())
            {
                R.string.error_emptyField
            } else
            {
                R.string.emptyString
            }

        fun isInputValid(): Boolean
        {
            return doctorNameError == R.string.emptyString
        }
    }
}