package com.rotamobile.gursan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rotamobile.gursan.R;

public class DısServisTalepList extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title,talep_list;
    private ImageButton back_btn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_talep_list);

        toolbar = findViewById(R.id.toolbar_top_talep_list);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_talep_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
