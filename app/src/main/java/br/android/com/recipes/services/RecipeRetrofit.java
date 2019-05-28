package br.android.com.recipes.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.interfaces.OnEventListener;
import br.android.com.recipes.interfaces.RecipeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daylo on 19/01/2018.
 */

public class RecipeRetrofit implements Callback<List<Recipe>> {
    private static final String baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private OnEventListener<String> mCallBack;

    private static Gson getGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public RecipeRetrofit(OnEventListener callback) {
        mCallBack = callback;
        getRecipes();
    }

    public static List<Recipe> getRecipesWidget() {

        Retrofit retrofit = getRetrofit();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<List<Recipe>> call = service.getRecipes();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void getRecipes() {
        Retrofit retrofit = getRetrofit();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<List<Recipe>> call = service.getRecipes();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        if (response.isSuccessful()) {
            mCallBack.onSuccess(response.body());
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        t.printStackTrace();
    }
}
