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

    // convert JSON string to Arraylist<Notice> format
    public static ArrayList<Notice> listFromJSONString(String inputString) {
        ArrayList<Notice> noticeDatas = new ArrayList<>();
        JSONObject jsonObject = null;
        noticeDatas.clear();
        Log.i("input JSON string", inputString + " ");
        try {
            jsonObject = new JSONObject(inputString);
            JSONArray arr = jsonObject.getJSONArray("items");
            for (int i = 0; i < arr.length(); i++) {
                Notice dataNode = new Notice();

                dataNode.setTitle(arr.getJSONObject(i).getString("title"));
                dataNode.setUrl(arr.getJSONObject(i).getString("link"));
                Log.i("dataNode title", dataNode.getTitle() + " / " + dataNode.getUrlString());

                noticeDatas.add(dataNode);
            }
        } catch (NullPointerException e) {
            Log.e("JSON", "input string is null");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON", "Parse Error");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("JSON", "Malformed URL Error");
        }

        return noticeDatas;
    }
}
