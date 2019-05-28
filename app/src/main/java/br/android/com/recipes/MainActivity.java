package br.android.com.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.android.com.recipes.adapters.DetailRecipeAdapter;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.interfaces.OnEventListener;
import br.android.com.recipes.interfaces.RecyclerViewOnclickListener;
import br.android.com.recipes.services.RecipeRetrofit;
import br.android.com.recipes.util.NetworkUtils;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnclickListener {
    private RecyclerView mRecyclerView;
    private DetailRecipeAdapter mDetailRecipeAdapter;
    private TextView mErrorMessageDisplay;
    public static final String EXTRA_RECIPE = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponentes();
    }

    private void initComponentes() {
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        if (!NetworkUtils.isNetworkConnected(this)) {
            showErrorConnection();
        } else {
            getRecipes();
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(this, DetailRecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE, (Recipe) mDetailRecipeAdapter.getItem(position));

        startActivity(intent);
    }

    private void getRecipes() {
        new RecipeRetrofit(new OnEventListener() {
            @Override
            public void onSuccess(List object) {
                setRecipesAdpter(object);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void setRecipesAdpter(List<Recipe> recipes) {
        mDetailRecipeAdapter = new DetailRecipeAdapter(this, recipes);
        mDetailRecipeAdapter.setRecyclerViewOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recipes_recycler_view);
        mRecyclerView.setAdapter(mDetailRecipeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showErrorConnection() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setText(R.string.msg_error_connection);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
