package com.example.abhishek.starrynights.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.starrynights.R;
import com.example.abhishek.starrynights.model.StarModel;
import com.squareup.picasso.Picasso;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Abhishek on 12/9/2017.
 * Adapter for the Stars, supports simple sort.
 */

public class StarRVAdapter extends RecyclerView.Adapter<StarRVAdapter.CustomVH> {

    //fields to hold the sort selection as well as the sort options
    private static final int SORT_BY_FULL_NAME = 0;
    private static final int SORT_BY_ID = 1;
    private int currentSort = -1;


    private List<StarModel> starModelList;
    private Context context;

    //click interface passes through the click on the adapter item
    private AdapterClickInterface clickInterface;


    /**
     * Constructor to receive the StarModel, Context, and the AdapterClickInterface from the presenter
     *
     * @param starModelList
     * @param context
     * @param clickInterface
     */

    public StarRVAdapter(List<StarModel> starModelList, Context context, AdapterClickInterface clickInterface) {
        this.starModelList = starModelList;
        sortByName();
        this.context = context;
        this.clickInterface = clickInterface;
    }


    /**
     * Simple ViewHolder creator
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.star_card, parent, false);
        return new CustomVH(view);
    }

    /**
     * Binds the model data to the view
     * The id, name and badge colour are found with the StarModel
     * The portrait image has to be downloaded from the url provided in StarModel, it is done via PICASSO image loader
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CustomVH holder, int position) {
        if (starModelList != null) {
            StarModel model = starModelList.get(position);
            holder.id.setText(model.getID());
            holder.fullName.setText(String.format("%s %s", model.getFirstName(), model.getLastName()));
            Log.d("Colour", "onBindViewHolder: colour = " + model.getBadgeColor() + "" + Color.parseColor(model.getBadgeColor().replace("0x", "#FF")));
            holder.star.setImageTintList(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(model.getBadgeColor().replace("0x", "#FF"))}));
            Picasso.with(context).load(Uri.decode(model.getPortrait())).into(holder.portrait);
        }
    }


    /**
     * Simple sort by FullName extracted from the StarModel
     */
    private void sortByName() {
        currentSort = SORT_BY_FULL_NAME;
        starModelList.sort(new Comparator<StarModel>() {
            @Override
            public int compare(StarModel star1, StarModel star2) {
                return (star1.getFirstName() + " " + star1.getLastName()).compareTo(star2.getFirstName() + " " + star2.getLastName());
            }
        });
    }


    /**
     * Simple sort by ID extracted from the StarModel
     */
    private void sortById() {
        currentSort = SORT_BY_ID;
        starModelList.sort(new Comparator<StarModel>() {
            @Override
            public int compare(StarModel star1, StarModel star2) {
                return (star1.getID()).compareTo(star2.getID());
            }
        });
    }

    /**
     * required to provide the true size of the data
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return starModelList != null ? starModelList.size() : 0;
    }


    /**
     * Used to sort the data based on user input passed through the presenter via Fragment Options menu
     *
     * @param which
     * @return if true is returned, then you must notify the adapter that data has changed.
     * else if false, the adapter does not need to be notified
     */
    public boolean sortBy(int which) {
        if (which == SORT_BY_FULL_NAME && currentSort != which) {
            sortByName();
            return true;
        } else if (which == SORT_BY_ID && currentSort != which) {
            sortById();
            return true;
        }
        return false;
    }


    /**
     * Basic VH class for the StarCard
     */
    public class CustomVH extends RecyclerView.ViewHolder {
        TextView id, fullName;
        ImageView portrait, star;

        public CustomVH(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_text_view);
            fullName = itemView.findViewById(R.id.full_name_text_view);
            portrait = itemView.findViewById(R.id.portrait_image_view);
            star = itemView.findViewById(R.id.star_image_view);
            //clicking on this itemView passes the element which the user has clicked on to the presenter
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickInterface.onItemClicked(starModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
