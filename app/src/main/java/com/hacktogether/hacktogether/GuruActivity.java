package com.hacktogether.hacktogether;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class GuruActivity extends AppCompatActivity {

    CheckBox c1; //ios
    CheckBox c2; //android
    CheckBox c3; //web
    CheckBox c4; //arduino and raspberry pi
    EditText devpost;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        this.setTitle("Gurus");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Questions/Concerns? contact@pennapps.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        c1 = (CheckBox)findViewById(R.id.checkbox1); //ios
        c2 = (CheckBox)findViewById(R.id.checkbox2); //android
        c3 = (CheckBox)findViewById(R.id.checkbox3); //web
        c4 = (CheckBox)findViewById(R.id.checkbox4); //arduino and raspberry pi
        save = (Button)findViewById(R.id.save);
        devpost = (EditText)findViewById(R.id.devpost);

        c1.setChecked(ParseUser.getCurrentUser().getBoolean("ios"));
        c2.setChecked(ParseUser.getCurrentUser().getBoolean("android"));
        c3.setChecked(ParseUser.getCurrentUser().getBoolean("web"));
        c4.setChecked(ParseUser.getCurrentUser().getBoolean("hardware"));

        try {
            ParseQuery<ParseObject> teamquery = ParseQuery.getQuery("Team");
            if(teamquery.whereEqualTo("Guru", ParseUser.getCurrentUser().getUsername()).count()>0){
                teamquery = teamquery.whereEqualTo("Guru", ParseUser.getCurrentUser().getUsername());
                ParseUser.getCurrentUser().put("assigned", true);
                ParseUser.getCurrentUser().put("P1", teamquery.get("P1"));
                ParseUser.getCurrentUser().put("P2", teamquery.get("P2"));
                ParseUser.getCurrentUser().put("P3", teamquery.get("P3"));
            }
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }

        if(ParseUser.getCurrentUser().getBoolean("assigned")){
            c1.setClickable(false);
            c2.setClickable(false);
            c3.setClickable(false);
            c4.setClickable(false);
            save.setClickable(false);
            String teams = "Your Team-members: " + ParseUser.getCurrentUser().get("P1") + ", \n" +
                    ParseUser.getCurrentUser().get("P2") + ", \n" +
                    ParseUser.getCurrentUser().get("P3");
            TextView header = (TextView) findViewById(R.id.header);
            header.setText(teams);
            devpost.setText(ParseUser.getCurrentUser().get("devpost").toString());
        }
    }
    public void onCheckboxClicked1(View view){
    }
    public void onCheckboxClicked2(View view){
    }
    public void onCheckboxClicked3(View view){
    }
    public void onCheckboxClicked4(View view){
    }
    public void onSaveClicked(View view) {
            if(!ParseUser.getCurrentUser().getBoolean("assigned")) {
                ParseUser.getCurrentUser().put("ios", c1.isChecked());
                ParseUser.getCurrentUser().put("android", c2.isChecked());
                ParseUser.getCurrentUser().put("web", c3.isChecked());
                ParseUser.getCurrentUser().put("hardware", c4.isChecked());
                ParseUser.getCurrentUser().put("devpost", devpost);
                try {
                    ParseUser.getCurrentUser().save();
                    new AlertDialog.Builder(this)
                            .setTitle("Success")
                            .setMessage("Your answers have been saved")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                new AlertDialog.Builder(this)
                        .setTitle("Sorry")
                        .setMessage("Since you're now in a team, you can't adjust your preferences")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }
