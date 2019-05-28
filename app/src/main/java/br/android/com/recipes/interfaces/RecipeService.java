package br.android.com.recipes.interfaces;

import java.util.List;

import br.android.com.recipes.bean.Recipe;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by daylo on 27/12/2017.
 */

public interface RecipeService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
