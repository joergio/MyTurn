package application.example.myturn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View){
        FirebaseApp.initializeApp(this)
        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val user = usernameEditText.text.toString();
        val pwd = passwordEditText.text.toString();
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(user,pwd)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }
                else
                    Toast.makeText(baseContext, R.string.failed, Toast.LENGTH_SHORT).show()
            }
    }

    fun joinUs(){}
}
