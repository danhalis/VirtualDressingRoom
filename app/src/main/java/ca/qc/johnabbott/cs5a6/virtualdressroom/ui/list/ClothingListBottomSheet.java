package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers.LowerBodyOutfitPhotoTable;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.dbhandlers.UpperBodyOutfitPhotoTable;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingStatus;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.Photo;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.BottomSheetClothingListBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;

/**
 * A bottom sheet dialog that can trash and set the priority to high for a task in a recycler view.
 */
public class ClothingListBottomSheet extends BottomSheetDialog
{
    private BottomSheetClothingListBinding binding;
    private ClothingItem item;
    private ClothingRecyclerViewAdapter adapter;
    private NavController navController;
    private UpperBodyOutfitPhotoTable topsTable;
    private LowerBodyOutfitPhotoTable bottomsTable;

    public ClothingListBottomSheet(@NonNull Context context, ClothingItem item, ClothingRecyclerViewAdapter adapter, NavController navController, Table clothingTable)
    {
        super(context);
        this.item = item;

        if (this.item.getType() == ClothingType.TOP)
        {
            this.topsTable = (UpperBodyOutfitPhotoTable) clothingTable;
        }
        else
        {
            this.bottomsTable = (LowerBodyOutfitPhotoTable) clothingTable;
        }

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
                Photo photo = new Photo();
                photo.setId(ClothingListBottomSheet.this.item.getId());
                photo.setBytes(ClothingListBottomSheet.this.item.getImage());

                try
                {
                    if (ClothingListBottomSheet.this.item.getType() == ClothingType.TOP)
                    {
                        ClothingListBottomSheet.this.topsTable.delete(photo);
                    }
                    else
                    {
                        ClothingListBottomSheet.this.bottomsTable.delete(photo);
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Error: " + e);
                }

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
                ClothingListBottomSheet.this.navController.navigate(R.id.action_modelFragment_to_cropPhotoFragment);
                dismiss();
            }
        });

        setContentView(binding.getRoot());
    }

}
