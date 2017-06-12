package mskim.hyspamplus.notices;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mskim.hyspamplus.R;
import mskim.hyspamplus.data.notice.Notice;
import mskim.hyspamplus.databinding.NoticesFragmentBinding;


public class NoticesFragment extends Fragment implements NoticesContract.View {
    private NoticesContract.Presenter mPresenter;

    private NoticesFragmentBinding binding;

    private NoticesAdapter mNoticesAdapter;


    public NoticesFragment() {

    }

    public static NoticesFragment newInstance() {
        return new NoticesFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: mPresenter is null on Instant run
        mNoticesAdapter = new NoticesAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.notices_fragment, container, false);

        binding.listNotice.setAdapter(mNoticesAdapter);
//        binding.listNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                NoticeItemBinding binding = DataBindingUtil.findBinding(view);
//                Log.i("Notice clicked", binding.getNotice().getTitle());
//                openNotice(binding.getNotice().getUrlString());
//            }
//        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showNotices(ArrayList<Notice> noticeList) {
        mNoticesAdapter.replaceData(noticeList);
        binding.noNotice.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyNotices() {
        binding.noNotice.setText(R.string.no_notice);
        binding.noNotice.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadError() {
        showSnackBar("Server Error");
        binding.noNotice.setText(R.string.server_error);
        binding.noNotice.setVisibility(View.VISIBLE);
    }


    public void showSnackBar(String string){
        Snackbar.make(binding.listNotice, string, Snackbar.LENGTH_SHORT).show();
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
