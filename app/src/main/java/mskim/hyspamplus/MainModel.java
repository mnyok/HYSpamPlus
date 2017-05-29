package mskim.hyspamplus;

import java.util.ArrayList;


public class MainModel {
    private MainPresenterImpl mainPresenter;
    private ArrayList<NoticeData> noticeList = new ArrayList<>();

    MainModel(MainPresenterImpl mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    // read JSON string from server and apply to listview
    public ArrayList<NoticeData> refreshNoticeList() {
        new FetchNoticeTask(this).execute();
        return noticeList;
    }

    public void addNotice(NoticeData notice) {
        noticeList.add(notice);
    }

    public void clearNotice() {
        noticeList.clear();
    }

    public void setNoticeList(ArrayList<NoticeData> noticeList) {
        this.noticeList.clear();
        this.noticeList.addAll(noticeList);
        this.mainPresenter.onNoticeUpdate();
    }

    public ArrayList<NoticeData> getNoticeList() {
        return noticeList;
    }

    public NoticeData getNotice(int index) {
        return noticeList.get(index);
    }
}
