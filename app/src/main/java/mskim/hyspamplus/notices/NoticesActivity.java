package mskim.hyspamplus.notices;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mskim.hyspamplus.data.notice.Notice;
import mskim.hyspamplus.R;

public class NoticesActivity extends AppCompatActivity implements NoticesContract.View{
    private NoticesContract.Presenter mNoticesPresenter;
    private NoticesAdapter mNoticesAdapter;

    private TextView noNoticeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("HY_CSE");

        new NoticesPresenter(this);

        noNoticeView = (TextView) findViewById(R.id.no_notice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView noticeListView = (ListView) findViewById(R.id.list_notice);
        mNoticesAdapter = new NoticesAdapter();
        noticeListView.setAdapter(mNoticesAdapter);
        noticeListView.setOnItemClickListener(noticeItemClickListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        mNoticesPresenter.start();
    }

    AdapterView.OnItemClickListener noticeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            openNotice(mNoticesAdapter.getItem(position).getUrlString());
        }
    };

    @Override
    public void openNotice(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW
                , Uri.parse(url));
        startActivity(browserIntent);
    }

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
            if (mNoticesPresenter.togglePushSetting()) {
                item.setTitle(R.string.setting_push_on);
            } else {
                item.setTitle(R.string.setting_push_off);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public SharedPreferences getSettingPreference() {
        return getSharedPreferences("setting", Activity.MODE_PRIVATE);

    }

    @Override
    public void showNotices(ArrayList<Notice> noticeList) {
        mNoticesAdapter.replaceData(noticeList);
        noNoticeView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyNotices() {
        noNoticeView.setText(R.string.no_notice);
        noNoticeView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadError() {
        noNoticeView.setText(R.string.server_error);
        noNoticeView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(NoticesContract.Presenter presenter) {
        // TODO: make fragment and separate it
        mNoticesPresenter = presenter;
    }

    @Override
    public void showSnackBar(String string){
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout_main);
        Snackbar.make(mainLayout, string, Snackbar.LENGTH_SHORT).show();
    }
}
