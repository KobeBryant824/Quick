package com.cxh.library.imageloader;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.cxh.library.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Desc: Fresco图片加载
 * Created by Hai (haigod7@gmail.com) on 2017/5/25 9:35.
 */
public class FrescoUtils {

    /**
     * 默认加载1
     *
     * @param path       图片地址
     * @param draweeView SimpleDraweeView
     */
    public static void loadImage(String path, SimpleDraweeView draweeView) {
        draweeView.setImageURI(path);
    }

    /**
     * 默认加载2
     *
     * @param path       图片地址
     * @param draweeView SimpleDraweeView
     * @param resources  Resources
     */
    public static void loadImage(String path, SimpleDraweeView draweeView, Resources resources) {
        loadImage(path, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, resources, draweeView);
    }

    /**
     * 默认加载配置
     *
     * @param path           图片地址
     * @param placeholderRes 加载中
     * @param failureRes     失败
     * @param retryRes       重试
     * @param resources      Resources
     * @param draweeView     SimpleDraweeView
     */
    public static void loadImage(String path, @DrawableRes int placeholderRes, @DrawableRes int failureRes,
                                 @DrawableRes int retryRes, Resources resources, SimpleDraweeView draweeView) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
        GenericDraweeHierarchy hierarchy = builder
                .setPlaceholderImage(placeholderRes)
                .setFailureImage(failureRes)
                .setRetryImage(retryRes)
                .build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setImageURI(path);
    }

    /**
     * 自定义图片尺寸
     *
     * @param path       图片地址
     * @param draweeView 显示的控件
     * @param width      50
     * @param height     50
     */
    public static void loadImage(String path, SimpleDraweeView draweeView, int width, int height) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(path))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 下方显示深蓝色的矩形进度条
     *
     * @param path       图片地址
     * @param draweeView SimpleDraweeView
     * @param resources  Resources
     */
    public static void loadImage(String path, Resources resources, SimpleDraweeView draweeView) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
        GenericDraweeHierarchy hierarchy = builder
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setImageURI(path);
    }

    /**
     * 先加载低分辨率图片，再加载高分辨率图片
     *
     * @param lowPath    低分辨率图片
     * @param heighPath  高分辨率图片
     * @param draweeView SimpleDraweeView
     */
    public static void loadImage(String lowPath, String heighPath, SimpleDraweeView draweeView) {
        Uri lowResUri = Uri.parse(lowPath);
        Uri highResUri = Uri.parse(heighPath);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                .setImageRequest(ImageRequest.fromUri(highResUri))
                .setOldController(draweeView.getController()) // 在指定一个新的controller的时候，使用setOldController，这可节省不必要的内存分配。
                .build();
        draweeView.setController(controller);
    }

    /**
     * 图片加载监听
     *
     * @param path               图片地址
     * @param draweeView         SimpleDraweeView
     * @param controllerListener ControllerListener<ImageInfo>
     */
    public static void loadImage(String path, SimpleDraweeView draweeView, ControllerListener<ImageInfo> controllerListener) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(path)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 开启重试
     *
     * @param path       图片地址
     * @param draweeView SimpleDraweeView
     */
    public static void loadImageRetry(String path, SimpleDraweeView draweeView) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(path)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 播放Gif
     *
     * @param path       图片地址
     * @param draweeView SimpleDraweeView
     */
    public static void loadGif(String path, SimpleDraweeView draweeView) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(path)
                .setAutoPlayAnimations(true)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 加载渐进式的网络JPEG图
     *
     * @param path       图片地址
     * @param draweeView SimpleDraweeView
     */
    public static void loadProgressiveImage(String path, SimpleDraweeView draweeView) {
        Uri uri0 = Uri.parse(path);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri0)
                .setProgressiveRenderingEnabled(true)   // 在开始加载之后，图会从模糊到清晰渐渐呈现。
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }
}
