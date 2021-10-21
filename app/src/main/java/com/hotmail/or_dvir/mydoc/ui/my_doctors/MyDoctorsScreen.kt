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
import androidx.compose.material.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel.MyDoctorsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination.DoctorDetailsScreen
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.shared.openMaps
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme
import com.hotmail.or_dvir.mydoc.ui.theme.Typography

@Composable
fun MyDoctorsScreen(viewModel: MyDoctorsViewModel)
{
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
                FloatingActionButton(
                    onClick = { viewModel.navigate(NewEditDoctorScreen(null)) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_person_filled),
                        contentDescription = stringResource(id = R.string.contentDescription_addDoctor)
                    )
                }
            },
            content = { ScreenContent(viewModel) },
        )
    }
}

@Composable
fun ScreenContent(viewModel: MyDoctorsViewModel)
{
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val uiState by viewModel.uiState.observeAsState(MyDoctorsUiState())

        uiState.apply {
            if (doctors.isEmpty())
            {
                EmptyView()
            } else
            {
                DoctorsList(doctors) {
                    viewModel.navigate(DoctorDetailsScreen(it.id))
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

@Composable
fun DoctorsList(
    doctors: List<Doctor>,
    onDoctorClicked: (Doctor) -> Unit
)
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

            if (index < doctors.lastIndex)
            {
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
}

@Composable
fun DoctorRow(
    doc: Doctor,
    onClick: (Doctor) -> Unit
)
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
                //name
                Text(
                    style = Typography.h6,
                    text = doc.name
                )

                specialty?.let { Text(text = it) }
                address?.let { Text(text = it.format()) }
            }
        }

        //navigation icon
        doc.address?.let {
            val context = LocalContext.current
            IconButton(
                onClick = { openMaps(context, it.format()) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = stringResource(id = R.string.contentDescription_navigate)
                )
            }
        }
    }
}

@Composable
fun EmptyView()
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
                text = stringResource(id = R.string.emptyView_myDoctors)
            )
        }
    }
}