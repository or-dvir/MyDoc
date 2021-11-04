package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
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
            val newDoc = doctor.copy(specialty = newInput)

            updateUiState(
                copy(doctor = newDoc)
            )
        }
    }

//    fun onStreetInputChanged(newInput: String)
//    {
//        uiState.value?.apply {
//            val newAddress =
//                doctor.address?.copy(street = newInput) ?: Address(newInput, "", "")
//            val newDoc = doctor.copy(address = newAddress)
//
//            updateUiState(
//                copy(
//                    streetError = "", //reset any errors
//                    doctor = newDoc
//                )
//            )
//        }
//    }

//    fun onHouseNumberInputChanged(newInput: String)
//    {
//        uiState.value?.apply {
//            val newAddress =
//                doctor.address?.copy(houseNumber = newInput) ?: Address("", newInput, "")
//            val newDoc = doctor.copy(address = newAddress)
//
//            updateUiState(
//                copy(
//                    houseNumberError = "", //reset any errors
//                    doctor = newDoc
//                )
//            )
//        }
//    }

//    fun onCityInputChanged(newInput: String)
//    {
//        uiState.value?.apply {
//            val newAddress =
//                doctor.address?.copy(city = newInput) ?: Address("", "", newInput)
//            val newDoc = doctor.copy(address = newAddress)
//
//            updateUiState(
//                copy(
//                    cityError = "", //reset any errors
//                    doctor = newDoc
//                )
//            )
//        }
//    }

//    fun onPostcodeInputChanged(newInput: String)
//    {
//        //only numbers are allowed for postcode
//        if (!PATTERN_NUMBER.matcher(newInput).matches())
//        {
//            return
//        }
//
//        uiState.value?.apply {
//            val postcode = if (newInput.isNotBlank()) newInput.toInt() else null
//            val newAddress =
//                doctor.address?.copy(postCode = postcode)
//                    ?: Address(
//                        "",
//                        "",
//                        "",
//                        postCode = postcode
//                    )
//
//            val newDoc = doctor.copy(address = newAddress)
//
//            updateUiState(
//                copy(doctor = newDoc)
//            )
//        }
//    }

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

//                //todo make each of these in "apply" block
//                // to make it clear which field we are validating
//                potential bug .
//                if user erases all address fields, is it set back to null?
//                if not, this will trigger even though it shouldnt
//                address?.apply {
//                    val addressError = getString(R.string.error_emptyAddressField)
//
//                    if (street.isBlank())
//                    {
//                        streetError = addressError
//                        isValid = false
//                    }
//
//                    if (houseNumber.isBlank())
//                    {
//                        houseNumberError = addressError
//                        isValid = false
//                    }
//
//                    if (city.isBlank())
//                    {
//                        cityError = addressError
//                        isValid = false
//                    }
//                }

            updateUiState(
                state.copy(errors = errors)
            )
        }

        return isValid
    }

//    fun onCountryInputChanged(newInput: String)
//    {
//        uiState.value?.apply {
//            val newAddress =
//                doctor.address?.copy(country = newInput) ?: Address(
//                    "",
//                    "",
//                    "",
//                    country = newInput
//                )
//
//
//
//
//            doctor.address?.copy(country = newInput) ?: run {
//                if(newInput.isBlank()) {
//                    set address to null
//
//                    this will not work. assume ONLY city is set to "g", now the user
//                    erases it. this block will not be invoked because address is not null.
//                            maybe similar logic needs to be invoked before copying???
//                }
//
//                Address(
//                    "",
//                    "",
//                    "",
//                    country = newInput
//                )
//            }
//
//
//
//
//                val newDoc = doctor.copy(address = newAddress)
//
//                updateUiState(
//                    copy(doctor = newDoc)
//                )
//            }
//        }
//    }

//    fun onApartmentInputChanged(newInput: String)
//    {
//        uiState.value?.apply {
//            val newAddress =
//                doctor.address?.copy(apartmentNumber = newInput) ?: Address(
//                    "",
//                    "",
//                    "",
//                    apartmentNumber = newInput
//                )
//
//            val newDoc = doctor.copy(address = newAddress)
//
//            updateUiState(
//                copy(doctor = newDoc)
//            )
//        }
//    }

//    fun onFloorInputChanged(newInput: String)
//    {
//        val numberMatcher = PATTERN_NUMBER.matcher(newInput)
//        val negativeNumberMatcher = PATTERN_NEGATIVE_NUMBER.matcher(newInput)
//
//        //only numbers and negative numbers allowed for floor
//        if (!numberMatcher.matches() && !negativeNumberMatcher.matches())
//        {
//            return
//        }
//
//        uiState.value?.apply {
//            val floor = if (newInput.isNotBlank()) newInput.toInt() else null
//            val newAddress =
//                doctor.address?.copy(floor = floor) ?: Address(
//                    "",
//                    "",
//                    "",
//                    floor = floor
//                )
//
//            val newDoc = doctor.copy(address = newAddress)
//
//            updateUiState(
//                copy(doctor = newDoc)
//            )
//        }
//    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    //todo there are too many fields (long form), there has to be a better way than this!!!
    data class NewEditDoctorUiState(
        //todo just for initialization. should i use something else?
        val doctor: Doctor = DoctorFactory.createEmptyDoctor(),
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