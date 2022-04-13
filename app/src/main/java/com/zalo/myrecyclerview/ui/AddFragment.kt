package com.zalo.myrecyclerview.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.FragmentAddBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.model.StudentViewModel


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: StudentViewModel by activityViewModels()

    private var student = Student(0, "", "", 0, "")


    private lateinit var inputTextName: EditText
    private lateinit var inputTextLastName: EditText
    private lateinit var inputTextAge: EditText
    private lateinit var actionButton: Button


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
        initComponent()
    }


    private fun initComponent() {
        inputTextName = binding.textInputNameEditText
        inputTextLastName = binding.textInputLastNameEditText
        inputTextAge = binding.textInputAgeEditText
        actionButton = binding.aggregateButton
        inputTextName.addTextChangedListener(textWatcher)
        inputTextLastName.addTextChangedListener(textWatcher)
        inputTextAge.addTextChangedListener(textWatcher)
    }

    fun addStudent() {
        student.name = inputTextName.text.toString()
        student.lastName = inputTextLastName.text.toString()
        student.age = inputTextAge.text.toString().toInt()
        student.gender = sharedViewModel.gender.value.toString()

        sharedViewModel.setStudent(student)
        Snackbar.make(binding.root, "Agregado exitosamente", Snackbar.LENGTH_SHORT).show()
        resetView()
    }

    fun cancel() {
        findNavController().navigate(R.id.action_addFragment_to_homeFragment)

    }

    private fun resetView() {
        inputTextName.text.clear()
        inputTextLastName.text.clear()
        inputTextAge.text.clear()

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            actionButton.isEnabled =
                inputTextName.text.isNotEmpty() && inputTextLastName.text.isNotEmpty() && inputTextAge.text.isNotEmpty()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


