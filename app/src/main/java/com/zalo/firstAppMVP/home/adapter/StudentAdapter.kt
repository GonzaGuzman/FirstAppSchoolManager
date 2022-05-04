package com.zalo.firstAppMVP.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.home.Student

import com.zalo.firstAppMVP.ui.HomeFragmentDirections

class StudentAdapter(private val list: List<Student>) : RecyclerView.Adapter<StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StudentViewHolder(layoutInflater.inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(studentID = item.id)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = list.size
}
