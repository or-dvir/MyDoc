package com.hotmail.or_dvir.mydoc.ui.shared

import android.content.Context
import android.content.Intent
import android.net.Uri

internal fun openMaps(context: Context, query: String)
{
    //todo do i need this?
    //      mapIntent.setPackage("com.google.android.apps.maps")
    // see https://developers.google.com/maps/documentation/urls/android-intents#intent_requests

    context.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/search/?api=1&query=$query")
        )
    )
}
