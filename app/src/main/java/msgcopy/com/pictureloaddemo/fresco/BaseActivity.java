package msgcopy.com.pictureloaddemo.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import msgcopy.com.pictureloaddemo.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=292050601,2473871589&fm=23&gp=0.jpg";

        Uri uri = Uri.parse(url);
        SimpleDraweeView draweeView1 = (SimpleDraweeView) findViewById(R.id.sdv_1);
//        draweeView1.setImageURI(uri);
        FrescoUtils.GenericDraweeHierarchy(draweeView1,url);

        SimpleDraweeView draweeView2 = (SimpleDraweeView) findViewById(R.id.sdv_2);
        draweeView2.setImageURI(uri);

        SimpleDraweeView draweeView3 = (SimpleDraweeView) findViewById(R.id.sdv_3);
        draweeView3.setImageURI(uri);

        SimpleDraweeView draweeView4 = (SimpleDraweeView) findViewById(R.id.sdv_4);
        draweeView4.setImageURI(uri);

        SimpleDraweeView draweeView5 = (SimpleDraweeView) findViewById(R.id.sdv_5);
        draweeView5.setImageURI(uri);

        SimpleDraweeView draweeView6 = (SimpleDraweeView) findViewById(R.id.sdv_6);
        draweeView6.setImageURI(uri);

        SimpleDraweeView draweeView7 = (SimpleDraweeView) findViewById(R.id.sdv_7);
        draweeView7.setImageURI(uri);

        SimpleDraweeView draweeView8 = (SimpleDraweeView) findViewById(R.id.sdv_8);
        draweeView8.setImageURI(uri);

        SimpleDraweeView draweeView9 = (SimpleDraweeView) findViewById(R.id.sdv_9);
        draweeView9.setImageURI(uri);

        SimpleDraweeView draweeView10 = (SimpleDraweeView) findViewById(R.id.sdv_10);
        draweeView10.setImageURI(uri);

        SimpleDraweeView draweeView11 = (SimpleDraweeView) findViewById(R.id.sdv_11);
        draweeView11.setImageURI(uri);
    }
}
