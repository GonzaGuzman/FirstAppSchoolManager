package com.zalo.firstAppMVP.detail.detailFragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentDetailBinding
import com.zalo.firstAppMVP.detail.detailDataSource.DetailDataSource
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import com.zalo.firstAppMVP.detail.detailPresenter.DetailPresenter
import com.zalo.firstAppMVP.detail.detailPresenter.DetailView
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.myAplicationClass.MyApplication

/*
Fragment encargado de mostrar y editar los atributos del estudiante
 */
class DetailFragment : Fragment(), DetailView {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailPresenter: DetailPresenter
    private var dBStudent = MyApplication.dataBase
    private var detailRepository = DetailRepository(dBStudent)
    private var detailDataSource = DetailDataSource(detailRepository)

    private var idStudent: Int = 0
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idStudent = it.getInt(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        detailPresenter = DetailPresenter(this, detailDataSource, resources)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        detailPresenter.getStudentById(idStudent)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            presenterDetailActions = detailPresenter
            detailFragment = this@DetailFragment
        }

    }

    override fun initView(student: Student) {
        binding.tvIdVisible.hint = student.id.toString()
        binding.tvNameVisible.hint = student.name
        binding.tvLastNameVisible.hint = student.lastName
        binding.tvAgeVisible.hint = student.age.toString()
        if (student.gender == getString(R.string.maleText))
            binding.rbMale.isChecked = true
        else
            binding.rbFemale.isChecked = true
    }

    override fun enabledViews() {
        binding.etName.isEnabled = true
        binding.etLastName.isEnabled = true
        binding.etAge.isEnabled = true
        binding.rbMale.isEnabled = true
        binding.rbFemale.isEnabled = true
        binding.btnSave.isEnabled = true
        binding.btnEdit.isEnabled = false
    }

    override fun disabledViews() {
        binding.etName.isEnabled = false
        binding.etLastName.isEnabled = false
        binding.etAge.isEnabled = false
        binding.rbMale.isEnabled = false
        binding.rbFemale.isEnabled = false
        binding.btnSave.isEnabled = false
        binding.btnEdit.isEnabled = true

    }


    override fun navigateTo() {
        findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
    }

    override fun showAlertDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.want_to_delete_student))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                detailPresenter.onNegativeButtonClicked()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                detailPresenter.onPositiveButtonClicked()
            }.show()

    }

    override fun dialogDismiss() {
        dialog?.dismiss()
    }

    override fun getUpdateName() {
        detailPresenter.setName(binding.etName.text.toString())
    }

    override fun getUpdateLastName() {
        detailPresenter.setLastName(binding.etLastName.text.toString())
    }

    override fun getUpdateAge() {
        detailPresenter.setAge(binding.etAge.text.toString().toInt())
    }


    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }


    companion object {
        const val ID = "studentID"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

