package fr.acos.tp01module07.dao.web;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.acos.tp01module07.model.Marque;
import fr.acos.tp01module07.services.services_web.MarqueService;
import fr.acos.tp01module07.services.services_web.bo.InfoMarque;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DAO pour récupérer les marques via un service REST.
 *
 * Created by acoss on 13/09/2018.
 */
public class MarqueDao
{
    /**
     * TAG pour les logs
     */
    private static final String TAG = "XXX";
    /**
     * Variable representant l'activité appelante.
     */
    private MarqueDaoAsync activite = null;
    /**
     *  Interface devant etre implementé par l'activité appelante.
     */
    public interface MarqueDaoAsync
    {
        void onDataReady(List<Marque> marques);
    }
    /**
     * Constructeur.
     *
     * @param activite
     */
    public MarqueDao(MarqueDaoAsync activite)
    {
        this.activite = activite;
    }

    /**
     * Cette méthode permet de récupérer toute les marques.
     *
     * @return
     */
    public List<Marque> get()
      {
          Log.i(TAG,"Appel de la fonction get() de la classe MarqueDao");

          Chargement chargement = new Chargement();
          chargement.execute(null,null,null);
          return null;
      }

    /**
     * Méthode utilisé par la méthode get()
     */
    private class Chargement extends AsyncTask<Void, Void, List<Marque> >
    {
        @Override
        protected List<Marque>  doInBackground(Void... voids)
        {
            Log.i(TAG,"Recherche des marques sur le web service");

            List<Marque> resultat = new ArrayList<>();

            //Création d'une implémentation de l'interface MarqueService
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://172.19.11.4:8080/BeDeveloper/").addConverterFactory(GsonConverterFactory.create()).build();

            MarqueService service = retrofit.create(MarqueService.class);

            //Récupération d'un objet de type Call qui permettra de faire des requêtes sur le web service
            //Les requêtes ont cette forme : http://172.19.11.0:8080/BeDeveloper/MarqueServlet
            final Call<InfoMarque> callMarques = service.getMarques();

            //Envoie de la requête
            try
            {
                Response reponse = callMarques.execute();

                //Récupération du résultat de type InfoMarque
                InfoMarque info = (InfoMarque) reponse.body();

                Log.i(TAG,"Nombre de marques recuperées : " + info.getMarque().size());

                for (String item : info.getMarque())
                {
                    resultat.add(new Marque(item));
                    Log.i(TAG,"item : " + item);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return resultat;
        }

        @Override
        protected void onPostExecute(List<Marque> marques) {
            super.onPostExecute(marques);

            activite.onDataReady(marques);
        }
    }
}
