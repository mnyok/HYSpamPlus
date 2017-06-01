package mskim.hyspamplus.data.notice;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class LoadNoticeTask extends AsyncTask<URL, Notice, ArrayList<Notice>> {
    private LoadNoticeContract.Callback callback;

    public LoadNoticeTask(LoadNoticeContract.Callback callback) {
        super();
        this.callback = callback;
    }

    @Override
    protected ArrayList<Notice> doInBackground(URL... urls) {
        ArrayList<Notice> noticeList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://cs.hanyang.ac.kr/board/info_board.php").get();
            Elements notices = doc.select(".bbs_con tbody tr");

            String url;
            for (Element notice : notices) {
                Element font = notice.select("td font").first();
                if (font != null) {
                    // Pinned Notice
                    continue;
                }

                notice = notice.select(".left a").first();
                if (notice == null || notice.text().equals("")) {
                    continue;
                }
                Log.i("notice data", notice.text() + " : " + notice.attr("href"));
                url = notice.baseUri() + notice.attr("href");
                noticeList.add(new Notice(notice.text(), url));
            }
        } catch (IOException e) {
            // In case 404: Unable to resolve host "cs.hanyang.ac.kr": No address associated with hostname
            Log.e("Load error", e.getMessage());
            cancel(true);
        }

        return noticeList;
    }

    @Override
    protected void onPostExecute(ArrayList<Notice> result) {
        Log.i("Fetch Notice Complete", result.size() + " notices");
        callback.onTasksLoaded(result);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onServerNotAvailable();
    }
}
