package fr.acos.tp01module07.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import fr.acos.tp01module07.R;
import fr.acos.tp01module07.adapter.ModeleAdapter;
import fr.acos.tp01module07.model.Modele;
import fr.acos.tp01module07.services.services_bdd.ModeleService;

/**
 * Activité permettant d'afficher tout les modèles.
 */
public class ModelesActivity extends MenuPrincipal implements ModeleAdapter.OnClicSurUnItem<Modele>, ModeleService.ModeleDaoAsync
{
    /**
     * TAG pour les logs
     */
    private static final String TAG = "XXX";
    /**
     * Variable pour gérer le RecyclerView
     */
    private RecyclerView mRecyclerView;
    /**
     * Variable pour gérer le RecyclerView
     */
    private RecyclerView.Adapter mAdapter;
    /**
     * Variable pour gérer le RecyclerView
     */
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * Callback exécuté au moment de la création de l'activité.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG,"ModelesActivity - Début de la méthode onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeles);

        //Récupéraction d'un objet représentant le Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_modeles);

        // Permet d’optimiser le chargement dans le cas ou le recycler view ne change pas de taille.
        mRecyclerView.setHasFixedSize(true);

        // Utilisation du linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String marque = getIntent().getStringExtra("Marque");
        final ModeleService service = new ModeleService(this);
        service.get(marque);
        Log.i(TAG,"ModelesActivity - Fin de la méthode onCreate()");
    }

    /**
     * Action réalisée lors d'un clic sur un élément du RecyclerView.
     */
    @Override
    public void onInteraction(Modele info)
    {
        Log.i(TAG,"ModelesActivity - Appel de onInteraction" );
        Toast.makeText(this, info.getDesignation(), Toast.LENGTH_SHORT).show();
    }

    /**
     *  Action réalisée lors de la fin du chargement des modeles par ModeleService(bdd).
     *
     * @param modeles
     */
    @Override
    public void onDataReadyModelesService(List<Modele> modeles) {
        Log.i(TAG,"ModelesActivity - Appel de onDataReadyModelesService" );
        //Création de l'adapteur
        mAdapter = new ModeleAdapter(modeles,this);
        //Lie l’adapter au recycler view
        mRecyclerView.setAdapter(mAdapter);
    }
}
