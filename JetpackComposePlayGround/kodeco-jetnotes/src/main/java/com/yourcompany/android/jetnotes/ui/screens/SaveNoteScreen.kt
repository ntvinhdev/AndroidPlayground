package com.yourcompany.android.jetnotes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetnotes.R
import com.yourcompany.android.jetnotes.domain.model.ColorModel
import com.yourcompany.android.jetnotes.domain.model.NEW_NOTE_ID
import com.yourcompany.android.jetnotes.domain.model.NoteModel
import com.yourcompany.android.jetnotes.ui.components.NoteColor
import com.yourcompany.android.jetnotes.util.fromHex
import com.yourcompany.android.jetnotes.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun SaveNoteScreen(
  viewModel: MainViewModel,
  onNavigateBack: () -> Unit = {}
) {
  val noteEntry: NoteModel by viewModel.noteEntry
    .observeAsState(NoteModel())
  val colors: List<ColorModel> by viewModel.colors
    .observeAsState(listOf())
  val bottomDrawerState: BottomDrawerState =
    rememberBottomDrawerState(BottomDrawerValue.Closed)
  val coroutineScope = rememberCoroutineScope()
  val moveNoteToTrashDialogShownState: MutableState<Boolean> =
    rememberSaveable { mutableStateOf(false) }

  Scaffold(
    topBar = {
      val isEditingMode: Boolean = noteEntry.id != NEW_NOTE_ID
      SaveNoteTopAppBar(
        isEditingMode = isEditingMode,
        onBackClick = { onNavigateBack.invoke() },
        onSaveNoteClick = {
          viewModel.saveNote(noteEntry)
          onNavigateBack.invoke()
        },
        onOpenColorPickerClick = {
          coroutineScope.launch {
            bottomDrawerState.open()
          }
        },
        onDeleteNoteClick = {
          moveNoteToTrashDialogShownState.value = true
        },
      )
    },
    content = {
      BottomDrawer(
        drawerState = bottomDrawerState,
        drawerContent = {
          ColorPicker(
            colors = colors,
            onColorSelect = { color ->
              val newNoteEntry = noteEntry.copy(color = color)
              viewModel.onNoteEntryChange(newNoteEntry)
            }
          )
        },
        content = {
          SaveNoteContent(
            note = noteEntry,
            onNoteChange = { updateNoteEntry ->
              viewModel.onNoteEntryChange(updateNoteEntry)
            }
          )
        }
      )

      if (moveNoteToTrashDialogShownState.value) {
        AlertDialog(
          onDismissRequest = {
            moveNoteToTrashDialogShownState.value = false
          },
          title = { Text("Move note to the trash?") },
          text = { Text("Alert you sure you want to move this note to the trash?") },
          confirmButton = {
            TextButton(
              onClick = {
                viewModel.moveNoteToTrash(noteEntry)
                onNavigateBack.invoke()
              }
            ) {
              Text("Confirm")
            }
          },
          dismissButton = {
            TextButton(
              onClick = {
                moveNoteToTrashDialogShownState.value = false
              }
            ) {
              Text("Dismiss")
            }
          }
        )
      }
    }
  )
}

@Composable
private fun SaveNoteTopAppBar(
  isEditingMode: Boolean,
  onBackClick: () -> Unit,
  onSaveNoteClick: () -> Unit,
  onOpenColorPickerClick: () -> Unit,
  onDeleteNoteClick:() -> Unit
) {
  TopAppBar(
    title = {
      Text(
        text = "Save Note",
        color = MaterialTheme.colors.onPrimary
      )
    },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = "Save Note Button",
          tint = MaterialTheme.colors.onPrimary
        )
      }
    },
    actions = {
      // Save note action icon
      IconButton(onClick = onSaveNoteClick) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Open Color Picker Button",
          tint = MaterialTheme.colors.onPrimary
        )
      }

      // Open color picker action icon
      IconButton(onClick = onOpenColorPickerClick) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_color_lens_24),
          contentDescription = "Open Color Picker Button",
          tint = MaterialTheme.colors.onPrimary
        )
      }

      // Delete action icon (show only in editing mode)
      if (isEditingMode) {
        IconButton(onClick = onDeleteNoteClick) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Note Button",
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  )
}

