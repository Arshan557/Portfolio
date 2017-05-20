package com.portfolio.arshan.portfolio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arshan on 15-Sep-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;
    List<Information> data = Collections.emptyList();

    public MyAdapter(Context context, List<Information> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            icon.setOnClickListener(this);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //delete(getPosition());
            //Toast.makeText(context, "Item deleted at " + getPosition(), Toast.LENGTH_SHORT).show();
            //context.startActivity(new Intent(context,Experience.class));
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
        }
}
