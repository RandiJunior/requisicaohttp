package com.example.requisicao_http.service;

import android.os.AsyncTask;

import com.example.requisicao_http.model.CEP;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, CEP> {

    private final String cep;

    public HttpService(String cep) {
        this.cep = cep;
    }

    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder res = new StringBuilder();
        if (this.cep != null && this.cep.length() == 8) {
            // realizar busca
            try {
                URL url = new URL("http://ws.matheuscastiglioni.com.br/ws/cep/find/" + this.cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    res.append(scanner.next());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Gson().fromJson(res.toString(), CEP.class);
    }
}
