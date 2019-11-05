package application.example.myturn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth


class DashboardActivity : AppCompatActivity(){

    val auth = FirebaseAuth.getInstance() as FirebaseAuth
    var USER_MAIL: String? = auth.currentUser?.email ?: ""

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportFragmentManager.findFragmentById(R.id.turnFragment) as FragmentTurnList
        with(supportFragmentManager.beginTransaction()) {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setTitle("Welcome, " + USER_MAIL)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    fun logout(view: View): Boolean{
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        return true
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        // Handle item selection
        return when (item?.itemId) {
            else -> super.onContextItemSelected(item)
        }
    }

    fun turnDetail(view: View){
        val button = findViewById<Button>(R.id.turnButton)
        Toast.makeText(baseContext, button.text,Toast.LENGTH_SHORT).show()
    }

    fun refresh(view: View){
        intent = Intent(this, DashboardActivity::class.java)
        finish()
        startActivity(intent)
    }
}
