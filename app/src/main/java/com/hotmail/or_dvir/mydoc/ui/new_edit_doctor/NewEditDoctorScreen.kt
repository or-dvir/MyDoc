package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
import com.hotmail.or_dvir.mydoc.models.ContactDetails
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.OpeningTime
import com.hotmail.or_dvir.mydoc.models.SimpleAddress
import com.hotmail.or_dvir.mydoc.ui.new_edit_doctor.NewEditDoctorViewModel.NewEditDoctorUiState
import com.hotmail.or_dvir.mydoc.ui.shared.ButtonWithHeader
import com.hotmail.or_dvir.mydoc.ui.shared.ExposedDropDownMenu
import com.hotmail.or_dvir.mydoc.ui.shared.Header
import com.hotmail.or_dvir.mydoc.ui.shared.LoadingIndicatorFullScreen
import com.hotmail.or_dvir.mydoc.ui.shared.OutlinedTextFieldWithError
import com.hotmail.or_dvir.mydoc.ui.theme.MyDocTheme
import org.koin.androidx.compose.getViewModel
import java.sql.Timestamp

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
                                stringResource(R.string.contentDescription_back)
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
fun PersonalDetailsSection(doc: Doctor, nameError: String)
{
    val viewModel = getViewModel<NewEditDoctorViewModel>()

    Column {
        doc.apply {
            Header(text = stringResource(R.string.personalDetails))

            name.apply {
                FormTextField(
                    text = this,
                    error = nameError,
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
        }
    }
}

@Composable
fun AddressSection(address: SimpleAddress?)
{
    val viewModel = getViewModel<NewEditDoctorViewModel>()

    Column {
        Header(text = stringResource(R.string.address))

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
    }
}

@Composable
fun ContactDetailsSection(
    contactDetails: ContactDetails?,
    emailError: String,
    websiteError: String,
    phoneNumberError: String,
)
{
    val viewModel = getViewModel<NewEditDoctorViewModel>()

    Column {
        Header(text = stringResource(R.string.contact))

        contactDetails?.phoneNumber.apply {
            FormTextField(
                keyboardType = KeyboardType.Phone,
                error = phoneNumberError,
                text = this.orEmpty(),
                hint = R.string.hint_phoneNumber,
                onTextChanged = { viewModel.onPhoneNumberInputChanged(it) }
            )
        }

        contactDetails?.email.apply {
            FormTextField(
                keyboardType = KeyboardType.Email,
                error = emailError,
                text = this.orEmpty(),
                hint = R.string.hint_email,
                onTextChanged = { viewModel.onEmailInputChanged(it) }
            )
        }

        contactDetails?.website.apply {
            FormTextField(
                error = websiteError,
                text = this.orEmpty(),
                hint = R.string.hint_website,
                onTextChanged = { viewModel.onWebsiteInputChanged(it) }
            )
        }
    }
}

@Composable
fun OpeningTimeRow(openingTime: OpeningTime)
{
    //todo
    // add button at bottom to add opening time
    // display each opening time as a row:
    //      drop down for day of week
    //          if already exists, show selected
    //      drop down for open time
    //          if already exists, show selected
    //      drop down for close time
    //          if already exists, show selected
    //      button to delete the entry
    // update caller on:
    //      row added
    //      row removed
    //      day of week changed
    //      from hour changed
    //      to hour changed

    Row(
        verticalAlignment = Alignment.CenterVertically,
        //todo which spacing is best?
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        //todo
        // the size of the menu changes size according to which entry is selected!!!
        // replace this with ButtonWithHeader so it matches the other ones?
        //      the "drop down" is a DropDownMenu which i already implemented
        //      in other places in the app
        //day of week
        ExposedDropDownMenu(
            values = OpeningTime.getDaysOfWeekShort(),
            onChange = { index, value ->
                //todo
            },
            label = { Text(text = stringResource(id = R.string.hint_day)) },
        )

        //from hour
        ButtonWithHeader(
            header = stringResource(id = R.string.from),
            buttonText = openingTime.getFromHourShort()
        ) {
            //todo onclick -> do something
        }

        //to hour
        ButtonWithHeader(
            header = stringResource(id = R.string.to),
            buttonText = openingTime.getToHourShort()
        ) {
            //todo onclick -> do something
        }
    }
}

@Composable
fun OpeningTimesSection(openingTimes: List<OpeningTime>?)
{
    val viewModel = getViewModel<NewEditDoctorViewModel>()

    Column {
        Header(text = stringResource(R.string.openingTimes))

        val borderColor = MaterialTheme.colors.onSurface.copy(
            alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity
        )

        Column(
            modifier = Modifier.border(1.dp, borderColor, RoundedCornerShape(5.dp))
        ) {

            //todo
            // display each opening time as a row (OpeningTimeRow function)
            openingTimes?.forEachIndexed { index, it ->
                OpeningTimeRow(it)
                if(index != openingTimes.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                //todo scroll down to new row on button click
                IconButton(onClick = { viewModel.addNewOpeningTime() }) {
                    Icon(
                        tint = MaterialTheme.colors.primary,
                        painter = painterResource(id = R.drawable.ic_add_circle),
                        contentDescription = stringResource(id = R.string.contentDescription_addOpeningTime)
                    )
                }
            }
        }
    }

    i stopped in this function
        keep working on opening times:
        add "delete" button to each row,
        make "form" and "to" buttons functional (open time picker)
        update doctor in viewmodel on changes
}

@Composable
fun ScreenContent(uiState: NewEditDoctorUiState)
{
    //todo handle uiState errors
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val sectionSpacerModifier = Modifier.height(8.dp)

            uiState.doctor.apply {
                //todo are the headers (in composables.kt) too big for this screen?
                PersonalDetailsSection(this, uiState.errors.nameError)
                Spacer(sectionSpacerModifier)
                AddressSection(address)
                Spacer(sectionSpacerModifier)
                ContactDetailsSection(
                    contactDetails = contactDetails,
                    emailError = uiState.errors.emailError,
                    websiteError = uiState.errors.websiteError,
                    phoneNumberError = uiState.errors.phoneNumberError
                )
                Spacer(sectionSpacerModifier)
                OpeningTimesSection(openingTimes)
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