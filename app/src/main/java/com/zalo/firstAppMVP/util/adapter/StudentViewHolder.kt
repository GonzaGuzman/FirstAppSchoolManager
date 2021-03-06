package com.zalo.firstAppMVP.util.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zalo.firstAppMVP.databinding.ItemBinding
import com.zalo.firstAppMVP.util.dataClassStudent.Student

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemBinding.bind(itemView)

    fun bind(student: Student) {
        val idStudent = student.id
        val nameDetail = "${student.lastName} , ${student.name}"
        binding.tvIdItem.text = idStudent.toString()
        binding.lastNameStudentItem.text = nameDetail
    }

}
