package com.example.chatquickz

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class UserAdapter(val context: Context, val UserList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val CurrentUser = UserList[position]
        holder.textView.text = CurrentUser.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", CurrentUser.name)
            intent.putExtra("uid", CurrentUser.Uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return UserList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById<TextView>(R.id.userTxt)
    }
}