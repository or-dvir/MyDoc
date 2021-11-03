package com.hotmail.or_dvir.mydoc.other

import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity
import com.hotmail.or_dvir.mydoc.models.Doctor
import java.util.UUID

fun DoctorEntity.toDoctor(): Doctor
{
    return Doctor(UUID.fromString(id), name)
}

fun List<DoctorEntity>.toDoctors(): List<Doctor> = this.map { it.toDoctor() }

fun Doctor.toDoctorEntity(): DoctorEntity
{
    return DoctorEntity(id.toString(), name)
}

fun List<Doctor>.toDoctorEntities(): List<DoctorEntity> = this.map { it.toDoctorEntity() }