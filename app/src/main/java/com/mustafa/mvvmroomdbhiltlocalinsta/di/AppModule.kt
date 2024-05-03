package com.mustafa.mvvmroomdbhiltlocalinsta.di


import android.content.Context
import androidx.room.Room
import com.mustafa.mvvmroomdbhiltlocalinsta.data.PostRepositoryImpl
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostDao
import com.mustafa.mvvmroomdbhiltlocalinsta.database.PostDatabase
import com.mustafa.mvvmroomdbhiltlocalinsta.presentation.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostDatabase(
        @ApplicationContext context: Context
    ): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "post_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: PostDatabase): PostDao {
        return appDatabase.postDao
    }

    @Provides
    @Singleton
    fun providePostRepository(appDao: PostDao): PostRepository = PostRepositoryImpl(appDao)

}