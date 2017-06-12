package mskim.hyspamplus.notices;

import java.util.ArrayList;

import mskim.hyspamplus.BasePresenter;
import mskim.hyspamplus.BaseView;
import mskim.hyspamplus.data.notice.Notice;

public interface NoticesContract {
    interface View extends BaseView<Presenter>{

        void showNotices(ArrayList<Notice> noticeList);

        void showEmptyNotices();

        void showLoadError();

        void showSnackBar(String string);

        void openNotice(String url);

    }

    interface Presenter extends BasePresenter{

        void loadNotices();

        void onNoticeClick(Notice notice);
    }
}
