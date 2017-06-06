package msgcopy.com.pictureloaddemo.fresco;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import msgcopy.com.pictureloaddemo.MyApplication;

import static com.facebook.drawee.backends.pipeline.Fresco.newDraweeControllerBuilder;

/**
 * Created by liang on 2017/5/22.
 */

/**
 * FrescoDraweeView在xml中设置的属性说明：
 * android:id="@+id/my_image_view" 【属性说明】id
 * android:layout_width="20dp" 【属性说明】设置宽度，不支持wrap，如果要设置宽高比, 需要在Java代码中指定，setAspectRatio(1.33f);
 * android:layout_height="20dp" 【属性说明】设置高度，不支持wrap
 * fresco:fadeDuration="300" 【属性说明】显示动画设置时长，单位毫秒
 * fresco:actualImageScaleType="focusCrop" 【属性说明】设置图片缩放. 通常使用focusCrop,该属性值会通过算法把人头像放在中间
 * fresco:placeholderImage="@color/wait_color" 【属性说明】默认图片（下载成功之前显示的图片）
 * fresco:placeholderImageScaleType="fitCenter"
 * fresco:failureImage="@drawable/error" 【属性说明】加载失败的时候显示的图片
 * fresco:failureImageScaleType="centerInside"
 * fresco:retryImage="@drawable/retrying" 【属性说明】加载失败,提示用户点击重新加载的图片(会覆盖failureImage的图片)
 * fresco:retryImageScaleType="centerCrop"
 * fresco:progressBarImage="@drawable/progress_bar" 【属性说明】提示用户正在加载,和加载进度无关
 * fresco:progressBarImageScaleType="centerInside"
 * fresco:progressBarAutoRotateInterval="1000"
 * fresco:backgroundImage="@color/blue"
 * fresco:overlayImage="@drawable/watermark"
 * fresco:pressedStateOverlayImage="@color/red"
 * fresco:roundAsCircle="false" 【属性说明】是不是设置圆圈
 * fresco:roundedCornerRadius="1dp" 【属性说明】圆角角度,180的时候会变成圆形图片
 * fresco:roundTopLeft="true"
 * fresco:roundTopRight="false"
 * fresco:roundBottomLeft="false"
 * fresco:roundBottomRight="true"
 * fresco:roundWithOverlayColor="@color/corner_color"
 * fresco:roundingBorderWidth="2dp"
 * fresco:roundingBorderColor="@color/border_color"
 */


public class FrescoUtils {

    public static void ReducePic(SimpleDraweeView draweeView, String url,int reqWidth, int reqHeight){

        Uri uri = Uri.parse(url);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(reqWidth,reqHeight))//图片压缩.jpg
                .setProgressiveRenderingEnabled(true)//支持图片渐进式加载
                .setAutoRotateEnabled(true) //如果图片是侧着,可以自动旋转
                .build();
        DraweeController draweeController = newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(imageRequest)
                .build();
        draweeView.setController(draweeController);
    }

    public static void LoadGif(SimpleDraweeView draweeView, String url){
        Uri uri = Uri.parse(url);
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        imageRequestBuilder.setRotationOptions(RotationOptions.autoRotate());
        imageRequestBuilder.setProgressiveRenderingEnabled(true);//支持图片渐进式加载
        if (UriUtil.isLocalFileUri(uri)) {
            imageRequestBuilder.setLocalThumbnailPreviewsEnabled(true);
        }
        ImageRequest imageRequest = imageRequestBuilder.build();
        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        draweeControllerBuilder.setOldController(draweeView.getController());
        draweeControllerBuilder.setImageRequest(imageRequest);
        draweeControllerBuilder.setTapToRetryEnabled(true); // 开启重试功能
        draweeControllerBuilder.setAutoPlayAnimations(true); // 自动播放gif动画
        DraweeController draweeController = draweeControllerBuilder.build();
        draweeView.setController(draweeController);
    }

    /**
     * 加载bitmap
     *
     * @param imageView
     * @param url
     * @param reqWidth
     * @param reqHeight
     */
    public static void LoadBitmap(final ImageView imageView, String url, int reqWidth, int reqHeight){

        Uri uri = Uri.parse(url);

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        //检查bitmap是否在缓存中
//        boolean inMemoryCache = imagePipeline.isInBitmapMemoryCache(uri);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(reqWidth, reqHeight))
                .build();
        // 获取已解码的图片，返回的是Bitmap
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, MyApplication.getInstance());
        DataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableBitmap>>() {
            @Override
            public void onNewResultImpl(DataSource<CloseableReference<CloseableBitmap>> dataSource) {
                if (!dataSource.isFinished()) {
                    return;
                }
                CloseableReference<CloseableBitmap> imageReference = dataSource.getResult();
                if (imageReference != null) {
                    final CloseableReference<CloseableBitmap> closeableReference = imageReference.clone();
                    try {
                        CloseableBitmap closeableBitmap = closeableReference.get();
                        Bitmap bitmap = closeableBitmap.getUnderlyingBitmap();
                        if (bitmap != null && !bitmap.isRecycled()) {
                            final Bitmap tempBitmap = bitmap.copy(bitmap.getConfig(), false);
                            imageView.setImageBitmap(tempBitmap);
                        }
                    } finally {
                        imageReference.close();
                        closeableReference.close();
                    }
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                Throwable throwable = dataSource.getFailureCause();
                if (throwable != null) {

                }
            }
        };
        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }

    /**
     * 设置淡入淡出效果
     */
    public static void GenericDraweeHierarchy(SimpleDraweeView draweeView, String url) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(draweeView.getResources());
        /**
         * 设置淡入淡出效果的显示时间
         * */
        Uri uri = Uri.parse(url);
        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(3000).build();
        DraweeController placeHolderDraweeController = newDraweeControllerBuilder()
                .setUri(uri) //为图片设置url
                .setTapToRetryEnabled(true) //设置在加载失败后,能否重试
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(placeHolderDraweeController);
        draweeView.setHierarchy(hierarchy);
    }

    /**
     * 以高斯模糊显示。
     *
     * @param draweeView View。
     * @param url        url.
     * @param iterations 迭代次数，越大越魔化。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
                    .setProgressiveRenderingEnabled(true)//支持图片渐进式加载
                    .build();
            AbstractDraweeController controller = newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showResIdBlur(SimpleDraweeView draweeView, int resId, int iterations, int blurRadius) {
        try {
            ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(resId)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
                    .setProgressiveRenderingEnabled(true)//支持图片渐进式加载
                    .build();
            AbstractDraweeController controller = newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
