package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.R
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

    override fun initUiState() = NewEditDoctorUiState()

    fun loadDoctor(doctorId: UUID?)
    {
        //creating a new doctor
        if (doctorId == null)
        {
            updateUiState(
                uiState.value!!.copy(
                    doctor = Doctor(UUID.randomUUID(), "", "")
                )
            )

            return
        }

        //doctorId is not null -> edit an existing doctor
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

    fun createNewDoctor()
    {
        //todo can this be combined with editDoctor()?
        // pretty sure firebase overrides if exists, and creates new if doesn't
        //todo do me
    }

    fun updateDoctor()
    {
        //todo can this be combined with createNewDoctor()?

        viewModelScope.launch(mainDispatcher) {
            uiState.value?.apply {
                updateUiState(
                    copy(isLoading = true)
                )

                doctor?.let {
                    val success = doctorsRepo.update(it)

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
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class NewEditDoctorUiState(
        val doctor: Doctor? = null,
        val error: String = "",
        val isLoading: Boolean = false
    )
    {
        @StringRes
        fun getTitle() =
            if (doctor == null)
            {
                R.string.title_newDoctor
            } else
            {
                R.string.title_editDoctor
            }
    }
}