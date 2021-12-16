package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;
import java.util.Objects;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.Photo;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.ViewHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels.CropPhotoViewModel;
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
    private CropPhotoViewModel viewModel;

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

    private void loadImageIntoCropView() {
        try {
            Long photoId = viewModel.getId();

            if (photoId == -1) {
                cropView.setBitmap(viewModel.getCurrentBitmap());
            }
            else {
                Photo photo;
                if (viewModel.getClothingType() == ClothingType.TOP) {
                    photo = activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().read(viewModel.getId());
                }
                else {
                    photo = activity.getApplicationDbHandler().getLowerBodyOutfitPhotoTable().read(viewModel.getId());
                }
                cropView.setBitmap(BitmapHelper.convertToBitmap(photo.getBytes()));
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_photo, container, false);

        activity = (MainActivity) getActivity();
        assert activity != null;
        viewModel = activity.getCropPhotoViewModel();

        if (cropView == null) {
            cropView = view.findViewById(R.id.cropView);
            cropView.setFragment(this);
        }
        else {
            initNewCropView();
        }

        loadImageIntoCropView();

        resultImageView = view.findViewById(R.id.cropResultImageView);

        Button undoButton = view.findViewById(R.id.undoCropButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewCropView();
            }
        });

        Button nextButton = view.findViewById(R.id.goToSaveCroppedPhotoFragmentButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = resultImageView.getDrawable();

                Bitmap bitmap;
                if (drawable == null) {
                    bitmap = cropView.getBitmap();
                }
                else {
                    bitmap = BitmapHelper.convertToBitmap(resultImageView.getDrawable());
                }

                viewModel.setCurrentBitmap(bitmap);
                activity.getNavController().navigate(R.id.action_cropPhotoFragment_to_saveCroppedPhotoFragment);
            }
        });

        return view;
    }

    private void initNewCropView() {

        if (!cropView.wasUsed()) return;

        CropView newCropView = new CropView(activity);
        newCropView.setFragment(CropPhotoFragment.this);
        ViewHelper.replaceView(cropView, newCropView);
        cropView = newCropView;

        loadImageIntoCropView();

        resultImageView.setImageBitmap(null);
    }

    public void setResultImageBitmap(Bitmap bitmap) {
        resultImageView.setImageBitmap(bitmap);
    }
}