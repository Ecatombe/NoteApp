package com.fabio.noteapp.di

import android.app.Application
import androidx.room.Room
import com.fabio.noteapp.feature_note.data.data_source.NoteDatabase
import com.fabio.noteapp.feature_note.data.data_source.NoteDatabase.Companion.DATABASE_NAME
import com.fabio.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.fabio.noteapp.feature_note.domain.repository.NoteRepository
import com.fabio.noteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}