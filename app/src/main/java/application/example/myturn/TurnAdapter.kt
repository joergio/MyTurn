package application.example.myturn

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.turn_layout.view.*

class TurnAdapter(val turns: List<Turn>): RecyclerView.Adapter<TurnAdapter.TurnViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.turn_layout, parent, false)
        val pos = TurnViewHolder(view).adapterPosition
        view.setOnClickListener(View.OnClickListener {
            fun onClick(v: View?){
                Toast.makeText(view.context,pos,Toast.LENGTH_SHORT).show()
            }
        })
        return TurnViewHolder(view)
    }

    override fun getItemCount() = turns.size

    override fun onBindViewHolder(holder: TurnViewHolder, position: Int) {
        val turn = turns[position]
        val turnEmail = turn.email
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        var button = holder.view.turnButton
        button.text = turn.name
        button.setAllCaps(false)
        if (turnEmail.equals(userEmail))
            button.setBackgroundColor(Color.GREEN)
        else if (turnEmail.equals(""))
            button.setBackgroundColor(Color.YELLOW)
        else
            button.setBackgroundColor(Color.RED)
        
        button.setOnClickListener(View.OnClickListener {
            val pos = holder.adapterPosition
            val intent = Intent(holder.view.context, DetailTurnActivity::class.java)
            intent.putExtra("turn",pos.toString())
            startActivity(holder.view.context,intent,null)
        })
    }

    class TurnViewHolder(val view: View): RecyclerView.ViewHolder(view)

}