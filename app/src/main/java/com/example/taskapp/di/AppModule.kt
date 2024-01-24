package com.example.taskapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskapp.data.database.TaskDatabase
import com.example.taskapp.domain.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext appContext:Context):TaskDatabase{
        return Room.databaseBuilder(
            appContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserViewModel(db:TaskDatabase):UserViewModel{
        return UserViewModel(db)
    }
}