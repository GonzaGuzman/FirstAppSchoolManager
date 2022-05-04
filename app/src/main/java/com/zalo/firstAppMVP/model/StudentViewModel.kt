package com.zalo.firstAppMVP.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zalo.firstAppMVP.homeActivity.Student
import com.zalo.firstAppMVP.util.MyApplication
import com.zalo.firstAppMVP.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private const val zero = 0

/*
ViewModel con los metodos para crear, eliminar, obtener y modificar estudiantes de database
 */
class StudentViewModel : ViewModel() {

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> = _age

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _studentsList = MutableLiveData<ArrayList<Student>>()
    val studentsList: LiveData<ArrayList<Student>> = _studentsList

    fun setName(name: String) {
        _name.value = name
    }

    fun setLastName(lastName: String) {
        _lastName.value = lastName
    }

    fun setAge(age: Int) {
        _age.value = age
    }

    fun setGender(genderSelected: String) {
        _gender.value = genderSelected
    }

    /*
    getStudent(): obtiene un estudiante por medio del id y lo asiga a la variable del objeto student, y ademas
        setea las variables individuales con los atributos del estudiante con el fin que al modificar el estudiante
        en caso de que el usuario deje algun campo en blanco por defecto mantenga el dato original del estudiante
    */
    fun getStudent(id: Int) {
        MyApplication.dataBase.studentDao().getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setName(it.name)
                setLastName(it.lastName)
                setAge(it.age)
                setGender(it.gender)
                _student.value = it
            }, {
                println(it.message)
            })
    }

    /*
    setStudent(): inserta un nuevo estudiante a la base de datos utilizanto los datos recolectados en las variables
        _name, _lastName, _age, _gender. por defecto el ID del estudiante es seteado con la constante zero ya su valor es
        autogenerado por database
     */

    fun setStudent() {
        val student = Student(
            zero,
            _name.value.toString(),
            _lastName.value.toString(),
            _age.value?.toInt() ?: 0,
            _gender.value.toString())

        CompositeDisposable()
            .add(
                MyApplication.dataBase.studentDao().insert(student)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeAndLogErrors {
                    }
            )

    }

    /*
    updateStudent(): Verifica y modifica de ser necesario los campos del estudiante antes de actualizarlos en database
     */

    fun updateStudent() {
        if (_student.value?.name != _name.value) {
            _student.value?.name = _name.value.toString()
        }
        if (_student.value?.lastName != _lastName.value) {
            _student.value?.lastName = _lastName.value.toString()
        }
        if (_student.value?.age != _age.value) {
            _student.value?.age = _age.value?.toInt() ?: 0
        }
        if (_student.value?.gender != _gender.value) {
            _student.value?.gender = _gender.value.toString()
        }

            MyApplication.dataBase.studentDao().update(_student.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAndLogErrors {
                }

    }

    /*
    deleteSetudent(): Elimina estudiante de database
     */

    fun deleteStudent() {
        _student.value?.let {
            MyApplication.dataBase.studentDao().delete(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAndLogErrors {
                }
        }?.let {
            CompositeDisposable()
                .add(
                    it
                )
        }
    }

    /*
    getAll(): Obtiene todos los estudiantes de database.
    NOTA: AUN EN PROCESO
     */

    fun getAll() {
        MyApplication.dataBase.studentDao().getAllStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndLogErrors { students ->
                _studentsList.value?.addAll(students)
            }
    }

    /*
    reset(): resetea las variableas a valores sin valor
     */
    fun reset() {
        _name.value = ""
        _lastName.value = ""
        _age.value = 0
        _gender.value = ""
    }

    /* init {
         reset()
     }

     */
}
