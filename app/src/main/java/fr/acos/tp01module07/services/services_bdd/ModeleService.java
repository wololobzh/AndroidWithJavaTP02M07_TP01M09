package fr.acos.tp01module07.services.services_bdd;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import fr.acos.tp01module07.dao.bdd.AppDatabase;
import fr.acos.tp01module07.model.Modele;

public class ModeleService
{
    /**
     * TAG pour les logs
     */
    private static final String TAG = "XXX";
    private ModeleService.ModeleDaoAsync activite = null;

    public List<Modele> get(String marque)
    {
        Log.i(TAG,"Appel de la fonction get() de la classe MarqueDao");

        ModeleService.Chargement chargement = new ModeleService.Chargement();
        chargement.execute(marque,null,null);
        return null;
    }

    public int count(String marque)
    {
        AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class, "database-name").build();
        fr.acos.tp01module07.dao.bdd.ModeleDao daoBdd = db.modeleDao();
        return daoBdd.count(marque);
    }

    private Activity getContext()
    {
        return (Activity)activite;
    }

    public interface ModeleDaoAsync
    {
        void onDataReadyModelesService(List<Modele> modeles);
    }

    public ModeleService(ModeleService.ModeleDaoAsync activite)
    {
        this.activite = activite;
    }

    private class Chargement extends AsyncTask<String, Void, List<Modele> >
    {
        @Override
        protected List<Modele>  doInBackground(String... marque)
        {
            Log.i(TAG,"Recherche des modeles sur la bdd");

            List<Modele> resultat = new ArrayList<>();

            //Création de la DAO base de données.
            AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class, "database-name").build();
            fr.acos.tp01module07.dao.bdd.ModeleDao daoBdd = db.modeleDao();

            return daoBdd.getAll(marque[0]);
        }

        @Override
        protected void onPostExecute(List<Modele> modeles) {
            super.onPostExecute(modeles);

            activite.onDataReadyModelesService(modeles);
        }
    }
}
