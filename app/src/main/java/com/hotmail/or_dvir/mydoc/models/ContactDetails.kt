package com.hotmail.or_dvir.mydoc.models

import android.util.Patterns
import androidx.room.ColumnInfo

data class ContactDetails(
    @ColumnInfo(name = COLUMN_PHONE_NUMBER)
    val phoneNumber: String?,
    @ColumnInfo(name = COLUMN_EMAIL)
    val email: String?,
)
{
    fun isEmailValid(): Boolean
    {
        if (email.isNullOrBlank())
        {
            //email is not mandatory
            return true
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneNumberValid(): Boolean
    {
        if (phoneNumber.isNullOrBlank())
        {
            //phone number is not mandatory
            return true
        }

        return Patterns.PHONE.matcher(phoneNumber).matches()
    }

    private companion object
    {
        const val COLUMN_PHONE_NUMBER = "_phoneNumber"
        const val COLUMN_EMAIL = "_email"
    }

    //todo
    // phone (format with https://developer.android.com/reference/android/telephony/PhoneNumberUtils.html)
    // email
    // website? (part of contact details?)
}

object ContactDetailsFactory
{
    fun createEmpty() = ContactDetails(null, null)
}