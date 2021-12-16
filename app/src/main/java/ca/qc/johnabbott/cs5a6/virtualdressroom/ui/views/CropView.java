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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor.CropPhotoFragment;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.models.Point;

public class CropView extends View implements View.OnTouchListener {

    private static final int MIN_POINTS = 12;
    private static final int STROKE_WIDTH = 5;
    private static final int DASH_LENGTH = 10;
    private static final int DASH_GAP = 5;

    private final Context context;
    private CropPhotoFragment fragment;

    private Bitmap emptyBitmap;
    private Bitmap bitmap;
    private boolean requiresScalingBitmap;
    private int scaledBitmapWidth;
    private int scaledBitmapHeight;
    private float yOffset;

    private final Paint lassoPaint;
    private final Path lassoPath;
    private final List<Point> lassoPoints;

    private boolean flgPathDraw = true;

    private Point firstPoint;
    private Point lastPoint;

    private boolean didRegisterFirstPoint;

    private boolean didCrop = false;

    public CropView(Context context) {
        super(context);

        this.context = context;

        setFocusable(true);
        setFocusableInTouchMode(true);

        Drawable emptyDrawable = AppCompatResources.getDrawable(this.context, R.drawable.empty);
        emptyBitmap = BitmapHelper.convertToBitmap(emptyDrawable);

        // init lasso paint
        lassoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lassoPaint.setStyle(Paint.Style.STROKE);
        lassoPaint.setPathEffect(
                new DashPathEffect(
                        new float[] { DASH_LENGTH, DASH_GAP },
                        0
                )
        );
        lassoPaint.setStrokeWidth(STROKE_WIDTH);
        lassoPaint.setColor(Color.WHITE);

        // init lasso path
        lassoPath = new Path();

        // init lasso points
        lassoPoints = new ArrayList<>();

        setOnTouchListener(this);

        didRegisterFirstPoint = false;
    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        setFocusable(true);
        setFocusableInTouchMode(true);

        Drawable emptyDrawable = AppCompatResources.getDrawable(this.context, R.drawable.empty);
        emptyBitmap = BitmapHelper.convertToBitmap(emptyDrawable);

        // init lasso paint
        lassoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lassoPaint.setStyle(Paint.Style.STROKE);
        lassoPaint.setPathEffect(
            new DashPathEffect(
                new float[] { DASH_LENGTH, DASH_GAP },
                0
            )
        );
        lassoPaint.setStrokeWidth(STROKE_WIDTH);
        lassoPaint.setColor(Color.WHITE);

        // init lasso path
        lassoPath = new Path();

        // init lasso points
        lassoPoints = new ArrayList<>();

        setOnTouchListener(this);

        didRegisterFirstPoint = false;
    }

    public void setFragment(CropPhotoFragment fragment) {
        this.fragment = fragment;
    }

