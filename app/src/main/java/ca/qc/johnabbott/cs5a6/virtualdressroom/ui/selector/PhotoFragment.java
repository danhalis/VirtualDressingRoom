package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.selector;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.FragmentPhotoBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.GalleryPhoto;


public class PhotoFragment extends Fragment {

    private FragmentPhotoBinding binding;
    private MainActivity mainActivity;
    private GalleryPhoto galleryPhoto;
    private ImageView imageView;
    public PhotoFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryPhoto=mainActivity.getGalleryPhotoDBHandler().getOriginalPhoto();
        imageView= binding.imageView;
        String url = galleryPhoto.getWebformatURL();
        Glide.with(mainActivity).load(url).into(imageView);
        binding.Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getNavController().navigate(R.id.action_photoFragment_to_selectPhotoFragment);
               }
        });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mainActivity.getNavController().navigate(R.id.action_photoFragment_to_cropPhotoFragment);

            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
}