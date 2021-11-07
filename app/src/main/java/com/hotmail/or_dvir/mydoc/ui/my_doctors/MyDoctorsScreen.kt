package com.hotmail.or_dvir.mydoc.ui.my_doctors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.DoctorDetailsScreen
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel.MyDoctorsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme
import com.hotmail.or_dvir.mydoc.ui.theme.Typography
import org.koin.androidx.compose.getViewModel

typealias OnDoctorClicked = (Doctor) -> Unit

@Composable
fun MyDoctorsScreen()
{
    val viewModel = getViewModel<MyDoctorsViewModel>()
    val uiState by viewModel.uiState.observeAsState(MyDoctorsUiState())

    //todo look into landscape mode
    MyDocTheme {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.title_myDoctors)) }
                )
            },
            floatingActionButton = {
                //only show FAB if there is no error
                if (uiState.error.isBlank())
                {
                    FloatingActionButton(
                        onClick = { viewModel.navigateToAppDestination(NewEditDoctorScreen(null)) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_person_filled),
                            contentDescription = stringResource(id = R.string.contentDescription_addDoctor)
                        )
                    }
                }
            },
            content = {
                ScreenContent(uiState) {
                    //on doctor click
                    viewModel.navigateToAppDestination(DoctorDetailsScreen(it.id))
                }
            }
        )
    }
}

@Composable
fun ScreenContent(uiState: MyDoctorsUiState, onDoctorClicked: OnDoctorClicked)
{
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.apply {
            when
            {
                error.isNotBlank() -> EmptyViewWithText(error)
                doctors.isEmpty() ->
                {
                    EmptyViewWithText(stringResource(id = R.string.emptyView_myDoctors))
                }
                doctors.isNotEmpty() -> DoctorsList(doctors, onDoctorClicked)
            }

            //this should be the LAST composable so it shows above everything else
            if (isLoading)
            {
                LoadingIndicatorFullScreen()
            }
        }
    }
}

@Composable
fun DoctorsList(doctors: List<Doctor>, onDoctorClicked: OnDoctorClicked)
{
    //todo
    // change this to grid when stable (currently experimental!)

    //todo add padding to bottom at end of list so FAB doesn't hide navigate button
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(items = doctors) { index, doc ->
            DoctorRow(
                doc = doc,
                onClick = onDoctorClicked
            )

            //todo can i move this to the theme?
            val dividerColor =
                if (MaterialTheme.colors.isLight)
                {
                    Color.Black
                } else
                {
                    Color.White
                }

            Divider(color = dividerColor)
        }
    }
}

@Composable
fun DoctorRow(doc: Doctor, onClick: OnDoctorClicked)
{
    //todo make this nicer
    // add image?

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(doc) }
            .padding(8.dp)
    ) {
        //doctor details
        Column {
            doc.apply {
                name.apply {
                    Text(
                        style = Typography.body1,
                        text = this
                    )
                }

                speciality?.apply {
                    Text(
                        style = Typography.body2,
                        text = this
                    )
                }

                address?.addressLine?.let {
                    Text(

                        text = it
                    )
                }
            }
        }

        //todo should i add navigation icon here? is it actually useful?
        // when the app expands (phone, email...) i wont have all the icons here AND in details screen.
        //navigation icon
//        doc.address?.let {
//            val context = LocalContext.current
//            IconButton(
//                onClick = { openMaps(context, it.getBasicAddress()) }
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_navigate),
//                    contentDescription = stringResource(id = R.string.contentDescription_navigate)
//                )
//            }
//        }
    }
}

@Composable
fun EmptyViewWithText(text: String)
{
    //todo make this look nicer.

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.ic_group_filled),
                contentDescription = ""
            )

            Text(
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                text = text
            )
        }
    }
}