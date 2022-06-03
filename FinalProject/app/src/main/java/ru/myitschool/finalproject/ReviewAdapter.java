package ru.myitschool.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<ReviewsBD> objects = new ArrayList<>();
    ReviewAdapter(Context context){
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){

        return  objects.size();
    }

    @Override
    public Object getItem(int position){
        return  objects.get(position);
    }

    @Override
    public long getItemId(int position){

        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.mylist, parent, false);
        }

        ReviewsBD r = getReviewsBD(position);

        ((TextView) view.findViewById(R.id.empty)).setText(r.getText());

        return view;
    }


    ReviewsBD getReviewsBD(int position) {
        return ((ReviewsBD) getItem(position));
    }

    public void refresh(List<ReviewsBD> list){
        if(list != null){
            objects.clear();
            objects.addAll(list);
            notifyDataSetChanged();
        }
    }
}
