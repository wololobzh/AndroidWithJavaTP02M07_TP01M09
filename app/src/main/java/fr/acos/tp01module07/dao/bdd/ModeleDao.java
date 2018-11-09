package fr.acos.tp01module07.dao.bdd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import fr.acos.tp01module07.model.Modele;

/**
 * Représente la dao pour la table Modele.
 *
 * Exploitée par l'orm Room.
 */
@Dao
public interface ModeleDao
{
    @Insert
    void insert(Modele modele);

    @Query("SELECT * FROM modele WHERE Marque = :marque")
    List<Modele> getAll(String marque);

    @Query("DELETE FROM modele")
    void deleteAll();

    @Query("SELECT count(*) FROM modele WHERE Marque = :marque")
    int count(String marque);
}
