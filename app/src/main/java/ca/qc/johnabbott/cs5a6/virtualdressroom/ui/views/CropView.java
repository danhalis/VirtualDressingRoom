package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.models.Point;

public class CropView extends View implements View.OnTouchListener {

    private static final int MIN_POINTS = 12;
    private static final int STROKE_WIDTH = 5;
    private static final int DASH_LENGTH = 10;
    private static final int DASH_GAP = 5;

    private final Paint paint;
    private final Path path;
    private static List<Point> points;

    private boolean flgPathDraw = true;

    private Point mFirstPoint = null;
    private Point mLastPoint = null;

    private boolean didRegisterFirstPoint = false;

    private Bitmap bitmap;
    private int scaledBitmapWidth;
    private int scaledBitmapHeight;
    private final Context mContext;
//    private final CropPhotoFragment fragment;

    private ImageView compositeImageView;

    public CropView(Context c) {
        super(c);

        mContext = c;
        setFocusable(true);
        setFocusableInTouchMode(true);

        Drawable drawable = AppCompatResources.getDrawable(mContext, R.drawable.shirt);
        bitmap = BitmapHelper.convertToBitmap(drawable);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(
            new DashPathEffect(
                new float[] { DASH_LENGTH, DASH_GAP },
                0
            )
        );
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(Color.WHITE);

        path = new Path();

        this.setOnTouchListener(this);
        points = new ArrayList<>();

        didRegisterFirstPoint = false;
    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);

        Drawable drawable = AppCompatResources.getDrawable(mContext, R.drawable.ic_android_black_24dp);
        bitmap = BitmapHelper.convertToBitmap(drawable);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(
            new DashPathEffect(
                new float[] { DASH_LENGTH, DASH_GAP },
                0
            )
        );
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(Color.WHITE);

        path = new Path();

        this.setOnTouchListener(this);
        points = new ArrayList<>();

