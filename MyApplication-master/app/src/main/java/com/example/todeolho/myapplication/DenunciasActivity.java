package com.example.todeolho.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todeolho.myapplication.DAO.Json;
import com.example.todeolho.myapplication.classes.CustomList;
import com.example.todeolho.myapplication.classes.Denuncia;
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

public class DenunciasActivity extends AppCompatActivity {

    private ArrayList<Denuncia> denunciasLista = new ArrayList<Denuncia>();

    ListView list;
    ArrayList<String> web = new ArrayList<String>();

    ArrayList<String> imageId = new ArrayList<String>();
    private String id_convenio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#437844")));

        Bundle bundle = this.getIntent().getExtras();
        id_convenio = bundle.getString("id_convenio").trim();

        String restURL = "http://citronics.com.br/api/denuncias/convenio/"+ id_convenio;
        new RestOperation().execute(restURL);

        CustomList adapter = new
                CustomList(DenunciasActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                SharedPreferences settings = getSharedPreferences("Usuario_Denuncia", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("idFacebook", denunciasLista.get(position).getIdFacebook());
                editor.putString("idDenuncia", denunciasLista.get(position).getIdDenuncia());
                editor.putString("nomeDenunciante", denunciasLista.get(position).getNomeDenunciante());
                editor.putString("tituloDenuncia", denunciasLista.get(position).getTituloDenuncia());
                editor.putString("idconvenio", denunciasLista.get(position).getIdConvenio());
                editor.putString("descricaoDenuncia", denunciasLista.get(position).getDescricaoDenuncia());

                editor.commit();

                Intent secondActivity = new Intent(DenunciasActivity.this, Denuncia_DetalhadaActivity.class);
                startActivity(secondActivity);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_denuncias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.adddenuncia:

                Bundle bundle = new Bundle();
                bundle.putString("id_convenio",id_convenio);

                Intent secondActivity = new Intent(DenunciasActivity.this, DenunciaConvenioActivity.class);
                startActivity(secondActivity.putExtras(bundle));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(DenunciasActivity.this);

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

                try {


                    JSONArray jsonArray = new JSONArray(content);

                        if(jsonArray.length() == 0){
                            Toast toast = Toast.makeText(getApplicationContext(),"Não há Denuncias Cadastradas!",Toast.LENGTH_LONG);
                            toast.show();
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());

                            Denuncia denuncia = new Denuncia();

                            denuncia.setIdFacebook(jsonObject.getString("idFacebook"));
                            denuncia.setIdDenuncia(jsonObject.getString("idDenuncia"));
                            denuncia.setNomeDenunciante(jsonObject.getString("nomeDenunciante"));
                            denuncia.setTituloDenuncia(jsonObject.getString("tituloDenuncia"));
                            denuncia.setDescricaoDenuncia(jsonObject.getString("descricaoDenuncia"));
                            denuncia.setIdConvenio(jsonObject.getString("idconvenio"));

                            denunciasLista.add(denuncia);

                        }

                        for (int i = 0; i < denunciasLista.size(); i++) {
                            web.add(denunciasLista.get(i).getTituloDenuncia());
                            imageId.add("https://graph.facebook.com/" + denunciasLista.get(i).getIdFacebook() + "/picture?height=120&width=120");

                        }

                    ListView lv = (ListView) findViewById(R.id.list);
                    lv.invalidateViews();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
