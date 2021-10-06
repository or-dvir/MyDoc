package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val specialty: String = "",
    val address: Address = Address()
)

add address to
    list of doctors (open maps!)
    doctor details (open maps!)
    new/edit doctor

//todo
// opening times