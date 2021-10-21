package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
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
fun FormSpacer() = Spacer(modifier = Modifier.height(5.dp))

@Composable
fun ScreenContent(viewModel: NewEditDoctorViewModel, uiState: NewEditDoctorUiState)
{
    //todo handle uiState errors

    i stopped here. go over all todo notes on this screen and fix them before continuing!!!

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //todo move content of screen above keyboard
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val maxWidthModifier = Modifier.fillMaxWidth()
            val focusManager = LocalFocusManager.current
            val clearFocus = { focusManager.clearFocus() }

            uiState.doctor.let { doc ->
                doc.name.apply {
                    //todo
                    // this is copied too many times. create a function with defaults
                    // focusDirection.Next not working
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = this,
                        error = stringResource(uiState.doctorNameError),
                        hint = R.string.hint_name,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onNameInputChanged(it) }
                    )
                }

                FormSpacer()

                doc.specialty.apply {
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = this.orEmpty(),
                        error = "", //all inputs are valid
                        hint = R.string.hint_speciality,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onSpecialityInputChanged(it) }
                    )
                }

                FormSpacer()

                doc.address.let { address ->
                    //street
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.street.orEmpty(),
                        error = uiState.streetError,
                        hint = R.string.hint_street,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onStreetInputChanged(it) }
                    )

                    FormSpacer()

                    //house number
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.houseNumber.orEmpty(),
                        error = uiState.houseNumberError,
                        hint = R.string.hint_houseNumber,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onHouseNumberInputChanged(it) }
                    )

                    FormSpacer()

                    //city
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.city.orEmpty(),
                        error = uiState.cityError,
                        hint = R.string.hint_city,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onCityInputChanged(it) }
                    )

                    FormSpacer()

                    //todo verity what inputs KeyboardType.Number permits
                    // ONLY allow digits! (no special characters!!!)
                    //postcode
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.postCode?.toString() ?: "",
                        error = "", //no errors for this field
                        hint = R.string.hint_postcode,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onPostcodeInputChanged(it.toInt()) }
                    )

                    FormSpacer()

                    //country
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.country.orEmpty(),
                        error = "", //no errors for this field
                        hint = R.string.hint_country,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onCountryInputChanged(it) }
                    )

                    FormSpacer()

                    //apartment number
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.apartmentNumber.orEmpty(),
                        error = "", //no errors for this field
                        hint = R.string.hint_apartment,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onApartmentInputChanged(it) }
                    )

                    FormSpacer()

                    //floor
                    //todo verity what inputs KeyboardType.Number permits
                    // ONLY allow digits and dash (basement floor)!
                    OutlinedTextFieldWithError(
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onAny = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        text = address?.floor?.toString() ?: "",
                        error = "", //no errors for this field
                        hint = R.string.hint_floor,
                        modifier = maxWidthModifier,
                        onTextChanged = { viewModel.onFloorInputChanged(it.toInt()) }
                    )
                }
            }

            //todo on last item in form, set keyboard IME action to "done" and clear focus on click
        }

        //this should be the LAST composable so it shows above everything else
        if (uiState.isLoading)
        {
            LoadingIndicatorFullScreen()
        }
    }
}