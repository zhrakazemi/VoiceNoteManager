package com.androidproject.voicenotemanager.data

// External to local
 fun Note.toLocal() = LocalNote(
    id = id,
    name = name,
    recordedVoice = recordedVoice,
    userNotes = userNotes,
    categoryId = categoryId,
)

fun Category.toLocal() = LocalCategory(
    id = id,
    name = name,
)

// Local to External
fun LocalNote.toExternal() = Note(
    id = id,
    name = name,
    recordedVoice = recordedVoice,
    userNotes = userNotes,
    categoryId = categoryId,
)

fun LocalCategory.toExternal() = Category(
    id = id,
    name = name,
)

@JvmName("localNoteToExternal")
fun List<LocalNote>.toExternal() = map(LocalNote::toExternal)
@JvmName("localCategoryToExternal")
fun List<LocalCategory>.toExternal() = map(LocalCategory::toExternal)
