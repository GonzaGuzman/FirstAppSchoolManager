package com.zalo.firstAppMVP.home.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentHomeBinding
import com.zalo.firstAppMVP.home.homePresenter.HomePresenter
import com.zalo.firstAppMVP.home.homePresenter.HomeView
import com.zalo.firstAppMVP.home.homeDataSource.HomeDataSource
import com.zalo.firstAppMVP.home.homeRepository.HomeRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import com.zalo.firstAppMVP.util.adapter.StudentAdapter
import com.zalo.firstAppMVP.util.loadingScreen.LoadingScreen
import com.zalo.firstAppMVP.util.myAplicationClass.MyApplication


class HomeFragment : Fragment(), HomeView {

    private lateinit var homePresenter: HomePresenter
    private var dBStudent = MyApplication.dataBase
    private var homeRepository = HomeRepository(dBStudent)
    private var homeDataSource = HomeDataSource(homeRepository)

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
=======
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentHomeBinding
import com.zalo.firstAppMVP.homeActivity.Student
import com.zalo.firstAppMVP.homeActivity.adapter.StudentAdapter
import com.zalo.firstAppMVP.model.SchoolViewModel
import com.zalo.firstAppMVP.util.MyApplication
import com.zalo.firstAppMVP.util.MySharedPreferences
import com.zalo.firstAppMVP.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


/*
vista principal
NOTA: FALTA REFACTORIZAR!!
 */
class HomeFragment : Fragment() {


    private val sharedViewModelSchool: SchoolViewModel by activityViewModels()

    //private val sharedViewModel: StudentViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentList: MutableList<Student>
>>>>>>> main
    private lateinit var adapter: StudentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
<<<<<<< HEAD
        homePresenter = HomePresenter(this, homeDataSource, resources)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homePresenter.initSchoolDate()
        homePresenter.initComponent()
=======
        sharedViewModelSchool.getSchool()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
>>>>>>> main
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
<<<<<<< HEAD
            presenterHomeActions = homePresenter
            homeFragment = this@HomeFragment
        }
        LoadingScreen.displayLoadingWithText(requireContext(), getString(R.string.please_wait), false)
    }

    override fun loadRecycler(studentList: List<Student>) {
        recyclerView = binding.recycler
        binding.recycler.layoutManager = LinearLayoutManager(context)
        adapter = StudentAdapter(studentList)
        binding.recycler.adapter = adapter
        LoadingScreen.hideLoading()
=======
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
initComponents(): carga studentList con todos los estudiantes de database y establece  adapter para
    recyclerView
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

    /*
    gotoAdd(): navega a addFragment
     */
    fun goToAdd() {
        findNavController().navigate(R.id.action_homeFragment_to_addFragment)
>>>>>>> main
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
<<<<<<< HEAD

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

=======
>>>>>>> main
}



