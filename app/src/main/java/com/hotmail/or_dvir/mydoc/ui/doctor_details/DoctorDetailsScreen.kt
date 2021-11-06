package com.hotmail.or_dvir.mydoc.ui.doctor_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme
import com.hotmail.or_dvir.mydoc.ui.theme.Typography
import com.hotmail.or_dvir.mydoc.ui.theme.actionBarIcons
import org.koin.androidx.compose.getViewModel

@Composable
fun DoctorsDetailsScreen()
{
    //todo
    // look into landscape mode

    MyDocTheme {
        val viewModel = getViewModel<DoctorDetailsViewModel>()
        val uiState by viewModel.uiState.observeAsState(DoctorDetailsUiState())
        val scaffoldState = rememberScaffoldState()
        val actionBarIconsColor = MaterialTheme.colors.actionBarIcons

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(uiState) },
            topBar = {
                if (uiState.error.isBlank())
                {
                    TopAppBar(
                        contentColor = actionBarIconsColor,
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent,
                        title = { /*no title*/ },
                        navigationIcon = {
                            IconButton(onClick = { viewModel.navigateBack() }) {
                                Icon(
                                    painterResource(id = R.drawable.ic_arrow_back),
                                    stringResource(id = R.string.contentDescription_back)
                                )
                            }
                        },
                        actions = { TopBarActions(uiState, actionBarIconsColor) }
                    )
                }
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
fun TopBarActions(uiState: DoctorDetailsUiState, iconsTint: Color)
{
    val context = LocalContext.current
    val viewModel = getViewModel<DoctorDetailsViewModel>()

    //navigate
//    uiState.doctor.address?.getBasicAddress()?.let {
//        IconButton(onClick = { openMaps(context, it) }) {
//            Icon(
//                tint = iconsTint,
//                painter = painterResource(id = R.drawable.ic_navigate),
//                contentDescription = stringResource(id = R.string.contentDescription_navigate)
//            )
//        }
//    }

    //edit
    IconButton(onClick = {
        viewModel.navigateToAppDestination(NewEditDoctorScreen(uiState.doctor.id))
    }) {
        Icon(
            tint = iconsTint,
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(id = R.string.contentDescription_edit)
        )
    }

    //overflow menu
    //todo align menu end to end of icon
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

//@Composable
//@Preview (name = "doctor details", showBackground = true)
//fun DoctorDetailsViewPreview()
//{
//    DoctorDetailsView(DoctorFactory.createDoctorForPreview())
//}

@Composable
fun DoctorDetailsView(doc: Doctor)
{
    //todo make this nicer
    // add photo?

    val padding = 8.dp
    Column(
        modifier = Modifier
            .padding(padding, 0.dp, padding, padding)
            .verticalScroll(rememberScrollState())
    ) {
        //name and speciality
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            doc.name.apply {
                Text(
                    style = Typography.h3,
                    text = this
                )
            }

            doc.speciality?.apply {
                Text(
                    style = Typography.h5,
                    text = this
                )
            }
        }

//        //todo should i just hide it or show something like "unknown"
//        // to encourage the user to add those details
//                doctor.specialty?.apply {
//                    DoctorDetailsSpacer()
//                    Text(stringResource(R.string.doctorDetails_speciality_s, this))
//                }
//
//                doctor.address?.apply {
//                    DoctorDetailsSpacer()
//                    Text(stringResource(R.string.doctorDetails_address_s, this.getBasicAddress()))
//
//                    getDetailedAddress(context)?.let {
//                        Text(
//                            modifier = Modifier.offset(8.dp),
//                            text = it
//                        )
//                    }
//                }
    }
}

@Composable
fun ErrorView(error: String)
{
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = error
        )
    }
}

@Composable
fun ScreenContent(uiState: DoctorDetailsUiState)
{
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.apply {
            if (error.isNotBlank())
            {
                ErrorView(error)
            } else
            {
                DoctorDetailsView(doctor)
            }

            //this should be the LAST composable so it shows above everything else
            if (isLoading)
            {
                LoadingIndicatorFullScreen()
            }
        }
    }
}