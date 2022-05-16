package com.example.interface1.ui.atm_dav;

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
import com.example.interface1.ListItemClass;
import com.example.interface1.R;
import com.example.interface1.databinding.FragmentAtmDavBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AtmDavFragment extends Fragment {

    private AtmDavViewModel atmdavViewModel;
    private FragmentAtmDavBinding binding;
    private Document doc;
    private Thread secThread;
    private Runnable runnable;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItemClass> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        atmdavViewModel =
                new ViewModelProvider(this).get(AtmDavViewModel.class);

        binding = FragmentAtmDavBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAtmDav;

        listView = (ListView)root.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new CustomArrayAdapter(getActivity(), R.layout.listitem1, arrayList, getLayoutInflater());
        listView.setAdapter(adapter);
        init();

        atmdavViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
//            doc = Jsoup.connect("https://www.banki.ru/products/currency/cash/kursk/").get();
//            Elements tables = doc.getElementsByTag("tbody");
//            Element table = tables.get(0);
//            Elements elements_from_table = table.children();
//            Log.d("MyLog", "size: " + table.children().get(1).child(6).text());
            doc = Jsoup.connect("https://time100.ru/").get();
            //Elements elements = doc.getElementsByClass("text-2xl");
            Elements elements = doc.getElementsByTag("h3");
            Element element= elements.get(0);

            ListItemClass items = new ListItemClass();
            items.setData_1(element.text().substring(4,5));
            //items.setData_2(table.children().get(0).child(6).text());

            arrayList.add(items);

            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                }
            });


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