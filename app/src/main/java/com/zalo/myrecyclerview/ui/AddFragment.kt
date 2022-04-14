package com.zalo.myrecyclerview.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.FragmentAddBinding
import com.zalo.myrecyclerview.model.StudentViewModel


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            addFragment = this@AddFragment
        }
    }

    fun updateName() {
        sharedViewModel.setName(binding.textInputName.text.toString())
    }


    fun updateLastName() {
        sharedViewModel.setLastName(binding.textInputLastName.text.toString())
    }

    fun updateAge() {
        if ((binding.textInputAge.text?.isNotEmpty() ?: "") as Boolean) {
            sharedViewModel.setAge(binding.textInputAge.text.toString().toInt())
        }
    }

    private fun setErrorName(error: Boolean) {
        if (error) {
            binding.studentName.isErrorEnabled = true
            binding.studentName.error = "POR FAVOR INGRESE EL NOMBRE"
        } else {
            binding.studentName.isErrorEnabled = false
        }
    }

    private fun setErrorLastName(error: Boolean) {
        if (error) {
            binding.studentLastName.isErrorEnabled = true
            binding.studentLastName.error = "POR FAVOR INGRESE EL APELLIDO"
        } else {
            binding.studentLastName.isErrorEnabled = false
        }
    }

    private fun setErrorAge(error: Boolean) {
        if (error) {
            binding.studentAge.isErrorEnabled = true
            binding.studentAge.error = "POR FAVOR INGRESE LA EDAD"
        } else {
            binding.studentAge.isErrorEnabled = false
        }
    }

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
            Snackbar.make(binding.root, "Agregado exitosamente", Snackbar.LENGTH_SHORT)
                .show()
            resetView()
        }
    }


    fun cancel() {
        findNavController().navigate(R.id.action_addFragment_to_homeFragment)

    }

    private fun resetView() {
        sharedViewModel.reset()
        binding.textInputName.text?.clear()
        binding.textInputLastName.text?.clear()
        binding.textInputAge.text?.clear()
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


