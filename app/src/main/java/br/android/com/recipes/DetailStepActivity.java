package br.android.com.recipes;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.bean.Step;
import br.android.com.recipes.fragments.DetailIngredientFragment;
import br.android.com.recipes.fragments.DetailStepFragment;

public class DetailStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Step step = (Step) getIntent().getParcelableExtra(DetailRecipeActivity.EXTRA_STEP);
        Recipe recipe = (Recipe) getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);
        boolean isIngredients = (boolean) getIntent().getBooleanExtra("ingredients", false);
        setTitle(recipe.getName());

        if(savedInstanceState == null) {
            if(isIngredients){
                DetailIngredientFragment detailStepFragment = new DetailIngredientFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.detail_ingredient_container, detailStepFragment)
                        .commit();
            }else{
                DetailStepFragment detailStepFragment = new DetailStepFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.detail_step_container, detailStepFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
