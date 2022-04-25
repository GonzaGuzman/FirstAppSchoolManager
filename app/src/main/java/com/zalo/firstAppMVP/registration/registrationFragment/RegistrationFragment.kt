package com.zalo.firstAppMVP.registration.registrationFragment


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
import com.zalo.firstAppMVP.databinding.FragmentRegistrationBinding
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository

class RegistrationFragment : Fragment(), RegistrationsView {


    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private var dialog: AlertDialog? = null
    private lateinit var registrationPresenter: RegistrationPresenter
    private var registrationRepository = RegistrationRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        registrationPresenter = RegistrationPresenter(this, registrationRepository, resources)
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        registrationPresenter.initView()
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            presenterRegistrationActions = registrationPresenter
            registrationFragmet = this@RegistrationFragment
        }

    }

    override fun initComponent(nameSchool: String, typeEducation: String) {
        binding.schoolNameEditText.setText(nameSchool)
        when (typeEducation) {
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
    }

    override fun viewDisabled() {
        binding.schoolNameEditText.isEnabled = false
        binding.primaryCheck.isEnabled = false
        binding.highSchoolCheck.isEnabled = false
        binding.bothOfThemCheck.isEnabled = false
    }

    override fun viewEnabled() {
        binding.schoolNameEditText.text?.clear()
        binding.schoolNameEditText.isEnabled = true
        binding.primaryCheck.isEnabled = true
        binding.highSchoolCheck.isEnabled = true
        binding.bothOfThemCheck.isEnabled = true
    }

    override fun setErrorName(error: Boolean) {
        if (error) {
            binding.schoolName.isErrorEnabled = true
            binding.schoolName.error = getString(R.string.please_enter_name)
        } else {
            binding.schoolName.isErrorEnabled = false
        }
    }

    override fun getSchoolName() =
        registrationPresenter.setSchoolName(binding.schoolNameEditText.text.toString())

    override fun getTypeEducation(): String {
        return when (binding.typeEduOptions.checkedRadioButtonId) {
            binding.primaryCheck.id -> getString(R.string.primaryEducation)
            binding.highSchoolCheck.id -> getString(R.string.highSchoolEducation)
            else -> getString(R.string.bothOfThem)
        }
    }

    override fun navigateTo() {
        findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
    }

    override fun showAlertCloseSession() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.close_session))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                registrationPresenter.onNegativeButtonClicked()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                registrationPresenter.onPositiveButtonClicked()
            }.show()
    }

    override fun dialogDismiss() {
        dialog?.dismiss()
    }

    override fun showSuccessSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

