package fr.isen.girardbonnefond.tennisschedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.Toast
import fr.isen.girardbonnefond.tennisschedule.calendar.CalendarActivity
import fr.isen.girardbonnefond.tennisschedule.databinding.ActivityLoginBinding
import fr.isen.girardbonnefond.tennisschedule.logadmin.AdminActivity
import fr.isen.girardbonnefond.tennisschedule.logadmin.DataBaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewAdmin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
        binding.buttonConnexion.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
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
                        }
                    }
                    else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Problème d'identification",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("dataBase", error.toString())
                }

            })
    }

    @IgnoreExtraProperties
    data class User(
        val username: String? = null,
        val email: String? = null,
        val password: String? = null,
        val uuid: String? = null
    ) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }
}