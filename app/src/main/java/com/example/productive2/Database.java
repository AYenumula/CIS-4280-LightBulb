package com.example.productive2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    //creating database
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "PROdb";
    private static final String DATABASE_TABLE = "dbtable";

    //column names for database
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";



    //context to bring data from different classes
    Database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }


    //instance of database is created in any class of the application
    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE tname = (id INT PRIMARY KEY, title TEXT, content TEXT, time TEXT, date TEXT);
        String query = "CREATE TABLE "+DATABASE_TABLE+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT,"+
                KEY_TIME+" TEXT" +" )";

            db.execSQL(query);
    }

    @Override
    //update database version if new version is available.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

    }
    // send from addNote activity to the database.
    public long addNote(NoteData note){
        SQLiteDatabase db = this.getWritableDatabase();
        //put data in value and set titles, details from the NoteData class when saved.
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,note.getTitle());
        contentValues.put(KEY_CONTENT,note.getContent());
        contentValues.put(KEY_DATE,note.getDate());
        contentValues.put(KEY_TIME,note.getTime());

        //after data inserted, database will create ID.
        long ID = db.insert(DATABASE_TABLE,null,contentValues);
        Log.d("Inserted","ID = " + ID);
        return ID;

    }
    // get one note.
    //take id as an argument
    public NoteData getNote(long id){
        //instance of database
        SQLiteDatabase db = this.getReadableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        //cursor points to the specific row in database.
        Cursor cursor=  db.query(DATABASE_TABLE,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);

        //execute query
        if(cursor != null)
            //cursor always starts at -1, so move to first position.
            cursor.moveToFirst();

            return new NoteData(
                    Long.parseLong(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));

    }

    // get list of notes present in database to display in recycler view.
    //retrieving all data from column.
    public List<NoteData> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<NoteData> allNotes = new ArrayList<>();

        //query to select all notes from database.
        String query = "SELECT * FROM " + DATABASE_TABLE +" ORDER BY "+KEY_ID+" DESC";
        //cursor points to specific database column
        Cursor cursor = db.rawQuery(query,null);


        if(cursor.moveToFirst()){
            do{
                NoteData note = new NoteData();
                note.setID(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));

                //add notes to ListView.
                allNotes.add(note);
            }while (cursor.moveToNext());
        }
        return allNotes;



    }
    //edit note function
    //getting values and putting values in c variable.
    public int editNote(NoteData note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getID());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        return db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(note.getID())});
    }
    // delete function
    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        // database query
        db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
}

