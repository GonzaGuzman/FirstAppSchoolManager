package com.zalo.myrecyclerview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zalo.myrecyclerview.databinding.FragmentAddBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
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
        initComponent()
    }


    private fun initComponent() {
        inputTextName = binding.studentName
        inputTextLastName = binding.studentLastName
        inputTextAge = binding.studentAge
        actionButton = binding.aggregateButton
        inputTextName.addTextChangedListener(textWatcher)
        inputTextLastName.addTextChangedListener(textWatcher)
        inputTextAge.addTextChangedListener(textWatcher)

        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

        actionButton.setOnClickListener {
            val genderDate = when (binding.radioGroupGender.checkedRadioButtonId) {
                binding.radioButtonFemale.id -> getString(R.string.femaleText)
                binding.radioButtonMale.id -> getString(R.string.maleText)
                else -> getString(R.string.noGenederText)
            }

            val student = Student(
                0,
                inputTextName.text.toString(),
                inputTextLastName.text.toString(),
                inputTextAge.text.toString().toInt(),
                genderDate
            )
            CompositeDisposable()
                .add(
                    MyApplication.dataBase.studentDao().insert(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeAndLogErrors {
                            resetView()
                            Toast.makeText(context, getString(R.string.save), Toast.LENGTH_SHORT).show()
                        }
                )

        }


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

}

