package com.zalo.myrecyclerview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.FragmentHomeBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.home.adapter.StudentAdapter
import com.zalo.myrecyclerview.model.SchoolViewModel
import com.zalo.myrecyclerview.util.MyApplication
import com.zalo.myrecyclerview.util.MySharedPreferences
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeFragment : Fragment() {


    private val sharedViewModelSchool: SchoolViewModel by activityViewModels()

    //private val sharedViewModel: StudentViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentList: MutableList<Student>
    private lateinit var adapter: StudentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        sharedViewModelSchool.getSchool()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            // viewModel = sharedViewModel
            viewModelSchool = sharedViewModelSchool
            homeFragment = this@HomeFragment
        }

        recyclerView = binding.recycler
        binding.recycler.layoutManager = LinearLayoutManager(context)

        studentList = ArrayList()
        binding.tvNameSchool.text = MySharedPreferences().schoolName
        binding.tvTypeEducation.text = MySharedPreferences().typeEducation
        initComponents()

    }

    /*
        fun getAllStudent(){
            sharedViewModel.getAll()
            adapter = StudentAdapter(sharedViewModel.studentsList.value)
        }
    */
    private fun initComponents() {
        MyApplication.dataBase.studentDao().getAllStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndLogErrors { students ->
                studentList.addAll(students)
                adapter = StudentAdapter(studentList)
                binding.recycler.adapter = adapter
            }
    }

    fun goToAdd() {
        findNavController().navigate(R.id.action_homeFragment_to_addFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



