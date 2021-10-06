package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.app.Application
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
    private var isEditing = false

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
        //creating a new doctor
        if (doctorId == null)
        {
            isEditing = false
            updateUiState(
                uiState.value!!.copy(
                    doctor = Doctor(UUID.randomUUID(), "", "")
                )
            )

            return
        }

        //doctorId is not null -> edit an existing doctor
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
        //todo can this be combined with createNewDoctor()?

        viewModelScope.launch(mainDispatcher) {
            uiState.value?.apply {
                updateUiState(
                    copy(isLoading = true)
                )

                doctor?.let {
                    val success =
                        if (isEditing)
                        {
                            doctorsRepo.update(it)
                        } else
                        {
                            doctorsRepo.add(it)
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
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class NewEditDoctorUiState(
        val doctor: Doctor = Doctor(UUID.randomUUID(), "", ""),
        val error: String = "",
        val isLoading: Boolean = false
    )
}