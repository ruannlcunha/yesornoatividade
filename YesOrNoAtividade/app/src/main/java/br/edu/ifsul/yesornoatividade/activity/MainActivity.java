package br.edu.ifsul.yesornoatividade.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsul.yesornoatividade.R;
import br.edu.ifsul.yesornoatividade.model.Answer;
import br.edu.ifsul.yesornoatividade.service.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String URL = "https://yesno.wtf/";

    private Retrofit retrofitCEP;

    private Button decideButton;

    private TextView resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.decideButton = findViewById(R.id.decideButton);
        this.resposta = findViewById(R.id.resposta);

        retrofitCEP = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        decideButton.setOnClickListener(this::onClick);

    }

    public void onClick(View view) {
        responder();
    }

    private void responder() {
        RestService restService = retrofitCEP.create(RestService.class);

        Call<Answer> call = restService.responder();

        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                if (response.isSuccessful()) {
                    Answer answer = response.body();
                    resposta.setText(answer.getAnswer());
                    Toast.makeText(getApplicationContext(), "Decidimos a sua resposta!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("ERRO:",t.getMessage());
            }
        });
    }

}