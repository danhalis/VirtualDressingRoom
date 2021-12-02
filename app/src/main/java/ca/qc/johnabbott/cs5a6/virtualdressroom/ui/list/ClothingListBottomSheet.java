package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingStatus;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.BottomSheetClothingListBinding;

/**
 * A bottom sheet dialog that can trash and set the priority to high for a task in a recycler view.
 */
public class ClothingListBottomSheet extends BottomSheetDialog
{
    private BottomSheetClothingListBinding binding;
    private ClothingItem item;
    private ClothingRecyclerViewAdapter adapter;
    private NavController navController;

    public ClothingListBottomSheet(@NonNull Context context, ClothingItem item, ClothingRecyclerViewAdapter adapter, NavController navController)
    {
        super(context);
        this.item = item;
        this.adapter = adapter;
        this.navController = navController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = BottomSheetClothingListBinding.inflate(getLayoutInflater());

        binding.trashLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                item.setStatus(ClothingStatus.DELETED);
                adapter.RemoveClothingItemFromList(item.getId());
                adapter.notifyDataSetChanged();
                dismiss();
            }
        });

        binding.editLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //ClothingListBottomSheet.this.navController.navigate(R.id.);
                dismiss();
            }
        });

        setContentView(binding.getRoot());
    }

}
