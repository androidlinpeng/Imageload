package msgcopy.com.pictureloaddemo.imageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import msgcopy.com.pictureloaddemo.R;

public class ImageShowTypeActivity extends ImageLoadBaseActivity {

    private String imageUrl = "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg";

    private ImageView ivNormal,ivFillet,ivCircular,image4;

    private DisplayImageOptions optionsN,optionsF,optionsC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show_type);

        ivNormal = (ImageView) findViewById(R.id.ivNormal);
        ivFillet = (ImageView) findViewById(R.id.ivFillet);
        ivCircular = (ImageView) findViewById(R.id.ivCircular);
        image4 = (ImageView) findViewById(R.id.image4);

        showNormal();
        showFillet();
        showCircular();

    }

    private void showCircular() {
        optionsN = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
        imageLoader.displayImage(imageUrl,ivNormal,optionsN);

    }

    private void showFillet() {
        optionsF = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
        imageLoader.displayImage(imageUrl,ivFillet,optionsF);
    }

    private void showNormal() {
        optionsC = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(1000))
                .displayer(new Displayer(0))
                .build();
        imageLoader.displayImage(imageUrl,ivCircular,optionsC);
    }


}
