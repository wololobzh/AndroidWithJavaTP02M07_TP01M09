package fr.acos.tp01module07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import fr.acos.tp01module07.adapter.MarqueAdapter;
import fr.acos.tp01module07.dao.MarqueDao;
import fr.acos.tp01module07.model.Marque;

/**
 * Activité principale.
 */
public class MainActivity extends AppCompatActivity implements MarqueAdapter.OnClicSurUnItem<Marque>, MarqueDao.MarqueDaoAsync
{
    /**
     * TAG pour les logs
     */
    private static final String TAG = "XXX";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Callback exécuté au moment de la création de l'activité.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupéraction d'un objet représentant le Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_marques);

        // Permet d’optimiser le chargement dans le cas ou le recycler view ne change pas de taille.
        mRecyclerView.setHasFixedSize(true);

        // Utilisation du linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MarqueDao dao = new MarqueDao(this);
        dao.get();
    }

    /**
     * Fonction exécutée lorsque la consultation de la DAO est terminée.
     *
     * @param marques
     */
    @Override
    public void onDataReady(List<Marque> marques)
    {
        //Création de l'adapteur
        mAdapter = new MarqueAdapter(marques,this);
        //Lie l’adapter au recycler view
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
    * Action réalisée lors d'un clic sur un élément du RecyclerView.
    */
    @Override
    public void onInteraction(Marque info)
    {
        Log.i(TAG,"Appel de onInteraction" );
        Toast.makeText(this, info.getLibelle(), Toast.LENGTH_SHORT).show();
    }
}
