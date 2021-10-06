package com.hotmail.or_dvir.mydoc.ui.shared

import java.util.UUID

sealed class NavigationDestination
{
    object PopStack : NavigationDestination()
    class NewEditDoctorScreen(val doctorId: UUID?) : NavigationDestination()
    class DoctorDetailsScreen(val doctorId: UUID) : NavigationDestination()
}