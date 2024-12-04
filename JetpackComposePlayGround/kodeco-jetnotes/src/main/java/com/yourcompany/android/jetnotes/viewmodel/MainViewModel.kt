package com.yourcompany.android.jetnotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yourcompany.android.jetnotes.data.repository.Repository
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View model used for storing the global app state.
 *
 * This view model is used for all screens.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

  val notesNoteInTrash: LiveData<List<NoteModel>> by lazy {
    repository.getAllNotesNotInTrash().asLiveData()
  }

  fun onCreateNewNoteClick() {

  }

  fun onNoteClick() {

  }

  fun onNoteCheckedChange(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.insertNote(note)
    }
  }
}
