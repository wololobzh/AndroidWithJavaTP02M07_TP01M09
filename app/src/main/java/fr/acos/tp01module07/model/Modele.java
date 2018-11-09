package fr.acos.tp01module07.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Classe représentant un modèle.
 */
@Entity
public class Modele
{
    @PrimaryKey
    int Id;

    @ColumnInfo
    String Marque;

    @ColumnInfo
    String ModeleDossier;

    @ColumnInfo
    String ModeleCommercial;

    @ColumnInfo
    String CNIT;

    @ColumnInfo
    String Designation;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMarque() { return Marque; }

    public void setMarque(String marque) { Marque = marque; }

    public String getModeleDossier() {
        return ModeleDossier;
    }

    public void setModeleDossier(String modeleDossier) {
        ModeleDossier = modeleDossier;
    }

    public String getModeleCommercial() {
        return ModeleCommercial;
    }

    public void setModeleCommercial(String modeleCommercial) { ModeleCommercial = modeleCommercial; }

    public String getCNIT() {
        return CNIT;
    }

    public void setCNIT(String CNIT) {
        this.CNIT = CNIT;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    @Override
    public String toString() {
        return "Modele{" +
                "Id=" + Id +
                ", ModeleDossier='" + ModeleDossier + '\'' +
                ", ModeleCommercial='" + ModeleCommercial + '\'' +
                ", CNIT='" + CNIT + '\'' +
                ", Designation='" + Designation + '\'' +
                '}';
    }
}
