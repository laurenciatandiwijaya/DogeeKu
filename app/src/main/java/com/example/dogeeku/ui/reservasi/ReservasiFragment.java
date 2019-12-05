package com.example.dogeeku.ui.reservasi;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dogeeku.DatabaseAccess;
import com.example.dogeeku.R;
import com.example.dogeeku.ui.home.ShopActivity;

import java.util.List;

public class ReservasiFragment extends Fragment {
    private ListView listView;
    private ListView listView2;

    private ReservasiViewModel mViewModel;

    public static ReservasiFragment newInstance() {
        return new ReservasiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reservasi_fragment, container, false);

        Button button_reservasi = (Button) v.findViewById(R.id.button_reservasi);

        this.listView = (ListView) v.findViewById(R.id.list_res_1);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.open();
        List<String> nama_barang = databaseAccess.getNama_Anjing();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nama_barang);
        this.listView.setAdapter(adapter1);

        this.listView2 = (ListView) v.findViewById(R.id.list_res_2);
        List<String> harga_barang = databaseAccess.getNama_Layanan();


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, harga_barang);
        this.listView2.setAdapter(adapter2);
        databaseAccess.close();

        button_reservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent (getActivity(), TambahReservasi.class);
                startActivity(register);
            }
        });

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReservasiViewModel.class);
        // TODO: Use the ViewModel
    }

}
