package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.AddressFactory
import com.hotmail.or_dvir.mydoc.models.ContactDetailsFactory
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.DoctorFactory
import com.hotmail.or_dvir.mydoc.models.OpeningTime
import com.hotmail.or_dvir.mydoc.models.OpeningTimeFactory
import com.hotmail.or_dvir.mydoc.repositories.DoctorsRepository
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel.NewEditDoctorUiState
import com.hotmail.or_dvir.mydoc.ui.shared.BaseViewModel
import com.hotmail.or_dvir.mydoc.ui.shared.isNull
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
    fun onWebsiteInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newContactDetails =
                doctor.contactDetails?.copy(website = newInput) ?: ContactDetailsFactory
                    .createDefault().copy(website = newInput)

            val newDoc = doctor.copy(contactDetails = newContactDetails)
            val newErrors = errors.copy(websiteError = "")

            updateUiState(
                copy(
                    errors = newErrors,
                    doctor = newDoc
                )
            )
        }
    }

    fun onEmailInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newContactDetails =
                doctor.contactDetails?.copy(email = newInput) ?: ContactDetailsFactory
                    .createDefault().copy(email = newInput)

            val newDoc = doctor.copy(contactDetails = newContactDetails)
            val newErrors = errors.copy(emailError = "")

            updateUiState(
                copy(
                    errors = newErrors,
                    doctor = newDoc
                )
            )
        }
    }

    fun onPhoneNumberInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newContactDetails =
                doctor.contactDetails?.copy(phoneNumber = newInput) ?: ContactDetailsFactory
                    .createDefault().copy(phoneNumber = newInput)

            val newDoc = doctor.copy(contactDetails = newContactDetails)
            val newErrors = errors.copy(phoneNumberError = "")

            updateUiState(
                copy(
                    errors = newErrors,
                    doctor = newDoc
                )
            )
        }
    }

    fun onNameInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(name = newInput)
            val newErrors = errors.copy(nameError = "")

            updateUiState(
                copy(
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
            val newAddress =
                doctor.address?.copy(addressLine = newInput) ?: AddressFactory.createDefault()
                    .copy(addressLine = newInput)

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onAddressNoteInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newAddress =
                doctor.address?.copy(note = newInput) ?: AddressFactory.createDefault()
                    .copy(note = newInput)

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    private fun addOrRemoveOpeningTimeRow(indexToRemove: Int?)
    {
        //todo
        // add explanation that using index to avoid this bug
        //      add some time A -> add some time B -> add time A again -> remove the SECOND time A ->
        //      "minus" function removes the FIRST occurrence

        val isAdding = timeToRemove.isNull()

        uiState.value?.apply {
            val newOpeningTimes =
                if (isAdding)
                {
                    doctor.openingTimes.plus(OpeningTimeFactory.createDefault())
                } else
                {
                    remove at given index

                }

            val newDoc = doctor.copy(openingTimes = newOpeningTimes)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun removeOpeningTimeRow(indexToRemove: Int) = addOrRemoveOpeningTimeRow(indexToRemove)
    fun addOpeningTimeRow() = addOrRemoveOpeningTimeRow(null)

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

                contactDetails?.apply {
                    if (!isPhoneNumberValid())
                    {
                        errors =
                            errors.copy(phoneNumberError = getString(R.string.error_invalidPhoneNumber))
                        isValid = false
                    }

                    if (!isEmailValid())
                    {
                        errors = errors.copy(emailError = getString(R.string.error_invalidEmail))
                        isValid = false
                    }

                    if (!isWebsiteValid())
                    {
                        errors = errors.copy(websiteError = getString(R.string.error_invalidUrl))
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
        val doctor: Doctor = DoctorFactory.createDefault(),
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
        val cityError: String = "",
        val phoneNumberError: String = "",
        val emailError: String = "",
        val websiteError: String = "",
    )
}