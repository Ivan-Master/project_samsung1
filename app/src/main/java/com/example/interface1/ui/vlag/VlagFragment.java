package com.example.interface1.ui.vlag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.interface1.CustomArrayAdapter;
import com.example.interface1.Dat1;
import com.example.interface1.ListItemClass;
import com.example.interface1.R;
import com.example.interface1.databinding.FragmenVlagBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VlagFragment extends Fragment {

    private VlagViewModel vlagViewModel;
    private FragmenVlagBinding binding;

    private Document doc;
    private Thread secThread;
    private Runnable runnable;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItemClass> arrayList;

    private DatabaseReference mDataBase;
    private String DAT_KEY="DAT1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vlagViewModel =
                new ViewModelProvider(this).get(VlagViewModel.class);

        binding = FragmenVlagBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textVlag;

        listView = (ListView)root.findViewById(R.id.listView_vlag);
        arrayList = new ArrayList<>();
        adapter = new CustomArrayAdapter(getActivity(), R.layout.listitem1, arrayList, getLayoutInflater());
        listView.setAdapter(adapter);
        init();

        vlagViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void init() {


        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();

    }
    private void getWeb() {
        try {
            doc = Jsoup.connect("https://www.banki.ru/products/currency/cash/kursk/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element table = tables.get(0);
            Elements elements_from_table = table.children();
            Log.d("MyLog", "size: " + table.children().get(1).child(6).text());

            ListItemClass items = new ListItemClass();
            items.setData_1(table.children().get(3).child(1).text());
            //items.setData_2(table.children().get(0).child(6).text());

            arrayList.add(items);

            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();


                }
            });
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    mDataBase= FirebaseDatabase.getInstance().getReference(DAT_KEY);
//                    String id=mDataBase.getKey();
//                    String name="Dat1";
//                    String value = items.getData_1();
//                    Dat1 dt1 = new Dat1(id,name,value);
//                    mDataBase.push().setValue(dt1);
//                }
//            }, 10000);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}