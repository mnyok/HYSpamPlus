package mskim.hyspamplus;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by orc12 on 2016-09-16.
 */
public class ServerHelper {

    public String getNoticeList() throws InterruptedException {
        final String[] readString = new String[1];

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("http://52.78.61.184:8025");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //read input stream and add split list to noticeList
                    readString[0] = readStream(in);

                    in.close();

                    urlConnection.disconnect();
                } catch (Exception e) {
//                  @TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();

        return readString[0];
    }

    // read from InputStream, convert to String
    private String readStream(InputStream in) throws Exception{
        String message = "";
        int c;

        InputStreamReader streamReader = new InputStreamReader(in);
        Log.i("encoding", streamReader.getEncoding());

        c = streamReader.read();
        while (c != -1) {
            message += (char) c;
            c = streamReader.read();
        }

        streamReader.close();
        return message;
    }
}
