package id.kudzoza.findtechnician.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.kudzoza.findtechnician.R;
import id.kudzoza.findtechnician.model.TypeModel;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeItem> {

    private List<TypeModel> model;
    private Context context;

    public TypeAdapter(Context context, List<TypeModel> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public TypeItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View typeView = inflater.inflate(R.layout.item_type, parent, false);
        return new TypeItem(typeView);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeItem holder, int position) {
        TypeModel type = model.get(position);

        holder.name.setText(type.name);
        holder.description.setText(type.description);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    static class TypeItem extends RecyclerView.ViewHolder {

        TextView name = itemView.findViewById(R.id.name);
        TextView description = itemView.findViewById(R.id.description);

        TypeItem(@NonNull View itemView) {
            super(itemView);
        }
    }

}
