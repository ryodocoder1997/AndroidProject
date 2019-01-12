package com.example.hoanglong.rssreader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView list;
    CustomAdapter customAdapter;

    Dialog infoDialog;
    CardView cardView;
    TextView txtCategory, txtTotal, txtNewest, txtOldest;
    ImageView imgCategory, closeBtn;
    Button confirmBtn;

    public String tab_name = "Thể thao";

    final int LOADING_TIME = 1500;

    private String newsType;

    public  ArrayList<News> newsarray = new ArrayList<>();

    public boolean isOldNews = false;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar searchToolbar = (Toolbar) findViewById(R.id.toolbarSearch);
        setSupportActionBar(searchToolbar);

        list = (ListView) findViewById(android.R.id.list);
        TextView emptyView = (TextView) findViewById(R.id.emptyView);

        final ProgressDialog dialog;

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_4));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_5));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Bundle bundle = getIntent().getExtras();
        newsType = bundle.getString("newsType");

        dialog = new ProgressDialog(MainActivity.this);

        dialog.setMessage("Đang tải...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        //Create DATABASE tintuc24h.sqlite
            database = new Database(this, "tintuc.sqlite", null, 1);

            database.ModifyData("CREATE TABLE IF NOT EXISTS NewsItem(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NewsName VARCHAR, " +
                    "Category VARCHAR, " +
                    "Title VARCHAR, " +
                    "Link VARCHAR, " +
                    "Image VARCHAR, " +
                    "Description VARCHAR, " +
                    "PubDate VARCHAR" +
                    ")");

        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (newsType){
                    case "VNExpress":
                        new ReadData_VNExpress().execute("https://vnexpress.net/rss/the-thao.rss");
                        break;
                    case "TuoiTre":
                        new ReadData_TuoiTre().execute("https://tuoitre.vn/rss/the-thao.rss");
                        break;
                    case "VietNamNet":
                        TabLayout.Tab tab = tabLayout.getTabAt(4).setText("Sức Khỏe");
                        new ReadData_VietNamNet().execute("https://vietnamnet.vn/rss/the-thao.rss");
                        break;
                }
                dialog.dismiss();
            }
        },LOADING_TIME);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0: {
                        tab_name = "Thể thao";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isOldNews == true) {
                                            ShowDatabaseData();
                                        } else {
                                            switch (newsType) {
                                                case "VNExpress":
                                                    new ReadData_VNExpress().execute("https://vnexpress.net/rss/the-thao.rss");
                                                    break;
                                                case "TuoiTre":
                                                    new ReadData_TuoiTre().execute("https://tuoitre.vn/rss/the-thao.rss");
                                                    break;
                                                case "VietNamNet":
                                                    new ReadData_VietNamNet().execute("https://vietnamnet.vn/rss/the-thao.rss");
                                                    break;
                                                }
                                        }
                                        dialog.dismiss();
                                    }
                                },LOADING_TIME);
                            }
                        });
                    }
                    break;
                    case 1: {
                        tab_name = "Thế giới";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isOldNews == true) {
                                            ShowDatabaseData();
                                        } else {
                                            switch (newsType) {
                                                case "VNExpress":
                                                    new ReadData_VNExpress().execute("https://vnexpress.net/rss/the-gioi.rss");
                                                    break;
                                                case "TuoiTre":
                                                    new ReadData_TuoiTre().execute("https://tuoitre.vn/rss/the-gioi.rss");
                                                    break;
                                                case "VietNamNet":
                                                    new ReadData_VietNamNet().execute("https://vietnamnet.vn/rss/the-gioi.rss");
                                                    break;
                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                },LOADING_TIME);
                            }
                        });
                    }
                    break;
                    case 2: {
                        tab_name = "Pháp luật";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isOldNews == true) {
                                            ShowDatabaseData();
                                        } else {
                                            switch (newsType) {
                                                case "VNExpress":
                                                    new ReadData_VNExpress().execute("https://vnexpress.net/rss/phap-luat.rss");
                                                    break;
                                                case "TuoiTre":
                                                    new ReadData_TuoiTre().execute("https://tuoitre.vn/rss/phap-luat.rss");
                                                    break;
                                                case "VietNamNet":
                                                    new ReadData_VietNamNet().execute("https://vietnamnet.vn/rss/phap-luat.rss");
                                                    break;
                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                },LOADING_TIME);
                            }
                        });
                    }
                    break;
                    case 3: {
                        tab_name = "Giải trí";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isOldNews == true) {
                                            ShowDatabaseData();
                                        } else {
                                            switch (newsType) {
                                                case "VNExpress":
                                                    new ReadData_VNExpress().execute("https://vnexpress.net/rss/giai-tri.rss");
                                                    break;
                                                case "TuoiTre":
                                                    new ReadData_TuoiTre().execute("https://tuoitre.vn/rss/giai-tri.rss");
                                                    break;
                                                case "VietNamNet":
                                                    new ReadData_VietNamNet().execute("https://vietnamnet.vn/rss/giai-tri.rss");
                                                    break;
                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                },LOADING_TIME);
                            }
                        });
                    }
                    break;
                    case 4: {
                        tab_name = "Du lịch";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isOldNews == true) {
                                            ShowDatabaseData();
                                        }
                                        else {
                                            switch (newsType) {
                                                case "VNExpress":
                                                    new ReadData_VNExpress().execute("https://vnexpress.net/rss/du-lich.rss");
                                                    break;
                                                case "TuoiTre":
                                                    new ReadData_TuoiTre().execute("https://tuoitre.vn/rss/du-lich.rss");
                                                    break;
                                                case "VietNamNet":
                                                    tab_name = "Sức khỏe";
                                                    new ReadData_VietNamNet().execute("https://vietnamnet.vn/rss/suc-khoe.rss");
                                                    break;
                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                },LOADING_TIME);
                            }
                        });
                    }
                    break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowWeb.class);
                intent.putExtra("link", newsarray.get(position).link);
                int Count = 0;
                if (isOldNews != true) {
                    Cursor data = database.GetData("SELECT Title FROM NewsItem WHERE Title = \"" + newsarray.get(position).title + "\"");
                    while (data.moveToNext()) {
                        String data_title = data.getString(0);
                        if (data_title != null) {
                            Count++;
                        }
                    }
                    if (Count == 0) {
                        database.ModifyData("INSERT INTO NewsItem VALUES(null,'" + newsType + "','"
                                + tab_name + "',\""
                                + newsarray.get(position).title + "\",'"
                                + newsarray.get(position).link + "','"
                                + newsarray.get(position).image + "',\""
                                + newsarray.get(position).description + "\",'"
                                + newsarray.get(position).pubDate + "')");
                    }
                }
                startActivity(intent);
            }
        });

        list.setEmptyView(emptyView); //If nothing in the list

    }

    class ReadData_VNExpress extends AsyncTask<String, Integer, String> {
        //Vnexpress
        //Ham Xu Ly
        @Override
        protected String doInBackground(String... params) {
            return readData_From_URL(params[0]);
        }

        //Ham Tra Ve
        @Override
        protected void onPostExecute(String s) {
            newsarray.clear();
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String description = "";
            String date = "";
            String image = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListdescription.item(i + 1).getTextContent(); //getText of CDATA

                String segments[] = cdata.split("</br>");

                Pattern p;
                if (cdata.contains("data-original")) {
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                } else {
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    image = matcher.group(1);
                    Log.d("hinhanh", image + "..........." + i);
                }
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                Log.d("title", title + ".........." + i);
                date = parser.getValue(element, "pubDate");
                link = parser.getValue(element, "link");

                description = segments[segments.length-1];

                newsarray.add(new News(newsType, title.replace("\"", "'"), link, image, description.replace("\"", "'"), date));
            }
            customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_list_item_1,newsarray);
            list.setAdapter(customAdapter);
            super.onPostExecute(s);
        }
    }

    class ReadData_TuoiTre extends AsyncTask<String, Integer, String> {
        //TuoiTre
        //Ham Xu Ly
        @Override
        protected String doInBackground(String... params) {
            return readData_From_URL(params[0]);
        }

        //Ham Tra Ve
        @Override
        protected void onPostExecute(String s) {
            newsarray.clear();
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListtitle = document.getElementsByTagName("title");
            NodeList nodeListlink = document.getElementsByTagName("link");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            NodeList nodeListpubDate = document.getElementsByTagName("pubDate");
            String title = "";
            String link = "";
            String description = "";
            String date = "";
            String image = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                String desc_cdata = nodeListdescription.item(i).getTextContent(); //getText of CDATA

                String segments[] = desc_cdata.split("</a>");

                Pattern p;
                if (desc_cdata.contains("data-original")) {
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^/>]*");
                } else {
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^/>]*");
                }
                Matcher matcher = p.matcher(desc_cdata);
                if (matcher.find()) {
                    image = matcher.group(1);
                    Log.d("hinhanh", image + "..........." + i);
                }
                Element element = (Element) nodeList.item(i);
                title = nodeListtitle.item(i + 1).getTextContent();
                Log.d("title", title + ".........." + i);
                date = nodeListpubDate.item(i + 1).getTextContent();
                Log.d("date", date + ".........." + i);
                link = nodeListlink.item(i + 1).getTextContent();
                Log.d("link", link + ".........." + i);

                description = segments[segments.length-1];

                newsarray.add(new News(newsType, title.replace("\"", "'"), link, image, description.replace("\"", "'"), date));
            }
            customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_list_item_1,newsarray);
            list.setAdapter(customAdapter);
            super.onPostExecute(s);
        }
    }

    class ReadData_VietNamNet extends AsyncTask<String, Integer, String> {
        //VietnamNet
        //Ham Xu Ly
        @Override
        protected String doInBackground(String... params) {
            return readData_From_URL(params[0]);
        }

        //Ham Tra Ve
        @Override
        protected void onPostExecute(String s) {
            newsarray.clear();
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String description = "";
            String date = "";
            String image = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                String desc_cdata = nodeListdescription.item(i + 1).getTextContent(); //getText of CDATA

                String segments[] = desc_cdata.split("</p>");

                Pattern p;
                if (desc_cdata.contains("data-original")) {
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^/>]");
                } else {
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^/>]");
                }
                Matcher matcher = p.matcher(desc_cdata);
                if (matcher.find()) {
                    image = matcher.group(1);
                    Log.d("hinhanh", image + "..........." + i);
                }

                description = segments[0].replace("<p>","").replace("<p class=\"t-j\">", "");

                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                Log.d("title", title + ".........." + i);
                date = parser.getValue(element, "pubDate");
                link = parser.getValue(element, "link");

                newsarray.add(new News(newsType, title.replace("\"", "'"), link, image, description.replace("\"", "'"), date));
            }
            customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_list_item_1,newsarray);
            list.setAdapter(customAdapter);
            super.onPostExecute(s);
        }
    }

    public String readData_From_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();

        switch (id) {
            case R.id.search:
            {
                SearchView mySearchView = (SearchView) item.getActionView();
                mySearchView.setQueryHint("Nhập tìm kiếm...");

                mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        customAdapter.filter(newText.trim());
                        return false;
                    }
                });
            }break;
            case R.id.info:
            {
                infoDialog = new Dialog(this);
                showInfoPopup();
            }break;
            case R.id.oldnews:
            {
                if (isOldNews == false) {
                    isOldNews = true;
                    ActionMenuItemView oldnews = (ActionMenuItemView) findViewById(R.id.oldnews);
                    item.setIcon(R.drawable.ic_playlist_add_check_on);
                    Toast.makeText(MainActivity.this, "Đã BẬT chế độ xem tin đã đọc",Toast.LENGTH_LONG).show();
                    ShowDatabaseData();
                }
                else {
                    isOldNews = false;
                    Toast.makeText(MainActivity.this, "Đã TẮT chế độ xem tin đã đọc",Toast.LENGTH_LONG).show();
                    recreate();
                }
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ShowDatabaseData() {
        newsarray.clear();
        Cursor data = database.GetData("SELECT * FROM NewsItem WHERE Category = '" + tab_name + "'");
        while (data.moveToNext()) {
            int data_id = data.getInt(0);
            String data_newsname = data.getString(1);
            String data_category = data.getString(2);
            String data_title = data.getString(3);
            String data_link = data.getString(4);
            String data_image = data.getString(5);
            String data_description = data.getString(6);
            String data_pubdate = data.getString(7);
            newsarray.add(new News(data_newsname, data_title, data_link, data_image, data_description, data_pubdate));
        }
        if (newsarray.size() != 0) {
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            dialog.setMessage("Đang tải...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);

            dialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_list_item_1,newsarray);
                    list.setAdapter(customAdapter);
                    dialog.dismiss();
                }
            },LOADING_TIME);
        }
        else {
            TextView emptyView = (TextView) findViewById(R.id.emptyView);
            list.setEmptyView(emptyView);
        }
    }
    public void showInfoPopup() {
        infoDialog.setContentView(R.layout.info_popup_layout);

        cardView = (CardView) infoDialog.findViewById(R.id.cardView);

        imgCategory = (ImageView) infoDialog.findViewById(R.id.ImgCategory);
        closeBtn = (ImageView) infoDialog.findViewById(R.id.BtnClose);

        txtCategory = (TextView) infoDialog.findViewById(R.id.TxtCategory);
        txtTotal = (TextView) infoDialog.findViewById(R.id.TxtTotal);
        txtNewest = (TextView) infoDialog.findViewById(R.id.TxtNewestFeeds);
        txtOldest = (TextView) infoDialog.findViewById(R.id.TxtOldestFeeds);

        confirmBtn = (Button) infoDialog.findViewById(R.id.BtnConfirm);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        String newewst[] = newsarray.get(0).pubDate.split("\\s\\+");
        String oldest[] = newsarray.get(newsarray.size()-1).pubDate.split("\\s\\+");



        txtCategory.setText(tab_name);
        txtTotal.setText(Integer.toString(newsarray.size()));
        txtNewest.setText(newewst[0]);
        txtOldest.setText(oldest[0]);

        switch (txtCategory.getText().toString()) {
            case "Thể thao": {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgCategory.setImageResource(R.drawable.football);
            }break;
            case "Thế giới": {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightRed));
                imgCategory.setImageResource(R.drawable.global);
            }break;
            case "Pháp luật": {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightOrange));
                imgCategory.setImageResource(R.drawable.scale);
            }break;
            case "Giải trí": {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightYellow));
                imgCategory.setImageResource(R.drawable.smile);
            }break;
            case "Du lịch": {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightPurple));
                imgCategory.setImageResource(R.drawable.plane);
            }break;
            case "Sức khỏe": {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                imgCategory.setImageResource(R.drawable.health);
            }break;
        }

        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        infoDialog.show();
    }
}