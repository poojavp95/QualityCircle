package com.example.pooja.qualitycircle;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectRegistration extends Fragment {

    private static final String REGISTER_URL = "http://lntebgqc.pe.hu/projectRegister.php";

    private EditText editProject_title;
    private EditText editLeader_name;
    private EditText editLeader_contact;
    private EditText editProject_members;
    private EditText editdate_id;
    private EditText editdate_comp;
    private EditText editExp_benefits;
    private ImageButton membersAdd;
    private Spinner spinProduct_Group;
    private ImageButton date_id;
    private ImageButton date_comp;
    private Button register;

    public ProjectRegistration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_project_registration, container, false);
        editProject_title=(EditText) view.findViewById(R.id.editProject_title);
        editLeader_name=(EditText) view.findViewById(R.id.editLeader_name);
        editLeader_contact=(EditText) view.findViewById(R.id.editLeader_contact);
        editProject_members=(EditText) view.findViewById(R.id.editProjectMembers);
        editdate_id=(EditText) view.findViewById(R.id.editdate_id);
        editdate_comp=(EditText) view.findViewById(R.id.editdate_comp);
        editExp_benefits=(EditText) view.findViewById(R.id.editExp_benefits);
        spinProduct_Group=(Spinner) view.findViewById(R.id.spinProuct_group);
        String a[]={"MK1 Assembly","MK1 Manufacturing","SDF Assembly","SDF Manufacturing","Starter Controller","MK1 DOL Assembly","VCB Assembly","CG Manufacturing","CG Assembly"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,a);
        spinProduct_Group.setAdapter(arrayAdapter);
        spinProduct_Group.setDropDownHorizontalOffset(40);
        membersAdd=(ImageButton)view.findViewById(R.id.buttonmembersAdd);
        membersAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Title");
                alert.setMessage("Message");

// Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        editProject_members.append("\n");
                        editProject_members.append((input.getText()));
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();

            }
        });
        date_id=(ImageButton) view.findViewById(R.id.buttondate_id);
        date_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();
            }
            class mDateSetListener implements DatePickerDialog.OnDateSetListener {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    // getCalender();
                    int mYear = year;
                    int mMonth = monthOfYear;
                    int mDay = dayOfMonth;
                    editdate_id.setText(new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mMonth + 1).append("/").append(mDay).append("/")
                            .append(mYear).append(" "));


                }
            }


        });

        date_comp=(ImageButton) view.findViewById(R.id.buttondate_comp);
        date_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();
            }
            class mDateSetListener implements DatePickerDialog.OnDateSetListener {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    // getCalender();
                    int mYear = year;
                    int mMonth = monthOfYear;
                    int mDay = dayOfMonth;
                    editdate_comp.setText(new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mDay).append("/").append(mMonth + 1).append("/")
                            .append(mYear).append(" "));


                }
            }
        });

        register=(Button) view.findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projecttitle=editProject_title.getText().toString();
                String leadername=editLeader_name.getText().toString();
                String leadercontact=editLeader_contact.getText().toString();
                String projectmembers=editProject_members.getText().toString();
                String productgroup=spinProduct_Group.getSelectedItem().toString();
                String dateid=editdate_id.getText().toString();
                String datecomp=editdate_comp.getText().toString();
                String expben=editExp_benefits.getText().toString();
                RegisterProject(projecttitle,leadername,leadercontact,projectmembers,productgroup,dateid,datecomp,expben);
            }
        });
        return view;
    }

    public void RegisterProject(String projecttitle,String leadername,String leadercontact,String projectmembers, final String productgroup,String dateid,String datecomp,String expben)
    {
        class Register extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            ConnectionClass ruc = new ConnectionClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.d("string",s);
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

                /*Intent intent = new Intent(Register.this,Topics.class);
                intent.putExtra("user",username);
                startActivity(intent);*/
            }

            @Override
            protected String doInBackground(String... params) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("username", "nouser");
                HashMap<String, String> data = new HashMap<>();
                data.put("project_title",params[0]);
                data.put("leader_name",params[1]);
                data.put("leader_contact",params[2]);
                data.put("leader_email","a");
                data.put("project_members",params[3]);
                data.put("product_group",params[4]);
                data.put("identification_date",params[5]);
                data.put("exp_completion_date",params[6]);
                data.put("expected_benefits",params[7]);
                data.put("send_for_approval","a");
                data.put("username", user);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        Register ru = new Register();
        ru.execute(projecttitle,leadername,leadercontact,projectmembers,productgroup,dateid,datecomp,expben);
        }
    }


