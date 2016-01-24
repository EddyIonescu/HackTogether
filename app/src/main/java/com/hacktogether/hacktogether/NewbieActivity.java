package com.hacktogether.hacktogether;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

public class NewbieActivity extends AppCompatActivity {

    CheckBox c1 = (CheckBox)findViewById(R.id.checkbox1); //ios
    CheckBox c2 = (CheckBox)findViewById(R.id.checkbox2); //android
    CheckBox c3 = (CheckBox)findViewById(R.id.checkbox3); //web
    CheckBox c4 = (CheckBox)findViewById(R.id.checkbox4); //arduino
    CheckBox c5 = (CheckBox)findViewById(R.id.checkbox5); //raspberry pi
    Button save = (Button)findViewById(R.id.save);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.parse_app_id),
                getResources().getString(R.string.parse_client_key));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact Us", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(ParseUser.getCurrentUser().getBoolean("assigned")){
            c1.setClickable(false);
            c2.setClickable(false);
            c3.setClickable(false);
            c4.setClickable(false);
            c5.setClickable(false);
            save.setClickable(false);
            String teams = "Team-members: " + ParseUser.getCurrentUser().get("p1") + ", \n" +
                    ParseUser.getCurrentUser().get("p2") + ", \n" + "Hacker-Guru: " +
                    ParseUser.getCurrentUser().get("guruname");
            TextView header = (TextView) findViewById(R.id.header);
            header.setText(teams);

            TextView devpost = (TextView) findViewById(R.id.devpost);
            devpost.setText(Html.fromHtml("<a href="
                    + ParseUser.getCurrentUser().get("gurudevpost") + "> " +
                    ParseUser.getCurrentUser().get("gurudevpost")));
            devpost.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
    public void onSaveClicked(View view) {
        if(c1.isChecked() && c2.isChecked()){
            new AlertDialog.Builder(this)
                    .setTitle("Sorry")
                    .setMessage("You've checked both iOS and Android. In order to properly learn one of them, please choose either one")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else{
            if(ParseUser.getCurrentUser().get("assigned").equals("no")) {
                ParseUser.getCurrentUser().put("ios", c1.isChecked());
                ParseUser.getCurrentUser().put("android", c2.isChecked());
                ParseUser.getCurrentUser().put("web", c3.isChecked());
                ParseUser.getCurrentUser().put("arduino", c4.isChecked());
                ParseUser.getCurrentUser().put("raspberry-pi", c5.isChecked());
                ParseUser.getCurrentUser().saveInBackground();
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
}
