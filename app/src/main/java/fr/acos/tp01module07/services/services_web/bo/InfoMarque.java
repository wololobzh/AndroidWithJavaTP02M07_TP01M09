package fr.acos.tp01module07.services.services_web.bo;

import java.util.List;

/**
 * Classe objet permettant de receptionner les valeurs de la réponse JSON du webservice
 * Les propriété doivent avoir les même noms que les propriétés de la réponse JSON.
 *
 * Created by acoss on 13/09/2018.
 */
public class InfoMarque
{
    private List<String> Marque ;

    public InfoMarque(List<String> marque) {
        Marque = marque;
    }

    public List<String> getMarque() {
        return Marque;
    }

    public void setMarque(List<String> marque) {
        Marque = marque;
    }
}
