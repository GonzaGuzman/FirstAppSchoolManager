package com.zalo.firstAppMVP.home.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentHomeBinding
import com.zalo.firstAppMVP.home.homeDataSource.HomeDataSourceImplements
import com.zalo.firstAppMVP.home.homePresenter.HomePresenter
import com.zalo.firstAppMVP.home.homePresenter.HomeView
import com.zalo.firstAppMVP.home.homeRepository.HomeRepository
import com.zalo.firstAppMVP.util.adapter.StudentAdapter
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import com.zalo.firstAppMVP.util.loadingScreen.LoadingScreen
import com.zalo.firstAppMVP.util.myAplicationClass.MyApplication


class HomeFragment : Fragment(), HomeView {

    private lateinit var homePresenter: HomePresenter
    private val dBStudent = MyApplication.dataBase
    private val homeRepository = HomeRepository(dBStudent)
    private val homeDataSourceImplements = HomeDataSourceImplements(homeRepository)
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homePresenter = HomePresenter(this, homeDataSourceImplements, resources)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homePresenter.initSchoolDate()
        homePresenter.initComponent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            presenterHomeActions = homePresenter
            homeFragment = this@HomeFragment
        }
    }

    override fun loadRecycler(studentList: List<Student>) {
        recyclerView = binding.recycler
        binding.recycler.layoutManager = LinearLayoutManager(context)
        adapter = StudentAdapter(studentList)
        binding.recycler.adapter = adapter
        LoadingScreen.hideLoading()
    }


    override fun showSchoolName(schoolName: String) {
        binding.tvNameSchool.text = schoolName

    }

    override fun showSchoolTypeEducation(typeEducation: String) {
        binding.tvTypeEducation.text = typeEducation
    }

    override fun navigateTo() {
        findNavController().navigate(R.id.action_homeFragment_to_addFragment)
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



