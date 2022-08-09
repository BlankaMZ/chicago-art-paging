package zuri.designs.artofchicago.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zuri.designs.artofchicago.data.local.ChicagoArtDatabase
import zuri.designs.artofchicago.util.Constants.CHICAGO_ART_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ChicagoArtDatabase {
        return Room.databaseBuilder(
            context,
            ChicagoArtDatabase::class.java,
            CHICAGO_ART_DATABASE
        ).build()
    }

}