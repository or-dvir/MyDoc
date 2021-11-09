package com.hotmail.or_dvir.mydoc.models

import android.util.Patterns
import androidx.room.ColumnInfo
import java.util.regex.Pattern

data class ContactDetails(
    @ColumnInfo(name = COLUMN_PHONE_NUMBER)
    val phoneNumber: String?,
    @ColumnInfo(name = COLUMN_EMAIL)
    val email: String?,
    @ColumnInfo(name = COLUMN_WEBSITE)
    val website: String?,
)
{
    //todo
    // more than 1 phone number
    // more than 1 email address
    // more than 1 email website

    private companion object
    {
        const val COLUMN_PHONE_NUMBER = "_phoneNumber"
        const val COLUMN_EMAIL = "_email"
        const val COLUMN_WEBSITE = "_website"
    }

    /**
     * assumes [str] is not mandatory. Meaning `null` will always match
     */
    private fun matchesPattern(str: String?, pattern: Pattern): Boolean
    {
        if (str.isNullOrBlank())
        {
            return true
        }

        return pattern.matcher(str).matches()
    }

    fun isEmailValid() = matchesPattern(email, Patterns.EMAIL_ADDRESS)
    fun isPhoneNumberValid() = matchesPattern(phoneNumber, Patterns.PHONE)
    fun isWebsiteValid() = matchesPattern(website, Patterns.WEB_URL)
}

object ContactDetailsFactory
{
    fun createEmpty() = ContactDetails(null, null, null)
}