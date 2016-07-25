package com.example.todeolho.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todeolho.myapplication.classes.Convenio;

import java.text.SimpleDateFormat;

public class ConvenioDetalhadoActivity extends AppCompatActivity {

    private Convenio convenio = new Convenio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio_detalhado);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#437844")));

        TextView txtModalidade = (TextView) findViewById(R.id.txtCnpj);
        //TextView txtOrgaoConcedente = (TextView) findViewById(R.id.txtOrgaoConcedente);
        TextView txtJustificativaResumida = (TextView) findViewById(R.id.txtJustificativaResumida);
        TextView txtObjetoResumido = (TextView) findViewById(R.id.txtObjetoResumido);
        TextView txtInicioVigencia = (TextView) findViewById(R.id.txtDataInicioVigencia);
        TextView txtFimVigencia = (TextView) findViewById(R.id.txtDataFimVigencia);
        TextView txtValorGlobal = (TextView) findViewById(R.id.txtValorGlobal);
        TextView txtValorRepasse = (TextView) findViewById(R.id.txtValorRepasse);
        TextView txtValorContraPartida = (TextView) findViewById(R.id.txtValorContraPartida);
        //TextView txtDataAssinatura = (TextView) findViewById(R.id.txtDataAssinatura);
        //TextView txtDataPublicao = (TextView) findViewById(R.id.txtDataPublicacao);
        //TextView txtSituacao = (TextView) findViewById(R.id.txtSituacao);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            convenio = (Convenio)getIntent().getSerializableExtra("convenio");
            txtModalidade.setText(convenio.getModalidade());

            String inicioVigencia = new SimpleDateFormat("dd-MM-yyyy").format(convenio.getData_inicio_vigencia());
            String fimVigencia = new SimpleDateFormat("dd-MM-yyyy").format(convenio.getData_fim_vigencia());
           // txtOrgaoConcedente.setText(convenio.getOrgao_concedente());
            txtJustificativaResumida.setText(convenio.getJustificativa_resumida());
            txtObjetoResumido.setText(convenio.getObjeto_resumido());
            txtInicioVigencia.setText(inicioVigencia);
            txtFimVigencia.setText(fimVigencia);
            txtValorGlobal.setText(String.valueOf(convenio.getValor_global()));
            txtValorRepasse.setText(String.valueOf(convenio.getValor_repasse()));
            txtValorContraPartida.setText(String.valueOf(convenio.getValor_contra_partida()));
            //txtDataAssinatura.setText(convenio.getData_assinatura().toString());
            //txtDataPublicao.setText(convenio.getData_publicacao().toString());
            //txtSituacao.setText(convenio.getSituacao());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_convenio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.denuncias:


                Bundle bundle = new Bundle();
                bundle.putString("id_convenio", convenio.getId());


                Intent secondActivity = new Intent(ConvenioDetalhadoActivity.this, DenunciasActivity.class);
                startActivity(secondActivity.putExtras(bundle));

//                if(AccessToken.getCurrentAccessToken() != null) {
//                    Intent intent = new Intent(ConvenioDetalhadoActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                }
//                else{
//                    Intent secondActivity = new Intent(ConvenioDetalhadoActivity.this, DenunciaConvenioActivity.class);
//                    startActivity(secondActivity.putExtras(bundle));
//                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
