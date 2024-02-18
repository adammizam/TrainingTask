package com.ama.trainingtask.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ama.trainingtask.R;
import com.ama.trainingtask.model.NoteModel;
import com.ama.trainingtask.view_model.NoteLocalDatabase;

public class NoteActivity extends AppCompatActivity {

    private Button btnEdit, btnSave, btnCancel, btnDelete;
    private TextView txtContent;
    private EditText editContent;
    private LinearLayout layoutEdit;
    private Toolbar mToolbar;

    private NoteModel noteModel;

    private NoteLocalDatabase mNoteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        noteModel = (NoteModel) intent.getSerializableExtra("CurrentNoteBundle");
        mNoteDB = new NoteLocalDatabase(this);

        //Tool bar
        mToolbar = findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(noteModel.getNoteTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        txtContent = findViewById(R.id.txtContent);
        editContent = findViewById(R.id.editTxtContent);
        layoutEdit = findViewById(R.id.layoutEditAppear);

        loadData();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtContent.setVisibility(View.INVISIBLE);
                editContent.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
                layoutEdit.setVisibility(View.VISIBLE);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoteDB.deleteNote(noteModel.getNoteId(), noteModel.getUserId());
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEditNote = editContent.getText().toString();

                if(!newEditNote.equals(noteModel.getNoteContent())&& !newEditNote.isEmpty()){
                    mNoteDB.updateNoteText(noteModel.getNoteId(), noteModel.getUserId(), newEditNote);
                    noteModel.setNoteContent(newEditNote);
                    loadData();
                } else if (newEditNote.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all the information", Toast.LENGTH_SHORT).show();
                } else {
                    loadData();
                }
            }
        });
    }

    private void loadData(){
        txtContent.setText(noteModel.getNoteContent());
        txtContent.setVisibility(View.VISIBLE);
        editContent.setText(noteModel.getNoteContent());
        editContent.setVisibility(View.INVISIBLE);
        btnEdit.setVisibility(View.VISIBLE);
        layoutEdit.setVisibility(View.INVISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}