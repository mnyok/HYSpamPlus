package mskim.hyspamplus.notices;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mskim.hyspamplus.R;
import mskim.hyspamplus.data.notice.Notice;
import mskim.hyspamplus.databinding.NoticeItemBinding;


class NoticesAdapter extends BaseAdapter {
    private NoticesContract.Presenter presenter;
    private ArrayList<Notice> notices = new ArrayList<>();

    NoticesAdapter(NoticesContract.Presenter presenter){
        this.presenter = presenter;
    }

    public void addItem(String title, String url){
        Notice item = new Notice(title, url);
        notices.add(item);
    }

    private void setItemList(ArrayList<Notice> itemList){
        notices = itemList;
    }

    public void replaceData(ArrayList<Notice> itemList){
        setItemList(itemList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notices.size();
    }

    @Override
    public Notice getItem(int position) {
        return notices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        NoticeItemBinding binding;
        if (view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.notice_item, parent, false);
        } else {
            binding = DataBindingUtil.findBinding(view);
        }
        binding.setPresenter(presenter);

        final Notice listElement = getItem(position);
        if (listElement != null){
            binding.setNotice(listElement);
            binding.executePendingBindings();
        }

        return binding.getRoot();
    }
}
