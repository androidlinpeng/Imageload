package msgcopy.com.pictureloaddemo.fresco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import msgcopy.com.pictureloaddemo.R;

public class ReduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduce);
        String url = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=190550685,1042291559&fm=23&gp=0.jpg";
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdv_reduce);
        FrescoUtils.ReducePic(draweeView,url,300,200);
    }
}
