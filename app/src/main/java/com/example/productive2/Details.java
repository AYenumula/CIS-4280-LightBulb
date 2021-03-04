package com.example.productive2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    TextView mDetails;
    Database db;
    NoteData note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDetails = findViewById(R.id.noteDetails);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("ID",0);

        db = new Database(this);
        note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        mDetails.setText(note.getContent());
        mDetails.setMovementMethod(new ScrollingMovementMethod());

        Toast.makeText(this, "Title = " + note.getTitle(), Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(note.getID());
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editNote) {
            //send user to the edit note activity

            Toast.makeText(this, "Edit Note", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Edit.class);
            intent.putExtra("ID", note.getID());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
