package com.hotmail.or_dvir.mydoc.ui.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotmail.or_dvir.mydoc.R

class MainActivity : AppCompatActivity()
{
    //todo
    // remove unused strings
    // remove unused icons/drawables
    // credit all icons
    // make all composables private!!! (as many as you can)
    // add clinics
    //      can have multiple doctors
    //      each doctor may have different days/hours at clinic
    //      probably will be even more complicated
    // in all text input fields, check long texts. multiline?
    //      might push other views out of screen!!!
    // check long lists
    // make sure all screens are scrollable
    // license - anyone can do whatever they want
    // data privacy:
    //      all data is stored on device (did i change this?)
    //      data is saved as-is and not encrypted
    //      data may be backed up by google or any other provider

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}