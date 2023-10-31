package br.edu.ifsul.yesornoatividade.service;

import br.edu.ifsul.yesornoatividade.model.Answer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestService {
    @GET("api/")
    Call<Answer> responder();
}
