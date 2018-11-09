package fr.acos.tp01module07.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import fr.acos.tp01module07.model.Modele;
import static fr.acos.tp01module07.R.*;

/**
 * Cette classe permet de créer les views du recycler view.
 *
 * ModeleAdapter.ViewHolder est définit à l’intérieur de la classe ModeleAdapter
 */
public class ModeleAdapter extends RecyclerView.Adapter<ModeleAdapter.ViewHolder> {

    /**
     * Permet de stocker les données à afficher.
     */

    private ArrayList<Modele> modeles;

    private OnClicSurUnItem action;

    /**
     * Fournit une référence aux vues pour chaque élément de données
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Chaque élément contient seulement une TextView
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(id.tv_modele);
            v.setOnClickListener(this);
        }

        /**
         * Action réalisée lors d'un clic sur un élément du RecyclerView.
         */
        @Override
        public void onClick(View v) {
            action.onInteraction(modeles.get(ModeleAdapter.ViewHolder.this.getAdapterPosition()));
        }
    }

    /**
     * Interface devant être implémenté par l'activité définissant le RecyclerView
     */
    public interface OnClicSurUnItem<T> {
        void onInteraction(T info);
    }

    /**
     * Constructeur qui attend les données à afficher en paramètre
     **/
    public ModeleAdapter(List<Modele> myDataset, ModeleAdapter.OnClicSurUnItem activite) {
        modeles = (ArrayList<Modele>) myDataset;
        action = activite;
    }

    /**
     * Créée un ViewHolder qui représente le fichier my_text_view.xml
     **/
    @Override
    public ModeleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.ligne_modele, parent, false);
        return new ModeleAdapter.ViewHolder(v);
    }

    /**
     * Remplie la vue représentant une ligne
     */
    @Override
    public void onBindViewHolder(ModeleAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(modeles.get(position).getDesignation());
    }

    /**
     * Retourne le nombre de données à afficher
     *
     * @return le nombre de données à afficher
     */
    @Override
    public int getItemCount() {
        return modeles.size();
    }
}