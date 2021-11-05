package com.hotmail.or_dvir.mydoc.other

import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.ui.shared.takeIfNotBlank
import java.util.UUID

fun DoctorEntity.toDoctor(): Doctor
{
    return Doctor(
        id = UUID.fromString(id),
        name = name,
        speciality = speciality,
        address = address
    )
}

fun List<DoctorEntity>.toDoctors(): List<Doctor> = this.map { it.toDoctor() }

fun Doctor.toDoctorEntity(): DoctorEntity
{
    return DoctorEntity(
        id = id.toString(),
        name = name,
        speciality = speciality?.takeIfNotBlank(),
        address = address?.takeIfNotEmpty()
    )
}

fun List<Doctor>.toDoctorEntities(): List<DoctorEntity> = this.map { it.toDoctorEntity() }