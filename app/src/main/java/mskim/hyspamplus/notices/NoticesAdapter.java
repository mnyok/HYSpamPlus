package mskim.hyspamplus.notices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mskim.hyspamplus.R;
import mskim.hyspamplus.data.notice.Notice;


class NoticesAdapter extends BaseAdapter {
    private ArrayList<Notice> notices = new ArrayList<>();

    NoticesAdapter(){

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
        TextView titleView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notice_item, parent, false);

            titleView = (TextView) view.findViewById(R.id.text_notice);
            //assign each Views

        } else {
            titleView = (TextView) view.getTag();
        }

        final Notice listElement = getItem(position);
        if (listElement != null){
            titleView.setText(getItem(position).getTitle());

            view.setTag(titleView);
        }

        return view;
    }
}
