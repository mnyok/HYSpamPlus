package mskim.hyspamplus;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by orc12 on 2016-09-16.
 */
public class NoticeData {
    private String title;
    private URL url;

    public NoticeData(){
    }

    public NoticeData(String title, String url){
        this.title = title;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    String getTitle(){
        return title;
    }

    void setTitle(String title){
        this.title = title;
    }

    URL getUrl(){
        return url;
    }

    String getUrlString(){
        return url.toString();
    }

    void setUrl(URL url){
        this.url = url;
    }

    void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    // convert JSON string to Arraylist<NoticeData> format
    static ArrayList<NoticeData> listFromJSONString(String inputString){
        ArrayList<NoticeData> noticeDatas = new ArrayList<>();
        JSONObject jsonObject = null;
        noticeDatas.clear();
        try {
            jsonObject = new JSONObject(inputString);
            JSONArray arr = jsonObject.getJSONArray("items");
            for (int i = 0; i <arr.length(); i++){
                NoticeData dataNode = new NoticeData();

                dataNode.setTitle(arr.getJSONObject(i).getString("title"));
                dataNode.setUrl(arr.getJSONObject(i).getString("link"));
                Log.i("dataNode title", dataNode.getTitle() + " / " + dataNode.getUrlString());

                noticeDatas.add(dataNode);
            }
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
