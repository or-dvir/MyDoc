package com.hotmail.or_dvir.mydoc.ui.shared

class TextFieldState(
    var text: String,
    var error: String,
    val inputValidator: (String) -> Boolean
)
{
    fun isInputValid() = inputValidator(text)
}