package com.hotmail.or_dvir.mydoc.ui.shared

import java.util.UUID

sealed class NavigationDestination
{
    object NewDoctorScreen : NavigationDestination()
    object PopStack : NavigationDestination()
    class DoctorDetailsScreen(val doctorId: UUID) : NavigationDestination()
}