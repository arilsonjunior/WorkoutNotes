package com.example.arilsonjunior.workoutnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ArilsonJr on 7/3/16.
 */
public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Notes> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<Notes> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_lista_notes, null);
            holder = new ViewHolder();
            holder.checkBoxItems = (CheckBox) convertView.findViewById(R.id.checkbox_item);
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.reporter);
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.headlineView.setText(listData.get(position).getTipoTreino());
        holder.reporterNameView.setText("Treino realizado em: " + listData.get(position).getData());
        holder.reportedDateView.setText("PSE: " + listData.get(position).getPse());
        return convertView;
    }

    static class ViewHolder {
        CheckBox checkBoxItems;
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
    }

}
