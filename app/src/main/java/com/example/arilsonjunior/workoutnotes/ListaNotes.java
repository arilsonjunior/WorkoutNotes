package com.example.arilsonjunior.workoutnotes;

import java.util.ArrayList;

/**
 * Created by ArilsonJr on 7/2/16.
 */
public class ListaNotes {

    private static ArrayList<String> arrayListNotes = new ArrayList<>();
    private static ArrayList<String> arrayListNotesCode = new ArrayList<>();
    private static ArrayList<String> arrayListNotesData = new ArrayList<>();
    private static ArrayList<String> arrayListNotesTipoTreino = new ArrayList<>();
    private static ArrayList<String> arrayListNotesPSR = new ArrayList<>();
    private static ArrayList<String> arrayListNotesPSE = new ArrayList<>();
    private static ArrayList<String> arrayListNotesNotes = new ArrayList<>();

    public static ArrayList<String> getArrayListNotes() {
        return arrayListNotes;
    }

    public static void setArrayListNotes(String data, String tipotreino, String psr, String pse, String notes) {
        ListaNotes.getArrayListNotes().add("\nData: " + data +
                "\nTipo de treino: " + tipotreino + "\nPSR: " + psr + "\nPSE: " + pse + "\nAnotações: " + notes + "\n");
    }


    public static void clearList() {
        ListaNotes.arrayListNotesCode.clear();
        ListaNotes.arrayListNotesData.clear();
        ListaNotes.arrayListNotesTipoTreino.clear();
        ListaNotes.arrayListNotesPSR.clear();
        ListaNotes.arrayListNotesPSE.clear();
        ListaNotes.arrayListNotesNotes.clear();
    }

    public static void setArrayListNotes(ArrayList<String> arrayListNotes) {
        ListaNotes.arrayListNotes = arrayListNotes;
    }


    public static ArrayList<String> getArrayListNotesData() {
        return arrayListNotesData;
    }

    public static void setArrayListNotesData(String s) {
        ListaNotes.arrayListNotesData.add(s);
    }

    public static ArrayList<String> getArrayListNotesTipoTreino() {
        return arrayListNotesTipoTreino;
    }

    public static void setArrayListNotesCode(String s) {
        ListaNotes.arrayListNotesCode.add(s);
    }

    public static ArrayList<String> getArrayListNotesCode() {
        return arrayListNotesCode;
    }

    public static void setArrayListNotesTipoTreino(String s) {
        ListaNotes.arrayListNotesTipoTreino.add(s);
    }

    public static ArrayList<String> getArrayListNotesPSR() {
        return arrayListNotesPSR;
    }

    public static void setArrayListNotesPSR(String s) {
        ListaNotes.arrayListNotesPSR.add(s);
    }

    public static ArrayList<String> getArrayListNotesPSE() {
        return arrayListNotesPSE;
    }

    public static void setArrayListNotesPSE(String s) {
        ListaNotes.arrayListNotesPSE.add(s);
    }

    public static ArrayList<String> getArrayListNotesNotes() {
        return arrayListNotesNotes;
    }

    public static void setArrayListNotesNotes(String s) {
        ListaNotes.arrayListNotesNotes.add(s);
    }
}
