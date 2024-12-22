package org.schoolmanager.project.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.schoolmanager.project.viewmodel.CoursesViewModel
import schoolmanager.composeapp.generated.resources.Res
import schoolmanager.composeapp.generated.resources.discord
import schoolmanager.composeapp.generated.resources.mail
import schoolmanager.composeapp.generated.resources.profilePicture
import schoolmanager.composeapp.generated.resources.back
import schoolmanager.composeapp.generated.resources.electronic_circuit
import schoolmanager.composeapp.generated.resources.settings


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.Dialog
import org.schoolmanager.project.ContactsViewModel
import schoolmanager.composeapp.generated.resources.Gmailt
import schoolmanager.composeapp.generated.resources.discord2
import schoolmanager.composeapp.generated.resources.gmail_ico
import schoolmanager.composeapp.generated.resources.linkedin
import schoolmanager.composeapp.generated.resources.profilephoto


@Composable
fun CourseCard(
    title: String,
    resource: DrawableResource,
    GoToCourseDetail: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp) // Fixed width to fit in a grid
            .height(150.dp) // Increased height for better appearance in a grid
            .clickable { GoToCourseDetail() },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Course image
            Image(
                painter = painterResource(resource),
                contentDescription = "$title Icon",
                modifier = Modifier
                    .size(60.dp) // Adjust size for grid presentation
            )
            // Course title
            Text(
                text = title,
                fontSize = 14.sp, // Smaller font for grid spacing
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 2 // Ensure long titles are constrained
            )
        }
    }
}


@Composable
fun ProfileScreen(BackHomePage: () -> Unit, GoToSettings: () -> Unit, GoToGrades: () -> Unit, contactsviewModel: ContactsViewModel) {
    val loggedInUserId by contactsviewModel.loggedInUserId.collectAsState()
    val viewModel = CoursesViewModel()
    val courses = viewModel.courses
    val iconColor = MaterialTheme.colors.onSurface
    // Use LazyColumn for all scrollable content
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(), // Fill the available size
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.back),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(75.dp)
                        .clickable { BackHomePage() }
                        .padding(bottom = 16.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )

                Text(loggedInUserId, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Image(
                    painter = painterResource(Res.drawable.settings),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(75.dp)
                        .clickable { GoToSettings() }
                        .padding(bottom = 16.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
        }

        item {

            Image(
                painter= painterResource(Res.drawable.profilephoto),
                contentDescription= "ProfilePhoto",
                modifier= Modifier
                    .clip(CircleShape)
                    .size(125.dp),
                contentScale= ContentScale.Crop
            )
        }

        item {
            Text(
                "Contact Infos",
                Modifier.padding(top = 35.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinkedinButton()
                    MailButton()
                    DiscordButton()
                }
            }
        }


        item {
            Text("Courses", Modifier.padding(top = 35.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        item {
            // Add the course grid here
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val coursesChunked = courses.chunked(3) // Split courses into rows of 3
                for (row in coursesChunked) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (course in row) {
                            CourseCard(
                                title = course.name,
                                resource = course.image,
                                GoToCourseDetail = {  }
                            )
                        }
                    }
                }
            }
        }

        item {
            Text("Grades", Modifier.padding(top = 35.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        item {
            LazyRow(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center // Centrer horizontalement
            ) {
                items(1) { index ->
                    Button(
                        onClick = { GoToGrades() },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(12.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "2024-2025",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp)) // Ajustez la hauteur selon les besoins
        }
    }
}

@Composable
fun MailButton() {
    var showPopup by remember { mutableStateOf(false) } // State to control popup visibility
    var emailAddress by remember { mutableStateOf("example@email.com") } // State for email address

    Button(
        onClick = { showPopup = true }, // Show popup on button click
        modifier = Modifier
            .padding(0.dp) // Remove padding around the button
            .size(40.dp), // Set the size of the button based on the image size
        contentPadding = PaddingValues(0.dp), // Remove extra padding inside the button
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), // Make button background transparent
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Image(
            painter = painterResource(Res.drawable.gmail_ico), // Path to your image
            contentDescription = "Email Logo",
            modifier = Modifier.fillMaxSize() // Make the image fill the button area
        )
    }

    if (showPopup) {
        Dialog(onDismissRequest = { showPopup = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp), // Rounded corners for the popup
                color = MaterialTheme.colors.surface, // Set background color
                elevation = 8.dp // Add shadow for better appearance
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // Padding inside the popup
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Email Address",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextField(
                        value = emailAddress,
                        onValueChange = { emailAddress = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showPopup = false }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = { showPopup = false }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DiscordButton() {
    var showPopup by remember { mutableStateOf(false) } // State to control popup visibility
    var discordHandle by remember { mutableStateOf("example#1234") } // State for Discord handle

    Button(
        onClick = { showPopup = true }, // Show popup on button click
        modifier = Modifier
            .padding(0.dp) // Remove padding around the button
            .size(40.dp), // Set the size of the button based on the image size
        contentPadding = PaddingValues(0.dp), // Remove extra padding inside the button
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), // Make button background transparent
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Image(
            painter = painterResource(Res.drawable.discord2), // Path to your image
            contentDescription = "Discord Logo",
            modifier = Modifier.fillMaxSize() // Make the image fill the button area
        )
    }

    if (showPopup) {
        Dialog(onDismissRequest = { showPopup = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp), // Rounded corners for the popup
                color = MaterialTheme.colors.surface, // Set background color
                elevation = 8.dp // Add shadow for better appearance
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // Padding inside the popup
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Discord Handle",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextField(
                        value = discordHandle,
                        onValueChange = { discordHandle = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showPopup = false }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = { showPopup = false }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun LinkedinButton() {
    var showPopup by remember { mutableStateOf(false) } // State to control popup visibility
    var linkedInUrl by remember { mutableStateOf("https://www.linkedin.com/in/example") } // State for LinkedIn URL

    Button(
        onClick = { showPopup = true }, // Show popup on button click
        modifier = Modifier
            .padding(0.dp) // Remove padding around the button
            .size(40.dp), // Set the size of the button based on the image size
        contentPadding = PaddingValues(0.dp), // Remove extra padding inside the button
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), // Make button background transparent
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Image(
            painter = painterResource(Res.drawable.linkedin), // Path to your image
            contentDescription = "LinkedIn Logo",
            modifier = Modifier.fillMaxSize() // Make the image fill the button area
        )
    }

    if (showPopup) {
        Dialog(onDismissRequest = { showPopup = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp), // Rounded corners for the popup
                color = MaterialTheme.colors.surface, // Set background color
                elevation = 8.dp // Add shadow for better appearance
            ) {
                Column(
                    modifier = Modifier.padding(16.dp), // Padding inside the popup
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LinkedIn URL",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextField(
                        value = linkedInUrl,
                        onValueChange = { linkedInUrl = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showPopup = false }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = { showPopup = false }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}