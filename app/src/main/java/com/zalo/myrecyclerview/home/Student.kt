package com.zalo.myrecyclerview.home

data class Student(
    val name: String,
    val lastName: String,
    val age: Int,
    val gender: String,
) {
    override fun toString() =
        " NOMBRE: $name \n APELLIDO: $lastName \n EDAD: $age \n GENERO: $gender"
}