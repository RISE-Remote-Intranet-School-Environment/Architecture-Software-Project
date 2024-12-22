package org.schoolmanager.project.ui.syllabus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.schoolmanager.project.viewmodel.SyllabusViewModel
import schoolmanager.composeapp.generated.resources.Res
import schoolmanager.composeapp.generated.resources.back


@Composable
fun ResumeOrderScreen(
    BackCart: () -> Unit,
    GoToHomeSyllabus: () -> Unit,
    syllabusviewModel: SyllabusViewModel
)
{
    // Charger les données du panier
    LaunchedEffect(Unit) {
        syllabusviewModel.fetchCart()
    }
    val cartItems by syllabusviewModel.cartItems.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val totalPrice = cartItems.sumOf { it.price * it.quantity }

    Column(
        modifier = Modifier.fillMaxSize().padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            Modifier.fillMaxWidth().padding(top = 12.dp, start = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.back),
                contentDescription = "Back to Course",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { BackCart() }
            )
            Spacer(modifier = Modifier.weight(0.55f))
            Text(
                text = "Resume Order",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                //                    modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }




        // Si le panier est vide
        if (cartItems.isEmpty()) {
            Text(
                text = "Your order is empty.",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, top= 32.dp, end=12.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp) // Espacement entre les sections de la page
            ){
            // Conteneur avec bordure
            item{
                // Conteneur avec bordure pour la liste des articles
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(2.dp, Color.Black),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    cartItems.forEachIndexed { index, item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp) // Espacement entre chaque livre
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = item.syllabus,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    )
                                    Text(
                                        text = "Quantity : ${item.quantity}",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Black
                                        )
                                    )
                                }
                                Text(
                                    text = "${item.price * item.quantity} €",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Prix total
                    val totalPrice = cartItems.sumOf { it.price * it.quantity }
                    val formattedTotalPrice = (totalPrice * 100).toInt() / 100.0
                    Text(
                        text = "TOTAL PRICE : $formattedTotalPrice €",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }


            item{Spacer(modifier = Modifier.height(16.dp))}

            // Bouton pour confirmer la commande
            item{Button(
                onClick = {
                    showDialog = true;
                    syllabusviewModel.confirmOrder(
                        cartItems = cartItems,
                        totalPrice = totalPrice,
                        onSuccess = { println("Order successfully placed!") },
                        onFailure = { println("Order confirmation failed!") }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 30, green = 100, blue = 50))
            ) {
                Text(
                    text = "CONFIRM THE ORDER",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            }
            item{Spacer(modifier = Modifier.height(100.dp))}

        }
    }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Order Confirmed!",
                        style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(red = 30, green = 100, blue = 50))
                    )
                },
                text = {
                    Text(
                        text = "Thank you for your order. It has been successfully placed.",
                        style = TextStyle(fontSize = 18.sp, color = Color.Black)
                    )
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { showDialog = false; GoToHomeSyllabus(); syllabusviewModel.clearCart() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(red = 30, green = 100, blue = 50))
                        ) {
                            Text(
                                text = "OK",
                                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            )
                        }
                    }
                }
            )
        }
    }
}
