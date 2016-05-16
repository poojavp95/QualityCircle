package com.example.pooja.qualitycircle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pooja on 7/5/16.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] result;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context con,String[] list)
    {
        context=con;
        result=list;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class Holder
    {
        TextView id;
        TextView pl;
        TextView proj_title;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view=inflater.inflate(R.layout.custom_list_view,null);
        holder.id=(TextView) view.findViewById(R.id.project_id);
        holder.pl=(TextView) view.findViewById(R.id.project_leader);
        holder.proj_title=(TextView)view.findViewById(R.id.proeject_title);
        String s[]=result[position].split(";");

        holder.id.setText(s[0]);
        holder.pl.setText(s[1]);
        holder.proj_title.setText(s[2]);
        return view;
    }
}
