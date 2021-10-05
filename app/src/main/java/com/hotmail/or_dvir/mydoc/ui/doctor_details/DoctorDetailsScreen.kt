package com.hotmail.or_dvir.mydoc.ui.doctor_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme

@Composable
fun DoctorsDetailsScreen(viewModel: DoctorDetailsViewModel, onBackButtonClicked: () -> Unit)
{
    //todo
    // look into landscape mode
    // add option to delete doctor. here or from list of doctors?
    //      if here, it takes an extra step so its safer
    // add option to edit doctor

    MyDocTheme {
        val uiState by viewModel.uiState.observeAsState(DoctorDetailsUiState())
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(viewModel, uiState) },
            topBar = {
                TopAppBar(
                    title = { Text(uiState.doctor.name) },
                    navigationIcon = {
                        IconButton(onClick = { onBackButtonClicked() }) {
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                stringResource(id = R.string.contentDescription_back)
                            )
                        }
                    },
                    actions = {
                        TopBarActions(
                            onDelete = { viewModel.deleteDoctor() }
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
)
{
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Text(
                text = stringResource(id = R.string.deleteDoctorConfirmation),
                style = MaterialTheme.typography.subtitle1,
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }) {
                Text(stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }) {
                Text(stringResource(id = R.string.no))
            }
        }
    )
}

@Composable
fun TopBarActions(onDelete: () -> Unit)
{
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    if (showDeleteConfirmation)
    {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteConfirmation = false },
            onConfirm = { onDelete() }
        )
    }

    IconButton(onClick = {
        showDeleteConfirmation = true
    }) {
        Icon(
            tint = Color.White,
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = stringResource(id = R.string.contentDescription_delete)
        )
    }

    IconButton(onClick = { /* todo */ }) {
        Icon(
            tint = Color.White,
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(id = R.string.contentDescription_edit)
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

            //todo make this nicer
            // add photo?
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.doctorDetails_name_s, doctor.name))
                Spacer(modifier = Modifier.height(5.dp))
                Text(stringResource(R.string.doctorDetails_speciality_s, doctor.specialty))
            }

            //this should be the LAST composable so it shows above everything else
            if (isLoading)
            {
                LoadingIndicatorFullScreen()
            }
        }
    }
}