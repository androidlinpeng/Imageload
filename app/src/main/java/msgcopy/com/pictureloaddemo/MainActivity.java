package msgcopy.com.pictureloaddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import msgcopy.com.pictureloaddemo.fresco.FrescoActivity;
import msgcopy.com.pictureloaddemo.glide.GlideBaseActivity;
import msgcopy.com.pictureloaddemo.imageloader.ImageLoaderActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.fresco:
                startActivity(new Intent(MainActivity.this,FrescoActivity.class));
                break;
            case R.id.image_loader:
                startActivity(new Intent(MainActivity.this,ImageLoaderActivity.class));
                break;
            case R.id.glide:
                startActivity(new Intent(MainActivity.this,GlideBaseActivity.class));
                break;
            default:
                break;
        }
    }
}
