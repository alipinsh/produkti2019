package com.example.project;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.MultiFormatOneDReader;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class BarcodeFinderAndReader {
    private static final String TAG = "[Finder]";
    private Mat barcodeImage;

    private MatOfPoint mLargestContour;
    private Mat kernel;
    private Mat gradX;
    private Mat gradY;
    private int depth;

    private Scalar lineColor;
    private Bitmap bmap;
    private int[] intArray;
    private LuminanceSource source;
    private BinaryBitmap bitmap;
    private Hashtable<DecodeHintType, ?> hints;
    private Reader reader;
    private Result result;
    private ResultPoint[] resultPoints;

    private String foundString;

    BarcodeFinderAndReader() {
        mLargestContour = new MatOfPoint();
        depth = CvType.CV_32F;
        gradX = new Mat();
        gradY = new Mat();
        kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));

        hints = new Hashtable<>();
        reader = new MultiFormatOneDReader(hints);
        lineColor = new Scalar(0, 255, 0);
    }

    public String read (Mat image) {
        bmap = Bitmap.createBitmap(image.width(), image.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(image, bmap);
        intArray = new int[bmap.getWidth() * bmap.getHeight()];
        bmap.getPixels(intArray, 0,bmap.getWidth(),0,0, bmap.getWidth(), bmap.getHeight());
        source = new RGBLuminanceSource(bmap.getWidth(), bmap.getHeight(), intArray);
        bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            result = reader.decode(bitmap);
            foundString = result.toString();
            resultPoints = result.getResultPoints();
            for (int i = 0; i < resultPoints.length - 1; i++) {
                Imgproc.line(image, resultToPoint(resultPoints[i]), resultToPoint(resultPoints[i + 1]), lineColor, 5);
            }
            return foundString;
        } catch (NotFoundException e ) {
            Log.i(TAG, "Not found exception");
        } catch (FormatException e) {
            Log.i(TAG, "Format exception");
        } catch (ChecksumException e) {
            Log.i(TAG, "Checksum exception");
        }

        return "";
    }

    public void findOld(Mat image) {
        Mat edited = image.clone();
        Imgproc.cvtColor(edited, edited, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Sobel(edited, gradX, depth, 1, 0, -1);
        Imgproc.Sobel(edited, gradY, depth, 0, 1, -1);

        Core.subtract(gradX, gradY, edited);
        Core.convertScaleAbs(edited, edited);

        Imgproc.blur(edited, edited, new Size(9, 9));
        Imgproc.threshold(edited, edited, 55, 255, Imgproc.THRESH_BINARY);

        Imgproc.morphologyEx(edited, edited, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.erode(edited, edited, new Mat(), new Point(), 4);
        Imgproc.dilate(edited, edited, new Mat(), new Point(), 4);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(edited, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (contours.isEmpty()) {
            return;
        }

        mLargestContour = contours.get(0);
        Iterator<MatOfPoint> each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint wrapper = each.next();
            if (Imgproc.contourArea(wrapper) > Imgproc.contourArea(mLargestContour))
                mLargestContour = wrapper;
        }

        MatOfPoint2f mlc = new MatOfPoint2f();
        mLargestContour.convertTo(mlc, CvType.CV_32F);
        RotatedRect rect = Imgproc.minAreaRect(mlc);
        //Mat box = new Mat();
        //Imgproc.boxPoints(rect, box);

        Point[] verts = new Point[4];
        rect.points(verts);
        for (int i = 0; i < 4; ++i) {
            Imgproc.line(image, verts[i], verts[(i+1)%4], new Scalar(0, 255, 0), 5);
        }

    }

    public MatOfPoint getContour() {
        return mLargestContour;
    }

    public Mat getBarcodeImage() {

        return barcodeImage;
    }

    public void drawSquare() {

    }

    private Point resultToPoint (ResultPoint rp) {
        return new Point(rp.getX(), rp.getY());
    }

}

