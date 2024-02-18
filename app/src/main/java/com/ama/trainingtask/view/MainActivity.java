package com.ama.trainingtask.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ama.trainingtask.Adapter.NoteAdapter;
import com.ama.trainingtask.R;
import com.ama.trainingtask.model.NoteModel;
import com.ama.trainingtask.view_model.AppUtil;
import com.ama.trainingtask.view_model.NoteLocalDatabase;
import com.ama.trainingtask.view_model.UserManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivityTestHere";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private NoteAdapter mNoteAdapter;
    private ArrayList<NoteModel> noteArrayList;
    private ImageView logoutImage;

    // Below edittext and button are all exist in the popup dialog view.
    private View popupInputDialogView = null;
    private TextView saveUserDataButton = null;
    private TextView cancelUserDataButton = null;
    private EditText newTitleNote = null;
    private EditText newContentNote = null;

    private NoteLocalDatabase noteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        logoutImage = findViewById(R.id.logoutImg);
        recyclerView = findViewById(R.id.recyclerViewNote);
        noteDB = new NoteLocalDatabase(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a AlertDialog Builder.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                initializePop();

                // Set the inflated layout view object to the AlertDialog builder.
                alertDialogBuilder.setView(popupInputDialogView);
                // Create AlertDialog and show.
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                //When user click the save user data button in the popup dialog.
                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "OnCLickSave");
                        addNoteToDB();
                        alertDialog.cancel();
                    }
                });
                cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "OnClickCancel");
                        Toast.makeText(MainActivity.this, "Button Cancel", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                    }
                });
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteArrayList = new ArrayList<>();
    }

    private void initializePop(){
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.popout_input_dialog, null);

        // Get user input edittext and button ui controls in the popup dialog.
        TextView titleMessageDialogue = popupInputDialogView.findViewById(R.id.box_title);
        titleMessageDialogue.setText("Add New Note");
        newTitleNote = popupInputDialogView.findViewById(R.id.editTxtNewTitleInput);
        newContentNote = popupInputDialogView.findViewById(R.id.editTxtNewContentInput);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.txtSavePopOut);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.txtCancelPopOut);
    }

    private void addNoteToDB(){
        String noteTitle = newTitleNote.getText().toString();
        String noteContent = newContentNote.getText().toString();
        Log.d(TAG, "Note Title: " + noteTitle);
        Log.d(TAG, "Note Content: " + noteContent);
        if(!noteTitle.isEmpty() && !noteContent.isEmpty()){
            boolean addNote = noteDB.addData(AppUtil.getRandomString(6), UserManager.getInstance(MainActivity.this).getUser().getUserId(),
                    noteTitle, noteContent);
            Log.d(TAG, "Result add: " + addNote);
            if(addNote){
                loadRecyclerView();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please fill all the information.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRecyclerView(){
        noteArrayList.clear();
        //get the data and append to a list
        Cursor data = noteDB.getAllDataBasedUser(UserManager.getInstance(this).getUser().getUserId());

        Log.d(TAG, "Data: " + data);
        if(data.moveToFirst()){
            do{
                //get the value from the database in column 1
                //then add it to the ArrayList
                String noteId = data.getString(1);
                String userId = data.getString(2);
                String noteTitle = data.getString(3);
                String noteContent = data.getString(4);

                Log.d(TAG, "NoteId: " + noteId);
                Log.d(TAG, "userId: " + userId);
                Log.d(TAG, "noteTitle: " + noteTitle);
                Log.d(TAG, "noteContent: " + noteContent);
                noteArrayList.add(new NoteModel(noteId, userId, noteTitle, noteContent));
            } while(data.moveToNext());
        } else {
            Toast.makeText(getApplicationContext(), "No note store for this user.", Toast.LENGTH_SHORT).show();
        }

        mNoteAdapter = new NoteAdapter(this, noteArrayList);
        recyclerView.setAdapter(mNoteAdapter) ;
        mNoteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onItemClickCardView(position);
            }
        });
    }

    private void onItemClickCardView(int position) {
        NoteModel clickedItem = noteArrayList.get(position);

        Intent detailIntent = new Intent(this, NoteActivity.class);
        detailIntent.putExtra("CurrentNoteBundle", (Serializable) clickedItem);
        startActivity(detailIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadRecyclerView();
    }
}