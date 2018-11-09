package fr.acos.tp01module07.dao.web;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import fr.acos.tp01module07.model.Modele;
import fr.acos.tp01module07.services.services_web.ModeleService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DAO pour récupérer les marques via un service REST.
 *
 * Created by acoss on 13/09/2018.
 */
public class ModeleDao
{
    /**
     * TAG pour les logs
     */
    private static final String TAG = "XXX";
    /**
     * Variable representant l'activité appelante.
     */
    private ModeleDaoAsync activite = null;
    /**
     *  Interface devant etre implementé par l'activité appelante.
     */
    public interface ModeleDaoAsync
    {
        void onDataModeleReady(List<Modele> modeles);
    }

    /**
     * Constructeur.
     *
     * @param activite
     */
    public ModeleDao(ModeleDaoAsync activite)
    {
        this.activite = activite;
    }


    /**
     * Cette methode va rechercher la liste des modeles selon une marque.
     *
     * @param marque
     * @return
     */
    public List<Modele> get(String marque)
    {
        List<Modele> resultat = null;
        Log.i(TAG,"Appel de la fonction get() de la classe ModeleDao avec marque = " + marque);

        Chargement chargement = new Chargement();
        chargement.execute(marque,null,null);
        return resultat;
    }

    /**
     * Cette fonction va rechercher la liste des modeles dans un nouveau thread.
     * Cette methode est utilisée par la fonction get.
     */
    private class Chargement extends AsyncTask<String, Void, List<Modele> >
    {
        @Override
        protected List<Modele>  doInBackground(String... marque)
        {
            Log.i(TAG,"Recherche des marques sur le web service");

            List<Modele> resultat = new ArrayList<>();

            //Création d'une implémentation de l'interface MarqueService
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://172.19.11.4:8080/BeDeveloper/").addConverterFactory(GsonConverterFactory.create()).build();

            ModeleService service = retrofit.create(ModeleService.class);

            //Récupération d'un objet de type Call qui permettra de faire des requêtes sur le web service
            //Les requêtes ont cette forme : http://172.19.11.0:8080/BeDeveloper/MarqueServlet
            final Call<List<Modele>> callModeles = service.getModelesByMarque(marque[0]);

            //Envoie de la requête
            try
            {
                Response reponse = callModeles.execute();
                //Récupération du résultat de type InfoMarque
                resultat = (List<Modele>) reponse.body();

                Log.i(TAG,"WEB MODELEDAO === TAILLE DE LA LISTE : " + resultat.size());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return resultat;
        }

        /**
         * Execute la fonction onDataModeleReady de l'activité appelante.
         *
         * @param marques
         */
        @Override
        protected void onPostExecute(List<Modele> marques)
        {
            super.onPostExecute(marques);
            activite.onDataModeleReady(marques);
        }
    }
}
