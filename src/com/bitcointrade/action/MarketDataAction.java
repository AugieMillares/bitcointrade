package com.bitcointrade.action;

import com.bitcointrade.service.MarketDataService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/26/13
 * Time: 8:11 PM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class MarketDataAction extends ActionSupport {
    private String marketData;

    @Override
    public String execute() throws Exception {
        marketData = new MarketDataService().getMarketData();
        return SUCCESS;
    }

    public String getMarketData() {
        return marketData;
    }

    public void setMarketData(String marketData) {
        this.marketData = marketData;
    }
}
