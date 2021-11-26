package ca.qc.johnabbott.cs5a6.cropimage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

public class CropView extends View implements View.OnTouchListener {

    private static final int STROKE_WIDTH = 5;
    private static final int DASH_LENGTH = 10;
    private static final int DASH_GAP = 5;

    private final Paint paint;
    public static List<Point> points;

    private boolean flgPathDraw = true;

    int DIST = 2;
    private Point mFirstPoint = null;
    private Point mLastPoint = null;

    private boolean didRegisterFirstPoint = false;

    private final Bitmap bitmap;
    private final Context mContext;

    public CropView(Context c) {
        super(c);

        mContext = c;
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

        this.setOnTouchListener(this);
        points = new ArrayList<>();

        didRegisterFirstPoint = false;
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
                if (points.size() > 12) {
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
        canvas.drawBitmap(bitmap, 0, 0, null);

        Path path = new Path();
        boolean first = true;

        for (int i = 0; i < points.size(); i += 2) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else if (i < points.size() - 1) {
                Point next = points.get(i + 1);
                path.quadTo(point.x, point.y, next.x, next.y);
            } else {
                mLastPoint = points.get(i);
                path.lineTo(point.x, point.y);
            }
        }
        canvas.drawPath(path, paint);
    }

    private boolean comparePoints(Point first, Point current) {
        int left_range_x = (int) (current.x - 3);
        int left_range_y = (int) (current.y - 3);

        int right_range_x = (int) (current.x + 3);
        int right_range_y = (int) (current.y + 3);

        if ((left_range_x < first.x && first.x < right_range_x)
                && (left_range_y < first.y && first.y < right_range_y)) {
            if (points.size() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void showCropDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override            public void onClick(DialogInterface dialog, int which) {
                Intent intent;
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        intent = new Intent(mContext, ImageCropActivity.class);
                        intent.putExtra("crop", true);
                        mContext.startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        intent = new Intent(mContext, ImageCropActivity.class);
                        intent.putExtra("crop", false);
                        mContext.startActivity(intent);

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