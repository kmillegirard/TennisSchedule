package fr.isen.girardbonnefond.tennisschedule.logadmin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.girardbonnefond.tennisschedule.databinding.ActivityAdminBinding
import java.util.*

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAjout.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (username == "admin" && password == "1234") {
                binding.buttonAjout.setOnClickListener {
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