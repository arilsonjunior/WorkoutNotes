package com.workoutnote;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arilsonjunior.workoutnotes.R;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ListaNotesActivity extends AppCompatActivity {

    private ListView lv1;
    private static ArrayList<String> arrayNotesCode = new ArrayList<>();
    public static String codeItemForEdit = "";
    private AlertDialog.Builder builder;
    private static String nameFile;
    private final File directoryFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private static String directorySFile;
    private boolean fileExported;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss", Locale.ENGLISH);
    DataBaseNotes baseNotes = new DataBaseNotes(ListaNotesActivity.this);
    public static ArrayList<Items> itemsArrayList;
    private int contPress = 0;
    private boolean contPressV;

    public static String getNameFile() {
        return nameFile;
    }

    public static void setNameFile(String nameFile) {
        ListaNotesActivity.nameFile = nameFile;
    }

    public static String getDirectorySFile() {
        return directorySFile;
    }

    public static void setDirectorySFile(String directorySFile) {
        ListaNotesActivity.directorySFile = directorySFile;
    }

    public static ArrayList<String> getArrayNotesCode() {
        return arrayNotesCode;
    }

    public static void setArrayNotesCode(ArrayList<String> arrayNotesCode) {
        ListaNotesActivity.arrayNotesCode = arrayNotesCode;
    }

    public File getDirectoryFile() {
        return directoryFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notes);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        builder = new AlertDialog.Builder(ListaNotesActivity.this);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        fileExported = false;
        lv1 = (ListView) findViewById(R.id.listaNotes);
        ArrayList image_details = getListData();
        itemsArrayList = getItemsArrayList();
        lv1 = (ListView) findViewById(R.id.listaNotes);
        lv1.setAdapter(new CustomListAdapter(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if (!contPressV) {
                    if (contPress == 3) {
                        Toast.makeText(ListaNotesActivity.this, "Para visualizar ou editar, mantenha o item pressionado.", Toast.LENGTH_LONG).show();
                        contPressV = true;
                    } else {
                        contPress++;
                    }
                }
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox_item);
                checkBox.setChecked(!checkBox.isChecked());
                if (checkBox.isChecked()) {
                    String s = String.valueOf(ListaNotes.getArrayListNotesCode().get(position));
                    getArrayNotesCode().add(s);
                    itemsArrayList.get(position).setCheckStatus(true);
                } else {
                    getArrayNotesCode().remove(ListaNotes.getArrayListNotesCode().get(position));
                    itemsArrayList.get(position).setCheckStatus(false);
                }
            }
        });
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                codeItemForEdit = String.valueOf(ListaNotes.getArrayListNotesCode().get(position));
                baseNotes = new DataBaseNotes(ListaNotesActivity.this);
                baseNotes.selectNoteForEdition(codeItemForEdit);
                NewNoteActivity.noteMode = true;
                Intent intent = new Intent(ListaNotesActivity.this, NewNoteActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }

    private ArrayList getListData() {
        ArrayList<Notes> results = new ArrayList<>();
        for (int i = 0; i < ListaNotes.getArrayListNotesData().size(); i++) {
            Notes newsData = new Notes();
            newsData.setTipoTreino(ListaNotes.getArrayListNotesTipoTreino().get(i));
            newsData.setData(ListaNotes.getArrayListNotesData().get(i));
            newsData.setPse(ListaNotes.getArrayListNotesPSE().get(i));
            results.add(newsData);
        }
        // Add some more dummy data for testing
        return results;
    }

    private ArrayList<Items> getItemsArrayList() {
        ArrayList<Items> itemsArrayList = new ArrayList<>();
        for (int i = 0; i < ListaNotes.getArrayListNotesData().size(); i++) {
            Items items = new Items();
            items.setPosition(i);
            items.setCheckStatus(false);
            itemsArrayList.add(items);
        }
        // Add some more dummy data for testing
        return itemsArrayList;
    }


    public void compartilharNotes(MenuItem menuItem) {
        if (getArrayNotesCode().isEmpty()) {
            builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
            builder.setMessage(getResources().getString(R.string.mensagem_erro_share_notes));
            builder.show();
        } else {
            try {
                if (!fileExported) {
                    baseNotes = new DataBaseNotes(ListaNotesActivity.this);
                    baseNotes.selectSpecificNotes(getArrayNotesCode());
                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setMessage(getResources().getString(R.string.message_share_file_process));
                    dialog.setCancelable(false);
                    dialog.show();
                    Calendar calendar = Calendar.getInstance();
                    setNameFile(simpleDateFormat.format(calendar.getTime()));
                    final File file = new File(getDirectoryFile(), getNameFile() + ".txt");
                    setDirectorySFile(file.toString());
                    FileWriter fileWriter = new FileWriter(file, true);
                    fileWriter.append(DataBaseNotes.select_result);
                    fileWriter.flush();
                    fileWriter.close();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            builder.setTitle(getResources().getString(R.string.titulo_dialog_exportacao_dados));
                            builder.setMessage(getResources().getString(R.string.message_share_file));
                            builder.setNeutralButton(getResources().getString(R.string.menu_share), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        shareData();
                                    } catch (Exception e) {
                                        builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
                                        builder.setMessage(e.toString());
                                        builder.show();
                                    }

                                }
                            });
                            builder.show();
                            Toast.makeText(ListaNotesActivity.this, getResources().getString(R.string.mensagem_dialog_exportacao_dados_local) + getDirectorySFile(), Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);
                    fileExported = true;
                } else {
                    builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
                    builder.setMessage(getResources().getString(R.string.mensagem_dialog_erro_exportacao_dados));
                    builder.setNeutralButton(getResources().getString(R.string.menu_share), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                shareData();
                            } catch (Exception e) {
                                builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
                                builder.setMessage(e.toString());
                                builder.show();
                            }

                        }
                    });
                    builder.show();
                }
            } catch (Exception e) {
                builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
                builder.setMessage(e.toString());
                builder.show();
            }
        }
    }

    public void shareData() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/text");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + getDirectorySFile()));
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "treino-" + getNameFile());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.text_compartilhar_dados)));
    }

    public void delNote(MenuItem menuItem) {
        if (getArrayNotesCode().isEmpty()) {
            builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
            builder.setMessage(getResources().getString(R.string.mensagem_alertdialog_lista_vazia));
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListaNotesActivity.this);
            builder.setTitle(getResources().getString(R.string.titulo_alertdialog_erro));
            builder.setMessage(getResources().getString(R.string.titulo_alertdialog_del_notes));
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    baseNotes = new DataBaseNotes(ListaNotesActivity.this);
                    baseNotes.deleteNotes(getArrayNotesCode());
                    getArrayNotesCode().clear();
                    refreshNotes();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            builder.show();
        }
    }

    public void refreshNotes(MenuItem menuItem) {
        refreshNotes();
    }

    public void refreshNotes() {
        ListaNotes.clearList();
        baseNotes.selectNotes();
        ArrayList image_details = getListData();
        lv1.setAdapter(new CustomListAdapter(this, image_details));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_notes, menu);
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
