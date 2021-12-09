package ca.qc.johnabbott.cs5a6.virtualdressroom;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.qc.johnabbott.cs5a6.virtualdressroom.databinding.FragmentSelectPhotoListBinding;
import ca.qc.johnabbott.cs5a6.virtualdressroom.placeholder.PlaceholderContent;
import ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite.DatabaseException;

/**
 * A fragment representing a list of Items.
 */
public class SelectPhotoFragment extends Fragment {
    private FragmentSelectPhotoListBinding binding;
    private SelectPhotoAdapter adapter;
    private MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }

    public SelectPhotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSelectPhotoListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.recyclerView.setHasFixedSize(true);


        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        try {
            adapter = new SelectPhotoAdapter(getContext(), mainActivity.getGalleryPhotoDBHandler().getGalleryPhotoTable().readAll(), this);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
  /* public  void fetchData(){
        StringRequest stringRequest= new StringRequest(Request.Method.GET, getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
images=Gson().fromJson(response, Pixabay.Class);
                List<Person> persons =gson.fromJson(json, new TypeToken<List<GalleryPhoto>>() {}.getType());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    private String getUrl() {
        return "https://pixabay.com/api/?key=24637787-3891e90ceb2f5d5081dd40724&q=clothes+man+woman&image_type=photo&pretty=true";

    }
    private void initDatafoot() throws FileNotFoundException {
        folder = new File(mainActivity.getFilesDir().getAbsolutePath().toString());
        GalleryPhoto galleryPhoto=new GalleryPhoto();
        allFiles = folder.list();

        if (allFiles.length == 0 ) {
            Toast.makeText(mainActivity, "no photos", Toast.LENGTH_LONG).show();
        } else {

            for (int i = 0; i < allFiles.length; i++) {
               Bitmap bitmap = BitmapFactory.decodeFile(folder+allFiles[i]);
               image.add(bitmap);

            }
        }
    }
}
*/