package fr.acos.tp01module07.services.services_web;

import java.util.List;

import fr.acos.tp01module07.model.Modele;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  Interface permettant de créer les fonctions d'accès au webservice
 *
 *  Created by acoss on 06/11/2018.
 */
public interface ModeleService
{
    /**
     * Permet d'envoyer une requête qui retournera une liste de modele szelon la marque
     *
     * Requête GET de la forme :
     *
     * http://172.19.11.0:8080/BeDeveloper/ModeleServlet?marque=MARQUE
     */
    @GET("ModeleServlet")
    Call<List<Modele>> getModelesByMarque(@Query("marque") String marque);
}
