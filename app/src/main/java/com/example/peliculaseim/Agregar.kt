package com.example.peliculaseim

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Agregar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etGenero = findViewById<EditText>(R.id.etGenero)
        val etAnio = findViewById<EditText>(R.id.etAnio)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {

            val intent = Intent()
            intent.putExtra("nombre", etNombre.text.toString())
            intent.putExtra("genero", etGenero.text.toString())
            intent.putExtra("anio", etAnio.text.toString())

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}