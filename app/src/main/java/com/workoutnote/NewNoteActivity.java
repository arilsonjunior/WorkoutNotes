package com.workoutnote;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


        ArrayList<String> spinnerValues = new ArrayList<>();
        ArrayList<String> spinnerTipoTreino = new ArrayList<>();

        spinnerValues.add("1");
        spinnerValues.add("2");
        spinnerValues.add("3");
        spinnerValues.add("4");
        spinnerValues.add("5");
        spinnerValues.add("6");
        spinnerValues.add("7");
        spinnerValues.add("8");
        spinnerValues.add("9");
        spinnerValues.add("10");

        spinnerTipoTreino.add("Rua");
        spinnerTipoTreino.add("Pista");
        spinnerTipoTreino.add("Rampa");
        spinnerTipoTreino.add("Circuito");
        spinnerTipoTreino.add("Fortalecimento");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues);
        ArrayAdapter<String> arrayAdapterTipoTreino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerTipoTreino);

        sp_psr = (Spinner) findViewById(R.id.sp_psr);
        sp_pse = (Spinner) findViewById(R.id.sp_pse);
        sp_tipo_treino = (Spinner) findViewById(R.id.sp_tipo_treino);
        sp_psr.setAdapter(arrayAdapter);
        sp_pse.setAdapter(arrayAdapter);
        sp_tipo_treino.setAdapter(arrayAdapterTipoTreino);

        if (!ListaNotesActivity.codeItemForEdit.equals("")) {
            editTextDate.setText(DataBaseNotes.data);
            int tipotreinoposition = 0;
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
            sp_tipo_treino.setSelection(tipotreinoposition);
            sp_psr.setSelection(Integer.parseInt(DataBaseNotes.psr) - 1);
            sp_pse.setSelection(Integer.parseInt(DataBaseNotes.pse) - 1);
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
                    DataBaseNotes baseNotes = new DataBaseNotes(NewNoteActivity.this);
                    baseNotes.updateData(ListaNotesActivity.codeItemForEdit, editTextDate.getText().toString(), sp_tipo_treino.getSelectedItem().toString(), sp_psr.getSelectedItem().toString(), sp_pse.getSelectedItem().toString(), editTextNotes.getText().toString());
                    NewNoteActivity.noteMode = false;
                    Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
                    startActivity(intent);
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

}
