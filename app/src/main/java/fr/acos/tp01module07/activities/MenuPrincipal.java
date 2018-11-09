package fr.acos.tp01module07.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.acos.tp01module07.R;

public class MenuPrincipal extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Charge le menu décrit du fichier menu_etoile.xml
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //Actions liées au différentes options du menu.
        switch (item.getItemId())
        {
            case R.id.item_marques :
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_statistiques :
                Toast.makeText(this, "Stats", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_rechercher :
                Toast.makeText(this, "Rechercher", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
