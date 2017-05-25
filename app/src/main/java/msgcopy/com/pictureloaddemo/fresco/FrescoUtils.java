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
 * android:layout_width="20dp"
 * android:layout_height="20dp"
 * fresco:fadeDuration="300" // 淡出时间，毫秒。
 * fresco:actualImageScaleType="focusCrop" // 等同于android:scaleType。
 * fresco:placeholderImage="@color/wait_color" // 加载中…时显示的图。
 * fresco:placeholderImageScaleType="fitCenter" // 加载中…显示图的缩放模式。
 * fresco:failureImage="@drawable/error" // 加载失败时显示的图。
 * fresco:failureImageScaleType="centerInside" // 加载失败时显示图的缩放模式。
 * fresco:retryImage="@drawable/retrying" // 重试时显示图。
 * fresco:retryImageScaleType="centerCrop" // 重试时显示图的缩放模式。
 * fresco:progressBarImage="@drawable/progress_bar" // 进度条显示图。
 * fresco:progressBarImageScaleType="centerInside" // 进度条时显示图的缩放模式。
 * fresco:progressBarAutoRotateInterval="1000" // 进度条旋转时间间隔。
 * fresco:backgroundImage="@color/blue" // 背景图，不会被View遮挡。
 * <p>
 * fresco:roundAsCircle="false" // 是否是圆形图片。
 * fresco:roundedCornerRadius="1dp" // 四角圆角度数，如果是圆形图片，这个属性被忽略。
 * fresco:roundTopLeft="true" // 左上角是否圆角。
 * fresco:roundTopRight="false" // 右上角是否圆角。
 * fresco:roundBottomLeft="false" // 左下角是否圆角。
 * fresco:roundBottomRight="true" // 左下角是否圆角。
 * fresco:roundingBorderWidth="2dp" // 描边的宽度。
 * fresco:roundingBorderColor="@color/border_color" 描边的颜色。
 */


public class FrescoUtils {

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
