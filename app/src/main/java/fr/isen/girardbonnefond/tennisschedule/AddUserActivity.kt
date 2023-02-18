package fr.isen.girardbonnefond.tennisschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.auth.User
import java.util.*

class AddUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val buttonAjout = findViewById<Button>(R.id.buttonAjout)

        buttonAjout.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            writeNewUser(username, email, password)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun writeNewUser(username: String, email: String, password: String) {
        val userId = UUID.randomUUID().toString()
        val user = LoginActivity.User(username, email, password, userId)
        DataBaseHelper.database.reference.child("users").child(user.uuid ?: "").setValue(user)
    }
}