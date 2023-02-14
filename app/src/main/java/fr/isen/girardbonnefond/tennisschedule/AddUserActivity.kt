package fr.isen.girardbonnefond.tennisschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.IgnoreExtraProperties
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
        }
    }

    fun writeNewUser(username: String, email: String, password: String) {
        val user = (LoginActivity.User::class.java)
        DataBaseHelper.database.reference.child("users").child((user ?: "") as String).setValue(user)
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