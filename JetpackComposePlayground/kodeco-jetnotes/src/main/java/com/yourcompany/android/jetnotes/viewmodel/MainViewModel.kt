package com.yourcompany.android.jetnotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yourcompany.android.jetnotes.data.repository.Repository
import com.yourcompany.android.jetnotes.domain.model.ColorModel
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * View model used for storing the global app state.
 *
 * This view model is used for all screens.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

  private var _noteEntry = MutableStateFlow<NoteModel>(NoteModel())
  val noteEntry: LiveData<NoteModel> = _noteEntry.asLiveData()

  val colors: LiveData<List<ColorModel>> by lazy {
    repository.getAllColors().asLiveData()
  }

  val notesNoteInTrash: LiveData<List<NoteModel>> by lazy {
    repository.getAllNotesNotInTrash().asLiveData()
  }

  fun onCreateNewNoteClick() {
    _noteEntry.value = NoteModel()
  }

  fun onNoteClick(note: NoteModel) {
    _noteEntry.value = note
  }

  fun onNoteEntryChange(note: NoteModel) {
    _noteEntry.value = note
  }

  fun saveNote(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.insertNote(note)
      withContext(Dispatchers.Main) {
        _noteEntry.value = NoteModel()
      }
    }
  }

  fun moveNoteToTrash(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.moveNoteToTrash(note.id)
    }
  }

  fun onNoteCheckedChange(note: NoteModel) {
    viewModelScope.launch(Dispatchers.Default) {
      repository.insertNote(note)
    }
  }
}
