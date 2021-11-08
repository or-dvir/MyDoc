package com.hotmail.or_dvir.mydoc.ui.shared

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes

fun String.takeIfNotBlank() = takeIf { this.isNotBlank() }

fun Context.openMaps(query: String)
{
    //todo do i need this?
    //      mapIntent.setPackage("com.google.android.apps.maps")
    // see https://developers.google.com/maps/documentation/urls/android-intents#intent_requests

    //todo handle empty string (show error?)
    startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/search/?api=1&query=$query")
        )
    )
}

fun Context.openDialer(number: String)
{
    startActivity(
        Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
    )
}

//region email
fun Context.email(
    email: String,
    @StringRes subjectRes: Int = -1,
    @StringRes bodyRes: Int = -1
): Boolean
{
    val subject =
        if (subjectRes == -1)
        {
            ""
        } else
        {
            resources.getString(subjectRes)
        }

    val body =
        if (bodyRes == -1)
        {
            ""
        } else
        {
            resources.getString(bodyRes)
        }

    return email(email, subject, body)
}

fun Context.email(
    email: String,
    subject: String = "",
    body: String = ""
): Boolean
{
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

        subject.takeIfNotBlank()?.let {
            putExtra(Intent.EXTRA_SUBJECT, it)
        }

        body.takeIfNotBlank()?.let {
            putExtra(Intent.EXTRA_TEXT, it)
        }
    }

    if (intent.resolveActivity(packageManager) != null)
    {
        startActivity(intent)
        return true
    }

    return false
}
//endregion