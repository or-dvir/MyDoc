package com.hotmail.or_dvir.mydoc.ui.doctor_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hotmail.or_dvir.mydoc.models.Doctor
import org.koin.core.component.KoinComponent
import java.util.UUID

class DoctorDetailsViewModel(app: Application) : AndroidViewModel(app), KoinComponent
{
    private val _uiState = MutableLiveData(DoctorDetailsUiState())
    val uiState: LiveData<DoctorDetailsUiState> = _uiState

//    private val navDestination = Channel<NavigationDestination>()
//    val navDestinationFlow = navDestination.receiveAsFlow()

    //todo should probably move this to a base view model
    // dont forget to remove from all other view models!!!
//    fun navigate(event: NavigationDestination)
//    {
//        viewModelScope.launch(Dispatchers.Main) {
//            navDestination.send(event)
//        }
//    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class DoctorDetailsUiState(
        //todo just for initialization. should i keep this or find another way?
        val doctor: Doctor = Doctor(UUID.randomUUID(), "", ""),
        val error: String = "",
        val isLoading: Boolean = false
    )
}