package com.bitcointrade.action;

import com.bitcointrade.service.ExchangeRatesService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/23/13
 * Time: 1:32 AM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class ExchangeRatesAction extends ActionSupport {
    private String exchangeRates;

    @Override
    public String execute() throws Exception {
        exchangeRates = new ExchangeRatesService().getExchangeRates();
        return SUCCESS;
    }

    public String getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(String exchangeRates) {
        this.exchangeRates = exchangeRates;
    }
}
