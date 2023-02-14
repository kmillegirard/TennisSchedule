package fr.isen.girardbonnefond.tennisschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getUser("chloe.bnfd@gmail.com", "test")
        //writeNewUser()
        //createBooking()
        //createReservation()

        val textViewAdmin = findViewById<TextView>(R.id.textViewAdmin)

        textViewAdmin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }

    fun getUser(email: String, password: String) {

        val buttonConnexion = findViewById<Button>(R.id.buttonAjout)

        DataBaseHelper.database.getReference("users")
            .orderByChild("email")
            .equalTo(email)

            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    Log.d("dataBase", snapshot.toString())
                    if(snapshot.exists()) {
                        val user = snapshot.children.first().getValue(User::class.java)
                        if(user?.password == password) {
                            Log.d("dataBase","connected")
                            // Connected
                            buttonConnexion.setOnClickListener {
                                val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                                startActivity(intent)
                            }
                        }
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