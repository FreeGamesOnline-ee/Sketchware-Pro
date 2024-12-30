package pro.sketchware.xml.resources.editors.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.databinding.ItemStyleBinding;
import pro.sketchware.databinding.PalletCustomviewBinding;
import pro.sketchware.xml.resources.editors.fragments.ThemesEditor;
import pro.sketchware.xml.resources.editors.models.StyleModel;
import pro.sketchware.xml.resources.editors.fragments.StylesEditor;

import java.util.ArrayList;
import java.util.List;

public class StylesAdapter extends RecyclerView.Adapter<StylesAdapter.StyleViewHolder> {

    private final List<StyleModel> stylesList;
    private final List<StyleModel> originalList;
    private final Fragment fragment;

    public StylesAdapter(ArrayList<StyleModel> stylesList, Fragment fragment) {
        this.stylesList = stylesList;
        this.originalList = new ArrayList<>(stylesList);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public StyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PalletCustomviewBinding binding = PalletCustomviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new StyleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleViewHolder holder, int position) {
        StyleModel style = stylesList.get(position);
        holder.bind(style);
    }

    @Override
    public int getItemCount() {
        return stylesList.size();
    }

    public void filter(String newText) {
        stylesList.clear();

        for (StyleModel style : originalList) {
            if (style.getStyleName().toLowerCase().contains(newText)) {
                stylesList.add(style);
            }
        }

        notifyDataSetChanged();
    }

    public class StyleViewHolder extends RecyclerView.ViewHolder {

        private final PalletCustomviewBinding binding;

        public StyleViewHolder(PalletCustomviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StyleModel style) {
            binding.title.setText(style.getStyleName());
            if (style.getParent().isEmpty()) {
                binding.sub.setText("No Parent");
            } else {
                binding.sub.setText(style.getParent());
            }

            binding.backgroundCard.setOnClickListener(view ->{
                if (fragment instanceof StylesEditor stylesEditor) {
                    stylesEditor.showStyleAttributesDialog(getAbsoluteAdapterPosition());
                } else if (fragment instanceof ThemesEditor themesEditor) {
                    themesEditor.showThemeAttributesDialog(getAbsoluteAdapterPosition());
                }
            });

            binding.getRoot().setOnLongClickListener(view -> {
                if (fragment instanceof StylesEditor stylesEditor) {
                    stylesEditor.showEditStyleDialog(getAbsoluteAdapterPosition());
                } else if (fragment instanceof ThemesEditor themesEditor) {
                    themesEditor.showEditThemeDialog(getAbsoluteAdapterPosition());
                }
                return true;
            });
        }
    }
}