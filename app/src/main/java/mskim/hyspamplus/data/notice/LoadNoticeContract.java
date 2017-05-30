package mskim.hyspamplus.data.notice;

import java.util.ArrayList;

/**
 * Created by orc12 on 2017-05-30.
 */

public interface LoadNoticeContract {

    interface LoadNoticeCallback {

        void onTasksLoaded(ArrayList<Notice> notices);

        void onServerNotAvailable();
    }
}
