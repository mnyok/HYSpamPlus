package mskim.hyspamplus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout mainLayout;
    ListView noticeListView;
    NoticeAdapter noticeAdapter;
    ArrayList<NoticeData> noticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("HY_CSE");

        mainLayout = (RelativeLayout) findViewById(R.id.layout_main);
        noticeListView = (ListView) findViewById(R.id.list_notice);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noticeAdapter = new NoticeAdapter(this);
        noticeListView.setAdapter(noticeAdapter);
        noticeListView.setOnItemClickListener(noticeItemClickListener);

        updateNoticeList();
    }

    AdapterView.OnItemClickListener noticeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(noticeList.get(position).getUrlString()));
            startActivity(browserIntent);
        }
    };

    //read JSON string from server and apply to listview
    void updateNoticeList() {
        ServerHelper serverHelper = new ServerHelper();

        try {
            String jsonString = serverHelper.getNoticeList();
            noticeList = NoticeData.listFromJSONString(jsonString);

        } catch (InterruptedException e) {
            Snackbar.make(mainLayout, "Error!", Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        noticeAdapter.setItemList(noticeList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //push notification setting
        if (id == R.id.setting_push) {
            SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
            SharedPreferences.Editor editor = setting.edit();

            //toggle push preference
            if (setting.getBoolean("push", true)){
                editor.putBoolean("push", false);
                item.setTitle(R.string.setting_push_off);
            } else {
                editor.putBoolean("push", true);
                item.setTitle(R.string.setting_push_on);
            }
            editor.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
