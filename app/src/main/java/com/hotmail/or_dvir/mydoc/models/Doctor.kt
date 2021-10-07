package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val specialty: String? = null,
    val address: Address? = null
)

//add address to
//    list of doctors (open maps!)
//    doctor details (open maps!)
//    new/edit doctor

//todo
// opening times
// contact details
//      phone
//      email
// link to website

object DoctorFactory {
    fun getDummyDoctor() = Doctor(UUID.randomUUID(), "Dr. Evil")
}