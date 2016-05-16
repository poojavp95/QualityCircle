package com.example.pooja.qualitycircle;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayAll extends Fragment {
    private static final String DISP_URL="http://lntebgqc.pe.hu/dispAll.php";
    private ListView listView;
    public DisplayAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_display_all, container, false);
        listView=(ListView)view.findViewById(R.id.displist_all);

        dispAll();
        return view;
    }
    public void dispAll()
    {
        class DispAll extends AsyncTask<String,Void,String>
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
                final String [] displist;
                displist=s.split("::");
                listView.setAdapter(new CustomAdapter(getActivity(),displist));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment fragment = null;
                        fragment = new DetailAll();
                        Bundle bundle=new Bundle();
                        bundle.putString("value",displist[position]);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction().addToBackStack(null)
                                .replace(R.id.content_frame, fragment).commit();

                    }
                });

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> d = new HashMap<>();
                ConnectionClass ruc = new ConnectionClass();
                String result = ruc.sendPostRequest(DISP_URL,d);
                return result;
            }
        }
        DispAll d=new DispAll();
        d.execute("");
    }


}
