package com.example.peliculaseim

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class Detalle : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("Peliculas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etGenero = findViewById<EditText>(R.id.etGenero)
        val etAnio = findViewById<EditText>(R.id.etAnio)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val nombre = intent.getStringExtra("nombre")
        val genero = intent.getStringExtra("genero")
        val anio = intent.getStringExtra("anio")
        val id = intent.getStringExtra("id")
        etNombre.setText(nombre)
        etGenero.setText(genero)
        etAnio.setText(anio)
        btnEliminar.setOnClickListener {
            myRef.child(id!!).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Película eliminada", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
        btnEditar.setOnClickListener {
            val peliculaActualizada = Peliculas(
                etNombre.text.toString(),
                etAnio.text.toString(),
                etGenero.text.toString(),
                id
            )
            myRef.child(id!!).setValue(peliculaActualizada)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Película actualizada", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
        }
    }
}