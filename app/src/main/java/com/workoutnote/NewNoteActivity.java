package com.workoutnote;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arilsonjunior.workoutnotes.R;

import java.util.ArrayList;
import java.util.Calendar;

public class NewNoteActivity extends AppCompatActivity {

    int mYear, mMonth, mDay;
    EditText editTextDate, editTextNotes;
    Spinner sp_psr, sp_pse, sp_tipo_treino;
    Button bt_insert_notes;
    private DataBaseNotes dataBaseNotes;
    private AlertDialog.Builder builder;
    public static boolean noteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bt_insert_notes = (Button) findViewById(R.id.bt_insert_notes);
        editTextDate = (EditText) findViewById(R.id.edt_date);
        editTextNotes = (EditText) findViewById(R.id.edt_notes);
        dataBaseNotes = new DataBaseNotes(this);
        builder = new AlertDialog.Builder(NewNoteActivity.this);
        builder.setPositiveButton("OK", null);
        builder.setTitle("Erro");


        ArrayList<String> spinnerValuesPSR = new ArrayList<>();
        ArrayList<String> spinnerValuesPSE = new ArrayList<>();
        ArrayList<String> spinnerTipoTreino = new ArrayList<>();

        spinnerValuesPSR.add("0 - Nenhuma recuperação");
        spinnerValuesPSR.add("1 - Muito pouca recuperação");
        spinnerValuesPSR.add("2 - Pouca recuperação");
        spinnerValuesPSR.add("3 - Recuperação moderada");
        spinnerValuesPSR.add("4 - Boa recuperação");
        spinnerValuesPSR.add("5 - Muito boa recuperação");
        spinnerValuesPSR.add("6 - ");
        spinnerValuesPSR.add("7 - Muito, muito boa recuperação");
        spinnerValuesPSR.add("8 - ");
        spinnerValuesPSR.add("9 - ");
        spinnerValuesPSR.add("10 - Totalmente recuperado");

        spinnerValuesPSE.add("0 - Nenhum esforço");
        spinnerValuesPSE.add("1 - Muito fraco");
        spinnerValuesPSE.add("2 - Fraco");
        spinnerValuesPSE.add("3 - Moderado");
        spinnerValuesPSE.add("4 - Um pouco forte");
        spinnerValuesPSE.add("5 - Forte");
        spinnerValuesPSE.add("6 - ");
        spinnerValuesPSE.add("7 - Muito forte");
        spinnerValuesPSE.add("8 - ");
        spinnerValuesPSE.add("9 - ");
        spinnerValuesPSE.add("10 - Esforço máximo");

        spinnerTipoTreino.add("Rua");
        spinnerTipoTreino.add("Pista");
        spinnerTipoTreino.add("Rampa");
        spinnerTipoTreino.add("Circuito");
        spinnerTipoTreino.add("Fortalecimento");
        spinnerTipoTreino.add("Prova");


