package com.zalo.firstAppMVP.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.databinding.FragmentDetailBinding
import com.zalo.firstAppMVP.home.Student
import com.zalo.firstAppMVP.presenter.DetailPresenter
import com.zalo.firstAppMVP.presenter.DetailView
import com.zalo.firstAppMVP.util.MyApplication

/*
Fragment encargado de mostrar y editar los atributos del estudiante
 */
class DetailFragment : Fragment(), DetailView {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: DetailPresenter
    private var dBStudent = MyApplication.dataBase

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
        presenter = DetailPresenter(this, dBStudent)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        presenter.getStudent(idStudent)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            detailFragment = this@DetailFragment
        }

    }

    override fun initView(student: Student) {
        binding.tvIdVisible.hint = student.id.toString()
        binding.tvNameVisible.hint = student.name
        binding.tvLastNameVisible.hint = student.lastName
        binding.tvAgeVisible.hint = student.age.toString()
        if (student.gender.equals(getString(R.string.maleText)))
            binding.rbMale.isChecked = true
        else
            binding.rbFemale.isChecked = true
    }

    fun setName() {
        presenter.setName(binding.etName.text.toString())

    }

    fun setLastName() {
        presenter.setLastName(binding.etLastName.text.toString())
    }

    fun setAge() {
        presenter.setAge(binding.etAge.text.toString().toInt())
    }

    fun setGender(gender:String) {
       presenter.setGender(gender)
        view?.hideKeyboard()
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

    override fun edit() {
         enabledViews()
    }

    override fun save() {
        presenter.updateStudent()
        Snackbar.make(binding.root, getString(R.string.modified_student), Snackbar.LENGTH_SHORT).show()
    }

    override fun delete() {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.want_to_delete_student))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.no)) { _, _ ->
                    dialog?.dismiss()
                }
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    presenter.deleteStudent()
                    Snackbar.make(binding.root, getString(R.string.delete_student), Snackbar.LENGTH_SHORT).show()
                }.show()

        }

    override fun navigateTo() {
        findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
    }

    companion object {
        const val ID = "studentID"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}


/*
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

     */

    /*
    enabledViews(): Activa todas las vistas
     */
