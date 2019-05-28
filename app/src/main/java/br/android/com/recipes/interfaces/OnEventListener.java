package br.android.com.recipes.interfaces;

import java.util.List;

import br.android.com.recipes.bean.Recipe;

/**
 * Created by daylo on 29/10/2017.
 */

public interface OnEventListener<T> {
    public void onSuccess(List<Recipe> recipes);
    public void onFailure(Exception e);
}
