<<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/adapter/StudentAdapter.kt
package com.zalo.firstAppMVP.util.adapter
========
package com.zalo.firstAppMVP.homeActivity.adapter
>>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/homeActivity/adapter/StudentAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.home.homeFragment.HomeFragmentDirections
<<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/adapter/StudentAdapter.kt
import com.zalo.firstAppMVP.util.dataClassStudent.Student
========
import com.zalo.firstAppMVP.homeActivity.Student
>>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/homeActivity/adapter/StudentAdapter.kt



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

