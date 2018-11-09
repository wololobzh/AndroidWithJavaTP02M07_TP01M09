package fr.acos.tp01module07.dao.bdd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import fr.acos.tp01module07.model.Modele;

/**
 * Cette classe représente la base de données.
 */
@Database(entities = {Modele.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ModeleDao modeleDao();
}

