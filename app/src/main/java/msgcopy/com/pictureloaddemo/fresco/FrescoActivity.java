package msgcopy.com.pictureloaddemo.fresco;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import msgcopy.com.pictureloaddemo.R;

import static msgcopy.com.pictureloaddemo.R.id.reduce;

public class FrescoActivity extends AppCompatActivity {

    String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=292050601,2473871589&fm=23&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        findViewById(R.id.basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrescoActivity.this,BaseActivity.class));
            }
        });
        findViewById(R.id.blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrescoActivity.this,BlurActivity.class));
            }
        });
        findViewById(R.id.bitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrescoActivity.this,BitmapActivity.class));
            }
        });
        findViewById(R.id.gif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrescoActivity.this,GifActivity.class));
            }
        });
        findViewById(reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrescoActivity.this,ReduceActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fresco_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        switch (item.getItemId()) {
            case R.id.evictFromMemoryCache:
                imagePipeline.evictFromMemoryCache(uri);
                break;
            case R.id.evictFromDiskCache:
                imagePipeline.evictFromDiskCache(uri);
                break;
            case R.id.clearMemoryCaches:
                imagePipeline.clearMemoryCaches();
                break;
            case R.id.clearDiskCaches:
                imagePipeline.clearDiskCaches();
                break;
            case R.id.clearCaches:
                imagePipeline.clearCaches();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 清除缓存中的一条url
     ImagePipeline现有函数可以删除缓存中的一条url。

     ImagePipeline imagePipeline = Fresco.getImagePipeline();
     Uri uri;
     imagePipeline.evictFromMemoryCache(uri);
     imagePipeline.evictFromDiskCache(uri);

     // combines above two lines
     imagePipeline.evictFromCache(uri);
     如同上面一样，evictFromDiskCache(Uri)假定你使用的是默认的CacheKeyFactory。如果你自定义，请使用evictFromDiskCache(ImageRequest)。

     清除缓存
     ImagePipeline imagePipeline = Fresco.getImagePipeline();
     imagePipeline.clearMemoryCaches();
     imagePipeline.clearDiskCaches();

     // combines above two lines
     imagePipeline.clearCaches();
     *
     */

}
