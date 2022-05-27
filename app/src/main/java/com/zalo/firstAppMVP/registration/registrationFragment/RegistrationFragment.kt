package com.zalo.firstAppMVP.registration.registrationFragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentRegistrationBinding
import com.zalo.firstAppMVP.network.APIServiceImpl
import com.zalo.firstAppMVP.registration.registrationDataSource.RegistrationDataSourceImplements
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationState
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository
import com.zalo.firstAppMVP.util.loadingScreen.LoadingScreen

class RegistrationFragment : Fragment(), RegistrationsView, AdapterView.OnItemClickListener {


    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val dialog: AlertDialog? = null
    private lateinit var registrationPresenter: RegistrationPresenter
    private val apiServiceImp = APIServiceImpl
    private val registrationRepository = RegistrationRepository(apiServiceImp)
    private val registrationState = RegistrationState()
    private val registrationDataSourceImplements =
        RegistrationDataSourceImplements(registrationRepository)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        registrationPresenter =
            RegistrationPresenter(this, registrationDataSourceImplements, resources, registrationState)
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
        binding.schoolNameAutoCompleteEditText.isEnabled = false
        binding.primaryCheck.isEnabled = false
        binding.highSchoolCheck.isEnabled = false
        binding.bothOfThemCheck.isEnabled = false
    }

    override fun viewEnabled() {
        binding.schoolNameAutoCompleteEditText.text?.clear()
        binding.schoolNameAutoCompleteEditText.isEnabled = true
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
            LoadingScreen.displayLoadingWithText(requireContext(),
                getString(R.string.please_wait),
                false)
        }
    }

    override fun getSchoolName() {
        registrationPresenter.setSchoolName(binding.schoolNameAutoCompleteEditText.text.toString())
    }

    override fun getTypeEducation() {
        val currentTypeEducation = when (binding.typeEduOptions.checkedRadioButtonId) {
            binding.primaryCheck.id -> getString(R.string.primaryEducation)
            binding.highSchoolCheck.id -> getString(R.string.highSchoolEducation)
            else -> getString(R.string.bothOfThem)
        }
        registrationPresenter.setTypeEducation(currentTypeEducation)
    }

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
                binding.primaryCheck.isChecked = true
            }.show()
    }

    override fun dialogDismiss() {
        dialog?.dismiss()
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(parent: AdapterView<*>?, radioGroup: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        registrationPresenter.validateTypeEducation(item)
    }
}

