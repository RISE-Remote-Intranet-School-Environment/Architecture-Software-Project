// ui.grades/GradesTable.kt
package org.schoolmanager.project.ui.grades

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.Text
import org.schoolmanager.project.viewmodel.GradesViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import org.schoolmanager.project.ContactsViewModel
import org.schoolmanager.project.data.model.GradeDetails


@Composable
fun GradesTable(viewModel: GradesViewModel, contactsViewModel: ContactsViewModel, loggedInUserId: String) {
    var errorMessage by remember { mutableStateOf("") }
    val results by viewModel._studentsCourses.collectAsState()

    LaunchedEffect(Unit) {
        try {
            println("call of function fetchGrades from viewmodel (student)")
            viewModel.fetchGrades(loggedInUserId)
        } catch (e: Exception) {
            errorMessage = "Failed to fetch grades"
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), // Ajout d'un peu d'espace entre les en-têtes et la ligne
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "", // Cellule vide pour aligner avec les titres des cours
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A90E2), // Utilisation d'un bleu moderne pour les titres
                modifier = Modifier.weight(3f) // La première colonne prend plus de place
            )
            listOf("jan", "jun", "sept").forEach { month ->
                Text(
                    text = month,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A90E2), // Un bleu plus moderne
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE0E0E0)) // Ligne de séparation légère
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Each course row
        results.forEach { studentCourse ->
            // Display main course
            GradeRow(courseTitle = studentCourse.course, finalGrades = studentCourse.final_grades)

            // Display sub-courses
            studentCourse.subgrades.forEach { subGrade ->
                SubGradeRow(
                    subTitle = subGrade.subcourse,
                    jan = subGrade.grades.jan,
                    juin = subGrade.grades.jun,
                    sept = subGrade.grades.sept
                )
            }
        }
    }
}

@Composable
fun GradeRow(courseTitle: String, finalGrades: GradeDetails) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(6.dp, RoundedCornerShape(8.dp)) // Ombre plus marquée mais moins forte que SubGradeRow
            .background(Color(0xFF2A5BB8), RoundedCornerShape(8.dp)) // Bleu plus foncé pour les GradeRow
            .padding(horizontal = 16.dp, vertical = 8.dp) // Espacement interne
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Ensure vertical alignment
        ) {
            // Titre du cours avec une couleur claire pour contraster avec le fond
            Text(
                text = courseTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White, // Texte blanc pour contraster avec le fond bleu foncé
                modifier = Modifier.weight(3f)
            )

            // Notes de janvier
            GradeCell(finalGrades.jan, Modifier.weight(1f))

            // Notes de juin
            GradeCell(finalGrades.jun, Modifier.weight(1f))

            // Notes de septembre
            GradeCell(finalGrades.sept, Modifier.weight(1f))
        }
    }

    // Ligne de séparation
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
    )
}

@Composable
fun SubGradeRow(subTitle: String, jan: String, juin: String, sept: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp)) // Ombre légère
            .background(Color(0xFFF7FAFF), RoundedCornerShape(8.dp)) // Fond bleu clair 0xFFF7FAFF
            .padding(horizontal = 16.dp, vertical = 8.dp) // Espacement interne
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sous-titre du sous-cours
            Text(
                text = subTitle,
                fontSize = 14.sp,
                color = Color(0xFF3E60A0),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(3f)
            )

            // Notes de janvier
            GradeCell(jan, Modifier.weight(1f))

            // Notes de juin
            GradeCell(juin, Modifier.weight(1f))

            // Notes de septembre
            GradeCell(sept, Modifier.weight(1f))
        }
    }
}

@Composable
fun GradeCell(grade: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp)) // Rounded corners for each cell
            .background(Color.White) // White background for the cell
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(4.dp)) // Subtle border
            .padding(8.dp), // Padding for the text inside
        contentAlignment = Alignment.Center // Center-align text
    ) {
        Text(
            text = grade,
            fontSize = 14.sp,
            color = when {
                grade == "-" -> Color.Gray // If the grade is empty or missing
                grade.toDoubleOrNull() != null && grade.toDouble() < 10 -> Color(0xFFFF6D6D) // Red for bad grades
                grade.toDoubleOrNull() != null -> Color(0xFF4CAF50) // Green for good grades
                else -> Color.Gray
            },
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}



