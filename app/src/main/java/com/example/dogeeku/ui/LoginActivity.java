package com.example.dogeeku.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.dogeeku.DatabaseAccess;
import com.example.dogeeku.DatabaseOpenHelper;
import com.example.dogeeku.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dogeeku.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ListView listView;
    protected Cursor cursor;
    DatabaseOpenHelper dbHelper;
    EditText email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DatabaseOpenHelper(this);

        email = (EditText) findViewById(R.id.nama_anjing);
        password = (EditText) findViewById(R.id.nama_layanan);

        Button button_back = (Button) findViewById(R.id.button_back);
        Button button_LOGIN = (Button) findViewById(R.id.login);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });

        button_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String emailDB = email.getText().toString();
                String passwordDB = password.getText().toString();
                String hasilLogin = databaseAccess.cekLogin(emailDB, passwordDB);

                if(hasilLogin == "kosong"){
                    Toast toast = Toast.makeText(getApplicationContext(), "Maaf, Email atau Password Anda Salah", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Welcome " + hasilLogin + " !", Toast.LENGTH_LONG);
                    toast.show();
                    Intent home = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(home);
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
