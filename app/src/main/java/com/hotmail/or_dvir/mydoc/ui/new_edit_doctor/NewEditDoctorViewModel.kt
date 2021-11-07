package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.AddressFactory
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.DoctorFactory
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel.NewEditDoctorUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import java.util.UUID
import java.util.regex.Pattern

class NewEditDoctorViewModel(app: Application) : BaseViewModel<NewEditDoctorUiState>(app)
{
    private companion object
    {
        //todo KEEP!!! WILL BE NEEDED IN THE FUTURE!!!
        private val PATTERN_NUMBER = Pattern.compile("^[0-9]*$")
        private val PATTERN_NEGATIVE_NUMBER = Pattern.compile("^-[0-9]*$")
    }

    private val doctorsRepo: DoctorsRepository by inject()

    private var isEditing = false
    private var firstTime = true

    override fun initUiState() = NewEditDoctorUiState()

    //todo move title to the UI state
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

            val doctor = doctorsRepo.getDoctor(doctorId)
            if (doctor == null)
            {
                updateUiState(
                    uiState.value!!.copy(
                        isLoading = false,
                        //todo handle errors
//                        error = getString(R.string.error_loadingDoctor)
                    )
                )
            } else
            {
                updateUiState(
                    uiState.value!!.copy(
                        isLoading = false,
                        doctor = doctor
                    )
                )
            }
        }
    }

    fun createOrUpdateDoctor()
    {
        if (!validateInput())
        {
            return
        }

        viewModelScope.launch(mainDispatcher) {
            uiState.value?.apply {
                updateUiState(
                    copy(isLoading = true)
                )

                val success =
                    if (isEditing)
                    {
                        doctorsRepo.updateDoctor(doctor)
                    } else
                    {
                        doctorsRepo.addDoctor(doctor)
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

    //todo way too many "on X input changed" functions.
    // can i make it better?
    fun onNameInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(name = newInput)
            val newErrors = errors.copy(nameError = "")

            updateUiState(
                copy(
                    //reset previous error
                    errors = newErrors,
                    doctor = newDoc
                )
            )
        }
    }

    fun onSpecialityInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(speciality = newInput)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onAddressLineInputChanged(newInput: String)
    {
        uiState.value?.apply {
            //using AddressFactory here and not directly creating an instance because
            //the factory might change in the future
            val newAddress =
                doctor.address?.copy(addressLine = newInput) ?:
                AddressFactory.createEmpty().copy(addressLine = newInput)

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onAddressNoteInputChanged(newInput: String)
    {
        uiState.value?.apply {
            //using AddressFactory here and not directly creating an instance because
            //the factory might change in the future
            val newAddress =
                doctor.address?.copy(note = newInput) ?:
                AddressFactory.createEmpty().copy(note = newInput)

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    private fun validateInput(): Boolean
    {
        var isValid = true
        //start with fresh error state
        var errors = UiErrors()

        uiState.value!!.let { state ->
            state.doctor.apply {
                name.apply {
                    if (isBlank())
                    {
                        errors = errors.copy(nameError = getString(R.string.error_emptyField))
                        isValid = false
                    }
                }
            }

            updateUiState(
                state.copy(errors = errors)
            )
        }

        return isValid
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    //todo there are too many fields (long form), there has to be a better way than this!!!
    data class NewEditDoctorUiState(
        //todo just for initialization. should i use something else?
        val doctor: Doctor = DoctorFactory.createEmpty(),
        val isLoading: Boolean = false,
        val errors: UiErrors = UiErrors()
    )

    //todo observe this class separately from the UI???
    //todo copying behaviour of UiState (all variables immutable and using copy() to change them)
    // can variables be var???
    data class UiErrors(
        val nameError: String = "",
        val streetError: String = "",
        val houseNumberError: String = "",
        val cityError: String = ""
    )
}