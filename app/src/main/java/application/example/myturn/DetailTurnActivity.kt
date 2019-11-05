package application.example.myturn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_detail_turn.*
import kotlinx.android.synthetic.main.content_detail_turn.*

class DetailTurnActivity : AppCompatActivity() {

    var id = 0
    var turn: Turn? = null
    val auth = FirebaseAuth.getInstance() as FirebaseAuth
    val USER_MAIL: String = auth.currentUser?.email ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_turn)
        // take id from intent extra
        id = intent.getStringExtra("turn").toInt()
        // fetch turn from model
        turn = Model.instance.getTurnsGivedId(id)
        descriptionText.setText(turn?.description ?: "")
        mailText.setText("Assigned to " + (turn?.email ?: ""))
        timeText.setText("Time: " + (turn?.time ?: ""))
    }

    fun changeTurn(view: View){
        if (!turn?.email.equals(USER_MAIL)) {
            Toast.makeText(this,"Cannot leave this turn",Toast.LENGTH_SHORT).show()
            return
        }
        Model.instance.changeTurn(id)
        back()
    }

    fun takeTurn(view: View){
        val mail = mailText.text.toString()
        if (!mail.equals("Assigned to ")) {
            Toast.makeText(this,"Cannot take this turn",Toast.LENGTH_SHORT).show()
            return
        }
        Model.instance.takeTurn(id,USER_MAIL)
        back()
    }

    fun back(){
        val intent = Intent(this, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)}

}
