package com.example.chatquickz

class User {
    var name:String? = null
    var email:String? = null
    var Uid:String? = null


    constructor(){}

    constructor(name: String?, email:String?, Uid:String?){
        this.name = name
        this.email = email
        this.Uid = Uid
    }
}