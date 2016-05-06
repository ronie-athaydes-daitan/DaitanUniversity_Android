package com.daitangroup.daitanuniversityandroid.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daitangroup.daitanuniversityandroid.R;
import com.daitangroup.daitanuniversityandroid.adapter.UserAdapter;
import com.daitangroup.daitanuniversityandroid.model.Repo;
import com.daitangroup.daitanuniversityandroid.model.User;
import com.daitangroup.daitanuniversityandroid.retrofit.GitHubService;
import com.daitangroup.daitanuniversityandroid.retrofit.RetrofitFactory;
import com.daitangroup.daitanuniversityandroid.retrofit.response.UsersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final UserAdapter mAdapter = new UserAdapter();

    private ListView mListView;

    private MenuItem mSearchItem;

    private SearchView mSearchView;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.menu_main);
            setupToolbarMenu(toolbar);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(mSearchUsersClickListener);
        }

        mListView = (ListView) findViewById(android.R.id.list);
        if (mListView != null) {
            View emptyView = findViewById(android.R.id.empty);

            mListView.setEmptyView(emptyView);
            mListView.setOnItemClickListener(this);
            mListView.setAdapter(mAdapter);
        }
    }

    private void setupToolbarMenu(Toolbar toolbar) {
        Menu menu = toolbar.getMenu();
        mSearchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public void onBackPressed() {
        if (MenuItemCompat.isActionViewExpanded(mSearchItem)) {
            MenuItemCompat.collapseActionView(mSearchItem);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        performUsersSearch(query);
        return true;
    }

    private void performUsersSearch(String query) {
        mProgressDialog = ProgressDialog.show(this, "Please wait", "Searching users...", true, false);

        GitHubService service = RetrofitFactory.createRetrofitService(GitHubService.class);
        Call<UsersResponse> call = service.listUsers(query);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                mProgressDialog.dismiss();

                if (response.isSuccessful()) {
                    UsersResponse r = response.body();
                    mAdapter.updateItems(r.getItems());
                } else {
                    clearList();

                    Snackbar.make(mListView, response.message(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", mSearchUsersClickListener)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

                clearList();
                mProgressDialog.dismiss();

                Snackbar.make(mListView, t.getMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", mSearchUsersClickListener)
                        .show();
            }
        });
    }

    private void clearList() {
        List<User> users = new ArrayList<>();
        mAdapter.updateItems(users);
    }

    private void performReposSearch(final String username) {
        mProgressDialog = ProgressDialog.show(this, "Please wait", "Searching repos...", true, false);

        GitHubService service = RetrofitFactory.createRetrofitService(GitHubService.class);
        Call<List<Repo>> call = service.listRepos(username);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                mProgressDialog.dismiss();

                if (response.isSuccessful()) {
                    List<Repo> repos = response.body();
                    startReposActivity(username, new ArrayList<>(repos));
                } else {
                    Snackbar.make(mListView, response.message(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Dismiss", null)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

                mProgressDialog.dismiss();

                Snackbar.make(mListView, t.getMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Dismiss", null)
                        .show();
            }
        });
    }

    private void startReposActivity(String username, ArrayList<Repo> repos) {
        Intent intent = new Intent(this, ReposActivity.class)
                .setAction(Intent.ACTION_DEFAULT)
                .putExtra(ReposActivity.ARG_USER_NAME, username)
                .putParcelableArrayListExtra(ReposActivity.ARG_USER_REPOS, repos);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        User selectedItem = (User) parent.getItemAtPosition(position);
        performReposSearch(selectedItem.getLogin());
    }

    private final View.OnClickListener mSearchUsersClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            performUsersSearch(mSearchView.getQuery().toString());
        }
    };
}