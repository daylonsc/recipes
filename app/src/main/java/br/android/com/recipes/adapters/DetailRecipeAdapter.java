package br.android.com.recipes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.android.com.recipes.R;
import br.android.com.recipes.bean.Ingredient;
import br.android.com.recipes.bean.Recipe;
import br.android.com.recipes.bean.Step;
import br.android.com.recipes.interfaces.RecyclerViewOnclickListener;

/**
 * Created by daylo on 03/01/2018.
 */

public class DetailRecipeAdapter extends RecyclerView.Adapter<DetailRecipeAdapter.DetailRecipeViewHolder> {
    private Context mContext;
    private List mObjects;
    private static LayoutInflater layoutInflater = null;
    private RecyclerViewOnclickListener recyclerViewOnclickListener;

    public DetailRecipeAdapter(Context context, List Objects) {
        mContext = context;
        mObjects = Objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public DetailRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayout();
        DetailRecipeViewHolder viewHolder = new DetailRecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailRecipeViewHolder holder, int position) {
        Object object = mObjects.get(position);
        if (object instanceof Step) {
            setDataStep((Step) object, holder);
        } else if (object instanceof Recipe) {
            setDataRecipe((Recipe) object, holder);
        } else {
            setDataIngredient((Ingredient) object, holder);
        }
    }

    private void setDataRecipe(Recipe recipe, DetailRecipeViewHolder holder) {
        holder.tvRecipeName.setText(recipe.getName());
    }

    private void setDataStep(Step step, DetailRecipeViewHolder holder) {
        holder.tvStepDescription.setText(step.getShortDescription());
    }

    private void setDataIngredient(Ingredient ingredient, DetailRecipeViewHolder holder) {
        holder.tvIngredientDescription.setText(ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (mObjects != null) {
            return mObjects.size();
        } else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return mObjects.get(position);
    }

    private View getLayout() {
        Object object = mObjects.get(0);
        View view = null;
        if (object instanceof Step) {
            view = layoutInflater.inflate(R.layout.item_detail_step, null);
        } else if (object instanceof Recipe) {
            view = layoutInflater.inflate(R.layout.item_recipe, null);
        } else {
            view = layoutInflater.inflate(R.layout.item_detail_ingredient, null);
        }
        return view;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnclickListener r) {
        recyclerViewOnclickListener = r;
    }

    public class DetailRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvStepDescription;
        public TextView tvRecipeName;
        public TextView tvIngredientDescription;

        public DetailRecipeViewHolder(View itemView) {
            super(itemView);
            tvStepDescription = (TextView) itemView.findViewById(R.id.tv_step_description);
            tvRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            tvIngredientDescription = (TextView) itemView.findViewById(R.id.tv_ingredient_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (recyclerViewOnclickListener != null) {
                recyclerViewOnclickListener.onClickListener(view, getPosition());
            }
        }
    }
}
