package com.example.obligatorisktwistermd.Models

data class User(val username: String, val password: String){
    override fun toString(): String {
        return "$username "
    }
}
