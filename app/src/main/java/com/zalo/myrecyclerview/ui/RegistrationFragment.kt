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
        sharedViewModelSchool.setTypeEducation(getString(R.string.primaryEducation))
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

    /*
    initComponent(): comprueba si ya hay datos cargados en MySharedPreferences de ser asi los carga en la views
        y luego las desactiva para que no puedan ser modificadas
        caso contrario las deja activadas para la edicion
     */

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

    /*
       setErrorName(): metodos que reciben como parametro un booleando, en caso de recibir
           "true"  envia un mensaje y activa el componente error
           "false" si el componente error fue activado previamente lo desactiva sino no hace nada
        */
    private fun setErrorName(error: Boolean) {
        if (error) {
            binding.schoolName.isErrorEnabled = true
            binding.schoolName.error = getString(R.string.please_enter_name)
        } else {
            binding.schoolName.isErrorEnabled = false
        }
    }

    /*
    updateName(): Setea en ViewModel el nombre de la escuela
     */

    fun updateName() {
        sharedViewModelSchool.setSchoolName(binding.schoolNameEditText.text.toString())
    }

    /*
    continueNextView(): comprueba que el campo nameEditText no este vacio de ser asi envia un mensaje de alerta
                        de otro modo setea los datos en MySharedPreferences y navega a HomeFragment
     */
    fun continueNextView() {
        if (!(binding.schoolNameEditText.text.isNullOrEmpty())) {
            if (MySharedPreferences().schoolName.isEmpty() && MySharedPreferences().typeEducation.isEmpty()) {
                sharedViewModelSchool.saveSchool()
            }
            findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
        } else
            setErrorName(true)
    }

    /*
   closeSession(): crea una aleterta para corroborar que el usuario desea cerrar sesion,
       de ser afirmativo cierra la sesion y habilita las vistas y elimina los datos de MySharedPreferences
       , caso contrario deja las vistas desactivadas
       (NOTA VER PORQUE NO FUNCIONA dialog.dismiss)

    */

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
                Snackbar.make(binding.root, getString(R.string.closed_session), Snackbar.LENGTH_SHORT).show()
            }.show()

    }


    /*
    viewDisabled(): desactiva todas las views
     */
    private fun viewDisabled() {
        binding.schoolNameEditText.isEnabled = false
        binding.primaryCheck.isEnabled = false
        binding.highSchoolCheck.isEnabled = false
        binding.bothOfThemCheck.isEnabled = false
    }

    /*
    viewEnabled(): activa todas las views
     */
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