package com.example.dogeeku.ui.shop;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dogeeku.DatabaseAccess;
import com.example.dogeeku.R;

import java.util.List;

public class ShopFragment extends Fragment {
    private ListView listView;
    private ListView listView2;

    private ShopViewModel mViewModel;

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.shop_fragment, container, false);

        this.listView = (ListView) v.findViewById(R.id.list_nama);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.open();
        List<String> nama_barang = databaseAccess.getNama_Barang();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nama_barang);
        this.listView.setAdapter(adapter1);

        this.listView2 = (ListView) v.findViewById(R.id.list_harga);
        List<String> harga_barang = databaseAccess.getHarga_Barang();


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, harga_barang);
        this.listView2.setAdapter(adapter2);
        databaseAccess.close();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        // TODO: Use the ViewModel
    }

}
