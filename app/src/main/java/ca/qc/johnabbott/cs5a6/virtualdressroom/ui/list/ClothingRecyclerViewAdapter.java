package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.list;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.ListItemClothingBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.ClothingType;

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
                public boolean onLongClick(View v) {
                    NavController navController = ((MainActivity)modelFragment.getActivity()).getNavController();
                    ClothingListBottomSheet clothingListBottomSheet = new ClothingListBottomSheet(modelFragment.getContext(), clothingItem, ClothingRecyclerViewAdapter.this, navController);
                    clothingListBottomSheet.show();

                    return false;
                }
            });

            this.binding.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add To Model
                }
            });

        }

        public void bind(ClothingItem item)
        {
            clothingItem = item;

            // Temporary

            if (clothingItem.getImage()[0] == 0)
            {
                binding.imageView.setBackgroundResource(R.drawable.black_shirt);
            }

            if (clothingItem.getImage()[0] == 1)
            {
                binding.imageView.setBackgroundResource(R.drawable.khaki);
            }


        }

    }
}