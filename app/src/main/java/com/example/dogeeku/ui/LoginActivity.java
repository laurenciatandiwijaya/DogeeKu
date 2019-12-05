package com.example.dogeeku.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.example.dogeeku.DatabaseAccess;
import com.example.dogeeku.DatabaseOpenHelper;
import com.example.dogeeku.MainActivity;
import com.example.dogeeku.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dogeeku.R;

import java.util.HashMap;
import java.util.List;
import android.app.Activity;


public class LoginActivity extends AppCompatActivity {

    private ListView listView;
    protected Cursor cursor;
    DatabaseOpenHelper dbHelper;
    EditText email, password;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Session Manager
        session = new SessionManager(getApplicationContext());
        TextView lblName = (TextView) findViewById(R.id.lblName);

        if(session.isLoggedIn()){
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // name
            String name = user.get(SessionManager.KEY_NAME);

            // displaying user data
            lblName.setText(Html.fromHtml("Halo " + name + "! Anda sudah Login" +"</b>"));
        }

        dbHelper = new DatabaseOpenHelper(this);

        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);

        Button button_back = (Button) findViewById(R.id.button_back);
        Button button_logout = (Button) findViewById(R.id.button_logout);
        Button button_LOGIN = (Button) findViewById(R.id.login);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                Intent login = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(login);
            }
        });

        button_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String emailDB = email.getText().toString();
                String passwordDB = password.getText().toString();
                String nama_lengkap = databaseAccess.cekLogin(emailDB, passwordDB);

                // Check if username, password is filled
                if(emailDB.trim().length() > 0 && passwordDB.trim().length() > 0){
                    if(nama_lengkap == "kosong"){
                        Toast toast = Toast.makeText(getApplicationContext(), "Maaf, Email atau Password Anda Salah", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else{
                        session.createLoginSession(emailDB, nama_lengkap);

                        Toast toast = Toast.makeText(getApplicationContext(), "Welcome " + nama_lengkap + " !", Toast.LENGTH_LONG);
                        toast.show();
                        Intent home = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(home);
                    }
                }
                else{
                    // user didn't entered username or password
                    Toast toast = Toast.makeText(getApplicationContext(), "Email atau password Anda kosong", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> quotes = databaseAccess.getQuotes();
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
        this.listView.setAdapter(adapter);
    }
}
