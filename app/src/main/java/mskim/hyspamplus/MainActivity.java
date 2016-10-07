package mskim.hyspamplus;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private MainPresenter mainPresenter;
    NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("HY_CSE");

        mainPresenter = new MainPresenterImpl(MainActivity.this);
        mainPresenter.setView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView noticeListView = (ListView) findViewById(R.id.list_notice);
        noticeAdapter = new NoticeAdapter(this);
        noticeListView.setAdapter(noticeAdapter);
        noticeListView.setOnItemClickListener(noticeItemClickListener);

        mainPresenter.updateNoticeList();

    }

    AdapterView.OnItemClickListener noticeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mainPresenter.onNoticeClick(position);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //push notification setting
        if (id == R.id.setting_push) {
            if (mainPresenter.togglePushSetting()) {
                item.setTitle(R.string.setting_push_on);
            } else {
                item.setTitle(R.string.setting_push_off);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setNoticeList(ArrayList<NoticeData> noticeList) {
        noticeAdapter.setItemList(noticeList);
    }

    @Override
    public void showSnackBar(String string){
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout_main);
        Snackbar.make(mainLayout, string, Snackbar.LENGTH_SHORT).show();
    }
}
