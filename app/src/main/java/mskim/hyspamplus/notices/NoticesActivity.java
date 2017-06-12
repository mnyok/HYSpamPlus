package mskim.hyspamplus.notices;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;

import mskim.hyspamplus.R;
import mskim.hyspamplus.databinding.NoticesActivityBinding;
import mskim.hyspamplus.util.SharedPreferenceManager;

public class NoticesActivity extends AppCompatActivity {
    private NoticesContract.Presenter mNoticesPresenter;
    private NoticesActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.notices_activity);
        FirebaseMessaging.getInstance().subscribeToTopic("HY_CSE");

        setSupportActionBar(binding.toolbar);


        NoticesFragment noticesFragment = (NoticesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (noticesFragment == null) {
            noticesFragment = NoticesFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_frame, noticesFragment);
            transaction.commit();
        }

        // Create the presenter
        mNoticesPresenter = new NoticesPresenter(noticesFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem pushItem = menu.findItem(R.id.setting_push);
        if (new SharedPreferenceManager(this).getPush()) {
            pushItem.setTitle(R.string.setting_push_on);
        } else {
            pushItem.setTitle(R.string.setting_push_off);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //push notification setting
        if (id == R.id.setting_push) {
            if (new SharedPreferenceManager(this).togglePush()) {
                item.setTitle(R.string.setting_push_on);
            } else {
                item.setTitle(R.string.setting_push_off);
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
