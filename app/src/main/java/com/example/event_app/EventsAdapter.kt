package com.example.event_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
class EventsAdapter (private var events: List<Event>, context: Context) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>(){

    private val db :EventsDatabaseHelper = EventsDatabaseHelper(context)
    class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    // here we set up item layout view, so focus here
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent,false)
        return EventViewHolder(view)
    }
    override fun getItemCount(): Int = events.size
    // we set data on the element
    override fun onBindViewHolder (holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title
        holder.contentTextView.text = event.content
        holder.timeTextView.text = event.time
        holder.dateTextView.text = event.date
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateEventActivity::class.java).apply {
                putExtra("event_id",event.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener(){
            db.deleteNote((event.id))
//             now reload the data & get fresh list of nodes
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note deleted!", Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshData(newEvents: List<Event>){
        events =  newEvents
        notifyDataSetChanged()
    }
}