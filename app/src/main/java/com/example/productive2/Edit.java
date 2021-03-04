package com.example.productive2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle,noteDetails;
    Calendar calender;
    String currentDate, currentTime;
    Database db;
    NoteData note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //receive data from details.java class
        Intent intent = getIntent();
        Long id = intent.getLongExtra("ID",0);
        db = new Database(this);
        note = db.getNote(id);



        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(note.getTitle());


        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        noteTitle.setText(note.getTitle());
        noteDetails.setText(note.getContent());

        // when someone types in note title, title should appear in the toolbar
        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //add current date and time of note
        //+1 is needed bc value starts at zero. With +1 August = 8, w/o August = 7.
        calender = Calendar.getInstance();
        currentDate = (calender.get(Calendar.MONTH)+1) + "/" + calender.get(Calendar.DAY_OF_MONTH) + "/"
                + calender.get(Calendar.YEAR);

        currentTime = pad(calender.get(Calendar.HOUR)) + ":" + pad(calender.get(Calendar.MINUTE));

        Log.d("calendar","Date and Time: " + currentDate + " and " + currentTime);
    }
    // put zero infront of number less than 10 for the time.
    private String pad(int i) {
        if(i < 10)
            return "0"+i;
        else
            return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        if(item.getItemId() == R.id.save) {
           note.setTitle(noteTitle.getText().toString());
           note.setContent(noteDetails.getText().toString());
            int id = db.editNote(note);
            if(id == note.getID()){
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error Updating Note", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(getApplicationContext(),Details.class);
            intent.putExtra("ID",note.getID());
            startActivity(intent);
        }else{
            noteTitle.setError("Note can't be blank.");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
