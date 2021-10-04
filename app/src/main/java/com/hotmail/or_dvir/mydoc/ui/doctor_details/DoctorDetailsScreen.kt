package com.hotmail.or_dvir.mydoc.ui.doctor_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme

@Composable
fun DoctorsDetailsScreen(viewModel: DoctorDetailsViewModel)
{
    //todo
    // look into landscape mode
    // add option to delete doctor. here or from list of doctors?
    //      if here, it takes an extra step so its safer
    // add back button on action bar

    MyDocTheme {
        val uiState by viewModel.uiState.observeAsState(DoctorDetailsUiState())
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(uiState.doctor.name) }
                )
            },
            content = { ScreenContent(viewModel, uiState) },
        )
    }
}

@Composable
fun ScreenContent(viewModel: DoctorDetailsViewModel, uiState: DoctorDetailsUiState)
{
    //todo handle errors

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        uiState.apply {

            //todo
            // add some content
            // remove this text
            Text("doctor details")

            //this should be the LAST composable so it shows above everything else
            if (isLoading)
            {
                LoadingIndicatorFullScreen()
            }
        }
    }
}