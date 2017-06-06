package msgcopy.com.pictureloaddemo.imageloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import msgcopy.com.pictureloaddemo.R;

/**
 * 注意事项
 1.上述提到的2个权限必须加入，否则会出错
 2.ImageLoaderConfiguration必须配置并且全局化的初始化这个配置ImageLoader.getInstance().init(config);  否则也会出现错误提示
 3.ImageLoader是根据ImageView的height，width确定图片的宽高。
 4.如果经常出现OOM（别人那边看到的，觉得很有提的必要）
 ①减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
 ②使用.bitmapConfig(Bitmap.config.RGB_565)代替ARGB_8888;
 ③使用.imageScaleType(ImageScaleType.IN_SAMPLE_INT)或者        try.imageScaleType(ImageScaleType.EXACTLY)；
 ④避免使用RoundedBitmapDisplayer.他会创建新的ARGB_8888格式的Bitmap对象；
 ⑤使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();
 */

/**
 *加载图片类型
 String imageUri = "http://site.com/image.png"; // from Web
 String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
 String imageUri = "content://media/external/audio/albumart/13"; // from content provider
 String imageUri = "assets://image.png"; // from assets
 String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
 */

public class ImageLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        findViewById(R.id.tv_load_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageLoaderActivity.this,ImageLoadTypeActivity.class));
            }
        });
        findViewById(R.id.tv_show_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageLoaderActivity.this,ImageShowTypeActivity.class));
            }
        });

    }

}
