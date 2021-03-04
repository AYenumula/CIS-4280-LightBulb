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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle,noteDetails;
    Calendar calender;
    String currentDate, currentTime;
    Button color;
    int DefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

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
            //after inserting data, will return ID from table.
            //getting content to put in the database(2).
            NoteData note = new NoteData(noteTitle.getText().toString(),noteDetails.getText().toString(),currentDate,currentTime);
            //call database
            Database db = new Database(this);
            //note data is passed through the add note function.
            db.addNote(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
