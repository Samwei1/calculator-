package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
/***************************************************************************************
 *    Reference:
 *    Title: SwipeMenuListView
 *    Author: baoyong zhang
 *    Date: 2016
 *    Code version:2.0
 *    Availability: https://github.com/baoyongzhang/SwipeMenuListView
 *
 ***************************************************************************************/
public class LoadActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private  static final  String TAG = "LoadActivity";
    SwipeMenuListView listView;
    Boolean landscape=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        populateListView();
    }

    private void populateListView() {
        Cursor data = databaseHelper.getData();
        ArrayList listdata = new ArrayList();
        while (data.moveToNext()) {
            listdata.add(data.getString(1));

        }
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listdata);
        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                       0x3F, 0x25)));
                deleteItem.setTitleColor(Color.WHITE);
                // set item width
                deleteItem.setWidth(170);
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        String names = listView.getItemAtPosition(position).toString();
                        String [] tokens = names.split("=");
                        String title = tokens[0]+"=";
                        String content = tokens[1];
                        Intent intent = new Intent(LoadActivity.this,Landscape.class);
                        intent.putExtra("title",title);
                        intent.putExtra("content",content);
                        startActivity(intent);

                        break;
                    case 1:
                        // delete
                        String name = listView.getItemAtPosition(position).toString();
                        toastMessage(name);
                        Cursor data = databaseHelper.getItemId(name);
                        int ItemId =-1;
                        while (data.moveToNext()){
                            ItemId=data.getInt(0);
                        }
                        if (ItemId>-1){
                            databaseHelper.deleteFunction(ItemId,name);
                            populateListView();
                        }else {
                            toastMessage("something got wrong ");
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getApplicationContext(), "landscape", Toast.LENGTH_SHORT).show();
            landscape= true;
        }else {
            Toast.makeText(getApplicationContext(), "portrait", Toast.LENGTH_SHORT).show();
            landscape=false;

        }
    }
    // modify backButton
    @Override
    public void onBackPressed() {
        if (landscape){
            Intent intent = new Intent(LoadActivity.this,Landscape.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(LoadActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}


