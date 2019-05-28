package br.android.com.recipes.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import br.android.com.recipes.DetailRecipeActivity;
import br.android.com.recipes.MainActivity;
import br.android.com.recipes.R;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.interfaces.OnEventListener;
import br.android.com.recipes.interfaces.RecipeService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<Recipe> mRecipe;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        mRecipe = RecipeRetrofit.getRecipesWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipe == null) return 0;
        return mRecipe.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mRecipe == null || mRecipe.size() == 0) return null;
        Recipe recicpe = mRecipe.get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);
        views.setImageViewResource(R.id.widget_recipe_image, R.drawable.ic_recipe);
        views.setTextViewText(R.id.widget_recipe_name, recicpe.getName());
        Bundle extras = new Bundle();
        extras.putParcelable(MainActivity.EXTRA_RECIPE, recicpe);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_recipe_image, fillInIntent);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

