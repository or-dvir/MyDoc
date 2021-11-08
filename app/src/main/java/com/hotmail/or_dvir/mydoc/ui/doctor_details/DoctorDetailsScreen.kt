package com.hotmail.or_dvir.mydoc.ui.doctor_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
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
import com.hotmail.or_dvir.mydoc.models.SimpleAddress
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.openMaps
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

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(uiState) },
            topBar = {
                if (uiState.error.isBlank())
                {
                    TopAppBar(
                        contentColor = colors.actionBarIcons,
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
                        actions = { TopBarActions(uiState) }
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
fun TopBarActions(uiState: DoctorDetailsUiState)
{
    val viewModel = getViewModel<DoctorDetailsViewModel>()

    //edit
    IconButton(onClick = {
        viewModel.navigateToAppDestination(NewEditDoctorScreen(uiState.doctor.id))
    }) {
        Icon(
            tint = colors.actionBarIcons,
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(id = R.string.contentDescription_edit)
        )
    }

    //overflow menu
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(
            tint = colors.actionBarIcons,
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

//@Composable
//@Preview (name = "doctor details", showBackground = true)
//fun DoctorDetailsViewPreview()
//{
//    DoctorDetailsView(DoctorFactory.createDoctorForPreview())
//}

@Composable
fun DoctorDetailsView(doc: Doctor)
{
    //todo for all optional details (e.g. speciality/address...) should i just hide them
    // or show something like "unknown" to encourage the user to add those details.
    // maybe show something like "this doctor is incomplete. click edit button to add details"

    val padding = 16.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = padding,
                end = padding,
                bottom = padding
            )
    ) {
        //todo not so nice and does not match the Card "theme"... make it better!
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

            Spacer(modifier = Modifier.height(16.dp))

            doc.address?.apply {
                AddressCard(this)
            }
        }
    }
}

@Composable
fun DetailsCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
{
    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier) {
            Text(
                text = title,
                style = Typography.h6,
                //todo when settled on colors, try primary variant
                color = colors.primary
            )

            content()
        }
    }
}

@Composable
fun AddressCard(address: SimpleAddress)
{
    //todo this will probably need to be "hoisted" so that all cards have
    // the same padding
    val paddingTop = 8.dp
    val paddingBottom = address.note?.let { paddingTop } ?: 0.dp

    DetailsCard(
        title = stringResource(id = R.string.address),
        modifier = Modifier.padding(
            top = paddingTop,
            bottom = paddingBottom,
            start = 10.dp,
            end = 10.dp,
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            //weight modifier required so long text doesn't push row out of screen
            Column(modifier = Modifier.weight(1f)) {
                address.addressLine?.let { Text(text = it) }
                address.note?.let { Text(text = it) }
            }

            //only show navigation icon if we have an address
            address.addressLine?.let {
                val context = LocalContext.current
                IconButton(onClick = { context.openMaps(it) }) {
                    Icon(
                        painterResource(id = R.drawable.ic_navigate),
                        contentDescription = stringResource(id = R.string.contentDescription_navigate)
                    )
                }
            }
        }
    }


//    Card(
//        elevation = 5.dp,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 10.dp, vertical = 8.dp)
//        ) {
//            //weight modifier required so long text doesn't push row out of screen
//            Column(modifier = Modifier.weight(1f)) {
//                CardTitle(title = stringResource(id = R.string.address))
//                address.addressLine?.let { Text(text = it) }
//                address.note?.let { Text(text = it) }
//            }
//
//            //only show navigation icon if we have an address
//            address.addressLine?.let {
//                val context = LocalContext.current
//                IconButton(onClick = { context.openMaps(it) }) {
//                    Icon(
//                        painterResource(id = R.drawable.ic_navigate),
//                        contentDescription = stringResource(id = R.string.contentDescription_navigate)
//                    )
//                }
//            }
//        }
//    }
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