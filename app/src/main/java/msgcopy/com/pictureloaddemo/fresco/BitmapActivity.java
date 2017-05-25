package msgcopy.com.pictureloaddemo.fresco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import msgcopy.com.pictureloaddemo.R;

public class BitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=292050601,2473871589&fm=23&gp=0.jpg";
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        FrescoUtils.LoadBitmap(imageView,url,100,50);
    }
}
