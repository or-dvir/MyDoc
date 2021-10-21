package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Address
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
        private val PATTERN_NUMBER = Pattern.compile("^[0-9]*$")
        private val PATTERN_NEGATIVE_NUMBER = Pattern.compile("^-[0-9]*$")
    }


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

    //todo way too many "on X input changed" functions.
    // can i make it better?
    fun onNameInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(name = newInput)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onSpecialityInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newDoc = doctor.copy(specialty = newInput)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onStreetInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newAddress =
                doctor.address?.copy(street = newInput) ?: Address(newInput, "", "")
            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onHouseNumberInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newAddress =
                doctor.address?.copy(houseNumber = newInput) ?: Address("", newInput, "")
            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onCityInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newAddress =
                doctor.address?.copy(city = newInput) ?: Address("", "", newInput)
            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onPostcodeInputChanged(newInput: String)
    {
        //only numbers are allowed for postcode
        if (!PATTERN_NUMBER.matcher(newInput).matches())
        {
            return
        }

        uiState.value?.apply {
            val postcode = if (newInput.isNotBlank()) newInput.toInt() else null
            val newAddress =
                doctor.address?.copy(postCode = postcode)
                    ?: Address(
                        "",
                        "",
                        "",
                        postCode = postcode
                    )

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onCountryInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newAddress =
                doctor.address?.copy(country = newInput) ?: Address(
                    "",
                    "",
                    "",
                    country = newInput
                )

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onApartmentInputChanged(newInput: String)
    {
        uiState.value?.apply {
            val newAddress =
                doctor.address?.copy(apartmentNumber = newInput) ?: Address(
                    "",
                    "",
                    "",
                    apartmentNumber = newInput
                )

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    fun onFloorInputChanged(newInput: String)
    {
        val numberMatcher = PATTERN_NUMBER.matcher(newInput)
        val negativeNumberMatcher = PATTERN_NEGATIVE_NUMBER.matcher(newInput)

        //only numbers and negative numbers allowed for floor
        if (!numberMatcher.matches() && !negativeNumberMatcher.matches())
        {
            return
        }

        uiState.value?.apply {
            val floor = if (newInput.isNotBlank()) newInput else null
            val newAddress =
                doctor.address?.copy(floor = floor) ?: Address(
                    "",
                    "",
                    "",
                    floor = floor
                )

            val newDoc = doctor.copy(address = newAddress)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    //todo there are too many fields (long form), there has to be a better way than this!!!
    data class NewEditDoctorUiState(
        //todo just for initialization. should i use something else?
        val doctor: Doctor = DoctorFactory.getDummyDoctor(),
        val isLoading: Boolean = false,
        val generalError: String = "",
        val streetError: String = "",
        val houseNumberError: String = "",
        val cityError: String = ""
    )
    {
        change the way errors are handled here... first click "done" button, then check input.
        copy from login screen.
        address is not mandatory, but if exists it MUST have street, house number, and city
        //todo this is immediately set, which means the moment the user opens
        // newEditDoctorScreen, he sees an error - not so nice UX
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