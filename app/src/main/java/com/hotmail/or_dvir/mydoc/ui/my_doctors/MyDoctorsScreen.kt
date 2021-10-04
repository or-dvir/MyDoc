package com.hotmail.or_dvir.mydoc.ui.my_doctors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsViewModel.MyDoctorsUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination.NewDoctorScreen
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme

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
                    onClick = { viewModel.navigate(NewDoctorScreen) }
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

        if (uiState.doctors.isEmpty())
        {
            EmptyView()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //todo


        }

        //this should be the LAST composable so it shows above everything else
        if (uiState.isLoading)
        {
            LoadingIndicatorFullScreen()
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

//@Composable
//fun RegisterScreen(viewModel: MyDoctorsViewModel)
//{
//    TeleviZiaComposeTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .border(2.dp, Color.Green)
//        ) {
//            val uiState by viewModel.uiState.observeAsState(RegisterUiState())
//
//            val maxWidthModifier = Modifier.fillMaxWidth()
//            val spacerModifier = Modifier.height(5.dp)
//
//            val focusManager = LocalFocusManager.current
//
//            val clearFocus = { focusManager.clearFocus() }
//            val clearFocusAndRegister = {
//                clearFocus()
//                viewModel.onRegisterClicked()
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//                    .padding(2.dp)
//                    .border(2.dp, Color.Red)
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//
//                //email field
//                OutlinedTextFieldWithError(
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(
//                        onAny = { focusManager.moveFocus(FocusDirection.Down) }
//                    ),
//                    text = uiState.emailText,
//                    error = uiState.emailError,
//                    hint = R.string.hint_email,
//                    modifier = maxWidthModifier,
//                    onTextChanged = { viewModel.onEmailInputChanged(it) }
//                )
//
//                Spacer(modifier = spacerModifier)
//
//                PasswordTextField(
//                    imeAction = ImeAction.Next,
//                    keyboardActions = KeyboardActions(
//                        onAny = { focusManager.moveFocus(FocusDirection.Down) }
//                    ),
//                    text = uiState.passwordText,
//                    error = uiState.passwordError,
//                    modifier = maxWidthModifier,
//                    onTextChanged = { viewModel.onPasswordInputChanged(it) }
//                )
//
//                Spacer(modifier = spacerModifier)
//
//                //password confirmation
//                PasswordTextField(
//                    imeAction = ImeAction.Done,
//                    keyboardActions = KeyboardActions(
//                        onAny = { clearFocus() }
//                    ),
//                    text = uiState.passwordConfirmationText,
//                    error = uiState.passwordConfirmationError,
//                    hint = R.string.hint_passwordConfirmation,
//                    modifier = maxWidthModifier,
//                    onTextChanged = { viewModel.onPasswordConfirmationInputChanged(it) }
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    modifier = maxWidthModifier,
//                    onClick = { clearFocusAndRegister() }
//                ) {
//                    Text(stringResource(id = R.string.register))
//                }
//            }
//
//            //this should be the LAST composable so it shows above everything else
//            if (uiState.isLoading)
//            {
//                LoadingIndicatorFullScreen()
//            }
//        }
//    }
//}