        ArrayAdapter<String> arrayAdapterPSR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValuesPSR);
        ArrayAdapter<String> arrayAdapterPSE = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValuesPSE);
        ArrayAdapter<String> arrayAdapterTipoTreino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerTipoTreino);

        sp_psr = (Spinner) findViewById(R.id.sp_psr);
        sp_pse = (Spinner) findViewById(R.id.sp_pse);
        sp_tipo_treino = (Spinner) findViewById(R.id.sp_tipo_treino);
        sp_psr.setAdapter(arrayAdapterPSR);
        sp_pse.setAdapter(arrayAdapterPSE);
        sp_tipo_treino.setAdapter(arrayAdapterTipoTreino);

        if (!ListaNotesActivity.codeItemForEdit.equals("")) {
            editTextDate.setText(DataBaseNotes.data);
            int tipotreinoposition = 0;
            int psr = 0;
            int pse = 0;
            switch (DataBaseNotes.tipotreino) {
                case "Rua":
                    tipotreinoposition = 0;
                    break;
                case "Pista":
                    tipotreinoposition = 1;
                    break;
                case "Rampa":
                    tipotreinoposition = 2;
                    break;
                case "Circuito":
                    tipotreinoposition = 3;
                    break;
                case "Fortalecimento":
                    tipotreinoposition = 4;
                    break;
            }
            switch (DataBaseNotes.psr) {
                case "0 - Nenhuma recuperação":
                    psr = 0;
                    break;
                case "1 - Muito pouca recuperação":
                    psr = 1;
                    break;
                case "2 - Pouca recuperação":
                    psr = 2;
                    break;
                case "3 - Recuperação moderada":
                    psr = 3;
                    break;
                case "4 - Boa recuperação":
                    psr = 4;
                    break;
                case "5 - Muito boa recuperação":
                    psr = 5;
                    break;
                case "6 - ":
                    psr = 6;
                    break;
                case "7 - Muito, muito boa recuperação":
                    psr = 7;
                    break;
                case "8 - ":
                    psr = 8;
                    break;
                case "9 - ":
                    psr = 9;
                    break;
                case "10 - Totalmente recuperado":
                    psr = 10;
                    break;
            }
            switch (DataBaseNotes.pse) {
                case "0 - Nenhum esforço":
                    pse = 0;
                    break;
                case "1 - Muito fraco":
                    pse = 1;
                    break;
                case "2 - Fraco":
                    pse = 2;
                    break;
                case "3 - Moderado":
                    pse = 3;
                    break;
                case "4 - Um pouco forte":
                    pse = 4;
                    break;
                case "5 - Forte":
                    pse = 5;
                    break;
                case "6 - ":
                    pse = 6;
                    break;
                case "7 - Muito forte":
                    pse = 7;
                    break;
                case "8 - ":
                    pse = 8;
                    break;
                case "9 - ":
                    pse = 9;
                    break;
                case "10 - Esforço máximo":
                    pse = 10;
                    break;
            }
            sp_tipo_treino.setSelection(tipotreinoposition);
            sp_psr.setSelection(psr);
            sp_pse.setSelection(pse);
            editTextNotes.setText(DataBaseNotes.note);
        }

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(NewNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        editTextDate.setText(String.valueOf(datepicker.getDayOfMonth()) + "/" + String.valueOf(datepicker.getMonth() + 1) + "/" + String.valueOf(datepicker.getYear()));
                        mYear = datepicker.getYear();
                        mMonth = datepicker.getMonth();
                        mDay = datepicker.getDayOfMonth();

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });


        editTextNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewNoteActivity.this);
                final EditText editText = new EditText(NewNoteActivity.this);
                builder.setView(editText);
                editText.setBackground(null);
                editText.setHeight(200);
                editText.setTextColor(Color.BLACK);
                editText.setText(editTextNotes.getText().toString());
                builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editTextNotes.setText(editText.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.setCancelable(false);
                editText.setHorizontallyScrolling(true);
                editText.setSingleLine(false);
                editText.setFocusable(true);
                editText.setSelection(0);
                editText.setCursorVisible(true);
                builder.show();
            }
        });

        bt_insert_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewNoteActivity.noteMode) {
                    try {
                        DataBaseNotes baseNotes = new DataBaseNotes(NewNoteActivity.this);
                        baseNotes.updateData(ListaNotesActivity.codeItemForEdit, editTextDate.getText().toString(), sp_tipo_treino.getSelectedItem().toString(), sp_psr.getSelectedItem().toString(), sp_pse.getSelectedItem().toString(), editTextNotes.getText().toString());
                        NewNoteActivity.noteMode = false;
                        ListaNotesActivity.codeItemForEdit = "";
                        Toast.makeText(NewNoteActivity.this, "Anotação editada com sucesso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        builder.setMessage(e.toString());
                        builder.show();
                    }
                } else {
                    try {
                        Notes notes = new Notes();
                        notes.setData(editTextDate.getText().toString());
                        notes.setTipoTreino(sp_tipo_treino.getSelectedItem().toString());
                        notes.setPsr(sp_psr.getSelectedItem().toString());
                        notes.setPse(sp_pse.getSelectedItem().toString());
                        notes.setNotes(editTextNotes.getText().toString());
                        dataBaseNotes.insertNotes(notes);
                        Toast.makeText(NewNoteActivity.this, "Anotações salvas com sucesso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        builder.setMessage(e.toString());
                        builder.show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ListaNotesActivity.codeItemForEdit = "";
        NewNoteActivity.noteMode = false;
        if (android.os.Build.VERSION.SDK_INT < 5 /* ECLAIR */
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
