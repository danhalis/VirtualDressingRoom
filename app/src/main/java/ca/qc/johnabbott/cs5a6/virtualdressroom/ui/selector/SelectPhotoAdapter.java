package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.selector;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ca.qc.johnabbott.cs5a6.virtualdressroom.MainActivity;
import ca.qc.johnabbott.cs5a6.virtualdressroom.R;
import ca.qc.johnabbott.cs5a6.virtualdressroom.data.models.GalleryPhoto;
import ca.qc.johnabbott.cs5a6.virtualdressroom.placeholder.PlaceholderContent.PlaceholderItem;
import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.FragmentSelectPhotoBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SelectPhotoAdapter extends RecyclerView.Adapter<SelectPhotoAdapter.ViewHolder> {

    private Context context;
    private List<GalleryPhoto> images;

    private SelectPhotoFragment selectPhotoFragment;

    public SelectPhotoAdapter(Context context, List<GalleryPhoto> images,SelectPhotoFragment selectPhotoFragment) {
        this.context = context;
        this.images = images;
        this.selectPhotoFragment=selectPhotoFragment;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSelectPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  FragmentSelectPhotoBinding binding;
        public ImageView imageView;
        private GalleryPhoto galleryPhoto;
        public ViewHolder(FragmentSelectPhotoBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            imageView=binding.image;
            binding.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) context;

                    mainActivity.getGalleryPhotoDBHandler().setOriginalPhoto(galleryPhoto);
                    mainActivity.getNavController().navigate(R.id.action_selectPhotoFragment_to_photoFragment);

                }
            });
        }
        public void bind(int position){
            galleryPhoto=images.get(position);
            String url = images.get(position).getWebformatURL();
            Glide.with(context).load(url).into(imageView);


        }

    }

}