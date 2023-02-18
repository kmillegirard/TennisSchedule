package fr.isen.girardbonnefond.tennisschedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity(){

    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textViewAdmin = findViewById<TextView>(R.id.textViewAdmin)
        textViewAdmin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        val buttonConnexion = findViewById<Button>(R.id.buttonConnexion)
        buttonConnexion.setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            getUser(email, password)
        }
    }

    fun getUser(email: String, password: String) {
        DataBaseHelper.database.getReference("users")
            .orderByChild("email")
            .equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("dataBase", snapshot.toString())
                    if (snapshot.exists()) {
                        val user = snapshot.children.first().getValue(User::class.java)
                        if (user?.password == password) {
                            val intent = Intent(this@LoginActivity, CalendarActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "Problème d'identification", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("dataBase", error.toString())
                }

            })
    }
    @IgnoreExtraProperties
    data class User(val username: String? = null,
                    val email: String? = null,
                    val password: String? = null,
                    val uuid: String? = null) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }
}