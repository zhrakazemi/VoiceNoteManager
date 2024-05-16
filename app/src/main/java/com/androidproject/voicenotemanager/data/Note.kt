package com.androidproject.voicenotemanager.data

data class Note(
    val id: String,
    val name: String,
    val recordedVoice: String,
    val attachment: List<String>,
) {

}