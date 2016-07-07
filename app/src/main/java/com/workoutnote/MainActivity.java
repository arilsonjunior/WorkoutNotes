package com.workoutnote;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.arilsonjunior.workoutnotes.R;

public class MainActivity extends AppCompatActivity {

    private DataBaseNotes dataBaseNotes;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dataBaseNotes = new DataBaseNotes(this);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton("OK", null);
        builder.setTitle("Erro");

    }

    public void newNote(View view) {
        Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
        startActivity(intent);
    }

    public void verNotes(View view) {
        try {
            ListaNotes.clearList();
            dataBaseNotes.selectNotes();
            Intent intent = new Intent(MainActivity.this, ListaNotesActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            builder.setMessage(e.toString());
            builder.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
