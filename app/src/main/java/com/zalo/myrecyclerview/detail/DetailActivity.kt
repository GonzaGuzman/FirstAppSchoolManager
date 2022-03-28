package com.zalo.myrecyclerview.detail

import android.widget.*
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.*
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.ActivityDetailBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication.Companion.dataBase
import com.zalo.myrecyclerview.util.showMessage
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class DetailActivity : GeneralActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var student: Student
    private lateinit var listGender: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        dataBase.studentDao().getById(intent.getIntExtra("itemId", 0))
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
                    dataBase.studentDao().delete(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeAndLogErrors {
                            "Datos Eliminados".showMessage(this)
                            finish()
                        }
                )
        }

        binding.btnModify.setOnClickListener {
            CompositeDisposable()
                .add(
                    dataBase.studentDao().update(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeAndLogErrors {
                            "Cambios Guardados".showMessage(this)
                            binding.btnModify.isEnabled = false
                            binding.spnGender.isEnabled = false
                            binding.spnGender.visibility = View.INVISIBLE
                            binding.tvGenderVisible.visibility = View.VISIBLE

                        }
                )
        }

        binding.tvIdVisible.setOnLongClickListener {
            "NO ES PODIBLE MODIFICAR ID".showMessage(this)
            true
        }

        binding.tvNameVisible.setOnLongClickListener {
            binding.tvNameVisible.visibility = View.INVISIBLE
            binding.etName.visibility = View.VISIBLE
            binding.etName.isEnabled = true
            binding.etName.setOnClickListener {
                if (binding.etName.text.toString().isEmpty()) {
                    "EL CAMPO NO PUEDE QUEDAR VACIO".showMessage(this)
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
                    "EL CAMPO NO PUEDE QUEDAR VACIO".showMessage(this)
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
                    "EL CAMPO NO PUEDE QUEDAR VACIO".showMessage(this)
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
            listGender = resources.getStringArray(R.array.options_gender)
            val adapterSpnGender =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listGender)
            binding.spnGender.adapter = adapterSpnGender
            binding.tvGenderVisible.visibility = View.INVISIBLE
            binding.spnGender.visibility = View.VISIBLE
            binding.spnGender.isEnabled = true
            binding.spnGender.onItemSelectedListener = this
            binding.btnModify.isEnabled = true
            true

        }

    }



    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        student.gender = listGender[p2]
        binding.tvGenderVisible.text = listGender[p2]

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}





