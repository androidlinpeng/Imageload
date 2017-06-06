package msgcopy.com.pictureloaddemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by liang on 2017/5/19.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    private DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        Fresco.initialize(this);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
                //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(500))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        //缩放类型mageScaleType:
        //EXACTLY :图像将完全按比例缩小的目标大小
        //EXACTLY_STRETCHED:图片会缩放到目标大小完全
        //IN_SAMPLE_INT:图像将被二次采样的整数倍
        //IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
        //NONE:图片不会调整

        //显示方式displayer
        //RoundedBitmapDisplayer（int roundPixels）设置圆角图片
        //FakeBitmapDisplayer（）这个类什么都没做
        //FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
        //SimpleBitmapDisplayer()正常显示一张图片

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public DisplayImageOptions getOptions(){
        return options;
    }

    public void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
//                .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
//                .taskExecutor(...)
//                .taskExecutorForCachedImages(...)
                .threadPoolSize(3) //线程池内加载的数量,建议1-5
                .threadPriority(Thread.NORM_PRIORITY - 1) //设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
//                .discCache(new UnlimitedDiscCache(cacheDir)) //自定义缓存路径
                .discCache(new LimitedAgeDiscCache(cacheDir, 7 * 24 * 60 * 60))// 自定义缓存路径,7天后自动清除缓存
                .diskCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)//缓存的文件数量
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
//                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) //使用HASHCODE对UIL进行加密命名
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
    }
}
