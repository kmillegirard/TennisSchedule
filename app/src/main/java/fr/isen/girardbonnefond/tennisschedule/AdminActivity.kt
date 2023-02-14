package fr.isen.girardbonnefond.tennisschedule

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

class AdminActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val buttonConnexion = findViewById<Button>(R.id.buttonAjout)


        buttonConnexion.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username == "admin" && password == "1234") {
                // Si correct, afficher un Toast de bienvenue
                Toast.makeText(this, "Bienvenue, admin!", Toast.LENGTH_SHORT).show()

                buttonConnexion.setOnClickListener {
                    val intent = Intent(this, AddUserActivity::class.java)
                    startActivity(intent)
                }
            } else {
                // Si incorrect, afficher un Toast d'erreur
                Toast.makeText(this, "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}