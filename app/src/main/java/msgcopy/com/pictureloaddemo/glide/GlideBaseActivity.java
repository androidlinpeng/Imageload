package msgcopy.com.pictureloaddemo.glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import msgcopy.com.pictureloaddemo.R;

public class GlideBaseActivity extends AppCompatActivity implements View.OnClickListener{

    String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=292050601,2473871589&fm=23&gp=0.jpg";

    private ImageView image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_base);

        image1 = (ImageView) findViewById(R.id.image1);

//        Glide.with(this).load(url).into(image1);

        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)//加载时图片
                .error(R.mipmap.ic_launcher)//加载失败图片
                .skipMemoryCache(true)//设置跳过内存缓存
                .priority(Priority.NORMAL)//设置下载优先级
                .diskCacheStrategy(DiskCacheStrategy.ALL)//设置缓存策略(all:缓存源资源和转换后的资源 none:不作任何磁盘缓存 source:缓存源资源 result：缓存转换后的资源)
                .animate(R.anim.alpha)
                .thumbnail(0.1f)
                .override(600,600)//设置加载尺寸
//                .centerCrop()//设置动态转换
//                .fitCenter()
                .transform(new GlideRoundTransform(this,200))//圆角转化器
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        imageView1.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(image1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clearDiskCache:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(GlideBaseActivity.this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                    }
                }).start();
                break;
            case R.id.clearMemory:
                Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
                break;
        }
    }
}
