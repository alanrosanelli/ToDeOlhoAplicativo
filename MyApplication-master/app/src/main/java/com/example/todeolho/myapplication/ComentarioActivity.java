package com.example.todeolho.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ComentarioActivity extends AppCompatActivity {

    private static final String URL = "http://citronics.com.br/api/denuncias/comentario";

    private Button btnSalva;
    private TextView txtComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#437844")));


        btnSalva = (Button) findViewById(R.id.btnSalva);
        txtComentario = (TextView) findViewById(R.id.txtComentario);

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncPOST().execute();
            }
        });
    }

    private class HttpAsyncPOST extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return post();
        }

        @Override
        protected void onPostExecute(String result) {
            //editTextNome.setText("");
            imprimirMensagem("Cadastrado com Sucesso!");

            Intent secondActivity = new Intent(ComentarioActivity.this, Denuncia_DetalhadaActivity.class);
            startActivity(secondActivity);
        }
    }


    private String post() {
        String mensagem = "";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        try {

            SharedPreferences settings = getSharedPreferences("Usuario_Denuncia", 0);
            String idDenuncia = settings.getString("idDenuncia", "");
            String id_facebook = settings.getString("idFacebook", "");
            String nome_Denunciante = settings.getString("nomeDenunciante", "");
            String comentario = txtComentario.getText().toString();


            JSONObject json = new JSONObject();
            json.put("idDenuncia", idDenuncia);
            json.put("idFacebook", id_facebook);
            json.put("nomeDenunciante", nome_Denunciante);
            json.put("comentario", comentario);


            post.setEntity(new ByteArrayEntity(json.toString().getBytes(
                    "UTF8")));
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(post);
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

    private void imprimirMensagem(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

}
