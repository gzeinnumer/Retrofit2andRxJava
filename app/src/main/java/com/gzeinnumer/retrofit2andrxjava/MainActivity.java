package com.gzeinnumer.retrofit2andrxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gzeinnumer.retrofit2andrxjava.adapter.AdapterRX;
import com.gzeinnumer.retrofit2andrxjava.model.ResponseNews;
import com.gzeinnumer.retrofit2andrxjava.network.RetroServer;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AdapterRX adapterRX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);

        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(RetroServer.getInstance().getBerita("us","e5430ac2a413408aaafdf60bfa27a874").
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseNews>() {
                    @Override
                    public void accept(ResponseNews responseNews) throws Exception {
                        sentDataToAdapter(responseNews);
                    }
                }));
    }

    private void sentDataToAdapter(ResponseNews responseNews) {
        adapterRX = new AdapterRX(getApplicationContext(),responseNews.getArticles());
        recyclerView.setAdapter(adapterRX);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
