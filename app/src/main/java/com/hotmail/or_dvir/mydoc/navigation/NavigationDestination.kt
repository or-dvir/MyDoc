package com.hotmail.or_dvir.mydoc.navigation

import java.util.UUID

sealed class NavigationDestination
{
    object PopStack : NavigationDestination()

    //todo do i need the doctors list screen? its the "home screen".
    //  will other screens need to navigate directly to it?
    //  if so, watch out for ciruclar navigation!!!
    class NewEditDoctorScreen(val doctorId: UUID?) : NavigationDestination()
    class DoctorDetailsScreen(val doctorId: UUID) : NavigationDestination()
}