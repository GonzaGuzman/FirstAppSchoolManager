package com.zalo.firstAppMVP.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentHomeBinding
import com.zalo.firstAppMVP.home.Student
import com.zalo.firstAppMVP.home.adapter.StudentAdapter
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



