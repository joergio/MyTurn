package application.example.myturn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_turns.*

class FragmentTurnList: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // take data from database, but for now only local data taken
        val view = inflater.inflate(R.layout.activity_turns, container, false)
        val context = view.getContext()
        var mail = FirebaseAuth.getInstance().currentUser?.email
        //val turns = Model.instance.getTurnsGivedUser(mail ?: "")
        val turns = Model.instance.getTurns()
        var recyclerViewTurns = view.findViewById(R.id.recyclerViewTurns) as RecyclerView
        recyclerViewTurns.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        recyclerViewTurns.adapter = TurnAdapter(turns)
        return view
    }

}