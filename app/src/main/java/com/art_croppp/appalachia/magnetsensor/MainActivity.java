package com.art_croppp.appalachia.magnetsensor;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;



public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback{

   /* private Camera ACamera;
    private Camera BCamera;
    private CameraPreview APreview;*/
    //  private CameraPreviewA BPreview;
    public Camera.PictureCallback APicture;
    private Camera.PictureCallback BPicture;
    private Button capture, switchCamera;
    private Context myContext;
    private LinearLayout AcameraPreview;
    private LinearLayout TakinglayoutInvisible;
    private LinearLayout TakinglayoutInvisibletopleft;
    private LinearLayout TakinglayoutInvisiblebottomright;
    //private LinearLayout a, b, c , d , e , f, g , h, i , j, k , l;
    private boolean cameraFront = false;
    private ImageView AiView;
    private BitmapDrawable Adrawable;
    private Bitmap Abitma;
    /*private FrameLayout Aview;
    private FrameLayout Bview;*/
    private Bitmap frobackBitmaps[] = new Bitmap[10];
    private boolean firstTakenBoolean = false;
    private int froBackInt = 0;
    private boolean FRONT90Or270 = true;
    private int sw;
    private int sh;
    private boolean tappedFrontLittle = false;
    private float leftLittleMove = 0f;
    private float topLIttleMove = 0f;
    private Bitmap biggerLittleOneForPath;
    private boolean initializeCameraOnceONActivityStart = false;

    private boolean FrontORBackPreviewOnTake = false;
    /*   Matrix rotateRight = new Matrix();

       int Abc=0;
       private Handler myHandler = new Handler();

       long finalTime = 0L;

       int A=5;*/
    private long startTime;
    int Ax = 0;
    int Ab = 0;
    private Handler mHandler;
    private final int ACTION_ADMOB_IS_LOADED = 1;
    private final int ACTION_EDIT_MODE = 3;
    private final int ACTION_TAKE_PICTURE = 4;
    private final int  ACTION_SWITCH_FRONT_BACK = 5;
    private final int ACTION_SAVE = 6;
    private final int ACTION_ZOOM = 7;
    private final int ACTION_FLASH = 8;
    private final int ACTION_RANDOM_FLASH = 9;
    GameView gameView;
    private int camID = 0 ;
    private int delayPeriod = 0;
    private final int ACTION_ADMOB_START_TIME = 2;
    private boolean newDelayPeriod = false;
    private boolean firstTaken = true;

    private int cameraFroBack =0;
    private int[] frobackDirection = new int[5];
    private int FroBackkk = 0;
    private Sensor ABCDEFGammaMag;
    private static SensorManager sensorService;
    private Sensor sensor;
    private boolean frobackONE = false;
    private boolean frobackTWO = false;
    private boolean frobackBackFroONE = false;
    private boolean frobackBackFroTWO = false;
    private boolean bitmapFiVE = false;
    private float abcup[] = new float[10];
    private float defgright[] = new float[10];
    private float hijarmreach[] = new float[10];
    private boolean clickedabcdefghij[] = new boolean[10];
    private int positionFroback[] = new int[10];
    /*private float fiveLeft = 0f;
    private float fiveTop = 0f;
    private boolean movePositionback = false;
    private float fiveLittleHeight = 0;
    private float fiveLittleWidth = 0;*/
    private float rightleft = 0;
    private float updown = 0;
    private boolean reSizeTheLittleOne = false;
    private int ALittleHeight = 0;
    private int ALittleWidth = 0;
    private LinearLayout linearLayout;
    private int zoom =0;
    private int zoomMax = 0;
    private boolean zoomInbool = false;
    private boolean zoomOutbool = false;
    private Bitmap mainBitmap,minusZoom,plusZoom;
    private boolean retryTosetZoom = false;
    private boolean initializeOnce = true;
    private Bitmap gallery;
    private String GalleryBitmapString;
    private Bitmap switchhh;
    private float toggleX = 0f;
    private float toggleTopY = 0f;
    private float toggleBottomY = 0f;
    private float mDist = 0f;
    private Bitmap flip;
    private Bitmap drawTwoBitmap;
    private Bitmap drawOneBitmap;


    /*private boolean resendMessageToStartCamera = false;
    private boolean pauseBoolean = false;*/
   /* private boolean startFlashingSequence = false;
    private int flashingCounter = 0;
    private LinearLayout ArrayoFLayout[] = new LinearLayout[30];*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        gameView = new GameView(this);
        linearLayout = (LinearLayout) findViewById(R.id.Bcam_preview);
        FroBackkk = 1;

        // The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        myContext = this;





        /*TakinglayoutInvisibletopleft = findViewById(R.id.tratopleft);
        TakinglayoutInvisiblebottomright = findViewById(R.id.trabottomright);*/

//        ArrayoFLayout[0] = findViewById(R.id.tra1);
//        ArrayoFLayout[1] = findViewById(R.id.tra3);
//        ArrayoFLayout[2] = findViewById(R.id.tra5);
//        ArrayoFLayout[3] = findViewById(R.id.trabottomright2);
//        ArrayoFLayout[4] = findViewById(R.id.trabottomright4);
//        ArrayoFLayout[5] = findViewById(R.id.trabottomright6);
//        ArrayoFLayout[6] = findViewById(R.id.trabottomright7);

        /*TakinglayoutInvisibletopleft.setVisibility(View.INVISIBLE);
        TakinglayoutInvisiblebottomright.setVisibility(View.INVISIBLE);*/

       /* ArrayoFLayout[0].setVisibility(View.INVISIBLE);
        ArrayoFLayout[1].setVisibility(View.INVISIBLE);
        ArrayoFLayout[2].setVisibility(View.INVISIBLE);
        ArrayoFLayout[3].setVisibility(View.INVISIBLE);
        ArrayoFLayout[4].setVisibility(View.INVISIBLE);
        ArrayoFLayout[5].setVisibility(View.INVISIBLE);
        ArrayoFLayout[6].setVisibility(View.INVISIBLE);*/

        // AcameraPreview.addView(gameView);
        // BcameraPreview = findViewById(R.id.Bcam_preview);

        /*// Add admob ads.
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-");
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mAdView.setLayoutParams(lp);

        linearLayout.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
        linearLayout.addView(gameView);

        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        startTime = SystemClock.uptimeMillis();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case ACTION_ADMOB_START_TIME:
                        //This is called after the first picture is taken
                        delayPeriod = 0;
                        newDelayPeriod = true;
                        break;
                    case ACTION_ADMOB_IS_LOADED:
                        //This is called automatically after delayperiod in START_TIME
                       // setCameraDisplayOrientation(MainActivity.this,camID,ACamera);
                        firstTakenBoolean = false;
                      //  ACamera.takePicture(null, null, APicture);
                        break;
                    case ACTION_EDIT_MODE:
                        //This goes into edit mode//////
                        if (FrontORBackPreviewOnTake){
                            String string = createImageFromBitmapBack(frobackBitmaps[9]);
                            string = createImageFromBitmapFront(frobackBitmaps[3]);
                        }else {
                            String string = createImageFromBitmapBack(frobackBitmaps[3]);
                            string = createImageFromBitmapFront(frobackBitmaps[9]);
                        }


                        break;
                    case ACTION_TAKE_PICTURE:
                        //This takes the first picture.... after this START_TIME is called automatically once the picture is taken
                        firstTakenBoolean = true;
                        break;
                    case ACTION_SWITCH_FRONT_BACK:

                        break;
                    case ACTION_SAVE:
                        //This saves the new bitmap with one on the other
                        bitmapFiVE = true;
                        break;
                    case ACTION_ZOOM:
                        //This zooms


                        break;
                    case ACTION_FLASH:
                        //This flashes on
                        //
                        bitmapFiVE = true;
                    case ACTION_RANDOM_FLASH:


                        break;
                }
            }
        };

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //SwitchWithoutTakingPic();
    }

    public String createImageFromBitmapBack(Bitmap bitmap) {
        String fileName = "backFroBack";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public String createImageFromBitmapFront(Bitmap bitmap) {
        String fileName = "FrontFroBack";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    private float AMagFieldArray[][] = new float[10000][4];
    private double MagnetStrength[] = new double[10000];
    private int MagneticAccuracy = 0;
    private int SensorChangedIterator = 0;
    private float sensorTopX = 0f,sensorBottomX = 0f;
    private float sensorCalOffset = 0f;
    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            MagneticAccuracy = accuracy;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South, 270=West
            SensorChangedIterator++;
            if (SensorChangedIterator > 10000){
                SensorChangedIterator = 0;
            }

            AMagFieldArray[SensorChangedIterator][0] = event.values[0];
            AMagFieldArray[SensorChangedIterator][1] = event.values[1];
            AMagFieldArray[SensorChangedIterator][2] = event.values[2];

            MagnetStrength[SensorChangedIterator] = Math.sqrt((AMagFieldArray[SensorChangedIterator][0] * AMagFieldArray[SensorChangedIterator][0])
                    + (AMagFieldArray[SensorChangedIterator][1] * AMagFieldArray[SensorChangedIterator][1])
                    + (AMagFieldArray[SensorChangedIterator][2] * AMagFieldArray[SensorChangedIterator][2])) - sensorCalOffset;
        }
    };

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the pointer ID


/*
        if (event.getPointerCount() > 1) {
            // handle multi-touch events

        } else {
            // handle single touch events
            if (action == MotionEvent.ACTION_UP) {
                handleFocus(event, params);
            }
        }*/
        return true;
    }

    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            //zoom in
            if (zoom < maxZoom) {
                zoom++;
                zoom++;
            }
            if (zoom > maxZoom){
                zoom = maxZoom;
            }

        } else if (newDist < mDist) {
            //zoom out
            if (zoom > 0) {
                zoom--;
                zoom--;
            }
            if (zoom < 0){
                zoom = 0;
            }

        }
        mDist = newDist;
        params.setZoom(zoom);
     //   ACamera.setParameters(params);
    }

   /* public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            ACamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }
*/    /** Determine the space between the first two fingers */
    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }



