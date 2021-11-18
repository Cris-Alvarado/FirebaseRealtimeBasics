package com.example.firebaserealtimebasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONException
import org.json.JSONObject

private lateinit var database: DatabaseReference
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference
        //database.setValue("Hola Mundo!")
        //database.child("hijo").setValue("HolaMundo2")

        //writeNewUser("001","nombre1", "user@gmail.com")
        val etId = findViewById<EditText>(R.id.etUserId)
        val etNombre = findViewById<EditText>(R.id.etUserName)
        val etCorreo = findViewById<EditText>(R.id.etUserEmail)
        val btnSend = findViewById<Button>(R.id.btnSet)
        btnSend.setOnClickListener {
            writeNewUser(etId.text.toString(), etNombre.text.toString(), etCorreo.text.toString())
            etId.text.clear()
            etNombre.text.clear()
            etCorreo.text.clear()
        }

        getUser("001")

    }

    fun getUser(userId: String){
        database.child("users").child(userId).get().addOnSuccessListener { record ->
           val json = JSONObject(record.value.toString())
            Log.d("Valoresdefirebase", "crudo: ${record.value}")
           Log.d("valoresdefirebase", "nombre ${json.getString("nombre")} correo: ${json.getString("correo")}")
        }
    }

    fun writeNewUser(userId: String, name: String, email: String){
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
    }
    fun removeUser(userId: String){
        database.child("Usuarios").child(userId).removeValue()
    }


}

class User(name: String, email: String){
    val nombre = name
    val correo = email
}