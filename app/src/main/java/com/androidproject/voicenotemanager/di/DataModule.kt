package com.androidproject.voicenotemanager.di

import android.content.Context
import androidx.room.Room
import com.androidproject.voicenotemanager.VOSKApi
import com.androidproject.voicenotemanager.data.DAO
import com.androidproject.voicenotemanager.data.Database
import com.androidproject.voicenotemanager.data.DefaultRepository
import com.androidproject.voicenotemanager.data.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repository: DefaultRepository): Repository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "Tasks.db"
        ).build()
    }

    @Provides
    fun provideDao(database: Database): DAO = database.Dao()
}

@Module
@InstallIn(SingletonComponent::class)
class VOSKModule {
    @Provides
    @Singleton
    fun provideVOSKApi(@ApplicationContext context: Context?): VOSKApi {
        return VOSKApi(context!!)
    }
}