package com.example.hoanglong.rssreader;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends ArrayAdapter<News>{

    ArrayList<News> items;
    ArrayList<News> arrayList;

    public CustomAdapter(Context context, int resource, ArrayList<News> items) {
        super(context, resource, items);
        this.items = items;

        this.arrayList = new ArrayList<News>();
        this.arrayList.addAll(items);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item_listview_layout, null);
        }
        News p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtnewsname = (TextView) view.findViewById(R.id.TxtNewsName);
            txtnewsname.setText(p.newsname);

            TextView txttitle = (TextView) view.findViewById(R.id.TxtTitle);
            txttitle.setText(p.title);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(p.image).into(imageView);

            TextView txtdesc  = (TextView) view.findViewById(R.id.descTxt);
            txtdesc.setText(p.description);

            TextView txtdate = (TextView) view.findViewById(R.id.dateTxt);
            txtdate.setText(p.pubDate);

        }
        return view;
    }

    //Filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for (News it : arrayList) {
                if (it.title.toLowerCase(Locale.getDefault())
                        .contains(charText.toLowerCase())) {
                    items.add(it);
                }
            }
        }
        notifyDataSetChanged();
    }
}
