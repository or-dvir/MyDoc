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
    /**
     * @return Boolean? whether or not [email] is in a valid email address, or `null` if [email] is null
     */
    fun isEmailValid() = email?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() }

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