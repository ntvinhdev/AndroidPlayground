package com.yourcompany.android.jetnotes.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import com.yourcompany.android.jetnotes.ui.components.Note
import com.yourcompany.android.jetnotes.viewmodel.MainViewModel

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun NotesScreen(
  viewModel: MainViewModel,
  onOpenNavigationDrawer: () -> Unit = {},
  onNavigateToSaveNote: () -> Unit = {}
) {
  val notes: List<NoteModel> by viewModel
    .notesNoteInTrash
    .observeAsState(listOf())

  val scaffoldState: ScaffoldState = rememberScaffoldState()

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        title = { Text(text = "JetNotes", color = MaterialTheme.colors.onPrimary) },
        navigationIcon = {
          IconButton(onClick = {
            onOpenNavigationDrawer.invoke()
          }) {
            Icon(
              imageVector = Icons.Filled.List,
              contentDescription = "Drawer Button"
            )
          }
        }
      )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          viewModel.onCreateNewNoteClick()
          onNavigateToSaveNote.invoke()
        },
        contentColor = MaterialTheme.colors.background,
        content = {
          Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Note Button"
          )
        }
      )
    },
    content = {
      if (notes.isNotEmpty()) {
        NotesList(
          notes = notes,
          onNoteCheckedChange = { viewModel.onNoteCheckedChange(it) },
          onNoteClick = {
            viewModel.onNoteClick(it)
            onNavigateToSaveNote.invoke()
          }
        )
      }
    }
  )
}

@Composable
private fun NotesList(
  notes: List<NoteModel>,
  onNoteCheckedChange: (NoteModel) -> Unit,
  onNoteClick: (NoteModel) -> Unit
) {
  LazyColumn {
    items(count = notes.size) { noteIndex ->
      val note = notes[noteIndex]
      Note(
        note = note,
        onNoteClick = onNoteClick,
        onNoteCheckedChange = onNoteCheckedChange
      )
    }
  }
}

@Preview
@Composable
private fun NotesListPreview() {
  NotesList(
    notes = listOf(
      NoteModel(1, "Note 1", "Content 1", null),
      NoteModel(2, "Note 2", "Content 2", false),
      NoteModel(3, "Note 3", "Content 3", true)
    ),
    onNoteCheckedChange = {},
    onNoteClick = {}
  )
}
