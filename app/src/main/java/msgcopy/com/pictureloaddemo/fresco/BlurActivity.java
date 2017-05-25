package msgcopy.com.pictureloaddemo.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import msgcopy.com.pictureloaddemo.R;

public class BlurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=292050601,2473871589&fm=23&gp=0.jpg";

        Uri uri = Uri.parse(url);
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdv_blur);
        draweeView.setAspectRatio(0.7f);
        FrescoUtils.showUrlBlur(draweeView,url,6,6);
    }
}
