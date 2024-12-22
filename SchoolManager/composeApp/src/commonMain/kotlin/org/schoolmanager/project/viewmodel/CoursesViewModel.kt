package org.schoolmanager.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import org.schoolmanager.project.ApiService
import org.schoolmanager.project.data.model.Course
import schoolmanager.composeapp.generated.resources.Res
import schoolmanager.composeapp.generated.resources.administration_reseau
import schoolmanager.composeapp.generated.resources.ai
import schoolmanager.composeapp.generated.resources.alternatif_monophase
import schoolmanager.composeapp.generated.resources.chem
import schoolmanager.composeapp.generated.resources.electronic_circuit
import schoolmanager.composeapp.generated.resources.mechanics
import schoolmanager.composeapp.generated.resources.motor
import schoolmanager.composeapp.generated.resources.physics
import schoolmanager.composeapp.generated.resources.programming
import schoolmanager.composeapp.generated.resources.robotics
import schoolmanager.composeapp.generated.resources.stat
import schoolmanager.composeapp.generated.resources.thermodynamics

import kotlinx.coroutines.flow.stateIn
import org.schoolmanager.project.data.model.API_Course
import org.schoolmanager.project.data.model.Contact
import org.schoolmanager.project.data.model.Syllabus


class CoursesViewModel : ViewModel() {
    val courses = listOf(
        Course(1, "Electrical", Res.drawable.alternatif_monophase, "LURKIN Quentin", "lrk@ecam.be"),
        Course(2, "Motors", Res.drawable.motor, "LOUIS Jean-Guillaume", "j3l@ecam.be"),
        Course(3, "Math", Res.drawable.electronic_circuit, "HILLEWAERE Ruben", "hlr@ecam.be"),
        Course(4, "Network", Res.drawable.administration_reseau, "DELHAY Quentin", "dlh@ecam.be"),
        Course(5, "Physics", Res.drawable.physics, "VERMEULEN Sophie", "svm@ecam.be"),
        Course(6, "Thermodynamics", Res.drawable.thermodynamics, "DUPONT Alain", "dpt@ecam.be"),
        Course(7, "Programming", Res.drawable.programming, "MARTIN Claire", "cmr@ecam.be"),
        Course(8, "Mechanics", Res.drawable.mechanics, "GIRARD Eric", "grd@ecam.be"),
        Course(9, "Robotics", Res.drawable.robotics, "LEGRAND Nicolas", "nl@ecam.be"),
        Course(10, "Artificial Intelligence", Res.drawable.ai, "PEETERS Marie", "mpr@ecam.be"),
        Course(11, "Statistics", Res.drawable.stat, "VANDENBERG Hugo", "hvd@ecam.be"),
        Course(12, "Chemistry", Res.drawable.chem, "LAMBERT Julie", "jlb@ecam.be"),
    )


    //FCT TO GET THE COURSE BY ID
    fun getCourseById(id: Int): Course? {
        return courses.find { it.id == id }
    }

    fun getAllCourses(): List<Course> {
        return courses
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val api_courses = MutableStateFlow<List<API_Course>>(emptyList())
    var searchQuery = MutableStateFlow("")

    fun fetchCourses(query: String = "all") {
        coroutineScope.launch {
            var fetchedCourses: List<API_Course> = emptyList()
            if (query == "all") {
                fetchedCourses = ApiService.fetchCourses()
            } else if (query == "unsubed") {
                fetchedCourses = ApiService.fetchUnsubedCourses()
            } else if (query == "subed") {
                fetchedCourses = ApiService.fetchSubedCourses()
            }
            api_courses.value = fetchedCourses
        }
    }

    fun addToSubbedCourses(courseId: Int, studentId: String) {
        coroutineScope.launch {
            try {
                ApiService.addToSubbedCourse(courseId, studentId)
            } catch (e: Exception) {
                println("Erreur dans ViewModel addToSubbedCourses : ${e.message}")
            }
        }
    }

    val filteredCourses: StateFlow<List<API_Course>>
        get() = api_courses.map { coursesList ->
            coursesList.filter { it.name.contains(searchQuery.value, ignoreCase = true) }
        }.stateIn(
            coroutineScope,
            SharingStarted.Lazily,
            emptyList()
        )


    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
}