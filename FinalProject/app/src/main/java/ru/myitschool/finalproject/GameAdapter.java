package ru.myitschool.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<GamesBD> objects = new ArrayList<>();
    public GameAdapter(Context context){
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

        GamesBD g = getGamesBD(position);

        ((TextView) view.findViewById(R.id.empty)).setText(g.getGame_name());

        return view;
    }


    public GamesBD getGamesBD(int position) {
        return ((GamesBD) getItem(position));
    }

    public void refresh(List<GamesBD> list){
        if(list != null){
            objects.clear();
            objects.addAll(list);
            notifyDataSetChanged();
        }
    }

}
