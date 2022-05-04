<<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/dataClassStudent/Student.kt
package com.zalo.firstAppMVP.util.dataClassStudent
========
package com.zalo.firstAppMVP.homeActivity
>>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/homeActivity/Student.kt

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student_entity")
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var lastName: String,
    var age: Int,
    var gender: String,
)
