package com.hotmail.or_dvir.mydoc.ui.doctor_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.shared.openMaps
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme

@Composable
fun DoctorsDetailsScreen(viewModel: DoctorDetailsViewModel)
{
    //todo
    // look into landscape mode

    MyDocTheme {
        val uiState by viewModel.uiState.observeAsState(DoctorDetailsUiState())
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(uiState) },
            topBar = {
                TopAppBar(
                    title = { Text(uiState.doctor.name) },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.navigateBack() }) {
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                stringResource(id = R.string.contentDescription_back)
                            )
                        }
                    },
                    actions = { TopBarActions(viewModel) }
                )
            }
        )
    }
}

@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit)
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
fun TopBarActions(viewModel: DoctorDetailsViewModel)
{
    val iconsTint = Color.White
    val context = LocalContext.current

    //navigate
    viewModel.getDoctorBasicAddress()?.let {
        IconButton(onClick = { openMaps(context, it) }) {
            Icon(
                tint = iconsTint,
                painter = painterResource(id = R.drawable.ic_navigate),
                contentDescription = stringResource(id = R.string.contentDescription_navigate)
            )
        }
    }

    //edit
    IconButton(onClick = {
        viewModel.navigate(NewEditDoctorScreen(viewModel.uiState.value?.doctor?.id))
    }) {
        Icon(
            tint = iconsTint,
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(id = R.string.contentDescription_edit)
        )
    }

    //overflow menu
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(
            tint = iconsTint,
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(id = R.string.contentDescription_more)
        )
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(onClick = { showDeleteConfirmation = true }) {
            Text(stringResource(id = R.string.delete))
        }
    }

    if (showDeleteConfirmation)
    {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteConfirmation = false },
            onConfirm = { viewModel.deleteDoctor() }
        )
    }
}

@Composable
fun DoctorDetailsSpacer()
{
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun ScreenContent(uiState: DoctorDetailsUiState)
{
    //todo handle uiState errors
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.apply {
            //todo make this nicer
            // add photo?
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                doctor.name.apply {
                    Text(stringResource(R.string.doctorDetails_name_s, this))
                }

                //todo should i just hide it or show something like "unknown"
                // to encourage the user to add those details
                doctor.specialty?.apply {
                    DoctorDetailsSpacer()
                    Text(stringResource(R.string.doctorDetails_speciality_s, this))
                }

                doctor.address?.apply {
                    DoctorDetailsSpacer()
                    Text(stringResource(R.string.doctorDetails_address_s, this.getBasicAddress()))

                    getAddressDetails(context)?.let {
                        Text(
                            modifier = Modifier.offset(8.dp),
                            text = it
                        )
                    }
                }
            }

            //this should be the LAST composable so it shows above everything else
            if (isLoading)
            {
                LoadingIndicatorFullScreen()
            }
        }
    }
}