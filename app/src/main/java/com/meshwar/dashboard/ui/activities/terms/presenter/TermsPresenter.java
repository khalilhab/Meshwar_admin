package com.meshwar.dashboard.ui.activities.terms.presenter;

import android.content.Context;

import com.meshwar.dashboard.data.network.model.City;
import com.meshwar.dashboard.data.network.model.Term;
import com.meshwar.dashboard.ui.activities.terms.view.TermsView;
import com.meshwar.dashboard.ui.base.BasePresenter;


public class TermsPresenter extends BasePresenter {

    private final TermsView view;

    public TermsPresenter(Context context, TermsView view) {
        super(context, view);
        this.view = view;
    }

    public void getTerm() {
        requestObject(true,mService.getTerm(getUserBearerToken()), "getTerm", Term.class, (apiResponse, item) -> {
            view.InitTerm(item);
        });
    }

    public void editTerms(String terms) {
        requestObject(true,mService.editTerm(getUserBearerToken(),terms), "editTerm", Term.class, (apiResponse, item) -> {
            view.termsEdited();
        });
    }
}
