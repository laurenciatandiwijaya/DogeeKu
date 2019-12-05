package com.example.dogeeku.ui.reservasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dogeeku.DatabaseAccess;
import com.example.dogeeku.MainActivity;
import com.example.dogeeku.R;

public class TambahReservasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_reservasi);



        final EditText nama_anjing = (EditText) findViewById(R.id.nama_anjing);
        final EditText nama_layanan= (EditText) findViewById(R.id.nama_layanan);
        final EditText hari_tanggal= (EditText) findViewById(R.id.hari_tanggal);

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

                final String anjingDB = nama_anjing.getText().toString();
                final String layananDB = nama_layanan.getText().toString();
                final String haritanggalDB = hari_tanggal.getText().toString();

                databaseAccess.insertReservasi(anjingDB, layananDB, haritanggalDB);

                Intent register = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(register);
            }
        });
    }
}
