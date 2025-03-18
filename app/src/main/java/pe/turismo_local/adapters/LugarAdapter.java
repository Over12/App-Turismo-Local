package pe.turismo_local.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.turismo_local.R;
import pe.turismo_local.model.LugarTuristico;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.LugarViewHolder> {
    private final List<LugarTuristico> lugares;

    public LugarAdapter(List<LugarTuristico> lugares) {
        this.lugares = lugares;
    }

    @NonNull
    @Override
    public LugarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugar, parent, false);
        return new LugarViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LugarViewHolder holder, int position) {
        LugarTuristico lugar = lugares.get(position);
        holder.txtNombre.setText(lugar.getNombre() + " - " + lugar.getCuidad());
        Glide.with(holder.itemView.getContext()).load(lugar.getImagen()).into(holder.imgLugar);
    }

    @Override
    public int getItemCount() {
        return lugares.size();
    }

    static class LugarViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        ImageView imgLugar;

        public LugarViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtLugar);
            imgLugar = itemView.findViewById(R.id.imgLugar);
        }
    }
}
