package com.daitangroup.daitanuniversityandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.daitangroup.daitanuniversityandroid.R;
import com.daitangroup.daitanuniversityandroid.adapter.RepoAdapter;
import com.daitangroup.daitanuniversityandroid.model.Repo;

import java.util.ArrayList;

public class ReposActivity extends AppCompatActivity {

    public static final String ARG_USER_NAME = "com.daitangroup.daitanuniversityandroid.ARG_USER_NAME";
    public static final String ARG_USER_REPOS = "com.daitangroup.daitanuniversityandroid.ARG_USER_REPOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        String username = getIntent().getStringExtra(ARG_USER_NAME);
        ArrayList<Repo> repos = getIntent().getParcelableArrayListExtra(ARG_USER_REPOS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(username + "'s Repos");
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        if (recyclerView != null) {
            RepoAdapter adapter = new RepoAdapter(repos);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}