package com.zalo.myrecyclerview.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zalo.myrecyclerview.util.MySharedPreferences


class SchoolViewModel : ViewModel() {

    private val _schoolName = MutableLiveData<String>()
    val schoolName: LiveData<String> = _schoolName

    private val _type = MutableLiveData<String>()
    val type: LiveData<String> = _type


    fun setSchoolName(nameSchool: String) {
        _schoolName.value = nameSchool
    }

    fun setTypeEducation(type: String) {
        _type.value = type
    }

    fun saveSchool() {
        MySharedPreferences().schoolName = _schoolName.value.toString()
        MySharedPreferences().typeEducation = _type.value.toString()
    }

    fun getSchool() {
        setSchoolName(MySharedPreferences().schoolName)
        setTypeEducation(MySharedPreferences().typeEducation)
    }


    fun resetSchool() {
        MySharedPreferences().wipe()
    }

    fun reset(){
        _schoolName.value =""
        _type.value = ""
    }

    init {
        reset()
    }

}