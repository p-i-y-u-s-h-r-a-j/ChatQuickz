package com.example.chatquickz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder> (){

    val ITEM_RECIVE = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.mssg_received, parent, false)
            ReceiveViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.mssg_sent, parent, false)
            SentViewHolder(view)
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java){
            //code for Sentview holder
            val currentMessage = messageList[position]

            val viewHolder = holder as SentViewHolder
            holder.sentMssg.text = currentMessage.message

        }else{
            //code for receiveviewholder
            val viewHolder = holder as ReceiveViewHolder
            holder.receivedMssg.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMssg = itemView.findViewById<TextView>(R.id.msgSent)
    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receivedMssg = itemView.findViewById<TextView>(R.id.msgReceived)
    }
}