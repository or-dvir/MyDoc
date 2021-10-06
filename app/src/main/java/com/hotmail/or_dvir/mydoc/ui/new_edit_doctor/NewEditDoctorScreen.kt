package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel.NewEditDoctorUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme

@Composable
fun NewEditDoctorScreen(viewModel: NewEditDoctorViewModel)
{
    //todo
    // look into landscape mode

    MyDocTheme {
        val uiState by viewModel.uiState.observeAsState(NewEditDoctorUiState())
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(uiState) },
            topBar = {
                TopAppBar(
                    title = {
                        Text(viewModel.getTitle())
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.navigateBack() }) {
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                stringResource(id = R.string.contentDescription_back)
                            )
                        }
                    },
                    actions = {
                        TopBarActions(viewModel)
                    }
                )
            }
        )
    }
}

@Composable
fun TopBarActions(viewModel: NewEditDoctorViewModel)
{
    IconButton(onClick = { viewModel.createOrUpdateDoctor() }) {
        Icon(
            tint = Color.White,
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = stringResource(R.string.contentDescription_save)
        )
    }
}

@Composable
fun ScreenContent(uiState: NewEditDoctorUiState)
{
    //todo handle uiState errors

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.apply {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                //todo
                // doctor name in edit text
                //      cannot be empty
                // doctor speciality in edit text (optional)
                //      make optional in model!!!
                Text("new/edit doctor")
            }

            //this should be the LAST composable so it shows above everything else
            if (isLoading)
            {
                LoadingIndicatorFullScreen()
            }
        }
    }
}