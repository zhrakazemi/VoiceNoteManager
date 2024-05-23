package com.androidproject.voicenotemanager.data


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Internal model used to represent a task stored locally in a Room database. This is used inside
 * the data layer only.
 *
 * See ModelMappingExt.kt for mapping functions used to convert this model to other
 * models.
 */
@Entity(
    tableName = "note"
)
data class LocalNote(
    @PrimaryKey val id: String,
    val name: String,
    val recordedVoice: String,
    val categoryId: String,
    val userNotes: String
)

@Entity(
    tableName = "category"
)
data class LocalCategory(
    @PrimaryKey val id: String,
    val name: String,
)
