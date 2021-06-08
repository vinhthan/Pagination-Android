package com.example.pagingnationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcy;
    private UserAdapter mUserAdapter;
    private List<User> mList;

    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage = 5;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcy = findViewById(R.id.rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcy.setLayoutManager(linearLayoutManager);

        mUserAdapter = new UserAdapter();
        rcy.setAdapter(mUserAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcy.addItemDecoration(itemDecoration);

        setFirstData();

        rcy.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                currentPage+=1;

                loadNextPage();

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

    }

    private void setFirstData() {
        mList = getListUser();
        mUserAdapter.setData(mList);

        if (currentPage < totalPage) {
            mUserAdapter.addLoading();
        } else {
            isLastPage = true;
        }
    }

    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> list = getListUser();

                mUserAdapter.removeLoading();
                mList.addAll(list);
                mUserAdapter.notifyDataSetChanged();

                isLoading = false;

                if (currentPage < totalPage) {
                    mUserAdapter.addLoading();
                } else {
                    isLastPage = true;
                }
            }
        }, 1000);

    }



    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(new User("Name "));
        }
        return list;
    }


}