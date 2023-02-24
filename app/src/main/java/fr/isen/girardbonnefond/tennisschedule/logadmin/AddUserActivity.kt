package fr.isen.girardbonnefond.tennisschedule.logadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.girardbonnefond.tennisschedule.LoginActivity
import fr.isen.girardbonnefond.tennisschedule.databinding.ActivityAddUserBinding
import java.util.*

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAjout.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

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