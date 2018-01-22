package com.anthony.frameimageeffect.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.anthony.frameimageeffect.R;
import com.anthony.frameimageeffect.widget.SSButton;
import com.anthony.frameimageeffect.widget.SSTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoServer extends FrameLayout implements SurfaceHolder.Callback {
    SSTextView testView;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    byte[] bytes;
    Bitmap bitmap;
    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;
    private final String tag = "VideoServer";

    SSButton start, stop, capture;
    private WeakReference<Activity> mActivity;

    public VideoServer(Activity activity) {
        super(activity);
        this.mActivity = new WeakReference<>(activity);
        init();
    }

    /** Called when the activity is first created. */

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.include_image_view, null);
        start.setOnClickListener(arg0 -> start_camera());
        stop = (SSButton) view.findViewById(R.id.btnStop);
        capture = (SSButton) view.findViewById(R.id.btnCapture);
        stop.setOnClickListener(arg0 -> stop_camera());
        capture.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            captureImage();
        });

        //surfaceView = (SurfaceView) view.findViewById(R.id.camera_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        rawCallback = (data, camera12) -> Log.d("Log", "onPictureTaken - raw");

        /** Handles data for jpeg picture */
        shutterCallback = () -> Log.i("Log", "onShutter'd");
        jpegCallback = (data, camera1) -> {
            try {
                bytes = data;
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            Log.d("Log", "onPictureTaken - jpeg");
        };
        addView(view);
    }

    public byte[] getByteArray() {
        if(bitmap != null) {
            return bytes;
        }
        return null;
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private void captureImage() {
        // TODO Auto-generated method stub
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    private void start_camera()
    {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            Log.e(tag, "init_camera: " + e);
            return;
        }

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
        } else {
            camera.setDisplayOrientation(0);
        }

        Camera.Parameters param;
        param = camera.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e(tag, "init_camera: " + e);
            return;
        }
    }

    private void stop_camera()
    {
        camera.stopPreview();
        camera.release();
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

}