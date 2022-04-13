package com.zalo.myrecyclerview.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.FragmentRegistrationBinding
import com.zalo.myrecyclerview.util.MySharedPreferences

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences = MySharedPreferences()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }


    private fun initComponent() {

        if (sharedPreferences.schoolName.isNotEmpty()) {
            binding.schoolNameEditText.isEnabled = false
            binding.primaryCheck.isEnabled = false
            binding.highSchoolCheck.isEnabled = false
            binding.bothOfThemCheck.isEnabled = false
            binding.schoolNameEditText.setText(MySharedPreferences().schoolName)
        }

        binding.btnCloseSession.setOnClickListener {
            // showCloseSessionAlert()
            closeSession()
        }


        binding.btnContinue.setOnClickListener {
            val nameSchool = binding.schoolNameEditText.text.toString()
            val typeEdu = binding.typeEduOptions.checkedRadioButtonId.toString()
            if (nameSchool.isEmpty()) {
                binding.schoolNameEditText.error =
                    getString(R.string.errorMessageEmptyNameSchool)
            } else {
                MySharedPreferences().schoolName = nameSchool
                MySharedPreferences().typeEducation = typeEdu
                findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
            }
        }
    }

    private fun showCloseSessionAlert() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.close_session))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->

            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                closeSession()
            }.show()

    }


    private fun closeSession() {
        binding.primaryCheck.isEnabled = true
        binding.highSchoolCheck.isEnabled = true
        binding.bothOfThemCheck.isEnabled = true
        binding.schoolNameEditText.isEnabled = true
        binding.schoolNameEditText.setText("")
        MySharedPreferences().wipe()

    }

}