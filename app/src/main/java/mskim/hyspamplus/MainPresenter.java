package mskim.hyspamplus;

import java.util.ArrayList;

public interface MainPresenter {

    void setView(MainPresenter.View view);

    void onNoticeClick(int position);

    void updateNoticeList();

    boolean togglePushSetting();

    interface View {
        void setNoticeList(ArrayList<NoticeData> noticeList);
        void showSnackBar(String string);
    }
}
