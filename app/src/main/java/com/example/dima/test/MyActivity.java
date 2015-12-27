package com.example.dima.test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


//this is test
public class MyActivity extends AppCompatActivity {

    static final String userText = "userText";
    private SQLiteDatabase db;
    ExampleDBHelper exampleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        exampleDB = new ExampleDBHelper(getApplicationContext());
        db = exampleDB.getWritableDatabase();
        String[] projection = {
                exampleDB.COLUMN_NAME_TEXT_ENTERED
        };
        String[] args = {Integer.toString(findViewById(R.id.edit_message).getId())};
        Cursor cursor = db.query(
                exampleDB.dbTableName,
                projection,
                exampleDB._ID+" = ?",
                args,null,null,null,null
        );

        cursor.moveToFirst();
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(cursor.getString(0));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        savedInstance.putString(userText, editText.getText().toString());
        super.onSaveInstanceState(savedInstance);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(savedInstanceState.getString(userText));
    }

    @Override
    protected void onStop() {
        super.onStop();
        ContentValues contentValues = new ContentValues();
        EditText editText = (EditText) findViewById(R.id.edit_message);
        contentValues.put(exampleDB._ID,findViewById(R.id.edit_message).getId());
        contentValues.put(exampleDB.COLUMN_NAME_TEXT_ENTERED, editText.getText().toString());
        db.insert(exampleDB.dbTableName, null, contentValues);
        String[] args = {Integer.toString(findViewById(R.id.edit_message).getId())};
        db.update(exampleDB.dbTableName,contentValues,exampleDB._ID + " = ?",args);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContentValues contentValues = new ContentValues();
        EditText editText = (EditText) findViewById(R.id.edit_message);
        contentValues.put(userText, editText.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText text = (EditText) findViewById(R.id.edit_message);
        String message = text.getText().toString();
        intent.putExtra("EXTRA_MESSAGE",message);
        startActivity(intent);
    }
}
