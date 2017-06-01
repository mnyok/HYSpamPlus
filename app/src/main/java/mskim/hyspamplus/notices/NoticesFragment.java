package mskim.hyspamplus.notices;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mskim.hyspamplus.R;
import mskim.hyspamplus.data.notice.Notice;


public class NoticesFragment extends Fragment implements NoticesContract.View {
    private NoticesContract.Presenter mPresenter;

    private TextView noNoticeView;

    private ListView noticesView;

    private NoticesAdapter mNoticesAdapter;


    public NoticesFragment() {

    }

    public static NoticesFragment newInstance() {
        return new NoticesFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoticesAdapter = new NoticesAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.notices_fragment, container, false);

        noNoticeView = (TextView) root.findViewById(R.id.no_notice);

        noticesView = (ListView) root.findViewById(R.id.list_notice);
        noticesView.setAdapter(mNoticesAdapter);
        noticesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                openNotice(mNoticesAdapter.getItem(position).getUrlString());

            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
        showSnackBar("Server Error");
        noNoticeView.setText(R.string.server_error);
        noNoticeView.setVisibility(View.VISIBLE);
    }


    public void showSnackBar(String string){
        Snackbar.make(noticesView, string, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(NoticesContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void openNotice(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW
                , Uri.parse(url));
        startActivity(browserIntent);
    }
}
