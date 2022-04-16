package com.zalo.myrecyclerview.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.FragmentRegistrationBinding
import com.zalo.myrecyclerview.model.SchoolViewModel
import com.zalo.myrecyclerview.util.MySharedPreferences

class RegistrationFragment : Fragment() {


    private val sharedViewModelSchool: SchoolViewModel by activityViewModels()
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        initComponent()
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModelSchool = sharedViewModelSchool
            registrationFragmet = this@RegistrationFragment
        }

    }

    private fun initComponent() {
        if (MySharedPreferences().schoolName.isNotEmpty() && MySharedPreferences().typeEducation.isNotEmpty()) {
            binding.schoolNameEditText.setText(MySharedPreferences().schoolName)
            when (MySharedPreferences().typeEducation) {
                getString(R.string.primaryEducation) -> {
                    binding.primaryCheck.isChecked = true
                }
                getString(R.string.highSchoolEducation) -> {
                    binding.highSchoolCheck.isChecked = true
                }
                else -> {
                    binding.bothOfThemCheck.isChecked = true
                }
            }
            viewDisabled()
        }
    }


    private fun setErrorName(error: Boolean) {
        if (error) {
            binding.schoolName.isErrorEnabled = true
            binding.schoolName.error = "POR FAVOR INGRESE EL NOMBRE"
        } else {
            binding.schoolName.isErrorEnabled = false
        }
    }

    fun updateName() {
        sharedViewModelSchool.setSchoolName(binding.schoolNameEditText.text.toString())
    }

    fun continueNextView() {
        if (!(binding.schoolNameEditText.text.isNullOrEmpty())) {
            if (MySharedPreferences().schoolName.isEmpty() && MySharedPreferences().typeEducation.isEmpty()) {
                sharedViewModelSchool.saveSchool()
            }
            findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
        } else
            setErrorName(true)
    }


    fun closeSession() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.close_session))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                viewDisabled()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewEnabled()
                binding.schoolNameEditText.text?.clear()
                MySharedPreferences().wipe()
                Snackbar.make(binding.root, "SESION CERRADA", Snackbar.LENGTH_SHORT).show()
            }.show()

    }


    private fun viewDisabled() {
        binding.schoolNameEditText.isEnabled = false
        binding.primaryCheck.isEnabled = false
        binding.highSchoolCheck.isEnabled = false
        binding.bothOfThemCheck.isEnabled = false
    }

    private fun viewEnabled() {
        binding.schoolNameEditText.isEnabled = true
        binding.primaryCheck.isEnabled = true
        binding.highSchoolCheck.isEnabled = true
        binding.bothOfThemCheck.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}