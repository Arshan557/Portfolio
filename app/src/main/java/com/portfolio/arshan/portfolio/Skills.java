package com.portfolio.arshan.portfolio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Skills extends Fragment {
    String[] skillTitle;
    GridView skillList;
    int[] images = {R.drawable.android, R.drawable.webservices, R.drawable.java,
            R.drawable.spring, R.drawable.webdevelopment, R.drawable.spring,
            R.drawable.php, R.drawable.javascript, R.drawable.html,
            R.drawable.c, R.drawable.mysql};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.skills, container, false);
        skillTitle = getResources().getStringArray(R.array.skillTitles);
        skillList = (GridView) view.findViewById(R.id.skillList);
        skillList.setAdapter(new MySkillAdapter(getActivity(), skillTitle, images));
        return view;
    }
}

class MySkillAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] titleArray;

    public MySkillAdapter(Context context, String[] titles, int[] imgs) {
        super(context, R.layout.skills_row,R.id.skillTitle,titles);
        this.context = context;
        this.images = imgs;
        this.titleArray = titles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MySkillsViewHolder holder = null;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.skills_row, parent, false);
            holder = new MySkillsViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MySkillsViewHolder) row.getTag();
        }
        /*String strPath = images.get(position).toString();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bm = BitmapFactory.decodeFile(strPath,options);
        holder.myImage.setImageBitmap(bm);
        if(bm!=null)
        {
         bm.recycle();
         bm=null;
        }*/
        //holder.myImage.setImageResource(images[position]);
        holder.myTitles.setText(titleArray[position]);
        return row;
    }
}

class MySkillsViewHolder {
    ImageView myImage;
    TextView myTitles;

    public MySkillsViewHolder(View row){
        myImage = (ImageView) row.findViewById(R.id.skillImage);
        myTitles = (TextView) row.findViewById(R.id.skillTitle);
    }

}
