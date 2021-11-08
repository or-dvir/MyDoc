package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import org.koin.androidx.compose.getViewModel

@Composable
fun NewEditDoctorScreen()
{
    //todo
    // look into landscape mode

    MyDocTheme {
        val viewModel = getViewModel<NewEditDoctorViewModel>()
        val uiState by viewModel.uiState.observeAsState(NewEditDoctorUiState())
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            content = { ScreenContent(uiState) },
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
                            //todo fix me! first check input!!
                            onClick = { viewModel.createOrUpdateDoctor() }
                        ) {
                            Icon(
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
    modifier: Modifier = Modifier,
    error: String = "",
    isLast: Boolean = false,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeClicked: (() -> Unit)? = null,
    onTextChanged: (String) -> Unit
)
{
    val focusManager = LocalFocusManager.current

    OutlinedTextFieldWithError(
        singleLine = singleLine,
        text = text,
        error = error,
        hint = hint,
        modifier = modifier.fillMaxWidth(),
        onTextChanged = onTextChanged,
        keyboardOptions = KeyboardOptions(
            imeAction = if (isLast) ImeAction.Done else imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onAny = {
                when
                {
                    isLast -> focusManager.clearFocus()
                    //todo when changing screen format from columns to something else,
                    // remember to change this
                    onImeClicked == null -> focusManager.moveFocus(FocusDirection.Down)
                    else -> onImeClicked()
                }
            }
        ),
    )

    if (!isLast)
    {
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun ScreenContent(uiState: NewEditDoctorUiState)
{
    val viewModel = getViewModel<NewEditDoctorViewModel>()

    //todo handle uiState errors

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            uiState.doctor.apply {
                name.apply {
                    FormTextField(
                        text = this,
                        error = uiState.errors.nameError,
                        hint = R.string.hint_name,
                        onTextChanged = { viewModel.onNameInputChanged(it) }
                    )
                }

                speciality.apply {
                    FormTextField(
                        text = this.orEmpty(),
                        hint = R.string.hint_speciality,
                        onTextChanged = { viewModel.onSpecialityInputChanged(it) }
                    )
                }

                address?.addressLine.apply {
                    FormTextField(
                        singleLine = false,
                        text = this.orEmpty(),
                        hint = R.string.hint_addressLine,
                        onTextChanged = { viewModel.onAddressLineInputChanged(it) }
                    )
                }

                address?.note.apply {
                    FormTextField(
                        imeAction = ImeAction.Default,
                        modifier = Modifier.defaultMinSize(minHeight = 100.dp),
                        singleLine = false,
                        text = this.orEmpty(),
                        hint = R.string.hint_addressNote,
                        onTextChanged = { viewModel.onAddressNoteInputChanged(it) }
                    )
                }

                contactDetails?.phoneNumber.apply {
                    FormTextField(
                        keyboardType = KeyboardType.Phone,
                        error = uiState.errors.phoneNumberError,
                        text = this.orEmpty(),
                        hint = R.string.hint_phoneNumber,
                        onTextChanged = { viewModel.onPhoneNumberInputChanged(it) }
                    )
                }

                contactDetails?.email.apply {
                    FormTextField(
                        keyboardType = KeyboardType.Email,
                        error = uiState.errors.emailError,
                        text = this.orEmpty(),
                        hint = R.string.hint_email,
                        onTextChanged = { viewModel.onEmailInputChanged(it) }
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