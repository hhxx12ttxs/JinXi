package cn.jinxi.user.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jinxi.R;
import cn.jinxi.utils.DataUtils;

/**
 * Created by user on 2015/10/18.
 */
public class ImageManager {

    private static ImageManager instance;
    private ImageLoader imageLoader;
    // options
    private DisplayImageOptions portraitOption;
    private DisplayImageOptions defaultOption;

    private ImageManager(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        portraitOption = new DisplayImageOptions.Builder() // setting
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_head_set)
                .showImageOnLoading(R.drawable.img_head_set)
                .showImageOnFail(R.drawable.img_head_set)
                .cacheOnDisk(true)
                .build();

        defaultOption = new DisplayImageOptions.Builder() // setting
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    public static ImageManager GetInstance(Context context) {
        if (instance == null) {
            synchronized (ImageManager.class) {
                if (instance == null) {
                    instance = new ImageManager(context);
                }
            }
        }
        return instance;
    }

    public static ImageManager GetInstance() {
        return instance;
    }

    public void displayPortrait(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, portraitOption);
    }

    public void displayDefaultImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, defaultOption);
    }

    public long getCacheSize() {
        return DataUtils.GetFolderSize(imageLoader.getDiskCache().getDirectory());
    }

    public void clearCache() {
        imageLoader.clearDiskCache();
    }

    public String getPortraitPath() {
        String path = "img_portrait.jpg";
        return GlobalSettingManager.GetCachePath() + "img_portrait.jpg";
    }


    public String getResizedPortraitPath() {
        return GlobalSettingManager.GetCachePath() + "img_portrait_resized.jpg";
    }


    //裁剪图片
    public Bitmap clipImage(String imageUri, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageUri, options);


        int beWidth = options.outWidth / width;
        int beHeight = options.outHeight / height;
        int size = beWidth < beHeight ? beWidth : beHeight;


        options.inJustDecodeBounds = false;
        options.inSampleSize = size <= 1 ? 1 : size;
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri, options);
        //
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        int degree = readPhotoRotateDegree(imageUri);
        if (degree > 0) {
            bitmap = rotateBitmap(degree, bitmap);
        }

        return bitmap;
    }

    //压缩图片
    public void compressImage(String imageUri) {
        try {
            Bitmap bitmap = clipImage(imageUri, 400, 400);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getResizedPortraitPath()));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap newbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return newbitmap;
    }

    public int readPhotoRotateDegree(String imageUri) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        return degree;
    }
}
