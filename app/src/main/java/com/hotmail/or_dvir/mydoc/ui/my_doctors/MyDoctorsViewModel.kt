package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.util.UUID

class MyDoctorsViewModel(app: Application) : AndroidViewModel(app), KoinComponent
{
    private val _uiState = MutableLiveData(MyDoctorsUiState())
    val uiState: LiveData<MyDoctorsUiState> = _uiState

    private val navDestination = Channel<NavigationDestination>()
    val navDestinationFlow = navDestination.receiveAsFlow()

    fun navigate(event: NavigationDestination)
    {
        viewModelScope.launch(Dispatchers.Main) {
            navDestination.send(event)
        }
    }

    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    data class MyDoctorsUiState(
        //todo only for testing. uncomment (and remove below row) when ready
//        val doctors: List<Doctor> = listOf(),
        val doctors: List<Doctor> = List(
            50
        ) { index ->
            Doctor(UUID.randomUUID(), "Dr. $index", "special $index")
        },
        val isLoading: Boolean = false
    )
}