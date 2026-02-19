package com.example.peliculaseim

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.google.firebase.database.ValueEventListener

class Home : AppCompatActivity() {
    private  lateinit var auth : FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference("Peliculas")

    lateinit var peliculas: ArrayList<Peliculas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val extras = intent.extras

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val listView = findViewById<ListView>(R.id.lista)
        listView.setOnItemClickListener{
            parent, view, position, id ->
            Toast.makeText(this,peliculas[position].nombre.toString(), Toast.LENGTH_LONG).show()
        }

        val agregaPeliculas = findViewById<FloatingActionButton>(R.id.agregaPeliculas)
        agregaPeliculas.setOnClickListener {
           val pelicula = Pelicula("nombre","genero", "anio")
            myRef.push().setValue(pelicula).addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"pelicula agregada", Toast.LENGTH_LONG).show()
                }
            }
        }

        /*val signout = findViewById<Button>(R.id.signout)
        val saludo = findViewById<TextView>(R.id.saludo)
        saludo.text= saludo.text.toString() + extras?.getCharSequence("email").toString()
        signout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }*/

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                peliculas = ArrayList<Peliculas>()

                val value = snapshot.value
                Log.d("real time database", "Value is: " + value)
                snapshot.children.forEach {
                    unit ->
                    var pelicula = Peliculas(unit.child("nombre").value.toString(),unit.child("anio").value.toString(),unit.child("genero").value.toString(),unit.key.toString())
                    peliculas.add(pelicula)
                }
                llenarListview()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("real time database", "Failed to read value.", error.toException())
            }

        })
    }
    fun llenarListview() {
        val lista = findViewById<ListView>(R.id.lista)
        val adaptador = PeliAdapter(this,peliculas)
        lista.adapter = adaptador
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}