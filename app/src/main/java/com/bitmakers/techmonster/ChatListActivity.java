package com.bitmakers.techmonster;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.bitmakers.techmonster.adapter_pkg.ChatListAdapter;
import com.bitmakers.techmonster.adapter_pkg.HomePageAdapter;

public class ChatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.jobportal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ListView lv = (ListView) findViewById(R.id.listView);
        ChatListAdapter ad = new ChatListAdapter(this);
        lv.setAdapter(ad);
    }

}
