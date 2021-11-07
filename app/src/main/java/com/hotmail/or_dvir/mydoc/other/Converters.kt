package com.hotmail.or_dvir.mydoc.other

import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity
import com.hotmail.or_dvir.mydoc.models.ContactDetails
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.SimpleAddress
import com.hotmail.or_dvir.mydoc.ui.shared.takeIfNotBlank
import java.util.UUID

fun DoctorEntity.toDoctor(): Doctor
{
    return Doctor(
        id = UUID.fromString(id),
        name = name,
        speciality = speciality,
        address = address,
        contactDetails = contactDetails
    )
}

fun List<DoctorEntity>.toDoctors(): List<Doctor> = this.map { it.toDoctor() }

fun Doctor.toDoctorEntity(): DoctorEntity
{
    val dbAddress = SimpleAddress(
        addressLine = address?.addressLine?.takeIfNotBlank(),
        note = address?.note?.takeIfNotBlank()
    )

    val dbContactDetails = ContactDetails(
        phoneNumber = contactDetails?.phoneNumber?.takeIfNotBlank(),
        email = contactDetails?.email?.takeIfNotBlank(),
    )

    return DoctorEntity(
        id = id.toString(),
        name = name,
        speciality = speciality?.takeIfNotBlank(),
        address = dbAddress,
        contactDetails = dbContactDetails
        )
}

fun List<Doctor>.toDoctorEntities(): List<DoctorEntity> = this.map { it.toDoctorEntity() }