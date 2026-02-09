package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // Estados para los campos de texto
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    // Estado para el mensaje de la alerta (Snackbar/Letrero)
    var mensajeAlerta by remember { mutableStateOf("") }

    // Lista simulada de usuarios
    val usuariosSimulados = listOf("Juan Pérez", "María García", "Carlos López")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Usuario") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cuadros de texto
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Guardar
            Button(onClick = {
                mensajeAlerta = "guardado exitosamente"
            }) {
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Letrero de confirmación (si hay mensaje, se muestra)
            if (mensajeAlerta.isNotEmpty()) {
                Text(
                    text = mensajeAlerta,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Lista de usuarios registrados (simulados)
            Text(text = "Usuarios Registrados:", style = MaterialTheme.typography.titleMedium)

            LazyColumn {
                items(usuariosSimulados) { usuario ->
                    UserItem(usuario) { nuevoMensaje ->
                        mensajeAlerta = nuevoMensaje
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(nombreUsuario: String, onAction: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(nombreUsuario)

        Box {
            Button(onClick = { expanded = true }) {
                Text("Opciones")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Editar") },
                    onClick = {
                        expanded = false
                        onAction("se pulsó Editar")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Eliminar") },
                    onClick = {
                        expanded = false
                        onAction("se pulsó Eliminar")
                    }
                )
            }
        }
    }
}