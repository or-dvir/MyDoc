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
    // create a doctor factory (need to initialize a doctor in many places

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}