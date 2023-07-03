package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test


class AddNoteTest {
    private var fakeRepository = FakeNoteRepository()
    private var addNote = AddNote(fakeRepository)

    @Test
    fun `Add note with empty content throws InvalidNoteException`() = runBlocking {
        val note = Note(
            title = "Title",
            content = "",
            timestamp = 1212022,
            color = 477373
        )
        try {
            addNote.invoke(note)
            fail("Expected a InvalidNoteException to be thrown")
        } catch (e: InvalidNoteException) {
            assertThat(e.message, `is`("The content of the note can't be empty."))
        }
    }

    @Test
    fun `Add note with empty title throws InvalidNoteException`() = runBlocking {
        val note = Note(
            title = "",
            content = "Content",
            timestamp = 1212022,
            color = 477373
        )
        try {
            addNote.invoke(note)
            fail("Expected a InvalidNoteException to be thrown")
        } catch (e: InvalidNoteException) {
            assertThat(e.message, `is`("The title of the note can't be empty."))
        }
    }

    @Test
    fun `Add note with valid title and content`() = runBlocking {
        val note = Note(
            title = "Title",
            content = "Content",
            timestamp = 1212022,
            color = 477373
        )

        addNote.invoke(note)

        val addedNotes = fakeRepository.getNotes().first()
        assertEquals(listOf(note), addedNotes)
    }

}