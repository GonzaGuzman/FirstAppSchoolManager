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
import com.zalo.firstAppMVP.home.Student
import com.zalo.firstAppMVP.detail.detailPresenter.DetailPresenter
import com.zalo.firstAppMVP.detail.detailPresenter.DetailView
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.MyApplication

/*
Fragment encargado de mostrar y editar los atributos del estudiante
 */
class DetailFragment : Fragment(), DetailView {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: DetailPresenter
    private var dBStudent = MyApplication.dataBase
    private var studentRepository = DetailRepository(dBStudent)

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
        presenter = DetailPresenter(this, studentRepository, resources)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        presenter.getStudentById(idStudent)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            presenterActions = presenter
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
                presenter.onNegativeButtonClicked()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                presenter.onPositiveButtonClicked()
            }.show()

    }

    override fun dialogDismiss() {
        dialog?.dismiss()
    }

    override fun getName() {
        presenter.setName(binding.etName.text.toString())
    }

    override fun getLastName() {
        presenter.setLastName(binding.etLastName.text.toString())
    }

    override fun getAge() {
        presenter.setAge(binding.etAge.text.toString().toInt())
    }


    override fun showSuccessSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showErrorSnackBar(message: String) {
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
