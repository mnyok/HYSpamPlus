package mskim.hyspamplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class NoticeAdapter extends BaseAdapter {
    TextView noticeTextView;
    Context mContext;
    ArrayList<NoticeData> noticeList = new ArrayList<>();
    NoticeData listElement;


    public NoticeAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void addItem(String title, String url){
        NoticeData item = new NoticeData(title, url);
        noticeList.add(item);
    }

    public void setItemList(ArrayList<NoticeData> itemList){
        noticeList = itemList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public NoticeData getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.listitem_notice, parent, false);

            noticeTextView = (TextView) convertView.findViewById(R.id.text_notice);
            //assign each Views

        } else {
            noticeTextView = (TextView) convertView.getTag();
        }

        listElement = getItem(position);
        if (listElement != null){
            noticeTextView.setText(getItem(position).getTitle());

            convertView.setTag(noticeTextView);
        }

        return convertView;
    }
}
