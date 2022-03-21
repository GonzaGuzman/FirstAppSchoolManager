package com.zalo.myrecyclerview.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.detail.DetailActivity
import com.zalo.myrecyclerview.home.Student

class StudentAdapter(private val list: List<Student>) : RecyclerView.Adapter<StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StudentViewHolder(layoutInflater.inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)

            intent.putExtra("itemId", item.id)
            holder.itemView.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int = list.size
}
