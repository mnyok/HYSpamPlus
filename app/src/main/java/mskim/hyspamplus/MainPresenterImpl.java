package mskim.hyspamplus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class MainPresenterImpl implements MainPresenter{
    private Activity activity;
    private MainModel mainModel;
    private MainPresenterImpl.View view;

    public MainPresenterImpl(Activity activity){
        this.activity = activity;
        this.mainModel = new MainModel();
    }

    @Override
    public void setView(MainPresenterImpl.View view){
        this.view = view;
    }

    @Override
    public void onNoticeClick(int position){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW
                , Uri.parse(mainModel.getNotice(position).getUrlString()));
        activity.startActivity(browserIntent);
    }

    @Override
    public void updateNoticeList(){
        mainModel.refreshNoticeList();
        if (mainModel.getNoticeList().isEmpty()) {
            view.showSnackBar("Server Error");
        }
        view.setNoticeList(mainModel.getNoticeList());
    }

    @Override
    public boolean togglePushSetting(){
        return SharedPreferenceManager.togglePush(activity);

    }
}
