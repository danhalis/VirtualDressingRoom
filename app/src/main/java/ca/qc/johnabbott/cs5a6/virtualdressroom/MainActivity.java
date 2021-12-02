package ca.qc.johnabbott.cs5a6.virtualdressroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;

import ca.qc.johnabbott.cs5a6.virtualdressroom.ui.editor.EditPhotoActivity;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        //Intent intent = new Intent(this, EditPhotoActivity.class);
        //startActivity(intent);
    }

    public NavController getNavController()
    {
        return navController;
    }
}