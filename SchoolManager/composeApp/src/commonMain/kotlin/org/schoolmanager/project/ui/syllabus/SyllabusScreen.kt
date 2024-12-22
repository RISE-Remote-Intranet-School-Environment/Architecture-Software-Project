package org.schoolmanager.project.ui.syllabus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.schoolmanager.project.data.model.Orientation
import org.schoolmanager.project.data.model.Syllabus
import org.schoolmanager.project.viewmodel.SyllabusViewModel
import schoolmanager.composeapp.generated.resources.Res
import schoolmanager.composeapp.generated.resources.back
import schoolmanager.composeapp.generated.resources.shopping_cart
@Composable
fun SyllabusScreen(
    BackCourse: () -> Unit,
    GoToCart: () -> Unit,
    orientation: Orientation,
    syllabusviewModel: SyllabusViewModel
) {
    LaunchedEffect(Unit) {
        syllabusviewModel.fetchSyllabus()
    }
    val iconColor = MaterialTheme.colors.onSurface
    val AllSyllabus by syllabusviewModel.syllabus.collectAsState()
    val syllabusList = remember(AllSyllabus) {
        syllabusviewModel.getSyllabusByOrientationId(orientation.id)
    }

    if (syllabusList.isEmpty()) {
        Text("No syllabus found for this orientation", color = Color.Gray, fontSize = 16.sp)
        return
    }

    val selectedItems = remember { mutableStateListOf<Boolean>().apply { addAll(List(syllabusList.size) { false }) } }
    val quantities = remember { mutableStateListOf<Int>().apply { addAll(syllabusList.map { it.quantity }) } }

    val allSelected = selectedItems.all { it }
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background) // Ajout du fond d'écran
            .padding(0.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.back),
                contentDescription = "Back to Course",
                modifier = Modifier
                    .size(77.dp)
                    .clickable { BackCourse() }
                    .padding(bottom = 16.dp)
            )

            Text(
                text = orientation.name,
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Image(
                painter = painterResource(Res.drawable.shopping_cart),
                contentDescription = "Cart",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { GoToCart() }
                    .padding(bottom = 16.dp, end = 15.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 0.dp, top = 18.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = allSelected,
                            onCheckedChange = { isChecked ->
                                for (i in selectedItems.indices) {
                                    selectedItems[i] = isChecked
                                }
                            }
                        )
                        Text(
                            text = "Select All",
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            syllabusList
                                .filterIndexed { index, _ -> selectedItems[index] }
                                .forEach { syllabus ->
                                    val updatedSyllabus = syllabus.copy(quantity = quantities[syllabusList.indexOf(syllabus)])
                                    syllabusviewModel.addToCart(updatedSyllabus)
                                }
                            showDialog.value = true
                        },
                        modifier = Modifier.size(width = 140.dp, height = 50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3E61A0))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(Res.drawable.shopping_cart),
                                contentDescription = "Panier",
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(iconColor)
                            )
                            Text(
                                text = "Add",
                                style = TextStyle(color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(17.dp)) {
                        syllabusList.forEachIndexed { index, syllabus ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .border(BorderStroke(1.dp, Color.Black))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Checkbox(
                                    checked = selectedItems[index],
                                    onCheckedChange = { isChecked -> selectedItems[index] = isChecked }
                                )
                                Column(
                                    Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp)
                                ) {
                                    Text(
                                        text = syllabus.syllabus,
                                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "${syllabus.price}€",
                                        style = TextStyle(fontSize = 16.sp, color = Color.Black)
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Button(
                                        onClick = { if (quantities[index] > 1) quantities[index] -= 1 },
                                        modifier = Modifier.size(38.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                                    ) {
                                        Text("-", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black))
                                    }
                                    Text(
                                        text = quantities[index].toString(),
                                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    Button(
                                        onClick = { quantities[index] += 1 },
                                        modifier = Modifier.size(38.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                                    ) {
                                        Text("+", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(
                        text = "Cart Update",
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3E61A0)
                        )
                    )
                },
                text = {
                    Text(
                        text = "Selected items have been added to the cart successfully.",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text(
                            "OK",
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E61A0)
                            )
                        )
                    }
                }
            )
        }
    }
}
