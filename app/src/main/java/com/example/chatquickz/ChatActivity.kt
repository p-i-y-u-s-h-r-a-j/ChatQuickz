package com.example.chatquickz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    var receiverRoom: String? = null
    var senderRoom: String? = null
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mDbRef = FirebaseDatabase.getInstance().getReference()
        val name = intent.getStringExtra("name")

        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().uid

        receiverRoom = senderUid + receiverUid
        senderRoom = receiverUid + senderUid

        supportActionBar?.title = name

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        //code to add data from database to recycler view

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendBtn.setOnClickListener{
            val message = msgBox.text.toString()
            if(message.isNotEmpty()){
                val messageObject = Message(message, senderUid!!)
                mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(messageObject)
                    }
            }
            msgBox.setText("")
        }
    }
}