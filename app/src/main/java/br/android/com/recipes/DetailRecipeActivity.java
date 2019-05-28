package br.android.com.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.android.com.recipes.adapters.DetailRecipeAdapter;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.bean.Step;
import br.android.com.recipes.fragments.DetailStepFragment;
import br.android.com.recipes.fragments.MasterListFragment;

public class DetailRecipeActivity extends AppCompatActivity implements MasterListFragment.OnItemListClickListener{
    private boolean mTwoPane;
    private static Recipe mRecipe;
    DetailRecipeAdapter mDetailRecipeAdapter;
    private final int POSITION_DEFAULT = 0;
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_INGREDIENTS = "ingredients";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mRecipe = (Recipe) getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);
        mDetailRecipeAdapter = new DetailRecipeAdapter(this, mRecipe.getSteps());
        setTitle(mRecipe.getName());

        if (findViewById(R.id.detail_recipe_linear_layout) != null) {

            mTwoPane = true;
            // Only create new fragments when there is no previously saved state
            if (savedInstanceState == null) {
                DetailStepFragment fragment = new DetailStepFragment();
                fragment.setRecipe(mRecipe);
                fragment.setStep((Step) mDetailRecipeAdapter.getItem(POSITION_DEFAULT));

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.detail_recipe_container, fragment)
                        .commit();

            }
        }else{
            mTwoPane = false;
        }
    }

    public void onItemListClickListener(int position) {
        Step step = (Step) mDetailRecipeAdapter.getItem(position);
        if (mTwoPane) {
            DetailStepFragment fragment = new DetailStepFragment();
            fragment.setStep(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_recipe_container, fragment)
                    .commit();
        } else {

            Intent intent = new Intent(this, DetailStepActivity.class);
            intent.putExtra(EXTRA_STEP, step);
            intent.putExtra(MainActivity.EXTRA_RECIPE, mRecipe);

            startActivity(intent);

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
