package com.bitcointrade.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcointrade.service.wallet.WalletKitInstance;
import com.bitcointrade.service.wallet.WalletService;
import com.bitcointrade.service.wallet.WalletUtils;
import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.AddressFormatException;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.Wallet;
import com.google.bitcoin.core.WrongNetworkException;
import com.google.bitcoin.store.UnreadableWalletException;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 12/29/13
 * Time: 11:10 PM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class LoadWalletServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        new WalletService().forwardingWallet();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        //fortesting ... do a mapping for this if it's working
        if (action.equalsIgnoreCase("displayforwardingwalletinfo"))
            new WalletUtils().displayForwardingWalletInfoFromMemory();
        if (action.equalsIgnoreCase("displayfeewalletinfo"))
            new WalletUtils().displayFeeWalletInfoFromMemory();
        if (action.equalsIgnoreCase("displayreceiverwalletinfo"))
            new WalletUtils().displayReceiverWalletInfoFromMemory();;
        if (action.equalsIgnoreCase("forwardbtc")) {
            try {
                String addressStr = "mznKGNMyLjxZdiNbno7mUFks9eop49TRdu";
                Wallet forwardingwallet = WalletKitInstance.getForwardingKit().wallet();
                Set<Transaction> txList = forwardingwallet.getTransactions(false);
                for (Transaction tx : txList) {
                    Address address = new Address(WalletKitInstance.getParams(),addressStr);
                    new WalletUtils().sendToUser(address, tx);
                    break;
                }
            } catch (WrongNetworkException e) {
                e.printStackTrace();
            } catch (AddressFormatException e) {
                e.printStackTrace();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
