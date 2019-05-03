package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Word;

public class MyHelper extends SQLiteOpenHelper {
    private static final String databaseName = "DictionaryDB";
    private static final int dbVersion = 1;

    public MyHelper(Context context) {
        super(context, databaseName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE WORDS" +
                "(" +
                "WordID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Word TEXT," +
                        "Meaning TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean InsertData(String word, String meaning, SQLiteDatabase db){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("Word", word);
            contentValues.put("Meaning", meaning);

            db.insert("WORDS",null,contentValues);
            return true;
        }
        catch (Exception e){
            Log.d("Error: ", e.toString());
            return false;
        }
    }

    public List<Word> GetAllWords(SQLiteDatabase db){
        List<Word> dictionaryList = new ArrayList<>();
        String[] columns = {"WordID","Word","Meaning"};
        Cursor cursor = db.query("WORDS",columns,null,null,null,null,null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                dictionaryList.add(new Word(cursor.getInt(0),cursor.getString(1), cursor.getString(2)));
            }
        }
        return dictionaryList;
    }

    public List<Word> GetWordByName(String word, SQLiteDatabase db){
        List<Word> dictionaryList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from WORDS where Word=?", new String[]{word});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                dictionaryList.add(new Word(cursor.getInt(0),cursor.getString(1), cursor.getString(2)));
            }
        }
        return dictionaryList;
    }

}
