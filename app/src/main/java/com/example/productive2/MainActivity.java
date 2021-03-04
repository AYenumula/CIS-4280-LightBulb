package com.example.productive2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    List<NoteData> notes;
    TextView noItemText;
    Database database;
    ImageView imageView;
    Spinner spinner;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);
        database = new Database(this);
        List<NoteData> getNotes = database.getNotes();
        recyclerView = findViewById(R.id.contentslist);
        imageView = findViewById(R.id.imageView);


        if(getNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getNotes);
        }

    }

    private void displayList(List<NoteData> getNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new Adapter(mContext,getNotes);
        recyclerView.setAdapter(adapter);
    }

    //make the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //register option menu with menu inflator

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addnote) {
            //start addNote activity when add button clicked.
            Intent intent = new Intent(this, AddNote.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            Toast.makeText(this, "Add Button clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<NoteData> getNotes = database.getNotes();
        if(getNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getNotes);
        }


    }

}