    public List<Point> getLassoPoints() {
        return lassoPoints;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!requiresScalingBitmap) {
            bitmap = scaleBitmap(bitmap);

            requiresScalingBitmap = true;
        }
    }

    private Bitmap scaleBitmap(Bitmap bitmap) {
        // scale image to screen width
        int bitMapWidth = bitmap.getWidth();
        int bitMapHeight = bitmap.getHeight();
        float ratio = (float) bitMapWidth / bitMapHeight;
        float scaledHeight = getWidth() / ratio;

        Drawable emptyDrawable = AppCompatResources.getDrawable(this.context, R.drawable.empty);
        emptyBitmap = BitmapHelper.convertToBitmap(emptyDrawable);
        emptyBitmap = Bitmap.createScaledBitmap(emptyBitmap, getWidth(), (int) scaledHeight, false);

        bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), (int) scaledHeight, false);
        scaledBitmapWidth = bitmap.getWidth();
        scaledBitmapHeight = bitmap.getHeight();
        yOffset = (float) (getHeight() - scaledBitmapHeight) / 2;

        return bitmap;
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
                if (comparePoints(firstPoint, point)) {
                    lassoPoints.add(firstPoint);
                    flgPathDraw = false;

                    showCropDialog();
                }
                // if the current touch point doesn't meet the very first point yet
                else {
                    lassoPoints.add(point);
                }
            }
            // if this is the very first touch point
            else {
                firstPoint = point;
                didRegisterFirstPoint = true;

                lassoPoints.add(point);
            }
        }

        // disable the view
        // onDraw() will be called in the future
        invalidate();

        // if the user released the touch
        // meaning this touch point is the last one
        if (event.getAction() == MotionEvent.ACTION_UP) {

            lastPoint = point;

            if (flgPathDraw) {
                if (lassoPoints.size() >= MIN_POINTS) {
                    //
                    if (!comparePoints(firstPoint, lastPoint)) {
                        flgPathDraw = false;
                        lassoPoints.add(firstPoint);

                        showCropDialog();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap, 0, yOffset, null);

        boolean first = true;

        for (int i = 0; i < lassoPoints.size(); i += 2) {
            Point point = lassoPoints.get(i);

            // if this is the first registered point
            if (first) {
                first = false;
                lassoPath.moveTo(point.x, point.y);
            }
            else if (i < lassoPoints.size() - 1) {
                Point next = lassoPoints.get(i + 1);
                lassoPath.quadTo(point.x, point.y, next.x, next.y);
            }
            // if this is the last registered point
            else {
                lastPoint = lassoPoints.get(i);
                lassoPath.lineTo(point.x, point.y);
            }
        }
        canvas.drawPath(lassoPath, lassoPaint);
    }

    private boolean comparePoints(Point first, Point second) {
        int left_range_x = (int) (second.x - 3);
        int left_range_y = (int) (second.y - 3);

        int right_range_x = (int) (second.x + 3);
        int right_range_y = (int) (second.y + 3);

        if ((left_range_x < first.x && first.x < right_range_x)
                && (left_range_y < first.y && first.y < right_range_y)) {

            if (lassoPoints.size() < MIN_POINTS) {
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

        Bitmap resultImage = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                bitmap.getConfig());

        Canvas canvas = new Canvas(resultImage);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        List<Point> points = getLassoPoints();

        // set up path and find path bound points
        float smallestX = Math.max(points.get(0).x, 0);
        float smallestY = Math.max(points.get(0).y - yOffset, 0);
        float biggestX = Math.min(points.get(0).x, scaledBitmapWidth);
        float biggestY = Math.min(points.get(0).y - yOffset, scaledBitmapHeight);

        for (int i = 0; i < points.size(); i++) {
            float pointX = Math.max(points.get(i).x, 0);
            pointX = Math.min(pointX, scaledBitmapWidth);

            float pointY = Math.max(points.get(i).y - yOffset, 0);
            pointY = Math.min(pointY, scaledBitmapHeight);

            if (pointX < smallestX) {
                smallestX = pointX;
            }
            if (pointX > biggestX) {
                biggestX = pointX;
            }
            if (pointY < smallestY) {
                smallestY = pointY;
            }
            if(pointY > biggestY) {
                biggestY = pointY;
            }

            path.lineTo(pointX, pointY);
        }

        canvas.drawPath(path, paint);

        Rect rect = new Rect(0, 0, scaledBitmapWidth, scaledBitmapHeight);

        // crop inside or outside
        if (keepInner) {
            // replace the region outside the path with empty bitmap
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            canvas.drawBitmap(emptyBitmap, null, rect, paint);

            // keep the region inside the path
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, null, rect, paint);

            // crop out only the region inside the path
            resultImage = Bitmap.createBitmap(resultImage, (int) smallestX, (int) smallestY, (int) (biggestX - smallestX), (int) (biggestY - smallestY));

            // re-scale image
            resultImage = scaleBitmap(resultImage);
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            canvas.drawBitmap(bitmap, null, rect, paint);
        }

        fragment.setResultImageBitmap(resultImage);

        this.setVisibility(GONE);

        didCrop = true;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean wasUsed() {
        return didCrop;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you Want to save Crop or Non-crop image?")
                .setPositiveButton("Crop", dialogClickListener)
                .setNegativeButton("Non-crop", dialogClickListener).show()
                .setCancelable(false);
    }
}