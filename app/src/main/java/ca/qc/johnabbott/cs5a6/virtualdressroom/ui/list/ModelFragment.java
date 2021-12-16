package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.Photo;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.FragmentModelListBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper.BitmapHelper;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels.CropPhotoViewModel;
import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.viewmodels.ObservableModel;

/**
 * A fragment representing a list of Items.
 */
public class ModelFragment extends Fragment
{
    private FragmentModelListBinding binding;
    private static final String ARG_CLOTHING_LIST_COLUMN_COUNT = "column-count";
    private int clothingColumnCount = 1;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ModelFragment() { }

    @SuppressWarnings("unused")
    public static ModelFragment newInstance(int columnCount)
    {
        ModelFragment fragment = new ModelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CLOTHING_LIST_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            clothingColumnCount = getArguments().getInt(ARG_CLOTHING_LIST_COLUMN_COUNT);
        }

        MainActivity activity = (MainActivity) getActivity();

        activity.getCropPhotoViewModel().addOnUpdateListener(this, new ObservableModel.OnUpdateListener<CropPhotoViewModel>()
        {
            @Override
            public void onUpdate(CropPhotoViewModel item)
            {
                ClothingRecyclerViewAdapter adapter = (ClothingRecyclerViewAdapter) binding.topsRecyclerView.getAdapter();

                List<Photo> photos = new ArrayList<>();

                List<ClothingItem> clothingItems = new ArrayList<>();

                try
                {
                    if (item.getClothingType() == ClothingType.TOP)
                    {
                        photos = activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().readAll();
                    }
                    else
                    {
                        adapter = (ClothingRecyclerViewAdapter) binding.bottomsRecyclerView.getAdapter();

                        photos = activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().readAll();
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Error: " + e);
                }

                for (int i = 0; i < photos.size(); i++)
                {
                    Photo thePhoto = photos.get(i);
                    clothingItems.set(i, new ClothingItem(thePhoto.getId().intValue(), item.getClothingType(), thePhoto.getBytes()));
                }

                adapter.setClothingItems(clothingItems);

                adapter.notifyDataSetChanged();
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentModelListBinding.inflate(inflater, container, false);

        Context context = getContext();

        binding.headButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getNavController().navigate(R.id.action_modelFragment_to_selectPhotoOptionsFragment);
            }
        });

        binding.topsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                binding.topsImageView.setImageDrawable(null);
            }
        });

        binding.bottomsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                binding.bottomsImageView.setImageDrawable(null);
            }
        });

        binding.topsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.bottomsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        MainActivity activity = (MainActivity) getActivity();

        List<Photo> topPhotos = new ArrayList<>();

        try {
            topPhotos = activity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable().readAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        List<ClothingItem> upperBodyItems = new ArrayList<>();

        for (int i = 0; i < topPhotos.size(); i++)
        {
            upperBodyItems.add(new ClothingItem(i, ClothingType.TOP, topPhotos.get(i).getBytes()));
            upperBodyItems.get(i).setId(topPhotos.get(i).getId());
        }

        binding.topsRecyclerView.setAdapter(new ClothingRecyclerViewAdapter(upperBodyItems, this, ClothingType.TOP));

        List<Photo> bottomPhotos = new ArrayList<>();

        try {
            bottomPhotos = activity.getApplicationDbHandler().getLowerBodyOutfitPhotoTable().readAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        List<ClothingItem> lowerBodyItems = new ArrayList<>();

        for (int i = 0; i < bottomPhotos.size(); i++)
        {
            lowerBodyItems.add(new ClothingItem(i, ClothingType.BOTTOM, bottomPhotos.get(i).getBytes()));
            lowerBodyItems.get(i).setId(bottomPhotos.get(i).getId());
        }

        binding.bottomsRecyclerView.setAdapter(new ClothingRecyclerViewAdapter(lowerBodyItems, this, ClothingType.BOTTOM));

        binding.topsRecyclerView.getAdapter();
        binding.bottomsRecyclerView.getAdapter();

        return binding.getRoot();
    }

    public FragmentModelListBinding getBinding()
    {
        return binding;
    }
}