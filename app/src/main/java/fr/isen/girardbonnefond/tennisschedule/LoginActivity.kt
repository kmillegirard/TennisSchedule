package fr.isen.girardbonnefond.tennisschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.girardbonnefond.tennisschedule.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    lateinit var email:String
    lateinit var password:String

    companion object{
        var url = "https://tennisschedule-ecba9-default-rtdb.europe-west1.firebasedatabase.app/"
        private const val TAG = "EmailPassword"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        onStart()
        binding.buttonConnexion.setOnClickListener{
            email = binding.email.text.toString()
            password = binding.password.text.toString()
            signIn(email,password)
        }
        val textViewAdmin = findViewById<TextView>(R.id.textViewAdmin)

        textViewAdmin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
       if(currentUser != null){
        reload(this)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Fail",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Email et/ou mot de passe incorrect(s)",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun sendEmailVerification() {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent= Intent(this,Log::class.java)
        startActivity(intent)
    }

    private fun reload(activity: LoginActivity) {
            activity.recreate()
        }
}