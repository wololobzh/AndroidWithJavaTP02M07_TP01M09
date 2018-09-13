package fr.acos.tp01module07.services_web;

import fr.acos.tp01module07.services_web.bo.InfoMarque;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 *
 * Interface permettant de créer les fonctions d'accès au webservice
 *
 * Created by acoss on 13/09/2018.
 */
public interface MarqueService
{
    /**
     * Permet d'envoyer une requête qui retournera une liste de marque
     *
     * Requête GET de la forme :
     *
     * http://172.19.11.0:8080/BeDeveloper/MarqueServlet
     */
    @GET("MarqueServlet")
    Call<InfoMarque> getMarques();
}
