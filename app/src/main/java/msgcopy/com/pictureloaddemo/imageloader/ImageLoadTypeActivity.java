package msgcopy.com.pictureloaddemo.imageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import msgcopy.com.pictureloaddemo.MyApplication;
import msgcopy.com.pictureloaddemo.R;

public class ImageLoadTypeActivity extends ImageLoadBaseActivity {

    private static final String TAG = "ImageLoadTypeActivity";

    private String imageUrl = "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg";

    private ImageView image_load,image_options,image_listener,image_progress,image_size;

    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load_type);

        options = MyApplication.getInstance().getOptions();

        image_load = (ImageView) findViewById(R.id.image_load);
        image_options = (ImageView) findViewById(R.id.image_options);
        image_listener = (ImageView) findViewById(R.id.image_listener);
        image_progress = (ImageView) findViewById(R.id.image_progress);
        image_size = (ImageView) findViewById(R.id.image_size);

        //displayImage()加载图片
        ImageLoad();
        ImageLoadOptions();
        ImageLoadListener();
        ImageLoadProgress();

        //loadimage()加载图片
        ImageLoadSize();

    }

    private void ImageLoadSize() {
        ImageSize imageSize = new ImageSize(300,300);
        ImageLoader.getInstance().loadImage(imageUrl,imageSize,options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.i(TAG,"ImageLoadProgress: Started");

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.i(TAG,"ImageLoadProgress: Failed");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.i(TAG,"ImageLoadProgress: Complete");
                image_size.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.i(TAG,"ImageLoadProgress: Cancelled");
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                //在这里更新 ProgressBar的进度信息
                Log.i(TAG,"ImageLoadProgress  total: "+total+"    current: "+current);
            }
        });
    }


    private void ImageLoadProgress() {
        Log.i(TAG,"ImageLoadProgress: ");
        imageLoader.displayImage(imageUrl, image_progress, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.i(TAG,"ImageLoadProgress: Started");

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.i(TAG,"ImageLoadProgress: Failed");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.i(TAG,"ImageLoadProgress: Complete");
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.i(TAG,"ImageLoadProgress: Cancelled");
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                //在这里更新 ProgressBar的进度信息
                Log.i(TAG,"ImageLoadProgress  total: "+total+"    current: "+current);
            }
        });
    }

    private void ImageLoadListener() {
        Log.i(TAG,"ImageLoadListener: ");
        imageLoader.displayImage(imageUrl, image_listener, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                //开始加载的时候执行
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                //加载失败的时候执行
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                //加载成功的时候执行
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                //加载取消的时候执行
            }
        });
    }

    private void ImageLoadOptions() {
        Log.i(TAG,"ImageLoadOptions: ");
        imageLoader.displayImage(imageUrl, image_options,options);
    }

    private void ImageLoad() {
        Log.i(TAG,"ImageLoad: ");
        imageLoader.displayImage(imageUrl, image_load);
    }

}
