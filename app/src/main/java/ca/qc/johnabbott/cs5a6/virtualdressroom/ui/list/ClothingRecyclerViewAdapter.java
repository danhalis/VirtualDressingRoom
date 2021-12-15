package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.list;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.ListItemClothingBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.Table;

import java.util.List;

public class ClothingRecyclerViewAdapter extends RecyclerView.Adapter<ClothingRecyclerViewAdapter.ViewHolder> {

    private List<ClothingItem> clothingItems;
    private ModelFragment modelFragment;
    private ClothingType clothingType;

    public ClothingRecyclerViewAdapter(List<ClothingItem> items, ModelFragment fragment, ClothingType type)
    {
        clothingItems = items;
        this.modelFragment = fragment;
        clothingType = type;
    }

    public void RemoveClothingItemFromList(Long id)
    {
        for (int i = 0; i < clothingItems.size(); i++)
        {
            if (clothingItems.get(i).getId().equals(id))
            {
                clothingItems.remove(i);
                break;
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return new ViewHolder(ListItemClothingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.bind(clothingItems.get(position));
    }

    @Override
    public int getItemCount()
    {
        return clothingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ListItemClothingBinding binding;
        public ClothingItem clothingItem;

        public ViewHolder(ListItemClothingBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;


            this.binding.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v)
                {
                    MainActivity mainActivity = (MainActivity) modelFragment.getActivity();

                    Table theTable;

                    if (ClothingRecyclerViewAdapter.this.clothingType == ClothingType.TOP)
                    {
                        theTable = mainActivity.getApplicationDbHandler().getUpperBodyOutfitPhotoTable();
                    }
                    else
                    {
                        theTable = mainActivity.getApplicationDbHandler().getLowerBodyOutfitPhotoTable();
                    }

                    NavController navController = mainActivity.getNavController();
                    ClothingListBottomSheet clothingListBottomSheet = new ClothingListBottomSheet(modelFragment.getContext(), clothingItem, ClothingRecyclerViewAdapter.this, navController, theTable);
                    clothingListBottomSheet.show();

                    return false;
                }
            });

            this.binding.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Bitmap bmpClothingItem = BitmapFactory.decodeByteArray(clothingItem.getImage(), 0, clothingItem.getImage().length);

                    if (clothingItem.getType() == ClothingType.TOP)
                    {
                        modelFragment.getBinding().topsImageView.setImageBitmap(bmpClothingItem);
                    }
                    else
                    {
                        modelFragment.getBinding().bottomsImageView.setImageBitmap(bmpClothingItem);
                    }

                }
            });

        }

        public void bind(ClothingItem item)
        {
            clothingItem = item;

            Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);

            binding.imageView.setImageBitmap(bmp);
        }

    }
}