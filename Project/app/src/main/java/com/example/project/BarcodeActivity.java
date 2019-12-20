package com.example.project;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Button;

import java.util.HashMap;
import java.util.TreeSet;

public class BarcodeActivity extends Activity implements CvCameraViewListener2 {
    private static final String t = "[CAMERA]";

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean mIsJavaCamera = true;
    private MenuItem mItemSwitchCamera = null;
    private Mat mFrame;
    private BarcodeFinderAndReader mReader;

    private HashMap<String, Boolean> prodcodes;
    private String foundString;
    private Boolean value;
    private TreeSet<String> stringSet;

    private Button showProductsButton;
    private Runnable showMessage1;
    private Runnable showMessage2;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(t, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public BarcodeActivity() {
        Log.i(t, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(t, "called onCreate");
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.camera_layout);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);

        showMessage1 = new Runnable() {
            public void run() {
                Toast.makeText(BarcodeActivity.this, "Found product from list!", Toast.LENGTH_SHORT).show();
            }
        };

        showMessage2 = new Runnable() {
            public void run() {
                Toast.makeText(BarcodeActivity.this, "Product already is scanned", Toast.LENGTH_SHORT).show();
            }
        };

        prodcodes = (HashMap<String, Boolean>) getIntent().getSerializableExtra("PRODCODES");
        Log.i(t, prodcodes.toString());
        stringSet = new TreeSet<String>();


        showProductsButton = (Button) findViewById(R.id.showProducts);
        showProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(t, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(t, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

    }

    public void onCameraViewStarted(int width, int height) {
        mReader = new BarcodeFinderAndReader();

    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mFrame = inputFrame.rgba();
       // Core.rotate(mFrame, mFrame, Core.ROTATE_90_CLOCKWISE);
        foundString = mReader.read(mFrame);
        Log.i(t, foundString);

        if (foundString != "") {
            //stringSet.add(foundString);
            value = prodcodes.get(foundString);
            if (value != null) {
                if (value) {
                    runOnUiThread(showMessage2);
                } else {
                    prodcodes.put(foundString, true);
                    runOnUiThread(showMessage1);
                }
            }
        }

        return mFrame;
    }

    @Override
    public void onBackPressed() {
        Log.i(t, "Pressed back");
        /*
        for (String s : stringSet) {
            if (prodcodes.containsKey(s)) {
                prodcodes.put(s, true);
            }
        }
        */

        Intent resultIntent = new Intent();
        resultIntent.putExtra("CAMERA_RESULT", prodcodes);
        setResult(RESULT_OK, resultIntent);
        finish();


    }
}