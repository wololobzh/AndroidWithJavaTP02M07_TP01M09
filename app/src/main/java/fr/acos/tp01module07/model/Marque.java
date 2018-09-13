package fr.acos.tp01module07.model;

/**
 * Classe représentant une marque.
 *
 * Created by acoss on 13/09/2018.
 */
public class Marque
{
   private String libelle;

    public Marque(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Marque{" +
                "libelle='" + libelle + '\'' +
                '}';
    }
}
