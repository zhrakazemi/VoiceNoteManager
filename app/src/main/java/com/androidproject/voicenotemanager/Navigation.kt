package com.androidproject.voicenotemanager

import androidx.navigation.NavHostController
import com.androidproject.voicenotemanager.DestinationsArgs.CATEGORY_ID_ARG
import com.androidproject.voicenotemanager.DestinationsArgs.NOTE_ID_ARG
import com.androidproject.voicenotemanager.Screens.NOTE_LIST_SCREEN
import com.androidproject.voicenotemanager.Screens.NOTE_SCREEN
import com.androidproject.voicenotemanager.Screens.CATEGORY_LIST_SCREEN
import com.androidproject.voicenotemanager.Screens.RECORD_SCREEN


    private object Screens {
        const val NOTE_SCREEN = "note"
        const val NOTE_LIST_SCREEN = "noteList"
        const val CATEGORY_LIST_SCREEN = "categoryList"
        const val RECORD_SCREEN = "record"
    }


    object DestinationsArgs {
        const val CATEGORY_ID_ARG = "categoryId"
        const val NOTE_ID_ARG = "noteId"
    }


    object Destinations {
        const val NOTES_ROUTE = "$NOTE_SCREEN?$NOTE_ID_ARG={$NOTE_ID_ARG}"
        const val NOTE_LIST_ROUTE = "$NOTE_LIST_SCREEN?$CATEGORY_ID_ARG={$CATEGORY_ID_ARG}"
        const val CATEGORY_LIST_ROUTE = CATEGORY_LIST_SCREEN
        const val RECORD_ROUTE = "$RECORD_SCREEN?$NOTE_ID_ARG={$NOTE_ID_ARG}"
    }

class NavigationActions(private val navController: NavHostController) {

}

