package com.example.steven.tripcar;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RegistroFragment.OnFragmentInteractionListener,LoginFragment.OnFragmentInteractionListener,CochesFragment.OnFragmentInteractionListener,GestionReservasFragment.OnFragmentInteractionListener,
        BienvenidoFragment.OnFragmentInteractionListener,CocheSelectFragment.OnFragmentInteractionListener{

/*para un boton de home en la esquina de abajo meterlo en app_Bar_activity
<android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:srcCompat="@android:drawable/ic_dialog_email" />
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Fragment fragment = new RegistroFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.getMenu().findItem(R.id.nav_exit).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_gestion).setVisible(false);
        TextView email  = (TextView)headerView.findViewById(R.id.emailLog);
        TextView nombre  = (TextView)headerView.findViewById(R.id.nombreLog);
        nombre.setText("user");

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        Fragment currentFragment  = null;
        boolean fragmentSeleccionado =  false;
        if (id == R.id.nav_loing) {
            currentFragment = new LoginFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_coches) {
            currentFragment = new CochesFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_gestion) {
            currentFragment = new GestionReservasFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_exit) {

            SharedPreferences prefe=getSharedPreferences("UsuarioEmail", Context.MODE_PRIVATE);
            String d=prefe.getString("Email", "");
            if (d.length()==0) {
                currentFragment = new LoginFragment();
                fragmentSeleccionado = true;
            }
            else {

                SharedPreferences.Editor editor=prefe.edit();
                editor.putString("Email","");
                editor.commit();
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView email  = (TextView)headerView.findViewById(R.id.emailLog);
                email.setText("usario@user.com");
                TextView nombre  = (TextView)headerView.findViewById(R.id.nombreLog);
                nombre.setText("user");
                navigationView.getMenu().findItem(R.id.nav_exit).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_gestion).setVisible(false);

                Toast toast1 =Toast.makeText(getApplicationContext(),"Sesión cerrada", Toast.LENGTH_SHORT);
                toast1.show();
                currentFragment = new LoginFragment();
                fragmentSeleccionado = true;

            }

        }
        if(fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,currentFragment).addToBackStack(null).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
