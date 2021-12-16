package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.Photo;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels.CropPhotoViewModel;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.views.CropView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveCroppedPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveCroppedPhotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainActivity activity;
    private CropPhotoViewModel viewModel;

    private ImageView resultImageView;

    public SaveCroppedPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveCroppedPhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveCroppedPhotoFragment newInstance(String param1, String param2) {
        SaveCroppedPhotoFragment fragment = new SaveCroppedPhotoFragment();
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
        View view = inflater.inflate(R.layout.fragment_save_cropped_photo, container, false);

        activity = (MainActivity) getActivity();
        assert activity != null;
        viewModel = activity.getCropPhotoViewModel();

//        //get the spinner from the xml.
//        Spinner dropdown = view.findViewById(R.id.outfitTypeSpinner);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
//                R.array.outfit_types,
//                android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        dropdown.setAdapter(adapter);

        resultImageView = view.findViewById(R.id.cropResultImageView);
        resultImageView.setImageBitmap(viewModel.getCurrentBitmap());

        Button saveButton = view.findViewById(R.id.saveCroppedPhotoButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapHelper.convertToBitmap(resultImageView.getDrawable());

                if (bitmap == null) return;

                byte[] bitmapData = BitmapHelper.convertToBytes(bitmap, Bitmap.CompressFormat.PNG, 100);

                Photo photo = new Photo()
                        .setBytes(bitmapData);

                String notificationDescription;

                try {
                    if (viewModel.getId() != -1) {
                        photo.setId(viewModel.getId());

                        if (viewModel.getClothingType() == ClothingType.TOP) {
                            activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().update(photo);
                            notificationDescription = "Edited photo for upper body successfully!";
                        }
                        else {
                            activity.getApplicationDbHandler().getLowerBodyOutfitPhotoTable().update(photo);
                            notificationDescription = "Edited photo for lower body successfully!";
                        }
                    }
                    else {
                        if (viewModel.getClothingType() == ClothingType.TOP) {
                            activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().create(photo);
                            notificationDescription = "Saved photo for upper body successfully!";
                        }
                        else {
                            activity.getApplicationDbHandler().getLowerBodyOutfitPhotoTable().create(photo);
                            notificationDescription = "Saved photo for lower body successfully!";
                        }
                    }

                    activity.getCropPhotoViewModel().notifyChange();

                } catch (DatabaseException e) {
                    e.printStackTrace();
                    if (viewModel.getId() != -1) {
                        notificationDescription = "Something wrong happened. Cannot save the changes.";
                    }
                    else {
                        notificationDescription = "Something wrong happened. Cannot save the photo.";
                    }
                }

                Intent newIntent = new Intent(activity, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                int requestCode = photo.hashCode();
                PendingIntent pendingIntent = PendingIntent.getActivity(activity,
                        requestCode, newIntent, PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        activity,
                        MainActivity.APPLICATION_NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentTitle("Virtual Dressing Room")
                        .setContentText(notificationDescription)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                Notification notification = builder.build();

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity);
                notificationManagerCompat.notify(requestCode, notification);

                activity.getNavController().navigate(R.id.action_saveCroppedPhotoFragment_to_modelFragment);
            }
        });

        return view;
    }
}