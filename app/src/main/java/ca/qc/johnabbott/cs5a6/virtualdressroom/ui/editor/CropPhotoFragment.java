package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.Photo;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.views.CropView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CropPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CropPhotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainActivity activity;
    private CropView cropView;
    private ImageView resultImageView;

    public CropPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CropPhotoFragment.
     */
    public static CropPhotoFragment newInstance(String param1, String param2) {
        CropPhotoFragment fragment = new CropPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_photo, container, false);

        activity = (MainActivity) getActivity();
        assert activity != null;
        activity.setCropPhotoFragment(this);

        Button saveButton = view.findViewById(R.id.saveCroppedPhotoButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapHelper.convertToBitmap(resultImageView.getDrawable());

                if (bitmap == null) return;

                byte[] bitmapData = BitmapHelper.convertToBytes(bitmap, Bitmap.CompressFormat.JPEG, 100);

                Photo photo = new Photo()
                        .setOutfitType(Photo.OutfitType.UPPER_BODY)
                        .setBytes(bitmapData);

                try {
                    activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().create(photo);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }
        });

        resultImageView = view.findViewById(R.id.cropResultImageView);

        cropView = view.findViewById(R.id.cropView);
        cropView.setFragment(this);

        try {
            List<Photo> photos = activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().readAll();

            if (!photos.isEmpty()) {
                cropView.setBitmap(BitmapHelper.convertToBitmap(photos.get(0).getBytes()));
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void setResultImageBitmap(Bitmap bitmap) {
        resultImageView.setImageBitmap(bitmap);
    }
}