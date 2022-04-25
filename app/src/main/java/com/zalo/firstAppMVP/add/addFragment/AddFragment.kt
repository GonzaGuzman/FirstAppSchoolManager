package com.zalo.firstAppMVP.add.addFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.add.addPresenter.AddPresenter
import com.zalo.firstAppMVP.add.addPresenter.AddView
import com.zalo.firstAppMVP.add.addRepository.AddRepository
import com.zalo.firstAppMVP.databinding.FragmentAddBinding
import com.zalo.firstAppMVP.util.MyApplication

/*
Fragment encargado de la vista de agregar nuevo estudiante
 */
class AddFragment : Fragment(), AddView {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addPresenter: AddPresenter
    private var dBStudent = MyApplication.dataBase
    private var addRepository = AddRepository(dBStudent)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        addPresenter = AddPresenter(this, addRepository, resources)
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            presenterAddActions = addPresenter
            addFragment = this@AddFragment
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setErrorName(error: Boolean) {
        if (error) {
            binding.studentName.isErrorEnabled = true
            binding.studentName.error = getString(R.string.please_enter_name)
        } else {
            binding.studentName.isErrorEnabled = false
        }
    }

    override fun setErrorLastName(error: Boolean) {
        if (error) {
            binding.studentLastName.isErrorEnabled = true
            binding.studentLastName.error = getString(R.string.please_enter_lastname)
        } else {
            binding.studentLastName.isErrorEnabled = false
        }
    }

    override fun setErrorAge(error: Boolean) {
        if (error) {
            binding.studentAge.isErrorEnabled = true
            binding.studentAge.error = getString(R.string.please_enter_age)
        } else {
            binding.studentAge.isErrorEnabled = false
        }
    }

    override fun setName() = addPresenter.setName(binding.textInputName.text.toString())

    override fun setLastName() = addPresenter.setLastName(binding.textInputLastName.text.toString())


    override fun setAge() {
        if ((binding.textInputAge.text?.isNotEmpty() ?: "") as Boolean) {
            addPresenter.setAge(binding.textInputAge.text.toString().toInt())
            /* if (!binding.textInputAge.didTouchFocusSelect()) {
                 binding.textInputAge.hideKeyboard()
             }*/
        }
    }

    override fun getGender(): String {
        return if (binding.radioButtonMale.isChecked) {
            getString(R.string.maleText)
        } else
            getString(R.string.femaleText)
    }

    override fun navigateTo() {

        findNavController().navigate(R.id.action_addFragment_to_homeFragment)
    }

    override fun showSuccessSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun validationForAdd(): Boolean {
        return ((!binding.textInputName.text.isNullOrEmpty()
                && !binding.textInputLastName.text.isNullOrEmpty()
                && !binding.textInputAge.text.isNullOrEmpty())
                )
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun resetView() {
        addPresenter.reset()
        binding.textInputName.text?.clear()
        binding.textInputLastName.text?.clear()
        binding.textInputAge.text?.clear()
    }

    /*fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }*/

}
/*

fun updateName() {
    sharedViewModel.setName(binding.textInputName.text.toString())
}
/*
updateLastName(): Setea en ViewModel el apellido del estudiante
*/
fun updateLastName() {
    sharedViewModel.setLastName(binding.textInputLastName.text.toString())
}
/*
updateAge(): Setea en ViewModel la edad del estudiante
*/
fun updateAge() {
    if ((binding.textInputAge.text?.isNotEmpty() ?: "") as Boolean) {
        sharedViewModel.setAge(binding.textInputAge.text.toString().toInt())
    }
}
/*
setErrorName(), setErrorLastName(), setErrorAge() : metodos que reciben como parametro un booleando, en caso de recibir
    "true"  envia un mensaje y activa el componente error
    "false" si el componente error fue activado previamente lo desactiva sino no hace nada

 */
private fun setErrorName(error: Boolean) {
    if (error) {
        binding.studentName.isErrorEnabled = true
        binding.studentName.error = getString(R.string.please_enter_name)
    } else {
        binding.studentName.isErrorEnabled = false
    }
}

private fun setErrorLastName(error: Boolean) {
    if (error) {
        binding.studentLastName.isErrorEnabled = true
        binding.studentLastName.error = getString(R.string.please_enter_lastname)
    } else {
        binding.studentLastName.isErrorEnabled = false
    }
}

private fun setErrorAge(error: Boolean) {
    if (error) {
        binding.studentAge.isErrorEnabled = true
        binding.studentAge.error = getString(R.string.please_enter_age)
    } else {
        binding.studentAge.isErrorEnabled = false
    }
}
/*
addStudent(): si alguno de los campos no fue completado envia un mensaje de alerta, caso contrario carga el
    nuevo estudiante en database
*/
fun addStudent() {

    if (binding.textInputName.text?.isEmpty() == true) {
        setErrorName(true)
    } else
        setErrorName(false)

    if (binding.textInputLastName.text?.isEmpty() == true) {
        setErrorLastName(true)
    } else
        setErrorLastName(false)

    if (binding.textInputAge.text?.isEmpty() == true) {
        setErrorAge(true)
    } else
        setErrorAge(false)


    if ((!binding.textInputName.text.isNullOrEmpty()
                && !binding.textInputLastName.text.isNullOrEmpty()
                && !binding.textInputAge.text.isNullOrEmpty())
    ) {
        sharedViewModel.setStudent()
        Snackbar.make(binding.root, getString(R.string.successfully_added), Snackbar.LENGTH_SHORT)
            .show()
        resetView()
    }
}

/*
cancel(): vuelve a HomeFragment
*/
fun cancel() {

    findNavController().navigate(R.id.action_addFragment_to_homeFragment)

}
/*

    resetView(): limpia todos los campos
    */
private fun resetView() {
    sharedViewModel.reset()
    binding.textInputName.text?.clear()
    binding.textInputLastName.text?.clear()
    binding.textInputAge.text?.clear()
}

/*
View.hideKeyboard(): funcion que oculta el teclado
NOTA: AHUN NO ESTA IMPLEMENTADA!!

 */

 */
/*
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
*/