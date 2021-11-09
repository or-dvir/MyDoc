package com.hotmail.or_dvir.mydoc.ui.shared

//todo am i using this class?
// try it!
class TextFieldState(
    var text: String,
    var error: String,
    val inputValidator: (String) -> Boolean
)
{
    fun isInputValid() = inputValidator(text)
}