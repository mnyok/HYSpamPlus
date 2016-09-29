package mskim.hyspamplus;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

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
    RelativeLayout mainLayout;
    ListView noticeListView;
    NoticeAdapter noticeAdapter;
    ArrayList<NoticeData> noticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("FirebaseToken", token);

        mainLayout = (RelativeLayout) findViewById(R.id.layout_main);
        noticeListView = (ListView) findViewById(R.id.list_notice);

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
}
