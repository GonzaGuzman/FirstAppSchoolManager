package com.zalo.firstAppMVP.add.addFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.add.addDataSource.AddDataSourceImplements
import com.zalo.firstAppMVP.add.addPresenter.AddPresenter
import com.zalo.firstAppMVP.add.addPresenter.AddState
import com.zalo.firstAppMVP.add.addPresenter.AddView
import com.zalo.firstAppMVP.add.addRepository.AddRepository
import com.zalo.firstAppMVP.databinding.FragmentAddBinding
import com.zalo.firstAppMVP.util.myAplicationClass.MyApplication

class AddFragment : Fragment(), AddView {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addPresenter: AddPresenter
    private val dBStudent = MyApplication.dataBase
    private val addRepository = AddRepository(dBStudent)
    private val addDataSourceImplements = AddDataSourceImplements(addRepository)
    private val state = AddState()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        addPresenter = AddPresenter(this, addDataSourceImplements, resources, state)
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
        }
    }

    override fun setGender() {
        val gender = if (binding.radioButtonMale.isChecked) {
            getString(R.string.maleText)
        } else
            getString(R.string.femaleText)
        addPresenter.setGender(gender)
    }

    override fun navigateTo() {

        findNavController().navigate(R.id.action_addFragment_to_homeFragment)
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }


    override fun resetView() {
        addPresenter.reset()
        binding.textInputName.text?.clear()
        binding.textInputLastName.text?.clear()
        binding.textInputAge.text?.clear()
        binding.radioButtonMale.isChecked = true
    }

    /*fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }*/

}
