package br.android.com.recipes.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

import br.android.com.recipes.MainActivity;
import br.android.com.recipes.R;
import br.android.com.recipes.adapters.DetailRecipeAdapter;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.bean.Step;


public class DetailIngredientFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private DetailRecipeAdapter mDetailRecipeAdapter;
    private Recipe mRecipe;


    public DetailIngredientFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_ingredients, container, false);

        mRecipe = (Recipe) getActivity().getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);

        mDetailRecipeAdapter = new DetailRecipeAdapter(getActivity(), mRecipe.getIngredients());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ingredients_recycler_view);
        mRecyclerView.setAdapter(mDetailRecipeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
