package org.schoolmanager.project
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.schoolmanager.project.ApiService
import org.schoolmanager.project.data.model.Contact
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ContactsViewModel {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val contacts = MutableStateFlow<List<Contact>>(emptyList())
    var searchQuery = MutableStateFlow("")
    var selectedContact = MutableStateFlow<Contact?>(null)
    var loggedInUserId = MutableStateFlow("")
        private set

    // Fonction pour récupérer les contacts depuis l'API
    fun fetchContacts() {
        coroutineScope.launch {
            println("Fetching contacts in viewmodel ")
            // Appeler l'API pour obtenir les contacts
            val fetchedContacts = ApiService.fetchContacts()
            // Mettre à jour la liste des contacts dans l'état
            contacts.value = fetchedContacts
            println("Fetched contacts: ${contacts.value.size}")
        }
    }

    fun setLoggedInUserId(userId: String) {
        println("Setting loggedInUserId to: $userId")
        loggedInUserId.value = userId
    }

    val filteredContacts: StateFlow<List<Contact>>
        get() = contacts.map { contactList ->
            contactList.filter {
                it.name.contains(searchQuery.value, ignoreCase = true) ||
                        it.id.contains(searchQuery.value, ignoreCase = true)
            }
        }.stateIn(
            coroutineScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onContactSelected(contact: Contact) {
        selectedContact.value = contact
    }

    fun clearSelectedContact() {
        selectedContact.value = null
    }

    // Méthode pour libérer les ressources lorsque la ViewModel n'est plus utilisée
    fun onClear() {
        coroutineScope.cancel()
    }
}
