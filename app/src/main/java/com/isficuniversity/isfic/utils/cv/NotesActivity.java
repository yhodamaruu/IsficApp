package com.isficuniversity.isfic.utils.cv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.isficuniversity.isfic.R;

import java.util.ArrayList;
import java.util.Map;

public class NotesActivity extends AppCompatActivity {

    private ListView notesListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notesTitles;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesListView = findViewById(R.id.noteslist);
        ImageButton addButton = findViewById(R.id.add_btn);
        TextView emptyView = findViewById(R.id.empty_view);

        sharedPreferences = getSharedPreferences("NotesApp", MODE_PRIVATE);
        notesTitles = new ArrayList<>();
        notesListView.setEmptyView(emptyView);

        loadNotes();

        adapter = new ArrayAdapter<>(this, R.layout.custom_list_item, R.id.noteTitle, notesTitles);
        notesListView.setAdapter(adapter);

        findViewById(R.id.back_btn).setOnClickListener(v -> finish());

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(NotesActivity.this, EditNote.class);
            startActivityForResult(intent, 1);
        });

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(NotesActivity.this, EditNote.class);
            intent.putExtra("title", notesTitles.get(position));
            startActivityForResult(intent, 1);
        });

        notesListView.setOnItemLongClickListener((parent, view, position, id) -> {
            showNoteOptionsDialog(position);
            return true;
        });
    }

    private void loadNotes() {
        notesTitles.clear();
        Map<String, ?> allNotes = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            String title = entry.getKey();
            notesTitles.add(title);
        }
    }

    private void showNoteOptionsDialog(int position) {
        String noteTitle = notesTitles.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options pour la note")
                .setItems(new String[]{"Éditer", "Supprimer"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            editNote(position);
                            break;
                        case 1:
                            deleteNote(position);
                            break;
                    }
                })
                .show();
    }

    private void editNote(int position) {
        Intent intent = new Intent(NotesActivity.this, EditNote.class);
        intent.putExtra("title", notesTitles.get(position));
        startActivityForResult(intent, 1);
    }

    private void deleteNote(int position) {
        String noteTitle = notesTitles.get(position);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(noteTitle);
        editor.apply();

        notesTitles.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            loadNotes();
            adapter.notifyDataSetChanged();
        }
    }
}
