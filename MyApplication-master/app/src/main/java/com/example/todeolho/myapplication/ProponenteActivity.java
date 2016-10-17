package com.example.todeolho.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.todeolho.myapplication.DAO.Json;
import com.example.todeolho.myapplication.classes.Proponente;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ProponenteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> proponenteLista = new ArrayList<String>();
    private ArrayList<Proponente> proponenteListaObjeto = new ArrayList<Proponente>();
    private String municipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proponente);

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

        Bundle bundle = this.getIntent().getExtras();
        String idMunicipio = bundle.getString("idMunicipio").trim();
        municipio = bundle.getString("nomeMunicipio").trim();


        TextView txtPesquisa = (TextView) findViewById(R.id.txtMunicipio);

//        String restURL = "http://api.convenios.gov.br/siconv/v1/consulta/proponentes.json?id_municipio=" + idMunicipio;
//        new RestOperation().execute(restURL);

        Proponente proponente =  new Proponente();

        proponente.setId("1");
        proponente.setCnpj("92034930");
        proponente.setNome("nome");
        proponente.setMunicipio("municipio");
        proponente.setEndereco("endereço");
        proponente.setCep("95600-000");
        proponente.setNomeresponsavel("nome_responsavel");
        proponente.setCpfresponsavel("03244869017");
        proponente.setTelefone("9286-3245");
        proponente.setInscricaoestadual("3143151351");
        proponente.setInscricaomunicipal("2454225");
        proponenteLista.add(proponente.getNome());
        proponenteListaObjeto.add(proponente);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,proponenteLista);

        ListView lv= (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                Intent secondActivity = new Intent(ProponenteActivity.this, ProponenteDetalhadoActivity.class);

                secondActivity.putExtra("proponente", proponenteListaObjeto.get(posicao));

                startActivity(secondActivity);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_buscamunicipio) {
            Intent secondActivity = new Intent(ProponenteActivity.this, MainActivity.class);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(ProponenteActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Por Favor espere...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {

            InputStream inputStream = null;
            String url;
            try{

                url = params[0];
                content = Json.getInputStream(url,inputStream);

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            if(error!=null){

            } else {


                String output = "";
                JSONObject jsonResponse;

                try {
                    jsonResponse = new JSONObject(content);
//
//                    JSONArray jsonArray = jsonResponse.optJSONArray("proponentes");
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject child = jsonArray.getJSONObject(i);
//
//                        Proponente proponente =  new Proponente();
//
//                        proponente.setId(child.getString("id"));
//                        proponente.setCnpj(child.getString("cnpj"));
//                        proponente.setNome(child.getString("nome"));
//                        proponente.setMunicipio(municipio);
//                        proponente.setEndereco(child.getString("endereco"));
//                        proponente.setCep(child.getString("cep"));
//                        proponente.setNomeresponsavel(child.getString("nome_responsavel"));
//                        proponente.setCpfresponsavel(child.getString("cpf_responsavel"));
//                        proponente.setTelefone(child.getString("telefone"));
//                        proponente.setInscricaoestadual(child.getString("inscricao_estadual"));
//                        proponente.setInscricaomunicipal(child.getString("inscricao_municipal"));
//                        proponenteLista.add(proponente.getNome());
//                        proponenteListaObjeto.add(proponente);
//
//                   }


                    ListView lv= (ListView) findViewById(R.id.list);
                    lv.invalidateViews();
                    //showParsedJSON.setText(output);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
