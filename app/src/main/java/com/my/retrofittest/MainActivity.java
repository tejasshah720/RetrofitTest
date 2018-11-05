package com.my.retrofittest;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.my.retrofittest.adapter.AlbumAdapter;
import com.my.retrofittest.model.AlbumInfo;
import com.my.retrofittest.receiver.NetworkConnectionReceiver;
import com.my.retrofittest.rest.ApiClient;
import com.my.retrofittest.rest.ApiInterface;
import com.my.retrofittest.service.NetworkSchedulerService;
import com.my.retrofittest.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


/**
 * Created by Tejas Shah on 29/10/18.
 */
public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private TextView emptyView;
    private Context mContext;
    private AlbumAdapter albumAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProgressDialog progressDoalog;
    private Toolbar mToolbar;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        scheduleJob();
    }

    /*public void checkConnection(Toolbar toolbar) {
        boolean isConnected = NetworkConnectionReceiver.isConnected(getApplicationContext());
        Log.d(TAG,"isConnected val: "+isConnected);
    }*/

    private void init() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mContext = MainActivity.this;

        emptyView = findViewById(R.id.empty_view);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.show();

        boolean isInternetConnected = NetworkConnectionReceiver.isConnected(this);

        if (isInternetConnected) {

            //Defined interface Object
            ApiInterface apiService =
                    ApiClient.getClientInstance(getApplicationContext()).create(ApiInterface.class);

            Call<List<AlbumInfo>> call = apiService.getAllAlbumsTitle();
            try {
                call.enqueue(new Callback<List<AlbumInfo>>() {
                    @Override
                    public void onResponse(Call<List<AlbumInfo>> call, Response<List<AlbumInfo>> response) {
                        //progressDoalog.dismiss();
                        Log.d(TAG, "request url: " + response.raw().request().url());

                        if (response.code() == 200) {
                            setAlbumInfoAdapter(response.body());
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AlbumInfo>> call, Throwable t) {
                        //progressDoalog.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Util.showPopup(mContext, "", mContext.getResources().getString(R.string.no_internet_msg_text));
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(myJob);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled successfully!");
        }
    }

    private void setAlbumInfoAdapter(List<AlbumInfo> lstalbuminfo) {
        if(lstalbuminfo.size()>0){
            if(emptyView.getVisibility() == View.VISIBLE){
                emptyView.setVisibility(View.GONE);
            }
            if(recyclerView.getVisibility() == View.GONE){
                recyclerView.setVisibility(View.VISIBLE);
            }
            albumAdapter = new AlbumAdapter();
            recyclerView.setAdapter(albumAdapter);

            albumAdapter.addAll(lstalbuminfo);
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        checkConnection(mToolbar);

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
        startService(startServiceIntent);
    }

    @Override
    protected void onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(new Intent(this, NetworkSchedulerService.class));
        super.onStop();
    }

}
