package com.example.AndroidTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 23:53
 */

public class MySimpleArrayAdapter extends ArrayAdapter<Model> {
    private final Context context;
    private final List<Model> values;

    public MySimpleArrayAdapter(Context context, List<Model> values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    public static class ViewHolder {
        public TextView t;
        public ImageView i;
    }

    public void groupContentBySelection() {
        //List<Model>(values.size())
        Collections.sort(values, new Comparator<Model>() {
            @Override
            public int compare(Model model1, Model model2) {
                if(model1.isSelected() == model2.isSelected())
                    return 0;
                else
                    return model1.isSelected() ? 1 : -1;
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;

        if (convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.rowlayout, parent, false);

            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            ViewHolder holder = new ViewHolder();
            holder.t = textView;
            holder.i = imageView;
            rowView.setTag(holder);
        }

        ViewHolder tag = (ViewHolder) rowView.getTag();
        tag.t.setText(values.get(position).getName().toUpperCase());

        // Change the icon for Windows and iPhone
        if (values.get(position).isSelected()) {
            tag.i.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            tag.i.setImageResource(android.R.drawable.btn_star_big_off);
        }

        return rowView;
    }
}