package com.zalo.myrecyclerview.home.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zalo.myrecyclerview.databinding.ItemBinding
import com.zalo.myrecyclerview.home.Student

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemBinding.bind(itemView)

    fun bind(student: Student) {
        binding.lastNameStudentItem.text = student.lastName
        itemView.setOnClickListener {
            Toast.makeText(
                binding.lastNameStudentItem.context,
                student.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}