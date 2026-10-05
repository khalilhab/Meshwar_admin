package com.meshwar.dashboard.ui.activities.terms.view;


import com.meshwar.dashboard.data.network.model.Term;
import com.meshwar.dashboard.ui.base.BaseView;

import java.util.List;

public interface TermsView extends BaseView {

    void InitTerm(Term item);

    void termsEdited();
}
