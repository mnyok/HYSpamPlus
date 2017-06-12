package mskim.hyspamplus.data.notice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Notice {
    private String title;
    private URL url;

    Notice() {
    }

    public Notice(String title, String url) {
        this.title = title;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            setErrorUrl();
        }
    }

    private void setErrorUrl(){
        try {
            this.url = new URL("http://cs.hanyang.ac.kr/ready.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public String getUrlString() {
        return url.toString();
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
}
