// ui.grades/AcademicYearRow.kt
package org.schoolmanager.project.ui.grades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow


@Composable
fun AcademicYearRow(academicYear: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF4A90E2), // Bleu clair et moderne
                shape = RoundedCornerShape(12.dp) // Coins arrondis
            )
            .padding(horizontal = 16.dp, vertical = 12.dp) // Espacement interne
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)) // Effet d'ombre
    ) {
        Text(
            text = academicYear,
            color = Color.White, // Texte en blanc pour contraster avec le fond
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center) // Centrer le texte dans la boîte
        )
    }
}
