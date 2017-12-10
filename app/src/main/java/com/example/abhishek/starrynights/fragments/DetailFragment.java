package com.example.abhishek.starrynights.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.starrynights.DetailActivity;
import com.example.abhishek.starrynights.R;
import com.example.abhishek.starrynights.presenter.DetailPresenterImpl;
import com.example.abhishek.starrynights.presenter.IDetailPresenter;
import com.example.abhishek.starrynights.view.DetailFragView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhishek on 12/8/2017.
 */

public class DetailFragment extends Fragment implements DetailFragView {

    //Using ButterKnife for view binding
    @BindView(R.id.waiting_for_stars_text)
    TextView waitingText;
    @BindView(R.id.star_id_tv)
    TextView starIdTV;
    @BindView(R.id.first_name_tv)
    TextView firstNameTV;
    @BindView(R.id.last_name_tv)
    TextView lastNameTV;
    @BindView(R.id.description_tv)
    TextView decriptionTV;
    @BindView(R.id.born_tv)
    TextView bornTV;
    @BindView(R.id.occupation_tv)
    TextView occupationTV;
    @BindView(R.id.portrait_iv)
    ImageView portraitIV;
    @BindView(R.id.badge_star_iv)
    ImageView badgeStarIV;

    private IDetailPresenter presenter;
    private Bundle arguments;


    /**
     * Setup the presenter for later use
     * pass the argument which contains the StarModel JSON for the item the user has clicked
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
        presenter = new DetailPresenterImpl(this, arguments);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_holder_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * set the title if we are on a phone screen to indicate the user has navigated away from the list of stars activity/fragment
     * init the presenter
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof DetailActivity) {
            ((DetailActivity) getActivity()).setActionBarTitle(getString(R.string.focused_star));
        }
        presenter.init();
    }

    @Override
    public void hideWaitingForStarsText() {
        waitingText.setVisibility(View.GONE);
    }

    @Override
    public void setId(String id) {
        starIdTV.setText(id);
    }

    @Override
    public void setFirstNameTV(String firstName) {
        firstNameTV.setText(firstName);
    }

    @Override
    public void setLastNameTV(String lastName) {
        lastNameTV.setText(lastName);
    }

    @Override
    public void setPortraitIV(String portraitUrl) {
        Picasso.with(getContext()).load(Uri.decode(portraitUrl)).into(portraitIV);
    }

    @Override
    public void setBadgeColour(String badgeColor) {
        badgeStarIV.setImageTintList(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(badgeColor.replace("0x", "#FF"))}));
    }

    @Override
    public void setDescription(String description) {
        decriptionTV.setText(description);
    }

    @Override
    public void setBornTV(String born) {
        bornTV.setText(born != null ? born : getString(R.string.connection_required));
    }

    @Override
    public void setOccupationTV(String occupation) {
        occupationTV.setText(occupation != null ? occupation : getString(R.string.connection_required));
    }
}
