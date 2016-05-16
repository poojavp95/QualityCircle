package com.example.pooja.qualitycircle;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;

import static android.graphics.Color.parseColor;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMyList extends Fragment {
    private TableLayout tableLayout;
    private static  final String DETAIL_URL="http://lntebgqc.pe.hu/detailMy.php";
    public DetailMyList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String string="";

        View view=inflater.inflate(R.layout.fragment_detail_my_list, container, false);
        tableLayout=(TableLayout) view.findViewById(R.id.tabledetailmy);
        Bundle bundle=getArguments();
        if (bundle != null) {
            string = bundle.getString("value", "NULL");
        }
        String[] s=string.split(";");
        display(s[0]);

        return view;
    }

    public void display(final String string)
    {
        class DetAll extends AsyncTask<String,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("string", s);
                loading.dismiss();
                String values[]=s.split(";");
                for(int i=0;i<values.length;i++)
                {
                    TableRow tableRow=new TableRow(getActivity());;
                    tableRow.setPadding(30,30,30,30);
                    TextView tv=new TextView(getActivity());
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(15);
                    tv.setText(values[i]);
                    if(i%2==0)
                    {
                        int color= parseColor("#E0F2F1");
                        tableRow.setBackgroundColor(color);
                    }
                    else
                    {
                        tableRow.setBackgroundColor(Color.WHITE);
                    }
                    tableRow.addView(tv);
                    tableLayout.addView(tableRow,i);
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> d = new HashMap<>();
                d.put("seq_no",string);
                ConnectionClass ruc = new ConnectionClass();
                String result = ruc.sendPostRequest(DETAIL_URL,d);
                return result;
            }
        }
        DetAll d=new DetAll();
        d.execute(string);
    }


}
