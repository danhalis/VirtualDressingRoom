package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.list;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return new ViewHolder(ListItemClothingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.bind(clothingItems.get(position));

        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
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
        }

        public void bind(ClothingItem item)
        {
            clothingItem = item;
        }

    }
}