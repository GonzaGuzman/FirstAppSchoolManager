package com.zalo.firstAppMVP.registration.registrationFragment


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.AdapterView
import android.widget.ArrayAdapter
=======
>>>>>>> main
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentRegistrationBinding
<<<<<<< HEAD
import com.zalo.firstAppMVP.network.APIServiceImpl
import com.zalo.firstAppMVP.registration.registrationDataSource.RegistrationDataSource
=======
>>>>>>> main
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository

<<<<<<< HEAD
class RegistrationFragment : Fragment(), RegistrationsView, AdapterView.OnItemClickListener {
=======
class RegistrationFragment : Fragment(), RegistrationsView {
>>>>>>> main


    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
<<<<<<< HEAD

    private var dialog: AlertDialog? = null
    private lateinit var registrationPresenter: RegistrationPresenter
    private val apiServiceImp = APIServiceImpl
    private val registrationRepository = RegistrationRepository(apiServiceImp)
    private val registrationDataSource = RegistrationDataSource(registrationRepository)
=======
    private var dialog: AlertDialog? = null
    private lateinit var registrationPresenter: RegistrationPresenter
    private var registrationRepository = RegistrationRepository()

>>>>>>> main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
<<<<<<< HEAD

        registrationPresenter = RegistrationPresenter(this, registrationDataSource, resources)
=======
        registrationPresenter = RegistrationPresenter(this, registrationRepository, resources)
>>>>>>> main
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

<<<<<<< HEAD
        with(binding.schoolNameAutoCompleteEditText) {
            onItemClickListener = this@RegistrationFragment
        }

    }

    override fun listAdapter(schoolsNamesList: ArrayList<String>) {
        val schoolNamesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_list,
            schoolsNamesList
        )
        binding.schoolNameAutoCompleteEditText.setAdapter(schoolNamesAdapter)
    }

    override fun initComponent(nameSchool: String, typeEducation: String) {
        binding.schoolNameAutoCompleteEditText.setText(nameSchool)
=======
    }

    override fun initComponent(nameSchool: String, typeEducation: String) {
        binding.schoolNameEditText.setText(nameSchool)
>>>>>>> main
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
<<<<<<< HEAD
        binding.schoolNameAutoCompleteEditText.isEnabled = false
=======
        binding.schoolNameEditText.isEnabled = false
>>>>>>> main
        binding.primaryCheck.isEnabled = false
        binding.highSchoolCheck.isEnabled = false
        binding.bothOfThemCheck.isEnabled = false
    }

    override fun viewEnabled() {
<<<<<<< HEAD
        binding.schoolNameAutoCompleteEditText.text?.clear()
        binding.schoolNameAutoCompleteEditText.isEnabled = true
=======
        binding.schoolNameEditText.text?.clear()
        binding.schoolNameEditText.isEnabled = true
>>>>>>> main
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

<<<<<<< HEAD
    override fun getSchoolName() {
        registrationPresenter.setSchoolName(binding.schoolNameAutoCompleteEditText.text.toString())
    }
=======
    override fun getSchoolName() =
        registrationPresenter.setSchoolName(binding.schoolNameEditText.text.toString())
>>>>>>> main

    override fun getTypeEducation(): String {
        return when (binding.typeEduOptions.checkedRadioButtonId) {
            binding.primaryCheck.id -> getString(R.string.primaryEducation)
            binding.highSchoolCheck.id -> getString(R.string.highSchoolEducation)
            else -> getString(R.string.bothOfThem)
        }
    }

<<<<<<< HEAD
    override fun validateRadioButton(id: String) {
        when (id) {
            getString(R.string.primaryEducation) -> {
                binding.primaryCheck.isChecked = true
            }
            getString(R.string.highSchoolEducation) -> {
                binding.highSchoolCheck.isChecked = true
            }
            getString(R.string.bothOfThem) -> {
                binding.bothOfThemCheck.isChecked = true
            }
        }
    }

=======
>>>>>>> main
    override fun navigateTo() {
        findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
    }

    override fun showAlertCloseSession() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.close_session))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                registrationPresenter.onNegativeButtonClicked()
<<<<<<< HEAD
               }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                registrationPresenter.onPositiveButtonClicked()
               }.show()
=======
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                registrationPresenter.onPositiveButtonClicked()
            }.show()
>>>>>>> main
    }

    override fun dialogDismiss() {
        dialog?.dismiss()
    }

<<<<<<< HEAD
    override fun showSnackBar(message: String) {
=======
    override fun showSuccessSnackBar(message: String) {
>>>>>>> main
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
<<<<<<< HEAD

    override fun onItemClick(parent: AdapterView<*>?, radioGroup: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        registrationPresenter.validateTypeEducation(item)
    }
=======
>>>>>>> main
}

