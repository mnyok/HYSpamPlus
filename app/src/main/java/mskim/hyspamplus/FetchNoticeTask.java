package mskim.hyspamplus;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class FetchNoticeTask extends AsyncTask<URL, NoticeData, ArrayList<NoticeData>> {
    MainModel mainModel;

    FetchNoticeTask(MainModel mainModel) {
        super();
        this.mainModel = mainModel;
    }

    @Override
    protected ArrayList<NoticeData> doInBackground(URL... urls) {
        ArrayList<NoticeData> noticeList = new ArrayList<>();
        // TODO: ignore pinned notice
        try {
            Document doc = Jsoup.connect("http://cs.hanyang.ac.kr/board/info_board.php").get();
            Elements notices = doc.select(".bbs_con tbody .left a");

            String url;
            for (Element notice : notices) {
                if (notice.text().equals("")) {
                    continue;
                }
                Log.i("notice data", "[" + notice.nodeName() + "]" + notice.text() + ": " + notice.attr("href"));
                url = notice.baseUri() + notice.attr("href");
                noticeList.add(new NoticeData(notice.text(), url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return noticeList;
    }

    @Override
    protected void onPostExecute(ArrayList<NoticeData> result) {
        Log.i("Fetch Notice Complete", result.size() + " notices");
        mainModel.setNoticeList(result);
    }
}
