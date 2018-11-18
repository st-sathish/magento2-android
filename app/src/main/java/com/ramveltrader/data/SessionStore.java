package com.ramveltrader.data;


import com.ramveltrader.data.network.models.CategoryResponse;
import com.ramveltrader.data.network.models.ProductResponse;
import com.ramveltrader.data.network.models.StockKeepingUnitResponse;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public final class SessionStore {

    public static CategoryResponse sSelectedCategory = null;

    public static List<StockKeepingUnitResponse> sStockKeepingUnits = Collections.emptyList();

    public static ProductResponse productDetail = null;

    public static String accessToken = "";

    public static int quoteId = 0;

    public static String sSelectedMenu = null;

    public static JSONObject sSetAddress = null;

    public static String sEmail = null;

    private SessionStore() {

    }
}
