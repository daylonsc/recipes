package br.android.com.recipes.services;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import br.android.com.recipes.R;
import br.android.com.recipes.RecipeWidgetProvider;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.interfaces.OnEventListener;

public class RecipeWidgetService extends IntentService {

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handleActionUpdateRecipeWidgets();
    }

    private void handleActionUpdateRecipeWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds);
    }
}
