package com.hotmail.or_dvir.mydoc.ui.shared

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
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

//region copied from https://stackoverflow.com/questions/67111020/exposed-drop-down-menu-for-jetpack-compose#answer-69042850
@Composable
fun ExposedDropDownMenu(
    values: List<String>,
    onChange: (Int, String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity),
    shape: Shape = MaterialTheme.shapes.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    )
)
{
    ExposedDropDownMenuImpl(
        values = values,
        onChange = onChange,
        label = label,
        modifier = modifier,
        backgroundColor = backgroundColor,
        shape = shape,
        decorator = { color, width, content ->
            Box(
                Modifier
                    .drawBehind {
                        val strokeWidth = width.value * density
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    }
            ) {
                content()
            }
        }
    )
}

//copied from stack overflow (link in region name)
@Composable
private fun ExposedDropDownMenuImpl(
    values: List<String>,
    onChange: (Int, String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier,
    backgroundColor: Color,
    shape: Shape,
    decorator: @Composable (Color, Dp, @Composable () -> Unit) -> Unit
)
{
    var selectedIndex by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val indicatorColor =
        if (expanded) MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
        else MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity)

    val indicatorWidth = (if (expanded) 2 else 1).dp

    val labelColor =
        if (expanded) MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
        else MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)

    val trailingIconColor =
        MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity)
    val rotation: Float by animateFloatAsState(if (expanded) 180f else 0f)
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.width(IntrinsicSize.Min)) {
        decorator(indicatorColor, indicatorWidth) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = backgroundColor, shape = shape)
                    .onGloballyPositioned { textFieldSize = it.size.toSize() }
                    .clip(shape)
                    .clickable {
                        expanded = !expanded
                        focusManager.clearFocus()
                    }
                    .padding(start = 16.dp, end = 12.dp, top = 7.dp, bottom = 10.dp)
            ) {
                Column(Modifier.padding(end = 32.dp)) {
                    ProvideTextStyle(value = MaterialTheme.typography.caption.copy(color = labelColor)) {
                        label()
                    }
                    Text(
                        text = values[selectedIndex],
                        modifier = Modifier.padding(top = 1.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Change",
                    tint = trailingIconColor,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 4.dp)
                        .rotate(rotation)
                )

            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            values.forEachIndexed { i, v ->
//                val scope = rememberCoroutineScope()
                DropdownMenuItem(
                    onClick = {
                        onChange(i, v)
                        selectedIndex = i
                        expanded = false
//                        scope.launch {
//                            delay(150)
//                            expanded = false
//                        }
                    }
                ) {
                    Text(v)
                }
            }
        }
    }
}
//endregion stackoverflow 2

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
