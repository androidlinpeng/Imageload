package msgcopy.com.pictureloaddemo.fresco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;

import msgcopy.com.pictureloaddemo.R;

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        String url = "http://img4.178.com/acg1/201506/227753817857/227754566617.gif";
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdv_gif);
        FrescoUtils.LoadGif(simpleDraweeView,url);
    }
}
