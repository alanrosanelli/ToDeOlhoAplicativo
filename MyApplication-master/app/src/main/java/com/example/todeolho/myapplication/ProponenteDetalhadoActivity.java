package com.example.todeolho.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todeolho.myapplication.classes.Proponente;

public class ProponenteDetalhadoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Proponente proponente = new Proponente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proponente_detalhado);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#437844")));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        TextView txtCnpj = (TextView) findViewById(R.id.txtCnpj);
        TextView txtMunicipio = (TextView) findViewById(R.id.txtMunicipio);
        TextView txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        TextView txtCep = (TextView) findViewById(R.id.txtCep);
        TextView txtNomeResponsavel = (TextView) findViewById(R.id.txtNomeResponsavel);
        TextView txtCpfResponsavel = (TextView) findViewById(R.id.txtCpfResponsavel);
        TextView txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        TextView txtInscEstadual = (TextView) findViewById(R.id.txtInscEstadual);
        TextView txtInscMunicipal = (TextView) findViewById(R.id.txtInscMunicipal);

        txtCnpj.setLineSpacing(15f,1f);
        txtNomeResponsavel.setLineSpacing(15f,1f);
        txtCpfResponsavel.setLineSpacing(15f,1f);
        txtTelefone.setLineSpacing(15f,1f);
        txtMunicipio.setLineSpacing(15f,1f);
        txtEndereco.setLineSpacing(15f,1f);
        txtInscEstadual.setLineSpacing(15f,1f);
        txtInscMunicipal.setLineSpacing(15f,1f);
        txtCep.setLineSpacing(15f,1f);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            proponente = (Proponente)getIntent().getSerializableExtra("proponente");
            txtCnpj.setText(proponente.getCnpj());
            txtMunicipio.setText(proponente.getMunicipio());
            txtEndereco.setText(proponente.getEndereco());
            txtCep.setText(proponente.getCep());
            txtNomeResponsavel.setText(proponente.getNomeresponsavel());
            txtCpfResponsavel.setText(proponente.getCpfresponsavel());
            txtTelefone.setText(proponente.getTelefone());
            txtInscEstadual.setText(proponente.getInscricaoestadual());
            txtInscMunicipal.setText(proponente.getInscricaomunicipal());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:

                Bundle bundle = new Bundle();
                bundle.putString("id_proponente", proponente.getId());

                Intent secondActivity = new Intent(ProponenteDetalhadoActivity.this, ConvenioActivity.class);
                startActivity(secondActivity.putExtras(bundle));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_buscamunicipio) {
            Intent secondActivity = new Intent(ProponenteDetalhadoActivity.this, MainActivity.class);
            startActivity(secondActivity);
        }

//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        }

        else if (id == R.id.nav_share) {

        }

//        else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
