package com.zalo.myrecyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zalo.myrecyclerview.databinding.FragmentDetailBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

const val KEY_ID = "idStudent"

class DetailFragment : Fragment() {

    private var idStudent = 0
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var retriever = 0
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idStudent = it.getInt(ID)
        }
        if (savedInstanceState != null) {
            retriever = savedInstanceState.getInt(KEY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        retriever = idStudent
        initComponent()
    }

    private fun retrieverExtras() {
        binding.tvIdVisible.text = student.id.toString()
        binding.tvNameVisible.text = student.name
        binding.tvLastNameVisible.text = student.lastName
        binding.tvAgeVisible.text = student.age.toString()
        binding.tvGenderVisible.text = student.gender

    }

    private fun initComponent() {
        MyApplication.dataBase.studentDao().getById(retriever)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                student = it
                retrieverExtras()
            }, {
                println(it.message)

            })

        binding.btnRemove.setOnClickListener {
            CompositeDisposable()
                .add(
                    MyApplication.dataBase.studentDao().delete(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeAndLogErrors {
                            Toast.makeText(requireContext(), "Datos Eliminados", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                        }
                )
        }

        binding.btnModify.setOnClickListener {
            CompositeDisposable()
                .add(
                    MyApplication.dataBase.studentDao().update(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeAndLogErrors {
                            Toast.makeText(requireContext(),
                                "Cambios Guardados",
                                Toast.LENGTH_SHORT).show()
                            binding.btnModify.isEnabled = false
                            binding.tvGenderVisible.visibility = View.VISIBLE

                        }
                )
        }

        binding.tvIdVisible.setOnLongClickListener {
            Toast.makeText(requireContext(), "NO ES PODIBLE MODIFICAR ID", Toast.LENGTH_SHORT)
                .show()
            true
        }

        binding.tvNameVisible.setOnLongClickListener {
            binding.tvNameVisible.visibility = View.INVISIBLE
            binding.etName.visibility = View.VISIBLE
            binding.etName.isEnabled = true
            binding.etName.setOnClickListener {
                if (binding.etName.text.toString().isEmpty()) {
                    binding.tvNameVisible.visibility = View.VISIBLE
                    binding.etName.visibility = View.INVISIBLE
                    binding.tvNameVisible.visibility = View.VISIBLE
                    binding.etName.isEnabled = false
                } else {
                    student.name = binding.etName.text.toString()
                    binding.tvNameVisible.text = binding.etName.text.toString()
                    binding.etName.visibility = View.INVISIBLE
                    binding.tvNameVisible.visibility = View.VISIBLE
                    binding.etName.isEnabled = false
                    binding.btnModify.isEnabled = true
                }
            }
            true
        }


        binding.tvLastNameVisible.setOnLongClickListener {
            binding.tvLastNameVisible.visibility = View.INVISIBLE
            binding.etLastName.visibility = View.VISIBLE
            binding.etLastName.isEnabled = true
            binding.etLastName.setOnClickListener {
                if (binding.etLastName.text.toString().isEmpty()) {
                    binding.tvLastNameVisible.visibility = View.VISIBLE
                    binding.etLastName.visibility = View.INVISIBLE
                    binding.tvLastNameVisible.visibility = View.VISIBLE
                    binding.etLastName.isEnabled = false
                } else {
                    student.lastName = binding.etLastName.text.toString()
                    binding.tvLastNameVisible.text = binding.etLastName.text.toString()
                    binding.etLastName.visibility = View.INVISIBLE
                    binding.tvLastNameVisible.visibility = View.VISIBLE
                    binding.etLastName.isEnabled = false
                    binding.btnModify.isEnabled = true
                }
            }
            true
        }

        binding.tvAgeVisible.setOnLongClickListener {
            binding.tvAgeVisible.visibility = View.INVISIBLE
            binding.etAge.visibility = View.VISIBLE
            binding.etAge.isEnabled = true
            binding.etAge.setOnClickListener {
                if (binding.etAge.text.toString().isEmpty()) {
                    binding.tvAgeVisible.visibility = View.VISIBLE
                    binding.etAge.visibility = View.INVISIBLE
                    binding.tvAge.visibility = View.VISIBLE
                    binding.etAge.isEnabled = false
                } else {
                    student.age = binding.etAge.text.toString().toInt()
                    binding.tvAgeVisible.text = binding.etAge.text.toString()
                    binding.etAge.visibility = View.INVISIBLE
                    binding.tvAgeVisible.visibility = View.VISIBLE
                    binding.etAge.isEnabled = false
                    binding.btnModify.isEnabled = true
                }
            }
            true
        }

        binding.tvGenderVisible.setOnLongClickListener {
            binding.tvGenderVisible.visibility = View.INVISIBLE
            binding.rbMale.visibility = View.VISIBLE
            binding.rbFemale.visibility = View.VISIBLE
            binding.rbMale.isEnabled = true
            binding.rbFemale.isEnabled = true
            binding.rbGender.setOnClickListener {
                if (binding.rbGender.checkedRadioButtonId == R.id.rbMale) {
                    student.gender = binding.rbMale.text.toString()
                } else {
                    student.gender = binding.rbFemale.text.toString()
                }

                binding.tvGenderVisible.text = student.gender
                binding.rbMale.visibility = View.INVISIBLE
                binding.rbFemale.visibility = View.INVISIBLE
                binding.rbMale.isEnabled = false
                binding.rbFemale.isEnabled = false
                binding.btnModify.isEnabled = true
            }
            binding.btnModify.isEnabled = true
            true

        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_ID, retriever)
        super.onSaveInstanceState(outState)
    }


    companion object {
        const val ID = "studentID"
    }
}