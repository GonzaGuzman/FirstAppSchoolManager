package com.zalo.firstAppMVP.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zalo.firstAppMVP.databinding.ItemBinding
import com.zalo.firstAppMVP.home.Student

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
