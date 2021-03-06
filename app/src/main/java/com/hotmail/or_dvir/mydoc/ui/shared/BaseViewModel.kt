package com.hotmail.or_dvir.mydoc.ui.shared

import android.app.Application
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<UiState>(val app: Application) : AndroidViewModel(app), KoinComponent
{
    internal val mainDispatcher = Dispatchers.Main

    private val _uiState = MutableLiveData(this.initUiState())
    val uiState: LiveData<UiState> = _uiState

    private val navDestination = Channel<NavigationDestination>()
    val navDestinationFlow = navDestination.receiveAsFlow()

    abstract fun initUiState(): UiState

    fun navigateBack() = navigateToAppDestination(NavigationDestination.PopStack)

    fun navigateToAppDestination(event: NavigationDestination)
    {
        viewModelScope.launch(mainDispatcher) {
            navDestination.send(event)
        }
    }

    internal fun updateUiState(@NonNull newState: UiState)
    {
        _uiState.value = newState!!
    }

    internal fun getString(@StringRes stringRes: Int) = app.getString(stringRes)
}