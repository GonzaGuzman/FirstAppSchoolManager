package com.zalo.myrecyclerview.home.adapter


import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zalo.myrecyclerview.databinding.ItemBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemBinding.bind(itemView)

    fun bind(student: Student) {
        val idStudent = student.id
        binding.tvIdItem.text = idStudent.toString()
        binding.lastNameStudentItem.text = student.lastName
    }
}


