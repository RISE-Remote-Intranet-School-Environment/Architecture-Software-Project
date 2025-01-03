package org.schoolmanager.project.ui.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.schoolmanager.project.ContactsViewModel
import org.schoolmanager.project.viewmodel.GradesViewModel
import schoolmanager.composeapp.generated.resources.ESSchool
import schoolmanager.composeapp.generated.resources.Res

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, viewModel: ContactsViewModel, viewModelGrades: GradesViewModel) {
    var textValue by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    // Fetch contacts or display them
    val contacts by viewModel.contacts.collectAsState()

    LaunchedEffect(Unit) {
        try {
            println("Attempting to fetch contacts...")
            viewModel.fetchContacts()
            println("Contacts fetched: ${contacts.size}")
        } catch (e: Exception) {
            // Show an error message to the user
            println("Error fetching contacts: ${e.message}")
            errorMessage = "Failed to fetch contacts."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Add the image from AboutScreen
        Image(
            painter = painterResource(Res.drawable.ESSchool),
            contentDescription = "Logo",
            modifier = Modifier
                .clip(CircleShape) // Make the image round
                .size(150.dp) // Make the image smaller
                .padding(bottom = 32.dp) // Add more space below the image
        )

        println("Rendering LoginScreen UI")
        Text("Enter your student ID", style = MaterialTheme.typography.h4)
        // TextField for input
        OutlinedTextField(
            value = textValue,
            onValueChange = { textValue = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, // Restrict to a single line of input
            isError = errorMessage.isNotEmpty() // Show error state for TextField when there is an error
        )
        // Display error message if the ID is not found
        if (errorMessage.isNotEmpty()) {
            println("Displaying error message: $errorMessage")
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        // Button for login, only enable if the student ID is valid
        Button(
            onClick = {
                println("Login button clicked with ID: $textValue")
                if (contacts.any { it.id == textValue }) {
                    // Proceed with login
                    viewModel.setLoggedInUserId(textValue)
                    println("Login successful for ID: $textValue")
                    viewModelGrades.setStudentId(textValue)
                    println("call of function setStudentId from viewmodel (student) : $textValue")
                    onLoginSuccess()
                } else {
                    // Show error message if the ID doesn't exist in the contacts list
                    errorMessage = "Student ID not found!"
                    println("Login failed: $errorMessage")
                }
            },
            shape = RoundedCornerShape(16.dp), // Add rounded corners with a 16.dp radius
        ) {
            Text("Login")
        }
    }
}
