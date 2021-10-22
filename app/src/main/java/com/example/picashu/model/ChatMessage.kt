package com.example.picashu.model

data class ChatMessage(var id: String, var text: String, var fromId: String, var toId: String, var timestamp: Long) {
    constructor() : this("", "", "", "", -1)
}