        didRegisterFirstPoint = false;
    }

    public static List<Point> getPoints() {
        return points;
    }

    //    public CropView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        mContext = context;
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2);
//        paint.setColor(Color.WHITE);
//
//        this.setOnTouchListener(this);
//        points = new ArrayList<Point>();
//        bfirstpoint = false;
//
//    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        // get current touch point
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();

        if (flgPathDraw) {

            // if the very first touch point was registered
            if (didRegisterFirstPoint) {

                // if the current touch point meets the very first touch point
                // meaning the lasso has been enclosed
                if (comparePoints(mFirstPoint, point)) {
                    points.add(mFirstPoint);
                    flgPathDraw = false;

                    showCropDialog();
                }
                // if the current touch point doesn't meet the very first point yet
                else {
                    points.add(point);
                }
            }
            // if this is the very first touch point
            else {
                mFirstPoint = point;
                didRegisterFirstPoint = true;

                points.add(point);
            }
        }

        // disable the view
        // onDraw() will be called in the future
        invalidate();

        // if the user released the touch
        // meaning this touch point is the last one
        if (event.getAction() == MotionEvent.ACTION_UP) {

            mLastPoint = point;

            if (flgPathDraw) {
                if (points.size() >= MIN_POINTS) {
                    //
                    if (!comparePoints(mFirstPoint, mLastPoint)) {
                        flgPathDraw = false;
                        points.add(mFirstPoint);

                        showCropDialog();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {

        int bitMapWidth = bitmap.getWidth();
        int bitMapHeight = bitmap.getHeight();
        float ratio = (float) bitMapWidth / bitMapHeight;
        float height = getWidth() / ratio;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), (int) height, false);
        scaledBitmapWidth = scaledBitmap.getWidth();
        scaledBitmapHeight = scaledBitmap.getHeight();
        canvas.drawBitmap(scaledBitmap, 0, (float) (getHeight() - scaledBitmap.getHeight()) / 2, null);

        boolean first = true;

        for (int i = 0; i < points.size(); i += 2) {
            Point point = points.get(i);

            // if this is the first registered point
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            }
            else if (i < points.size() - 1) {
                Point next = points.get(i + 1);
                path.quadTo(point.x, point.y, next.x, next.y);
            }
            // if this is the last registered point
            else {
                mLastPoint = points.get(i);
                path.lineTo(point.x, point.y);
            }
        }
        canvas.drawPath(path, paint);
    }

    private boolean comparePoints(Point first, Point second) {
        int left_range_x = (int) (second.x - 3);
        int left_range_y = (int) (second.y - 3);

        int right_range_x = (int) (second.x + 3);
        int right_range_y = (int) (second.y + 3);

        if ((left_range_x < first.x && first.x < right_range_x)
                && (left_range_y < first.y && first.y < right_range_y)) {

            if (points.size() < MIN_POINTS) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    private void crop(boolean keepInner) {

//        Bitmap fullScreenBitmap =
//                Bitmap.createBitmap(this.getWidth(), this.getHeight(), bitmap.getConfig());
//
//        Canvas canvas = new Canvas(fullScreenBitmap);
//
//        Path path = new Path();
//        List<Point> points = CropView.getPoints();
//        for (int i = 0; i < points.size(); i++) {
//            path.lineTo(points.get(i).x, points.get(i).y);
//        }
//
//        // Cut out the selected portion of the image...
//        Paint paint = new Paint();
//        canvas.drawPath(path, paint);
//        if (keepInner)
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        else
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//
//        // Frame the cut out portion...
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
//        canvas.drawPath(path, paint);
//
//        // Create a bitmap with just the cropped area.
//        Region region = new Region();
//        Region clip = new Region(0, 0, fullScreenBitmap.getWidth(), fullScreenBitmap.getHeight());
//        region.setPath(path, clip);
//        Rect bounds = region.getBounds();
//        Bitmap croppedBitmap =
//                Bitmap.createBitmap(fullScreenBitmap, fullScreenBitmap.getWidth()/2, fullScreenBitmap.getHeight()/2,
//                        bounds.width(), bounds.height());



        DisplayMetrics dm = new DisplayMetrics();

        MainActivity activity = (MainActivity) mContext;
        try {
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        catch (Exception ex) {
        }

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        compositeImageView = activity.getCropPhotoFragment().getResultImageView();

        Drawable drawable = AppCompatResources.getDrawable(mContext, R.drawable.ic_android_black_24dp);
        Bitmap bitmap = BitmapHelper.convertToBitmap(drawable);
        bitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, false);
        Bitmap resultingImage = Bitmap.createBitmap(
                screenWidth,
                screenHeight,
                bitmap.getConfig());
//        bitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, false);

//        Bitmap resultingImage = Bitmap.createBitmap(
//                bitmap.getWidth(),
//                bitmap.getHeight(),
//                bitmap.getConfig());

        Canvas canvas = new Canvas(resultingImage);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        List<Point> points = CropView.getPoints();
        for (int i = 0; i < points.size(); i++) {
            path.lineTo(points.get(i).x, points.get(i).y - 200);
        }

        canvas.drawPath(path, paint);

        // crop inside or outside
        if (keepInner) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        }

        Rect rect = new Rect(0, 0, scaledBitmapWidth, scaledBitmapHeight);
//        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.drawBitmap(bitmap, null, rect, paint);
//        canvas.drawBitmap(bitmap, 0, (float) (getHeight() - resultingImage.getHeight()) / 2, paint);

//        canvas.drawBitmap(resultingImage, 0, (float) (getHeight() - resultingImage.getHeight()) / 2, paint);


//        resultingImage = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, false);
        resultingImage.setWidth(scaledBitmapWidth);
        resultingImage.setHeight(scaledBitmapHeight);
        compositeImageView.setImageBitmap(resultingImage);
//        compositeImageView.setMaxWidth(scaledBitmapWidth);
//        compositeImageView.setMaxHeight(scaledBitmapHeight);

        this.setVisibility(GONE);
//        compositeImageView.setImageBitmap(croppedBitmap);

    }

    private void showCropDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        crop(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        crop(false);
                        didRegisterFirstPoint = false;

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you Want to save Crop or Non-crop image?")
                .setPositiveButton("Crop", dialogClickListener)
                .setNegativeButton("Non-crop", dialogClickListener).show()
                .setCancelable(false);
    }
}