package com.am.demo.catsandjokes.model.cats;

import java.util.List;

/**
 * Created by malbor806 on 24.05.2017.
 */

public interface OnCatResponseListener {
    void onCatResponse(List<Cat> cats);
}
