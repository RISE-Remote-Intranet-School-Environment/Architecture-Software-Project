package org.schoolmanager.project.ui.syllabus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

import org.jetbrains.compose.resources.painterResource
import org.schoolmanager.project.viewmodel.SyllabusViewModel
import schoolmanager.composeapp.generated.resources.Res
import schoolmanager.composeapp.generated.resources.back
import schoolmanager.composeapp.generated.resources.trash

@Composable
fun CartSyllabusScreen(
    BackHomeSyllabus: () -> Unit,
    GoToResumeOrder: () -> Unit,
    syllabusviewModel: SyllabusViewModel
) {
    LaunchedEffect(Unit) {
        syllabusviewModel.fetchCart()
    }
    val cartItems by syllabusviewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background) // Ajout du fond d'écran
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.back),
                contentDescription = "Back to Course",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { BackHomeSyllabus() }
            )
            Spacer(modifier = Modifier.weight(0.55f))
            Text(
                text = "My Cart",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        if (cartItems.isEmpty()) {
            Text("Your Cart is empty", style = TextStyle(fontSize = 18.sp, color = Color.Gray))
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    val totalPrice = cartItems.sumOf { it.price * it.quantity }
                    val formattedTotalPrice = (totalPrice * 100).toInt() / 100.0
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TOTAL : $formattedTotalPrice€",
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 25.dp)
                        )

                        Button(
                            onClick = { GoToResumeOrder() },
                            modifier = Modifier
                                .size(width = 160.dp, height = 50.dp)
                                .padding(end = 20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 30, green = 100, blue = 50))
                        ) {
                            Text(
                                text = "ORDER",
                                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            )
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, bottom = 0.dp, end = 16.dp, top = 0.dp)
                            .border(
                                BorderStroke(2.dp, Color.Black),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(13.dp)
                    ) {
                        cartItems.forEach { item ->
                            CartItem(
                                title = item.syllabus,
                                description = "Bloc ${item.idorientation}",
                                price = item.price,
                                quantity = item.quantity,
                                onQuantityChange = { newQuantity ->
                                    syllabusviewModel.updateCartItemQuantity(item.id, newQuantity)
                                },
                                onRemove = {
                                    syllabusviewModel.removeFromCart(item.id)
                                }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}



@Composable
fun CartItem(
    title: String,
    description: String,
    price: Double,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        // Titre de l'article
        Text(
            text = "$description - $title",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "${price}€", // Prix fixe
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End // Aligne à la fin de la ligne
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                    enabled = quantity > 1,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    modifier = Modifier.size(38.dp)
                ) {
                    Text("-", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black))
                }
                Text(
                    text = quantity.toString(),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Button(
                    onClick = { onQuantityChange(quantity + 1) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    modifier = Modifier.size(38.dp)
                ) {
                    Text("+", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black))
                }
            }

            Spacer(modifier = Modifier.width(16.dp))


            Image(
                painter = painterResource(Res.drawable.trash),
                contentDescription = "Remove Item",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { showDialog = true }
            )
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Confirmation",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this article?",
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(
                        onClick = {
                            onRemove()
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        modifier = Modifier
                            .size(width = 120.dp, height = 50.dp)
                    ) {
                        Text("Yes", color= Color.Black,style = TextStyle(fontSize = 18.sp))
                    }
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 62, green = 96, blue = 160)),
                        modifier = Modifier
                            .size(width = 120.dp, height = 50.dp)
                    ) {
                        Text("No",  color= Color.White,style = TextStyle(fontSize = 18.sp))
                    }

                }
            }
        )
    }
}
