package application.example.myturn

import android.content.Intent
import android.database.DatabaseUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.*
import com.google.firebase.database.collection.ArraySortedMap
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File

class Model {

    private var turns: List<Turn> = init()
    lateinit var database: DatabaseReference

    private fun init():List<Turn> {

     /*   var localTurns = listOf(
            Turn(0,"8:00", "08:00","mario@mario.net","Messa delle 08:00, molto presto"),
            Turn(1,"8:50", "08:50","yoshi@yoshi.net","Un po' meno presto"),
            Turn(2,"9:30", "09:30","mario@mario.net", "Eccoci qua"),
            Turn(3,"11:30", "11:30","luigi@luigi.net", "Eccoci qua"),
            Turn(4,"12:30", "12:30","", "Eccoci qua")
        )
*/
        turns = listOf(Turn())
        var localTurns = listOf(Turn())

        database = FirebaseDatabase.getInstance().getReference()

        // è un'esemplificazione per avere sempre la stessa situazione
        writeDatabase(database)

        // copia locale del database: esemplificazione
        localTurns = listOf(
            Turn(0,"08:50", "08:50","mario@mario.net","Messa delle 08:00, molto presto"),
            Turn(1,"09:30", "09:30","luigi@luigi.net","Messa delle 09:30, molto presto"),
            Turn(2,"11:30", "11:30","mario@mario.net", "Messa delle 11:00")
        )

        for (x in 0..2){
            val reference = database.child(x.toString())
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val turn = dataSnapshot.getValue(Turn::class.java)
                    val localTurns = turns.toMutableList()
                    // change local copy
                    if (localTurns.size > x)
                        localTurns.removeAt(x)
                    localTurns.add(turn!!)
                    turns = localTurns.toList().sortedWith(compareBy({it.id}))
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        }

        return localTurns.sortedWith(compareBy({it.id}))
    }

    private fun writeDatabase(database: DatabaseReference) {
        database.child("0").child("id").setValue(0)
        database.child("0").child("name").setValue("08:50")
        database.child("0").child("time").setValue("08:50")
        database.child("0").child("email").setValue("mario@mario.net")
        database.child("0").child("description").setValue("Messa delle 08:00, molto presto")
        database.child("1").child("id").setValue(1)
        database.child("1").child("name").setValue("09:30")
        database.child("1").child("time").setValue("09:30")
        database.child("1").child("email").setValue("luigi@luigi.net")
        database.child("1").child("description").setValue("Messa delle 09:30, molto presto")
        database.child("2").child("id").setValue(2)
        database.child("2").child("name").setValue("11:00")
        database.child("2").child("time").setValue("11:00")
        database.child("2").child("email").setValue("mario@mario.net")
        database.child("2").child("description").setValue("Messa delle 11:00")

    }

    fun getTurns():List<Turn>{
        // fetch data from database (or file, or...)
        // returns data
        return turns
    }

    fun getTurnsGivedId(id: Int): Turn?{

        turns.forEach(){
            if (it.id.equals(id))
                return it
        }
        return null
    }

    fun getTurnsGivedUser(mail:String): List<Turn>{
        val userTurns: ArrayList<Turn>
        userTurns = arrayListOf<Turn>()
        val listTurns : List<Turn>
        turns.forEach(){
            if (it.email.equals(mail))
                userTurns.add(it)
        }
        listTurns = userTurns.toList()
        return listTurns
    }

    fun changeTurn(id: Int){
        turns.forEach(){
            if (it.id.equals(id))
                it.email = ""
        }

        database.child(id.toString()).child("email").setValue("")
       /* val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL,"luigi@luigi.net")
        intent.putExtra(Intent.EXTRA_SUBJECT,"cambio turno")
        intent.putExtra(Intent.EXTRA_TEXT,"ciao")
        intent.setType("message\rfc822")*/
    }

    fun takeTurn(id: Int, mail: String){
        // insert mail in turn entry
        turns.forEach(){
            if (it.id.equals(id))
                it.email = mail
        }
        database.child(id.toString()).child("email").setValue(mail)
    }

    companion object {
        @JvmStatic val instance = Model()
    }



}

