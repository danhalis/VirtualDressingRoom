package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.models.Point;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.views.CropView;

public class CropPhotoActivity extends AppCompatActivity {

    ImageView compositeImageView;
    boolean crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_photo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crop = extras.getBoolean("crop");
        }

        DisplayMetrics dm = new DisplayMetrics();
        try {
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        catch (Exception ex) {
        }

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        compositeImageView = (ImageView) findViewById(R.id.imageView);

        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.ic_android_black_24dp);
        Bitmap bitmap = BitmapHelper.convertToBitmap(drawable);

        Bitmap resultingImage = Bitmap.createBitmap(screenWidth,
                screenHeight,
                bitmap.getConfig());

        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        List<Point> points = CropView.getPoints();
        for (int i = 0; i < points.size(); i++) {
            path.lineTo(points.get(i).x, points.get(i).y);
        }
        canvas.drawPath(path, paint);

        if (crop) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        }
        // crop inside or outside
        canvas.drawBitmap(bitmap, 0, 0, paint);

        compositeImageView.setImageBitmap(resultingImage);
    }
}