package com.example.peliculaseim

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var correo: EditText
    private lateinit var contraseña: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        correo = findViewById<EditText>(R.id.editTextTextPassword)
        contraseña = findViewById<EditText>(R.id.editTextTextPassword2)
    }

    fun login(view: View){
        val elCorreo = correo.text.toString()
        val laContraseña = contraseña.text.toString()
        auth.signInWithEmailAndPassword(elCorreo, laContraseña).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Login Exitoso", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,Home::class.java).putExtra("email" , task.result.user?.email.toString()))
            }else{
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart(){
        super.onStart()

        val usuarioActual = Firebase.auth.currentUser
        if(usuarioActual!=null){
            Toast.makeText(this, "Usuario Previamente autentificado", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,Home::class.java).putExtra("email" , usuarioActual.email))
        }
    }
}