/*    //make picture and save to a folder
    private static File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", "JCG Camera");

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;

    }*/

    class GameView extends SurfaceView implements Runnable {
        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        Canvas canvas;
        long fps;
        private long timeThisFrame;

        String sunset = "#FB6542";
        String burntOrange = "#DE7A22";
        String sea = "#20948B";
        String peacockBlue = "#1E656D";
        String lagoon = "#6AB187";
        String ChristmasRed = "#B3000C";

        Rect textBounds = new Rect();
        final Paint wallPaint1 = new Paint();
        final Paint w2 = new Paint();
        final Paint wp1 = new Paint();
        final Paint red = new Paint();
        final Paint blue = new Paint();
        final Paint green = new Paint();

        private Message message;
        private Rect textBounds2= new Rect();
        private Paint wp2 = new Paint();

        public GameView(Context context) {
            super(context);
            ourHolder = getHolder();
            playing = true;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            while (playing) {
                long startFrameTime = System.currentTimeMillis();
                draw();

                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }
                if (newDelayPeriod){
                    delayPeriod++;
                    if (delayPeriod > 10){
                        Message message = mHandler.obtainMessage(ACTION_ADMOB_IS_LOADED, null);
                        message.sendToTarget();
                        delayPeriod = 0;
                        newDelayPeriod = false;
                    }
                }
            }
        }


        private float zoomY = 1f,zoomX = 1f;
        private float center = 0f, top = 0f, bottom = 0f, right = 0f, left = 0f, resolution = 0f, halfResolution = 0f;
        String topValue = "", bottomValue = "";
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void draw() {

            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                if (initializeOnce){
                    initialize();
                    for (int o = 0; o < 10; o++){
                        makeCalClicks(o);
                    }

                    right = sw*0.95f;
                    left = sw*0.2f;
                    bottom = sh*0.95f;
                    top = sh*0.05f;
                    center = top + (0.5f*(bottom-top));
                    resolution = 500;
                    halfResolution = resolution*0.5f;
                    topValue = halfResolution + "";
                    bottomValue = "-"+topValue;
                }

                canvas.drawColor(Color.DKGRAY);


                //canvas.drawText(fiveLittleHeight+" "+fps+ " " +fiveLittleWidth,sw*0.15f,sh*0.8f,wallPaint1);

                canvas.drawLine(0,sh*0.8f,sw,sh*0.8f,wp1);
                canvas.drawLine(sw*0.2f,0,sw*0.2f,sh,wp1);
                //X
                double maxValue = 0d;
                for(int j = 1; j < SensorChangedIterator; j++) {
                    if(maxValue < AMagFieldArray[j][0]){
                        maxValue = AMagFieldArray[j][0];
                    }
                    if (maxValue < AMagFieldArray[j][1]){
                        maxValue = AMagFieldArray[j][1];
                    }
                    if (maxValue < AMagFieldArray[j][2]){
                        maxValue = AMagFieldArray[j][2];
                    }
                    if (maxValue < MagnetStrength[j]){
                        maxValue = MagnetStrength[j];
                    }
                }
                if (maxValue > 300){
                    resolution =(float) maxValue*2;
                }else{
                    resolution = 500;
                }
                halfResolution = resolution * 0.5f;
                topValue = halfResolution + "";
                bottomValue = "-" + topValue;

                int gaps;
                int tuuui;
                if (SensorChangedIterator>100){
                    tuuui = SensorChangedIterator-100;
                }else{
                    tuuui = 1;
                }
                gaps = SensorChangedIterator + 1;
                float distanceBetweenGapsX = zoomX*(right - sw*0.2f)/gaps;
             /*   zoomX = zoomX+0.0001f;
                if (zoomX > 3f){
                    zoomX = 0.01f;
                }*/
                //Y
                float scale = ((bottom - top)/resolution)*zoomY;
           /*     zoomY = zoomY+0.0001f;
                if (zoomY > 3f){
                    zoomY = 0.01f;
                }*/

                int acount = 0;
                for(int j = tuuui; j < SensorChangedIterator; j++){
               //     canvas.drawText(AMagFieldArray[j][0]+"+"+AMagFieldArray[j][1]+"+"+AMagFieldArray[j][2],sw*0.01f + sw*0.5f*acount,sh*0.1f+sw*0.045f*j,wp1);
                    acount++;
                    if (acount > 1){
                        acount = 0;
                    }



                    canvas.drawLine(0,center,sw,center,red);
                    canvas.drawText("0",sw*0.1f,center,wp1);
                    canvas.drawLine(sw*0.17f,center,sw*0.23f,center,blue);
                    canvas.drawLine(sw*0.17f,bottom,sw*0.23f,bottom,red);
                    canvas.drawText(bottomValue,sw*0.1f,bottom,wp1);
                    canvas.drawLine(sw*0.17f,top,sw*0.23f,top,green);
                    canvas.drawText(topValue,sw*0.1f,top,wp1);
                    //

                    canvas.drawCircle(sw*0.2f + distanceBetweenGapsX*j, center -(scale*AMagFieldArray[j][0]),sw*0.00523f,red);
                    canvas.drawLine(sw*0.2f + distanceBetweenGapsX*(j-1), center-(scale*AMagFieldArray[j-1][0]),
                            sw*0.2f + distanceBetweenGapsX*j, center-(scale*AMagFieldArray[j][0]),red);


                    canvas.drawCircle(sw*0.2f + distanceBetweenGapsX*j, center-(scale*AMagFieldArray[j][1]),sw*0.00523f,green);
                    canvas.drawLine(sw*0.2f + distanceBetweenGapsX*(j-1), center-(scale*AMagFieldArray[j-1][1]),
                            sw*0.2f + distanceBetweenGapsX*j, center-(scale*AMagFieldArray[j][1]),green);


                    canvas.drawCircle(sw*0.2f + distanceBetweenGapsX*j, center-(scale*AMagFieldArray[j][2]),sw*0.00523f,blue);
                    canvas.drawLine(sw*0.2f + distanceBetweenGapsX*(j-1), center-(scale*AMagFieldArray[j-1][2]),
                            sw*0.2f + distanceBetweenGapsX*j, center-(scale*AMagFieldArray[j][2]),blue);


                    canvas.drawCircle(sw*0.2f + distanceBetweenGapsX*j, (float) (center-(scale*MagnetStrength[j])),sw*0.00523f,wp1);
                    canvas.drawLine(sw*0.2f + distanceBetweenGapsX*(j-1), (float) (center-(scale*MagnetStrength[j-1])),
                            sw*0.2f + distanceBetweenGapsX*j, (float) (center-(scale*MagnetStrength[j])),blue);
                }

                canvas.drawText(" " + (MagnetStrength[SensorChangedIterator]),sw*0.05f ,sh*0.1f,wp1);
                canvas.drawText(" " + (AMagFieldArray[SensorChangedIterator][0]),sw*0.05f ,sh*0.2f,red);
                canvas.drawText(" " + (AMagFieldArray[SensorChangedIterator][1]),sw*0.05f ,sh*0.3f,green);
                canvas.drawText(" " + (AMagFieldArray[SensorChangedIterator][2]),sw*0.05f ,sh*0.4f,blue);
                canvas.drawText(" " + (sensorTopX),sw*0.05f ,sh*0.6f,blue);
                canvas.drawText(" " + (sensorBottomX),sw*0.05f ,sh*0.7f,blue);
                canvas.drawText(" " + (sensorCalOffset),sw*0.05f ,sh*0.8f,blue);
                canvas.drawText(" " + (fps),sw*0.95f ,sh*0.9f,blue);

                for (int u = 0; u < 7;u++){
                    canvas.drawRect(clickX[u]-0.5f*clickWidth,clickY[u]-0.5f*clickWidth,
                            clickX[u]+1.5f*clickWidth,clickY[u]+0.5f*clickWidth,blue);
                    canvas.drawText(CalClickFunction[u],clickX[u]-0.098f*clickWidth,clickY[u],wp1);
                }

              /*  if (gallery != null) {
                    canvas.drawBitmap(gallery, sw*0.03f, defgright[1] - 0.5f * gallery.getHeight(), wp1);
                }*/
               /* if (switchhh != null){
                    canvas.drawBitmap(switchhh, sw - switchhh.getWidth(), defgright[1] - 0.5f * switchhh.getHeight(), wp1);
                }*/
                //drawStart();


                /*if (zoomInbool){
                    zoom++;
                    if (zoom > zoomMax){
                        zoom = zoomMax;
                    }
                    message = mHandler.obtainMessage(ACTION_ZOOM, null);
                    message.sendToTarget();
                }else if (zoomOutbool){
                    zoom--;
                    if (zoom < 0){
                        zoom = 0;
                    }
                    message = mHandler.obtainMessage(ACTION_ZOOM, null);
                    message.sendToTarget();
                }
                if (retryTosetZoom){
                    message = mHandler.obtainMessage(ACTION_ZOOM, null);
                    message.sendToTarget();
                    retryTosetZoom = false;
                }*/
                /*if (startFlashingSequence) {
                    countCameraFlash();
                }*/
                //canvas.drawText(""+GalleryBitmapString.length(), 0,sh*0.1f,wp1);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        private void toggleBtn() {
            canvas.drawCircle(sw*0.76f + flip.getWidth()*0.5f, (defgright[1] - 0.5f*mainBitmap.getHeight() + (mainBitmap.getHeight() - flip.getHeight())) + flip.getHeight()*0.5f,sw*0.08f,wp2);
            canvas.drawBitmap(flip, sw*0.76f, defgright[1] - 0.5f*mainBitmap.getHeight() + (mainBitmap.getHeight() - flip.getHeight()), wallPaint1);
        }

        public void drawStart(){
            // pa.setTextSize(sw*0.08f);
            // pa.setColor(Color.parseColor(ivory));
            // canvas.drawText("+"+LevelScore+"/"+LevelAmountOutOf + "  " + TotalScore + "/" + TotalAmountOutOf,sw*0.1f,sh*0.1f,pa);

            TextPaint txtPaint = new TextPaint();
            txtPaint.setColor(Color.WHITE);
            txtPaint.setTextSize(sw * 0.055f);
            String topTxt = " %" + " ZOOM";
            if (zoomMax != 0) {
                topTxt = zoom + " %" + " ZOOM";
            }
               /* int mTextWidth2;
                int mTextHeight2;*/
            txtPaint.getTextBounds(topTxt, 0, topTxt.length(), textBounds2);

            StaticLayout mTextLayout = new StaticLayout(topTxt, txtPaint, (int) (sw), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(abcup[1],defgright[3]+minusZoom.getHeight()*0.7f);
            mTextLayout.draw(canvas);
            canvas.restore();
        }
        /*private void countCameraFlash() {
            flashingCounter++;
            if (flashingCounter > 28){
                flashingCounter = 0;
                Message message = mHandler.obtainMessage(ACTION_RANDOM_FLASH, null);
                message.sendToTarget();
            }
        }*/

        /* public void novaLauncher(float xStartt, float yStartt, float xFini, float yFini, int iterator){
             *//*distanceFlickedLaunch[LaunchIterator[iterator][iteratorIterator[iterator]]] = (float) Math.sqrt((xStartt - xFini) * (xStartt - xFini) + (yStartt - yFini) * (yStartt - yFini));*//*
            distanceFlickedLaunch[iterator][iteratorIterator[iterator]] = circleLaunchSpeed[iterator] + sw *0.235f;
            dirLaunch[iterator][iteratorIterator[iterator]] = (float) Math.atan2(((yFini) - (yStartt)), ((xFini) - (xStartt)));
            LaunchTrue[iterator][iteratorIterator[iterator]] = true;
            xCircleLauncher[iterator][iteratorIterator[iterator]] = xStartt;
            yCircleLauncher[iterator][iteratorIterator[iterator]] = yStartt;
            LaunchspikeCounter[iterator][iteratorIterator[iterator]] = (raand.nextInt(14)) * 10;
            if (LaunchspikeCounter[iterator][iteratorIterator[iterator]] < 30){
                LaunchspikeCounter[iterator][iteratorIterator[iterator]] = 30;
            }
            /////////////////////////////////////////////////////////////
            //the iterator for the launch is updated here so checks can be made for the next rounc here
            iteratorIterator[iterator]++;
            if (iteratorIterator[iterator] >= 100){
                iteratorIterator[iterator] = 0;
            }
            LaunchTrue[iterator][iteratorIterator[iterator]] = false;
        }
*/
        private Bitmap getResizedBitmapforFive(Bitmap bm, float newWidth, float newHeight) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ( newWidth) / width;
            float scaleHeight = (newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            // "RECREATE" THE NEW BITMAP
            return Bitmap.createBitmap(bm, 0, 0, width, height,
                    matrix, false);
        }

        public void makeBitmapFIVE(){
            frobackBitmaps[5] = overlay(frobackBitmaps[3],frobackBitmaps[4]);
        }
        public Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
            Bitmap bmOverlay = Bitmap.createBitmap((int) (bmp1.getWidth()), (int) (bmp1.getHeight()), bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, new Matrix(), null);

            canvas.drawBitmap(bmp2, bmp1.getWidth()*0.01f*rightleft, bmp1.getHeight()*0.01f*updown, null);
            return bmOverlay;
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void initialize(){
            retryTosetZoom = false;
            zoomInbool = false;
            zoomOutbool = false;
            sh = canvas.getHeight();
            sw = canvas.getWidth();
            wallPaint1.setTextSize(sw*0.2f);
            w2.setColor(Color.BLACK);
            wp1.setColor(Color.WHITE);

            red.setColor(Color.RED);
            red.setStrokeWidth(sw*0.00323f);
            blue.setColor(Color.BLUE);
            blue.setStrokeWidth(sw*0.00323f);
            green.setColor(Color.GREEN);
            green.setStrokeWidth(sw*0.00323f);

            wp1.setTextSize(sw*0.023f);
            red.setTextSize(sw*0.023f);
            blue.setTextSize(sw*0.023f);
            green.setTextSize(sw*0.023f);
            wp2.setColor(Color.WHITE);

            wallPaint1.setColor(Color.parseColor(ChristmasRed));
            initializeOnce = false;
            for (int i = 1 ; i < 4; i++){
                abcup[i] =sw*0.25f*i;
                defgright[i] =sh*0.43f ;
                hijarmreach[i] = sw*0.07f;
                clickedabcdefghij[i] = false;
            }
            leftLittleMove = sw*0.5f;
            topLIttleMove = sh*0.5f;
            positionFroback[7] = 2;
            ALittleHeight = 24;
            ALittleWidth = 24;

            FrontORBackPreviewOnTake = false;

            toggleX = sw*0.93f;
            toggleBottomY = sh*0.5f;
            toggleTopY = sh*0.1f;

        }

        // method for base64 to bitmap
        public Bitmap decodeBase64(String input) {
            byte[] decodedByte = Base64.decode(input, 0);
            return BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
        }

        public Bitmap getCroppedBitmap(Bitmap bitmap) {

            Bitmap output = Bitmap.createBitmap((bitmap.getWidth()),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);


            // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                    sw*0.07f, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }

        public Bitmap getResizedBitmapNORotate(Bitmap bm, float newHeight, float newWidth) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ( newWidth) / width;
            float scaleHeight = (newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            return Bitmap.createBitmap(bm, 0, 0, width, height,
                    matrix, false);
        }

        public Bitmap getResizedBitmap(Bitmap bm, float newHeight, float newWidth, int bitmapNumber1Or2) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ( newWidth) / width;
            float scaleHeight = (newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            int orientation = this.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //code for portrait mode
                if (FRONT90Or270){
                    if (bitmapNumber1Or2 == 1){
                        matrix.postRotate(90);
                    }else {
                        matrix.postScale(1.0f,-1.0f);
                        matrix.postRotate(270);
                    }
                }else {
                    if (bitmapNumber1Or2 == 1){
                        matrix.postScale(1.0f,-1.0f);
                        matrix.postRotate(270);
                    }else {
                        matrix.postRotate(90);
                    }
                }
            }
            // "RECREATE" THE NEW BITMAP
            return Bitmap.createBitmap(bm, 0, 0, width, height,
                    matrix, false);
        }

        public Bitmap getResizedBitmapLittleOne(Bitmap bm, float newHeight, float newWidth) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ( newWidth) / width;
            float scaleHeight = (newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            //code for portrait mode
               /* matrix.postScale(1.0f,-1.0f);
                matrix.postRotate(270);*/

            // "RECREATE" THE NEW BITMAP
            return Bitmap.createBitmap(bm, 0, 0, width, height,
                    matrix, false);
        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        private int calClickIterator = 0;
        private float clickX[] = new float[100];
        private float clickY[] = new float[100];
        private float clickWidth = 0f;
        private String CalClickFunction[] = new String[100];

        private void makeCalClicks(int iterator){
            clickX[iterator] = sw*0.9f;
            clickY[iterator] = sh*0.1f+sh*0.1f*iterator+sh*0.023f;
            clickWidth = sh*0.08f;
            switch (iterator){
                case 0:
                    CalClickFunction[iterator] = "Xup";
                    break;
                case 1:
                    CalClickFunction[iterator] = "Xdown";
                    break;
                case 2:
                    CalClickFunction[iterator] = "Yup";
                    break;
                case 3:
                    CalClickFunction[iterator] = "Ydown";
                    break;
                case 4:
                    CalClickFunction[iterator] = "Zup";
                    break;
                case 5:
                    CalClickFunction[iterator] = "Zdown";
                    break;
                case 6:
                    CalClickFunction[iterator] = "save";
                    break;
                case 7:
                    CalClickFunction[iterator] = "Xup";
                    break;
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int action = event.getAction();
            switch(action){

                case MotionEvent.ACTION_DOWN:


                    for (int t =0;t < 7;t++){
                        if (Math.abs(x - clickX[t]) < clickWidth) {
                            if (Math.abs(y - clickY[t]) < clickWidth) {
                                switch (t) {
                                    case 0:
                                        sensorTopX = AMagFieldArray[SensorChangedIterator][0];
                                        CalClickFunction[t] = "Xup";
                                        break;
                                    case 1:
                                        sensorBottomX = AMagFieldArray[SensorChangedIterator][0];
                                        CalClickFunction[t] = "Xdown";
                                        break;
                                    case 2:
                                        CalClickFunction[t] = "Yup";
                                        break;
                                    case 3:
                                        CalClickFunction[t] = "Ydown";
                                        break;
                                    case 4:
                                        CalClickFunction[t] = "Zup";
                                        break;
                                    case 5:
                                        CalClickFunction[t] = "Zdown";
                                        break;
                                    case 6:
                                        sensorCalOffset = Math.abs(sensorTopX-sensorBottomX);
                                        CalClickFunction[t] = "save";
                                        SensorChangedIterator = 0;
                                        break;
                                    case 7:
                                        CalClickFunction[t] = "Xup";
                                        break;
                                }
                            }
                        }
                    }




                    for (int j = 1;j < 4; j++){
                        if (Math.abs(x - abcup[j]) <= hijarmreach[j]){
                            if (Math.abs(y - defgright[j]) <= hijarmreach[j]){
                                switch (j){
                                    /*case 1:
                                        zoomOutbool = true;
                                        *//*zoom--;
                                        if (zoom < 0){
                                            zoom = 0;
                                        }
                                        message = mHandler.obtainMessage(ACTION_ZOOM, null);
                                        message.sendToTarget();*//*
                                        break;*/
                                    case 1:
                                      /*  Intent intent = new Intent();
                                        intent.setAction(android.content.Intent.ACTION_VIEW);
                                        intent.setType("image*//*");
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);*/
                                        break;
                                    case 2:
                                        message = mHandler.obtainMessage(ACTION_TAKE_PICTURE, null);
                                        message.sendToTarget();
                                        /*zoom++;
                                        if (zoom > zoomMax){
                                            zoom = zoomMax;
                                        }
                                        message = mHandler.obtainMessage(ACTION_ZOOM, null);
                                        message.sendToTarget();*/
                                        break;
                                    /*case 3:
                                        message = mHandler.obtainMessage(ACTION_TAKE_PICTURE, null);
                                        message.sendToTarget();
                                        //The Take Picture Code
                                        break;
                                    case 4:
                                        //Switch The Camera
                                        message = mHandler.obtainMessage(ACTION_SWITCH_FRONT_BACK, null);
                                        message.sendToTarget();
                                        break;
                                    case 5:
                                        //Switch The Camera And Save For Now
                                        message = mHandler.obtainMessage(ACTION_SAVE, null);
                                        message.sendToTarget();
                                        break;
                                        default:
                                            break;*/
                                }
                            }
                        }
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    if (tappedFrontLittle){
                        if (frobackBitmaps[4]!=null) {
                            leftLittleMove = x - frobackBitmaps[4].getWidth() * 0.5f;
                            topLIttleMove = y - frobackBitmaps[4].getHeight() * 0.5f;
                        }
                    }
/*
                    for (int j = 1;j < 4; j++) {
                        if (Math.abs(x - abcup[j]) <= hijarmreach[j]) {
                            if (Math.abs(y - defgright[j]) <= hijarmreach[j]) {
                                switch (j) {
                                    case 1:
                                        zoom--;
                                        if (zoom < 0){
                                            zoom = 0;
                                        }
                                        message = mHandler.obtainMessage(ACTION_ZOOM, null);
                                        message.sendToTarget();
                                        break;
                                    case 2:
                                        zoom++;
                                        if (zoom > zoomMax){
                                            zoom = zoomMax;
                                        }
                                        message = mHandler.obtainMessage(ACTION_ZOOM, null);
                                        message.sendToTarget();
                                        break;
                                    case 3:
                                        message = mHandler.obtainMessage(ACTION_TAKE_PICTURE, null);
                                        message.sendToTarget();
                                        //The Take Picture Code
                                        break;
                                    case 4:
                                        //Switch The Camera
                                        message = mHandler.obtainMessage(ACTION_SWITCH_FRONT_BACK, null);
                                        message.sendToTarget();
                                        break;
                                    case 5:
                                        //Switch The Camera And Save For Now
                                        message = mHandler.obtainMessage(ACTION_SAVE, null);
                                        message.sendToTarget();
                                        break;
                                    default:

                                        break;
                                }
                            }
                        }
                    }*/
                    break;
                case MotionEvent.ACTION_UP:
                    /*zoomOutbool = false;
                    zoomInbool = false;
                    reSizeTheLittleOne = false;*/
                    if (tappedFrontLittle){
                        tappedFrontLittle = false;
                        if (frobackBitmaps[4]!=null) {
                            leftLittleMove = x - frobackBitmaps[4].getWidth() * 0.5f;
                            topLIttleMove = y - frobackBitmaps[4].getHeight() * 0.5f;
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;

                case MotionEvent.ACTION_OUTSIDE:
                    break;
            }
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();

        // soundOne.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // soundOne.pause();
        //when on Pause, release camera in order to be used from other applications
        gameView.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //    ArewAd.destroy(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(new Bundle());
        initializeOnce = true;
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}