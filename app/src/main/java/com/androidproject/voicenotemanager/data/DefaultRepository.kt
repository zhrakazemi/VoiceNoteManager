package com.androidproject.voicenotemanager.data

import androidx.room.Dao


/**
 * @param localDataSource - The local data source
 */
class DefaultRepository(
    private val localDataSource: Dao,
) {

}

