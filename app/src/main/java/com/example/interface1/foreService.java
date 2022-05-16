package com.example.interface1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.interface1.ui.notif.NotifFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;


public class foreService extends Service {
    private Document doc;
    private Runnable runnable;
    private Thread secThread;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItemClass> arrayList;

    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = new Bundle();
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, NotifFragment.class);//!!!!!!!!
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.baseline_pets_black_24)
                .setContentIntent(pendingIntent)
                .build();
//        listView = (ListView) listView.findViewById(R.id.listView_gaz);
//        arrayList = new ArrayList<>();
//        adapter = new CustomArrayAdapter(GazFragment.class, R.layout.listitem1, arrayList,);
//        listView.setAdapter(adapter);
        startForeground(1, notification);
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
//                    doc = Jsoup.connect("https://www.banki.ru/products/currency/cash/kursk/").get();
//                    Elements tables = doc.getElementsByTag("tbody");
//                    Element table = tables.get(0);
//                    Elements elements_from_table = table.children();
//                    String el = table.children().get(0).child(1).text();
//                    Log.d("MyLog", "service  " + el);
//                    intent.setAction("com.example.action.CAT");
//                    intent.putExtra("name",el);
//                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                    sendBroadcast(intent);
                    doc = Jsoup.connect("https://time100.ru/").get();
                    //Elements elements = doc.getElementsByClass("text-2xl");
                    Elements elements = doc.getElementsByTag("h3");
                    Element element= elements.get(0);
                    Log.d("MyLog", "service  " + element.text());

                    //условие +ресирвер


                }
             catch (IOException e) {
                 e.printStackTrace();
             }
            }
        };
        secThread = new Thread(runnable);
        secThread.start();

            //Log.d("MyLog", "size: " + table.children().get(1).child(6).text());

//            ListItemClass items = new ListItemClass();
//            items.setData_1(table.children().get(0).child(1).text());
            //items.setData_2(table.children().get(0).child(6).text());
            //intent.putExtra("name",table.children().get(0).child(1).text());

//            arrayList.add(items);
//            adapter.notifyDataSetChanged();






        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
