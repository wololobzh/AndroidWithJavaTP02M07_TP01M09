package fr.acos.tp01module07.activities;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import java.util.List;
import fr.acos.tp01module07.R;
import fr.acos.tp01module07.adapter.MarqueAdapter;
import fr.acos.tp01module07.dao.bdd.AppDatabase;
import fr.acos.tp01module07.dao.web.MarqueDao;
import fr.acos.tp01module07.dao.web.ModeleDao;
import fr.acos.tp01module07.model.Marque;
import fr.acos.tp01module07.model.Modele;
import fr.acos.tp01module07.services.services_bdd.ModeleService;

/**
 * Activité principale.
 */
public class MainActivity extends MenuPrincipal implements MarqueAdapter.OnClicSurUnItem<Marque>, MarqueDao.MarqueDaoAsync, ModeleDao.ModeleDaoAsync, ModeleService.ModeleDaoAsync
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
     * Varaible peremttant d'enregistrer la marque séléctionnée.
     */
    private String marqueSelectionnee;
    /**
     * Callback exécuté au moment de la création de l'activité.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG,"MainActivity - Début de la méthode onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permet de vider la bdd au démarrage de l'application
        if(false)
        {
            Log.i(TAG, "MainActivity - Vidage de la bdd.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "MainActivity - Début de la suppression des éléments de la table modeles");
                    //Création de la DAO base de données.
                    AppDatabase db = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "database-name").build();
                    fr.acos.tp01module07.dao.bdd.ModeleDao daoBdd = db.modeleDao();
                    daoBdd.deleteAll();
                    Log.i(TAG, "MainActivity - Fin de la suppression des éléments de la table modeles");
                }
            }).start();
        }

        //Récupéraction d'un objet représentant le Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_marques);

        // Permet d’optimiser le chargement dans le cas ou le recycler view ne change pas de taille.
        mRecyclerView.setHasFixedSize(true);

        // Utilisation du linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MarqueDao dao = new MarqueDao(this);
        dao.get();
        Log.i(TAG,"MainActivity - Fin de la méthode onCreate()");
    }

    /**
     * Fonction exécutée lorsque la consultation de la DAO Marque est terminée.
     *
     * @param marques
     */
    @Override
    public void onDataReady(List<Marque> marques)
    {
        Log.i(TAG,"MainActivity - Début de la méthode onDataReady()");
        //Création de l'adapteur
        mAdapter = new MarqueAdapter(marques,this);
        //Lie l’adapter au recycler view
        mRecyclerView.setAdapter(mAdapter);
        Log.i(TAG,"MainActivity - Fin de la méthode onDataReady()");
    }
    /**
     * Fonction exécutée lorsque la consultation de la DAO Modele est terminée.
     *
     * @param modeles
     */
    @Override
    public void onDataModeleReady(List<Modele> modeles)
    {
        Log.i(TAG,"Entrée dans la méthode  - onDataModeleReady");
        new TransfertAsync().execute(modeles);
    }
    /**
    * Action réalisée lors d'un clic sur une Marque.
    */
    @Override
    public void onInteraction(Marque info)
    {
        marqueSelectionnee = info.getLibelle();
        AskTransfert ask = new AskTransfert();
        ask.execute();
    }
    /**
     * Implementation obligatoire pour modelesService, cette méthode est executée lorsque tous les modèles sont récupérés via la fonction get().
     *
     * @param modeles
     */
    @Override
    public void onDataReadyModelesService(List<Modele> modeles)
    {

    }

    /**
     * Permet de transférer les modèles du web dans la bdd.
     */
    private class TransfertAsync extends AsyncTask<List<Modele>,Void,Void>
    {
        @Override
        protected Void doInBackground(List<Modele>... lists)
        {
            Log.i(TAG,"MainActivity - TransfertAsync - Entrée dans la méthode doInBackground pour commencer le transfert WEB -> BDD");
            //Création de la DAO base de données.
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database-name").build();
            fr.acos.tp01module07.dao.bdd.ModeleDao daoBdd = db.modeleDao();
            Log.i(TAG,"MainActivity - Nombre de modèles venant du web : " + lists[0].size());
            for(Modele item : lists[0])
            {
                item.setMarque(marqueSelectionnee);
                Log.i(TAG,"Id : " + item.getId());
                daoBdd.insert(item);
            }
            Log.i(TAG,"MainActivity - TransfertAsync - Fin du transfert WEB -> BDD ");
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG,"MainActivity - TransfertAsync - Début de onPostExecute() ");
            super.onPostExecute(aVoid);
            Log.i(TAG,"MainActivity - TransfertAsync - Intent ");
            Intent intent = new Intent(MainActivity.this, ModelesActivity.class);
            intent.putExtra("Marque",marqueSelectionnee);
            Log.i(TAG,"MainActivity - TransfertAsync - startActivity ");
            startActivity(intent);
        }
    }

    /**
     * Permet de gérer la demande ou non de transfert des modèles du web vers la bdd.
     */
    private class AskTransfert extends AsyncTask<Void,Void,Integer>
    {
        @Override
        protected Integer doInBackground(Void... marques)
        {
            ModeleService service = new ModeleService(MainActivity.this);
            return service.count(marqueSelectionnee);
        }

        @Override
        protected void onPostExecute(Integer nbEnregistrement)
        {
            super.onPostExecute(nbEnregistrement);
            Log.i(TAG,"onPostExecute - nbEnregistrement : " + nbEnregistrement);

            if(nbEnregistrement == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Voulez-vous charger les modèles de la marque ?");
                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Log.i(TAG,"Appel de onInteraction" );
                        ModeleDao dao = new ModeleDao(MainActivity.this);
                        dao.get(marqueSelectionnee);
                    }
                });
                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
            {
                    Log.i(TAG,"onPostExecute - Transfert déja fait.");
                    Intent intent = new Intent(MainActivity.this, ModelesActivity.class);
                    intent.putExtra("Marque",marqueSelectionnee);
                    Log.i(TAG,"MainActivity - TransfertAsync - startActivity ");
                    startActivity(intent);
            }
        }
    }
}