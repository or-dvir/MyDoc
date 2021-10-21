package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.annotation.StringRes
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
fun FormTextField(
    text: String,
    @StringRes hint: Int,
    error: String = "",
    putSpacerBelow: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeClicked: (() -> Unit)? = null,
    onTextChanged: (String) -> Unit
)
{
    val focusManager = LocalFocusManager.current

    OutlinedTextFieldWithError(
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            //todo FocusDirection.Next doesn't seem to be working
            onAny = {
                if (onImeClicked == null)
                {
                    focusManager.moveFocus(FocusDirection.Next)
                } else
                {
                    onImeClicked()
                }
            }
        ),
        text = text,
        error = error,
        hint = hint,
        modifier = Modifier.fillMaxWidth(),
        onTextChanged = onTextChanged
    )

    if (putSpacerBelow)
    {
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun ScreenContent(viewModel: NewEditDoctorViewModel, uiState: NewEditDoctorUiState)
{
    //todo handle uiState errors

    i stopped here.go over all todo notes on this screen and fix them before continuing!!!

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val focusManager = LocalFocusManager.current
            val clearFocus = { focusManager.clearFocus() }

            uiState.doctor.let { doc ->
                doc.name.apply {
                    FormTextField(
                        text = this,
                        error = stringResource(uiState.doctorNameError),
                        hint = R.string.hint_name,
                        onTextChanged = { viewModel.onNameInputChanged(it) }
                    )
                }

                doc.specialty.apply {
                    FormTextField(
                        text = this.orEmpty(),
                        hint = R.string.hint_speciality,
                        onTextChanged = { viewModel.onSpecialityInputChanged(it) }
                    )
                }

                doc.address.let { adrs ->
                    adrs?.street.orEmpty().apply {
                        FormTextField(
                            text = this,
                            error = uiState.streetError,
                            hint = R.string.hint_street,
                            onTextChanged = { viewModel.onStreetInputChanged(it) }
                        )
                    }

                    adrs?.houseNumber.apply {
                        FormTextField(
                            text = this.orEmpty(),
                            error = uiState.houseNumberError,
                            hint = R.string.hint_houseNumber,
                            onTextChanged = { viewModel.onHouseNumberInputChanged(it) }
                        )
                    }

                    adrs?.city.apply {
                        FormTextField(
                            text = this.orEmpty(),
                            error = uiState.cityError,
                            hint = R.string.hint_city,
                            onTextChanged = { viewModel.onCityInputChanged(it) }
                        )
                    }

                    //todo verity what inputs KeyboardType.Number permits
                    // ONLY allow digits! (no special characters!!!)
                    adrs?.postCode.apply {
                        FormTextField(
                            keyboardType = KeyboardType.Number,
                            text = this?.toString() ?: "",
                            hint = R.string.hint_postcode,
                            onTextChanged = { viewModel.onPostcodeInputChanged(it.toInt()) }
                        )
                    }

                    adrs?.country.apply {
                        FormTextField(
                            text = this.orEmpty(),
                            hint = R.string.hint_country,
                            onTextChanged = { viewModel.onCountryInputChanged(it) }
                        )
                    }

                    adrs?.apartmentNumber.apply {
                        FormTextField(
                            text = this.orEmpty(),
                            hint = R.string.hint_apartment,
                            onTextChanged = { viewModel.onApartmentInputChanged(it) }
                        )
                    }

                    //todo verity what inputs KeyboardType.Number permits
                    // ONLY allow digits and dash (basement floor)!
                    adrs?.floor.apply {
                        FormTextField(
                            keyboardType = KeyboardType.Number,
                            text = this?.toString() ?: "",
                            hint = R.string.hint_floor,
                            onTextChanged = { viewModel.onFloorInputChanged(it.toInt()) }
                        )
                    }
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