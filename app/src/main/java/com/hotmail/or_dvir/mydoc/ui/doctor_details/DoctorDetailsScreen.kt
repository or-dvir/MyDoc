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
import com.hotmail.or_dvir.mydoc.models.ContactDetails
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.SimpleAddress
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.doctor_details.DoctorDetailsViewModel.DoctorDetailsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.Header
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.openDialer
import com.hotmail.or_dvir.mydoc.ui.shared.openMaps
import com.hotmail.or_dvir.mydoc.ui.shared.openUrl
import com.hotmail.or_dvir.mydoc.ui.shared.sendEmail
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme
import com.hotmail.or_dvir.mydoc.ui.theme.Typography
import com.hotmail.or_dvir.mydoc.ui.theme.actionBarIcons
import org.koin.androidx.compose.getViewModel

/**
 * First - icon resource
 *
 * Second - content description resource
 *
 * Third - click listener
 */
typealias CardAction = Triple<Int, Int, () -> Unit>

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

            //space from the title
            CardSpacer()

            doc.address?.apply { AddressCard(this) }
            CardSpacer()
            doc.contactDetails?.apply { ContactDetailsCard(this) }
        }
    }
}

@Composable
fun CardSpacer()
{
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun CardContentSpacer()
{
    Spacer(modifier = Modifier.height(3.dp))
}


@Composable
fun DoctorDetailsCard(
    title: String,
    actions: List<CardAction>,
    content: @Composable () -> Unit
)
{
    Card(
        elevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            Header(text = title)
            content()

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                actions.forEach {
                    IconButton(onClick = it.third) {
                        Icon(
                            painterResource(id = it.first),
                            contentDescription = stringResource(id = it.second)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactDetailsCard(contactDetails: ContactDetails)
{
    val context = LocalContext.current
    val actions = mutableListOf<CardAction>()

    contactDetails.phoneNumber?.let {
        actions.add(
            CardAction(
                R.drawable.ic_phone,
                R.string.contentDescription_makeCall
            ) { context.openDialer(it) }
        )
    }

    contactDetails.email?.let {
        actions.add(
            CardAction(
                R.drawable.ic_email,
                R.string.contentDescription_sendEmail
            ) { context.sendEmail(it) }
        )
    }

    contactDetails.website?.let {
        actions.add(
            CardAction(
                R.drawable.ic_internet,
                R.string.contentDescription_openWebsite
            ) { context.openUrl(it) }
        )
    }

    DoctorDetailsCard(
        title = stringResource(id = R.string.contact),
        actions = actions
    ) {
        Column {
            contactDetails.phoneNumber?.let {
                Text(text = it)
            }

            CardContentSpacer()
            contactDetails.email?.let {
                Text(text = it)
            }

            CardContentSpacer()
            contactDetails.website?.let {
                Text(text = it)
            }
        }
    }
}

@Composable
fun AddressCard(address: SimpleAddress)
{
    //todo
    // the icons have their own padding, so if the last row has an icon, there will be extra padding.
    // same for all other card.
    // possible solution is to use Icon instead of IconButton

    val context = LocalContext.current
    val actions = mutableListOf<CardAction>()

    address.addressLine?.let {
        actions.add(
            CardAction(
                R.drawable.ic_navigate,
                R.string.contentDescription_navigate
            ) { context.openMaps(it) }
        )
    }

    DoctorDetailsCard(
        title = stringResource(id = R.string.address),
        actions = actions
    ) {
        Column {
            address.addressLine?.let { Text(text = it) }
            CardContentSpacer()
            address.note?.let { Text(text = it) }
        }
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