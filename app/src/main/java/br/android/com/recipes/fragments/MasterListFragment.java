/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package br.android.com.recipes.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import br.android.com.recipes.DetailRecipeActivity;
import br.android.com.recipes.DetailStepActivity;
import br.android.com.recipes.MainActivity;
import br.android.com.recipes.R;
import br.android.com.recipes.adapters.DetailRecipeAdapter;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.bean.Step;
import br.android.com.recipes.interfaces.RecyclerViewOnclickListener;

public class MasterListFragment extends Fragment implements RecyclerViewOnclickListener {

    OnItemListClickListener mCallback;
    Recipe mRecipe;
    RecyclerView mRecyclerView;
    DetailRecipeAdapter mDetailRecipeAdapter;
    Button mButton;

    @Override
    public void onClickListener(View view, int position) {
        mCallback.onItemListClickListener(position);
    }

    public interface OnItemListClickListener {
        void onItemListClickListener(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mRecipe = (Recipe) getActivity().getIntent().getParcelableExtra("recipe");
            mCallback = (OnItemListClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    public MasterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        mButton = (Button) rootView.findViewById(R.id.button_ingredients);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailStepActivity.class);
                intent.putExtra(MainActivity.EXTRA_RECIPE, mRecipe);
                intent.putExtra(DetailRecipeActivity.EXTRA_INGREDIENTS, true);

                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_recycler_view);
        mDetailRecipeAdapter = new DetailRecipeAdapter(getContext(), mRecipe.getSteps());
        mDetailRecipeAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mDetailRecipeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}
