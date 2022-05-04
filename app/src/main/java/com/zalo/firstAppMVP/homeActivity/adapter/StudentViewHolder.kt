<<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/adapter/StudentViewHolder.kt
package com.zalo.firstAppMVP.util.adapter
========
package com.zalo.firstAppMVP.homeActivity.adapter
>>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/homeActivity/adapter/StudentViewHolder.kt

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zalo.firstAppMVP.databinding.ItemBinding
<<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/adapter/StudentViewHolder.kt
import com.zalo.firstAppMVP.util.dataClassStudent.Student
========
import com.zalo.firstAppMVP.homeActivity.Student
>>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/homeActivity/adapter/StudentViewHolder.kt

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemBinding.bind(itemView)

    fun bind(student: Student) {
        val idStudent = student.id
        val nameDetail = "${student.lastName} , ${student.name}"
        binding.tvIdItem.text = idStudent.toString()
        binding.lastNameStudentItem.text = nameDetail
    }

    //NOTA: Ver porque no funciona Resources con marcadores de posicion
}
