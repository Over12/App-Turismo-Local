package pe.turismo_local.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import pe.turismo_local.R;
import pe.turismo_local.databinding.ActivityContainerBinding;
import pe.turismo_local.view.fragments.TacnaMapFragment;
import pe.turismo_local.view.fragments.ArequipaMapFragment;
import pe.turismo_local.view.fragments.LimaMapFragment;

public class ContainerActivity extends AppCompatActivity {
    private ActivityContainerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LimaMapFragment())
                .commit();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        binding.bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_arequipa) {
                selectedFragment = new ArequipaMapFragment();
            }

            if (id == R.id.nav_lima) {
                selectedFragment = new LimaMapFragment();
            }

            if (id == R.id.nav_tacna) {
                selectedFragment = new TacnaMapFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}