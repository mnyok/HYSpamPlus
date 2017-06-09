package mskim.hyspamplus.notices;

import android.util.Log;

import java.util.ArrayList;

import mskim.hyspamplus.data.notice.Notice;
import mskim.hyspamplus.data.notice.LoadNoticeTask;
import mskim.hyspamplus.data.notice.LoadNoticeContract;

public class NoticesPresenter implements NoticesContract.Presenter {
    private NoticesContract.View view;

    NoticesPresenter(NoticesContract.View view){
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadNotices();
    }

    @Override
    public void loadNotices(){
        new LoadNoticeTask(new LoadNoticeContract.Callback() {
            @Override
            public void onTasksLoaded(ArrayList<Notice> notices) {
                Log.i("load Notices", "finished");

                if (notices.isEmpty()) {
                    view.showEmptyNotices();
                } else {
                    view.showNotices(notices);
                }
            }

            @Override
            public void onServerNotAvailable() {
                view.showLoadError();
            }
        }).execute();
    }

    // TODO: separate to handler class
    @Override
    public void onNoticeClick(Notice notice) {
        Log.i("Notice clicked", notice.getTitle());
        view.openNotice(notice.getUrlString());
    }
}
