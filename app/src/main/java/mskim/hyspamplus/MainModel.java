package mskim.hyspamplus;

import java.util.ArrayList;


public class MainModel {
    private ArrayList<NoticeData> noticeList = new ArrayList<>();

    // read JSON string from server and apply to listview
    public ArrayList<NoticeData> refreshNoticeList() {
        ServerHelper serverHelper = new ServerHelper();

        String jsonString = null;
        try {
            jsonString = serverHelper.getNoticeList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //If jsonString is null, it returns empty ArrayList(not null)
        noticeList = NoticeData.listFromJSONString(jsonString);

        return noticeList;
    }

    public void setNoticeList(ArrayList<NoticeData> noticeList) {
        this.noticeList = noticeList;
    }

    public ArrayList<NoticeData> getNoticeList() {
        return noticeList;
    }

    public NoticeData getNotice(int index) {
        return noticeList.get(index);
    }
}
