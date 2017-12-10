package com.example.abhishek.starrynights.presenter;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.abhishek.starrynights.model.StarModel;
import com.example.abhishek.starrynights.view.DetailFragView;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Abhishek on 12/9/2017.
 */

public class DetailPresenterImpl implements IDetailPresenter {
    StarModel model;
    private DetailFragView detailFragView;
    private Bundle arguments;

    private String born;
    private String occupation;

    public DetailPresenterImpl(DetailFragView detailFragView, Bundle arguments) {
        this.detailFragView = detailFragView;
        this.arguments = arguments;
    }

    /**
     * init the view by checking wether we are default or we have to show the details for the star
     */
    @Override
    public void init() {
        if (arguments.getString("star", null) != null) {
            //hide the waiting for the stars text if we have an actual result within the arguemnt passed to the fragment
            detailFragView.hideWaitingForStarsText();
            model = new Gson().fromJson(arguments.getString("star"), StarModel.class);
            if (model != null) {
                detailFragView.setId(model.getID());
                detailFragView.setFirstNameTV(model.getFirstName());
                detailFragView.setLastNameTV(model.getLastName());
                detailFragView.setPortraitIV(model.getPortrait());
                detailFragView.setBadgeColour(model.getBadgeColor());
                detailFragView.setDescription(model.getDescription());
                scapeWebForBornAndOccupation();
            }
        }
    }


    /**
     * here we can scape for any additional information from the wiki pages for each of the stars by searching by their First and last name
     */
    private void scapeWebForBornAndOccupation() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    //using JSoup to quickly scape the wikipedia page
                    Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + model.getFirstName() + "_" + model.getLastName()).get();
                    //find the table
                    Element table = doc.select("[class=infobox biography vcard]").first();

                    //iterate over the table rows
                    for (Element tr : table.select("tr")) {
                        //find the table header corresponding to the "Born" value
                        if (tr.select("th").text().equalsIgnoreCase("Born")) {
                            //pull out the text value on the table div
                            //save the values in the simple field and pass to the view on the postExecute (cannot interact with UI from background threads)
                            born = tr.select("td").text();
                        }
                        //repeat of above for "Occupation"
                        if (tr.select("th").text().equalsIgnoreCase("Occupation")) {
                            occupation = tr.select("td").text();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //update the view with the values we got from wikipedia
                detailFragView.setBornTV(born);
                detailFragView.setOccupationTV(occupation);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
