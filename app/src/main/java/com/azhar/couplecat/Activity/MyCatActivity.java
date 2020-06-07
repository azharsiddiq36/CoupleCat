package com.azhar.couplecat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.azhar.couplecat.Adapter.MyCatAdapter;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCatActivity extends AppCompatActivity {
    SessionManager sessionManager;
    CoupleCatInterface coupleCatInterface;
    @BindView(R.id.rv_mycat)
    RecyclerView recyclerView;
    MyCatAdapter myCatAdapter;

    RecyclerView.LayoutManager layoutManager;
    HashMap<String, String> map;
    ArrayList Kucing = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cat);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MyCatActivity.this);
        coupleCatInterface = CombineApi.getApiService();
        map = sessionManager.getDetailsLoggin();
        layoutManager = new LinearLayoutManager(MyCatActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loadMyCat();
    }

    private void loadMyCat() {
        Call<ResponseMyCat> myCatCall = coupleCatInterface.myListCat(map.get(sessionManager.KEY_PENGGUNA_ID));
        myCatCall.enqueue(new Callback<ResponseMyCat>() {
            @Override
            public void onResponse(Call<ResponseMyCat> call, Response<ResponseMyCat> response) {
                if (response.body().getStatus()==200){
                    Kucing = (ArrayList) response.body().getData();
                    myCatAdapter = new MyCatAdapter(MyCatActivity.this,Kucing);
                    recyclerView.setAdapter(myCatAdapter);
                    myCatAdapter.notifyDataSetChanged();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<ResponseMyCat> call, Throwable t) {

            }
        });
    }

    /*
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.menu_search,menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchManager searchManager = (SearchManager)MyCatActivity.this.getSystemService(Context.SEARCH_SERVICE);
            if (searchItem !=null){
                searchView = (SearchView) searchItem.getActionView();
            }
            if (searchView!=null){
                searchView.setSearchableInfo(searchManager.getSearchableInfo(MyCatActivity.this.getComponentName()));
                queryTextListener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.i("onQueryTextSubmit", query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if(myCatAdapter!=null){
                            myCatAdapter.getFilter().filter(newText);
                        }
                        return true;
                    }
                };
                searchView.setOnQueryTextListener(queryTextListener);
            }
            super.onCreateOptionsMenu(menu,menuInflater);
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_search:
                    // Not implemented here
                    return false;
                default:
                    break;
            }
            searchView.setOnQueryTextListener(queryTextListener);
            return super.onOptionsItemSelected(item);
        }
        */
    @OnClick(R.id.lyBtnAdd)
    public void lyBtnAdd(View view){
        Intent gotoaddmycat = new Intent (MyCatActivity.this,AddMyCatActivity.class);
        startActivity(gotoaddmycat);
    }
}
