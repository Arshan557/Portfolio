package com.portfolio.arshan.portfolio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Tools extends Fragment {
    String[] skillTitle;
    GridView skillList;
    int[] images = {R.drawable.androidstudio, R.drawable.eclipse, R.drawable.soapui,
            R.drawable.dreamweaver, R.drawable.xampp, R.drawable.sqldeveloper, R.drawable.phonegap};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tools, container, false);
        skillTitle = getResources().getStringArray(R.array.toolTitles);
        skillList = (GridView) view.findViewById(R.id.toolsList);
        skillList.setAdapter(new MyToolAdapter(getActivity(), skillTitle, images));
        return view;
    }
}

class MyToolAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] titleArray;

    public MyToolAdapter(Context context, String[] titles, int imgs[]) {
        super(context, R.layout.skills_row,R.id.skillTitle,titles);
        this.context = context;
        this.images = imgs;
        this.titleArray = titles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyToolsViewHolder holder = null;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.skills_row, parent, false);
            holder = new MyToolsViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MyToolsViewHolder) row.getTag();
        }
        holder.myImage.setImageResource(images[position]);
        holder.myTitles.setText(titleArray[position]);
        return row;
    }
}

class MyToolsViewHolder {
    ImageView myImage;
    TextView myTitles;

    public MyToolsViewHolder(View row){
        myImage = (ImageView) row.findViewById(R.id.skillImage);
        myTitles = (TextView) row.findViewById(R.id.skillTitle);
    }

}
