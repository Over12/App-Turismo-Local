package pe.turismo_local.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pe.turismo_local.R;
import pe.turismo_local.adapters.LugarAdapter;
import pe.turismo_local.model.LugarTuristico;

public class FavListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fav_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<LugarTuristico> lugares = getIntent().getParcelableArrayListExtra("lugares");

        View txtEmpty = findViewById(R.id.txtEmpty);
        View txtTitulo = findViewById(R.id.txtTitulo);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        if (lugares == null || lugares.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
            txtTitulo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.GONE);
            txtTitulo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            LugarAdapter adapter = new LugarAdapter(lugares);
            recyclerView.setAdapter(adapter);
        }
    }
}