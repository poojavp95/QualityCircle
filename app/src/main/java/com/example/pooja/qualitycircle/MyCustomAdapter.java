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
public class MyCustomAdapter extends BaseAdapter {
    Context context;
    String[] result;
    private static LayoutInflater inflater=null;
    public MyCustomAdapter(Context con,String[] list)
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
        TextView project_status;
        TextView proj_title;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view=inflater.inflate(R.layout.custom_mylist_view,null);
        holder.id=(TextView) view.findViewById(R.id.project_id);
        holder.proj_title=(TextView) view.findViewById(R.id.project_title);
        holder.project_status=(TextView)view.findViewById(R.id.project_status);
        String s[]=result[position].split(";");

        holder.id.setText(s[1]);
        holder.project_status.setText(s[3]);
        holder.proj_title.setText(s[2]);
        return view;
    }
}
