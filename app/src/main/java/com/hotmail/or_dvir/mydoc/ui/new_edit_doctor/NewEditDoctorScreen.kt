package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel.NewEditDoctorUiState
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.OutlinedTextFieldWithError
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme

@Composable
fun NewEditDoctorScreen(viewModel: NewEditDoctorViewModel)
{
    //todo
    // look into landscape mode

    MyDocTheme {
        val uiState by viewModel.uiState.observeAsState(NewEditDoctorUiState())
        val scaffoldState = rememberScaffoldState()
        val isInputValid = uiState.isInputValid()

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(viewModel, uiState) },
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
                        IconButton(
                            enabled = isInputValid,
                            onClick = { viewModel.createOrUpdateDoctor() }
                        ) {
                            val alpha = if (isInputValid) 1f else 0.5f

                            Icon(
                                modifier = Modifier.alpha(alpha),
                                tint = Color.White,
                                painter = painterResource(id = R.drawable.ic_check),
                                contentDescription = stringResource(R.string.contentDescription_save)
                            )
                        }
                    }
                )
            }
        )
    }
}

@Composable
fun ScreenContent(viewModel: NewEditDoctorViewModel, uiState: NewEditDoctorUiState)
{
    //todo handle uiState errors

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val maxWidthModifier = Modifier.fillMaxWidth()
            val focusManager = LocalFocusManager.current
            val clearFocus = { focusManager.clearFocus() }

            //doctor name
            OutlinedTextFieldWithError(
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onAny = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                text = uiState.doctor.name,
                error = stringResource(uiState.doctorNameError),
                hint = R.string.hint_name,
                modifier = maxWidthModifier,
                onTextChanged = { viewModel.onDoctorNameInputChanged(it) }
            )

            Spacer(modifier = Modifier.height(5.dp))

            //doctor speciality.
            //note: even though there are no errors on this field, using OutlinedTextFieldWithError
            //for consistency
            OutlinedTextFieldWithError(
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onAny = { clearFocus() }
                ),
                text = uiState.doctor.specialty.orEmpty(),
                error = "", //all inputs are valid for speciality
                hint = R.string.hint_speciality,
                modifier = maxWidthModifier,
                onTextChanged = { viewModel.onDoctorSpecialityInputChanged(it) }
            )
        }

        //this should be the LAST composable so it shows above everything else
        if (uiState.isLoading)
        {
            LoadingIndicatorFullScreen()
        }
    }
}