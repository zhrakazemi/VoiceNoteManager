package com.androidproject.voicenotemanager

import com.androidproject.voicenotemanager.Navigation.TodoDestinationsArgs.CATEGORY_ID_ARG
import com.androidproject.voicenotemanager.Navigation.TodoDestinationsArgs.NOTE_ID_ARG
import com.androidproject.voicenotemanager.Navigation.TodoScreens.NOTE_LIST_SCREEN
import com.androidproject.voicenotemanager.Navigation.TodoScreens.NOTE_SCREEN
import com.androidproject.voicenotemanager.Navigation.TodoScreens.CATEGORY_LIST_SCREEN
import com.androidproject.voicenotemanager.Navigation.TodoScreens.RECORD_SCREEN
class Navigation {

    private object TodoScreens {
        const val NOTE_SCREEN = "note"
        const val NOTE_LIST_SCREEN = "noteList"
        const val CATEGORY_LIST_SCREEN = "categoryList"
        const val RECORD_SCREEN = "record"
    }


    object TodoDestinationsArgs {
        const val CATEGORY_ID_ARG = "categoryId"
        const val NOTE_ID_ARG = "noteId"
    }


    object TodoDestinations {
        const val NOTES_ROUTE = "$NOTE_SCREEN?$NOTE_ID_ARG={$NOTE_ID_ARG}"
        const val NOTE_LIST_ROUTE = "$NOTE_LIST_SCREEN?$CATEGORY_ID_ARG={$CATEGORY_ID_ARG}"
        const val CATEGORY_LIST_ROUTE = CATEGORY_LIST_SCREEN
        const val RECORD_ROUTE = "$RECORD_SCREEN?$NOTE_ID_ARG={$NOTE_ID_ARG}"
    }

}