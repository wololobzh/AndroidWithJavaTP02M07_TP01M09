package fr.acos.tp01module07.adapter;

/**
 * Created by acoss on 13/09/2018.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import fr.acos.tp01module07.R;
import fr.acos.tp01module07.model.Marque;

/**
 * Cette classe permet de créer les views du recycler view.
 *
 * MarqueAdapter.ViewHolder est définit à l’intérieur de la classe MarqueAdapter
 */
public class MarqueAdapter extends RecyclerView.Adapter<MarqueAdapter.ViewHolder> {

    /**
     * Permet de stocker les données à afficher.
     */

    private ArrayList<Marque> marques;

    private OnClicSurUnItem action;

    /**
     * Fournit une référence aux vues pour chaque élément de données
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // Chaque élément contient seulement une TextView
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.tv_marque);
            v.setOnClickListener(this);
        }

        /**
         * Action réalisée lors d'un clic sur un élément du RecyclerView.
         */
        @Override
        public void onClick(View v) {
            action.onInteraction(marques.get(ViewHolder.this.getAdapterPosition()));
        }
    }

    /**
     * Interface devant être implémenté par l'activité définissant le RecyclerView
     */
    public interface OnClicSurUnItem<T> {
        void onInteraction(T info);
    }

    /**
     *  Constructeur qui attend les données à afficher en paramètre
     **/
    public MarqueAdapter(List<Marque> myDataset, OnClicSurUnItem activite) {
        marques = (ArrayList<Marque>)myDataset;
        action = activite;
    }

    /**
     *  Créée un ViewHolder qui représente le fichier my_text_view.xml
     **/
    @Override
    public MarqueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ligne_marque, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Remplie la vue représentant une ligne
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(marques.get(position).getLibelle());
    }

    /**
     * Retourne le nombre de données à afficher
     *
     * @return le nombre de données à afficher
     */
    @Override
    public int getItemCount() {
        return marques.size();
    }
}