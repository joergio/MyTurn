package application.example.myturn

import android.content.Context
import android.util.Xml
import java.io.StringWriter
import java.util.ArrayList

object FileHelper {

    // File salvato in:
    //      /data/data/it.unito.di.educ.pdm18kotlin3v1bis/files
    const val dataFileName = "my_course_data.xml"

    fun init() {
        val serializer = Xml.newSerializer()
        val writer = StringWriter()
        val turns = data()
        with(serializer) {
            setOutput(writer)
            startDocument("UTF-8", true)
            startTag(null, "turns")
            for (turn in turns) {
                startTag(null, "turn")
                attribute(null, "name", turn.name)
                attribute(null, "time", turn.time)
                attribute(null, "email", turn.email)
                attribute(null, "description", turn.description)
                endTag(null, "turn")
            }
            endTag(null, "turns")
            endDocument()
        }

        //val fos = MainActivity..openFileOutput(dataFileName, Context.MODE_PRIVATE)
      //  fos.write(writer.toString().toByteArray())
     //   fos.close()
    }

    private fun data(): ArrayList<Turn> {
        val turns = ArrayList<Turn>()
        var turn: Turn

        turn = Turn(0,"Messa delle 8:00", "08:00","mario@mario.it","Messa delle 08:00, molto presto")
        turns.add(turn)
        turn = Turn(1,"Messa delle 8:00", "08:00","mario@mario.it","Messa delle 08:00, molto presto")
        turns.add(turn)
        turn = Turn(2,"Messa delle 8:00", "08:00","mario@mario.it","Messa delle 08:00, molto presto")
        turns.add(turn)
        turn = Turn(3,"Messa delle 8:00", "08:00","mario@mario.it","Messa delle 08:00, molto presto")
        turns.add(turn)
        return turns
    }
}
