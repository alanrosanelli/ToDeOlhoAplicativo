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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todeolho.myapplication.DAO.Json;
import com.example.todeolho.myapplication.classes.Comentario;
import com.example.todeolho.myapplication.classes.CustomList;
import com.example.todeolho.myapplication.classes.Denuncia;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Denuncia_DetalhadaActivity extends AppCompatActivity {

    private static final String URL = "http://citronics.com.br/api/denuncias/";

    ListView list;
    private TextView txtNomeDenunciante;
    private TextView txtTituloDenuncia;
    private TextView txtDescricaoDenuncia;
    private ImageView imgDenunciante;
    private Button btnSalva;
    private Button btnComentario;

    private String id_denuncia;
    private String id_convenio;

    private ArrayList<Comentario> comentarioLista = new ArrayList<Comentario>();

    ArrayList<String> web = new ArrayList<String>();
    ArrayList<String> imageId = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia__detalhada);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#437844")));


        txtNomeDenunciante = (TextView) findViewById(R.id.txtNomeDenunciante);
        txtTituloDenuncia = (TextView) findViewById(R.id.txtTituloDenuncia);
        txtDescricaoDenuncia = (TextView) findViewById(R.id.txtDescricaoDenuncia);
        imgDenunciante = (ImageView) findViewById(R.id.imgPessoa);
        btnSalva = (Button) findViewById(R.id.btnSalvar);
        btnComentario = (Button) findViewById(R.id.btnComentario);

        SharedPreferences settings = getSharedPreferences("Usuario_Denuncia", 0);
        String nomeDenunciante = settings.getString("nomeDenunciante", "");
        String tituloDenuncia = settings.getString("tituloDenuncia", "");
        String descricao_Denuncia = settings.getString("descricaoDenuncia", "");
        String id_facebook = settings.getString("idFacebook", "");

        id_denuncia = settings.getString("idDenuncia", "");
        id_convenio = settings.getString("idconvenio", "");

        String restURL = "http://www.citronics.com.br/api/denuncias/convenio/comentario/"+ id_denuncia;
        new RestOperation().execute(restURL);

        CustomList adapter = new
                CustomList(Denuncia_DetalhadaActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            // seleciona item
            }
        });

        txtNomeDenunciante.setText(nomeDenunciante);
        txtDescricaoDenuncia.setText(descricao_Denuncia);
        txtTituloDenuncia.setText(tituloDenuncia);

        Glide.with(getBaseContext())
                .load("https://graph.facebook.com/" + id_facebook + "/picture?height=120&width=120")
                .centerCrop()
                .fitCenter()
                .override(300, 100).into(imgDenunciante); // id do teu imageView.

        SharedPreferences settings2 = getSharedPreferences("Usuario", 0);
        String id_FacebookUserLogado = settings.getString("ID_Facebook", "");

        txtTituloDenuncia.setEnabled(false);
        txtDescricaoDenuncia.setEnabled(false);
        if( id_facebook == id_FacebookUserLogado){
            txtTituloDenuncia.setEnabled(true);
            txtDescricaoDenuncia.setEnabled(true);
        }
        else{
            btnSalva.setVisibility(View.GONE);
        }

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btnComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondActivity = new Intent(Denuncia_DetalhadaActivity.this, ComentarioActivity.class);
                startActivity(secondActivity);

            }
        });
    }

    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(Denuncia_DetalhadaActivity.this);

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


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());

                        Comentario comentario = new Comentario();

                        comentario.setIdComentario(jsonObject.getString("idComentario"));
                        comentario.setIdDenuncia(jsonObject.getString("idDenuncia"));
                        comentario.setIdFacebook(jsonObject.getString("idFacebook"));
                        comentario.setNomeDenunciante(jsonObject.getString("nomeDenunciante"));
                        comentario.setComentario(jsonObject.getString("comentario"));

                        comentarioLista.add(comentario);

                    }

                    for (int i = 0; i < comentarioLista.size(); i++) {
                        web.add(comentarioLista.get(i).getComentario());
                        imageId.add("https://graph.facebook.com/" + comentarioLista.get(i).getIdFacebook() + "/picture?height=120&width=120");

                    }

                    ListView lv = (ListView) findViewById(R.id.list);
                    lv.invalidateViews();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_apagadenuncia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delete:

                new HttpAsyncDELETE().execute();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpAsyncDELETE extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return delete();
        }

        @Override
        protected void onPostExecute(String result) {

            imprimirMensagem("Excluído com Sucesso!");

            Bundle bundle = new Bundle();
            bundle.putString("id_convenio", id_convenio);

            Intent secondActivity = new Intent(Denuncia_DetalhadaActivity.this, DenunciasActivity.class);
            startActivity(secondActivity.putExtras(bundle));

        }
    }

    private void imprimirMensagem(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private String delete() {
        String mensagem = "";

        HttpClient httpclient = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(URL + id_denuncia);
        try {
            delete.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(delete);
            mensagem = inputStreamToString(response.getEntity().getContent()).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mensagem;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String linha = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((linha = rd.readLine()) != null) {
                total.append(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

}
