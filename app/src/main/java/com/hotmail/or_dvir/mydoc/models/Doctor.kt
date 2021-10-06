package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val specialty: String = "",
    val address: Address = Address()
)

add address to
    list of doctors (quick navigation!)
    doctor details (add navigate button)
    new/edit doctor

//todo
// opening times