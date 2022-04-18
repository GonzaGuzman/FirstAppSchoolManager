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
import com.zalo.myrecyclerview.databinding.FragmentDetailBinding
import com.zalo.myrecyclerview.model.StudentViewModel

/*
Fragment encargado de mostrar y editar los atributos del estudiante
 */
class DetailFragment : Fragment() {

    private val sharedViewModel: StudentViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var idStudent: Int = 0


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
        sharedViewModel.getStudent(idStudent)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            detailFragment = this@DetailFragment

        }

    }

    /*
    updateName(): Setea en ViewModel el nombre del estudiante
     */

    fun updateName() {
        sharedViewModel.setName(binding.etName.text.toString())
    }

    /*
    updateLastName(): Setea en ViewModel el apellido del estudiante
     */

    fun updateLastName() {
        sharedViewModel.setLastName(binding.etLastName.text.toString())
    }

    /*
      updateAge(): Setea en ViewModel la edad del estudiante
    */

    fun updateAge() {
        sharedViewModel.setAge(binding.etAge.text.toString().toInt())
    }

    /*
    udpStudent(): actualiza los datos del estudiante en database
                  desactiva los campos para que no puedan volver a ser modificados
                  envia un mensaje de confirmacion de actualizacion
     */
    fun updStudent() {
        sharedViewModel.updateStudent()
        disabledViews()
        Snackbar.make(binding.root, getString(R.string.modified_student), Snackbar.LENGTH_SHORT).show()
        sharedViewModel.getStudent(idStudent)
    }


    /*
    deleteStudent(): crea una aleterta para corroborar que el usuario desea eliminar el estudiante,
        de ser afirmativo elimina el estudiante, caso contrario vuelve a obtener el estudiante(
        NOTA: Ver porque no funciona dialog.dismiss)

     */
    fun deleteStudent() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.want_to_delete_student))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                sharedViewModel.getStudent(idStudent)
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                sharedViewModel.deleteStudent()
                Snackbar.make(binding.root, getString(R.string.delete_student), Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }.show()

    }

    /*
    enabledViews(): Activa todas las vistas
     */
    fun enabledViews() {
        binding.etName.isEnabled = true
        binding.etLastName.isEnabled = true
        binding.etAge.isEnabled = true
        binding.rbMale.isEnabled = true
        binding.rbFemale.isEnabled = true
        binding.btnSave.isEnabled = true
        binding.btnEdit.isEnabled = false
    }

    /*
    disabledViews(): Desactiva todas las vistas
     */
    fun disabledViews() {
        binding.etName.isEnabled = false
        binding.etLastName.isEnabled = false
        binding.etAge.isEnabled = false
        binding.rbMale.isEnabled = false
        binding.rbFemale.isEnabled = false
        binding.btnSave.isEnabled = false
        binding.btnEdit.isEnabled = true

    }

    companion object {
        const val ID = "studentID"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
