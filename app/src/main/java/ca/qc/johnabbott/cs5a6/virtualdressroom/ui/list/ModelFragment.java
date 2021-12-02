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

import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.FragmentModelListBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;

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
                //NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                //controller.navigate(R.id.action_taskListFragment_to_taskEditFragment);
            }
        });

        binding.topsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.bottomsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        List<ClothingItem> tempItems = new ArrayList<>();

        tempItems.add(new ClothingItem(0, ClothingType.TOP));
        tempItems.add(new ClothingItem(1, ClothingType.TOP));
        tempItems.add(new ClothingItem(2, ClothingType.TOP));
        tempItems.add(new ClothingItem(3, ClothingType.TOP));
        tempItems.add(new ClothingItem(4, ClothingType.TOP));
        tempItems.add(new ClothingItem(5, ClothingType.TOP));
        tempItems.add(new ClothingItem(6, ClothingType.TOP));

        binding.topsRecyclerView.setAdapter(new ClothingRecyclerViewAdapter(tempItems, this, ClothingType.TOP));

        List<ClothingItem> tempItems2 = new ArrayList<>();

        tempItems2.add(new ClothingItem(0, ClothingType.BOTTOM));
        tempItems2.add(new ClothingItem(1, ClothingType.BOTTOM));
        tempItems2.add(new ClothingItem(2, ClothingType.BOTTOM));
        tempItems2.add(new ClothingItem(3, ClothingType.BOTTOM));
        tempItems2.add(new ClothingItem(4, ClothingType.BOTTOM));
        tempItems2.add(new ClothingItem(5, ClothingType.BOTTOM));
        tempItems2.add(new ClothingItem(6, ClothingType.BOTTOM));

        binding.bottomsRecyclerView.setAdapter(new ClothingRecyclerViewAdapter(tempItems2, this, ClothingType.BOTTOM));

        binding.topsRecyclerView.getAdapter();
        binding.bottomsRecyclerView.getAdapter();

        return binding.getRoot();
    }
}