@Composable
private fun SaveNoteContent(
  note: NoteModel,
  onNoteChange: (NoteModel) -> Unit
) {
  val canBeCheckedOff: Boolean = note.isCheckedOff != null
  Column(modifier = Modifier.fillMaxSize()) {
    ContentTextField(
      label = "Title",
      text = note.title,
      onTextChange = { newTitle ->
        onNoteChange.invoke(note.copy(title = newTitle))
      }
    )
    ContentTextField(
      modifier = Modifier.heightIn(max = 240.dp).padding(top = 16.dp),
      label = "Body",
      text = note.content,
      onTextChange = { newContent ->
        onNoteChange.invoke(note.copy(content = newContent))
      }
    )
    NoteCheckOption(
      isChecked = canBeCheckedOff,
      onCheckedChange = { canBeCheckedOffNewValue ->
        val isCheckedOff: Boolean? = if (canBeCheckedOffNewValue) false else null
        onNoteChange.invoke(note.copy(isCheckedOff = isCheckedOff))
      }
    )
    PickedColor(color = note.color)
  }
}

@Composable
private fun ContentTextField(
  modifier: Modifier = Modifier,
  label: String,
  text: String,
  onTextChange: (String) -> Unit
) {
  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(label) },
    modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = MaterialTheme.colors.surface
    )
  )
}

@Composable
private fun NoteCheckOption(
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(modifier = Modifier.padding(8.dp).padding(top = 16.dp)) {
    Text(
      text = "Can note be checked off?",
      modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
    )
    Switch(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}

@Composable
private fun PickedColor(color: ColorModel) {
  Row(
    modifier = Modifier.padding(8.dp).padding(top = 16.dp)
  ) {
    Text(
      text = "Picked color",
      modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
    )
    NoteColor(
      color = Color.fromHex(color.hex),
      size = 40.dp,
      border = 1.dp,
      modifier = Modifier.padding(4.dp)
    )
  }
}

@Composable
private fun ColorPicker(
  colors: List<ColorModel>,
  onColorSelect: (ColorModel) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "Color picker",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(8.dp)
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
      items(colors.size) { itemIndex ->
        val color = colors[itemIndex]
        ColorItem(
          color = color,
          onColorSelect = onColorSelect
        )
      }
    }
  }
}

@Composable
fun ColorItem(
  color: ColorModel,
  onColorSelect: (ColorModel) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onColorSelect(color) }
  ) {
    NoteColor(
      color = Color.fromHex(color.hex),
      size = 80.dp,
      border = 2.dp,
      modifier = Modifier.padding(10.dp)
    )
    Text(
      text = color.name,
      fontSize = 22.sp,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .align(alignment = Alignment.CenterVertically)
    )
  }
}

@Preview
@Composable
fun SaveNoteTopAppbarPreview() {
  SaveNoteTopAppBar(
    isEditingMode = true,
    onBackClick = {},
    onSaveNoteClick = {},
    onOpenColorPickerClick = {},
    onDeleteNoteClick = {}
  )
}

@Preview
@Composable
fun SaveNoteContentPreview() {
  SaveNoteContent(
    note = NoteModel(title = "Title", content = "content"),
    onNoteChange = {}
  )
}

@Preview
@Composable
fun ContentTextFieldPreview() {
  ContentTextField(
    label = "Title",
    text = "",
    onTextChange = {}
  )
}

@Preview
@Composable
fun PickedColorPreview() {
  PickedColor(ColorModel.DEFAULT)
}

@Preview
@Composable
fun NoteCheckOptionPreview() {
  NoteCheckOption(false) { }
}

@Preview
@Composable
fun ColorItemPreview() {
  ColorPicker(
    colors = listOf(
      ColorModel.DEFAULT,
      ColorModel.DEFAULT,
      ColorModel.DEFAULT
    )
  ) { }
}