package com.hotmail.or_dvir.mydoc.ui.shared

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.theme.Typography

@Composable
fun ExposedDropDownMenu(
    label: String,
    menuItems: List<String>,
    onMenuItemSelected: (index: Int, item: String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
)
{
    ExposedDropdownMenu will be added in compose 1.1!!!
    you can try the preview with this version: 1.1.0-beta02
    there is a problem... the default click behaviour of text field does not allow
    to open the menu.
            try this:
                https://stackoverflow.com/questions/67111020/exposed-drop-down-menu-for-jetpack-compose#answer-69042850
    
    //todo
    // initially selected item
    // option for nothing selected

    //todo update value when clicking menu item
    var selectedItem by remember { mutableStateOf("") }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    //assume menu is collapsed by default
    var trailingIconImage: ImageVector = Icons.Default.KeyboardArrowDown
    var trailingIconContentDescription: String =
        stringResource(R.string.contentDescription_openMenu)

    if (isMenuExpanded)
    {
        trailingIconImage = Icons.Default.KeyboardArrowUp
        trailingIconContentDescription =
            stringResource(R.string.contentDescription_closeMenu)
    }

    //todo do i need this column???
    Column(modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = it.size.toSize()
                }
                .clickable { isMenuExpanded = !isMenuExpanded },
            readOnly = true,
            label = { Text(label) },
            value = selectedItem,
            onValueChange = {
                //todo check if this is called on recomposition (i.e. orientation change)
                onMenuItemSelected(menuItems.indexOf(it), it)
                isMenuExpanded = false
            },
            leadingIcon = leadingIcon,
            trailingIcon = {
                //todo do i need to make this clickable???
                Icon(
                    imageVector = trailingIconImage,
                    contentDescription = trailingIconContentDescription,
                    modifier = modifier.clickable { isMenuExpanded = !isMenuExpanded }
                )
            }
        )

        val menuWidth = with(LocalDensity.current) {
            textFieldSize.width.toDp()
        }

        DropdownMenu(
            modifier = Modifier.width(menuWidth),
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            menuItems.forEach {
                DropdownMenuItem(
                    onClick = { selectedItem = it }
                ) {
                    Text(it)
                }
            }
        }
    }
}

//@Composable
//fun FunctionToCopy()
//{
//    var expanded by remember { mutableStateOf(false) }
//    val suggestions = listOf("Item1", "Item2", "Item3")
//    var selectedText by remember { mutableStateOf("") }
//
//    var textfieldSize by remember { mutableStateOf(Size.Zero) }
//
//    val icon = if (expanded)
//        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
//    else
//        Icons.Filled.ArrowDropDown
//
//
//    Column() {
//        OutlinedTextField(
//            value = selectedText,
//            onValueChange = { selectedText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    //This value is used to assign to the DropDown the same width
//                    textfieldSize = coordinates.size.toSize()
//                },
//            label = { Text("Label") },
//            trailingIcon = {
//                Icon(icon, "contentDescription",
//                     Modifier.clickable { expanded = !expanded })
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
//        ) {
//            suggestions.forEach { label ->
//                DropdownMenuItem(onClick = {
//                    selectedText = label
//                }) {
//                    Text(text = label)
//                }
//            }
//        }
//    }
//}

@Composable
fun Header(
    text: String,
    style: TextStyle = Typography.h6,
    //todo when settled on colors, try primary variant
    color: Color = MaterialTheme.colors.primary,
    bottomSpace: Dp = 5.dp
)
{
    Column {
        Text(
            text = text,
            style = style,
            color = color
        )

        if (bottomSpace > 0.dp)
        {
            Spacer(Modifier.height(bottomSpace))
        }
    }
}

@Composable
fun LoadingIndicatorFullScreen(modifier: Modifier = Modifier)
{
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                //do nothing. the user should not be able to change anything while loading
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun OutlinedTextFieldWithError(
    text: String,
    error: String,
    @StringRes hint: Int,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextChanged: (String) -> Unit
)
{
    val showError = error.isNotBlank()

    Column {
        OutlinedTextField(
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            value = text,
            isError = showError,
            modifier = modifier,
            singleLine = singleLine,
            onValueChange = { onTextChanged(it) },
            label = { Text(text = stringResource(id = hint)) },
            keyboardOptions = keyboardOptions,
            trailingIcon = trailingIcon
        )

        if (showError)
        {
            Text(
                modifier = Modifier.offset(16.dp, 0.dp),
                style = MaterialTheme.typography.caption,
                text = error,
                color = MaterialTheme.colors.error
            )
        }
    }
}

@Composable
fun PasswordTextField(
    text: String,
    error: String,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    @StringRes hint: Int = R.string.hint_password,
    onTextChanged: (String) -> Unit
)
{
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val passwordTransformation: VisualTransformation
    @DrawableRes val passwordIconId: Int
    @StringRes val passwordContentDescriptionId: Int

    if (isPasswordVisible)
    {
        passwordIconId = R.drawable.ic_visibility_off
        passwordContentDescriptionId = R.string.contentDescription_hidePassword
        passwordTransformation = VisualTransformation.None
    } else
    {
        //password is hidden
        passwordIconId = R.drawable.ic_visibility_on
        passwordContentDescriptionId = R.string.contentDescription_showPassword
        passwordTransformation = PasswordVisualTransformation()
    }

    OutlinedTextFieldWithError(
        text = text,
        error = error,
        hint = hint,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = keyboardActions,
        visualTransformation = passwordTransformation,
        modifier = modifier,
        onTextChanged = onTextChanged,
        trailingIcon = {
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible }
            ) {
                Icon(
                    painter = painterResource(passwordIconId),
                    contentDescription = stringResource(passwordContentDescriptionId)
                )
            }
        }
    )
}
