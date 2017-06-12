package mskim.hyspamplus.data.notice;

import java.util.ArrayList;

public interface LoadNoticeContract {

    interface Callback {

        void onTasksLoaded(ArrayList<Notice> notices);

        void onServerNotAvailable();
    }
}
