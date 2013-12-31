package com.bitcointrade.service.wallet;

import java.io.File;

import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.params.TestNet3Params;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 12/22/13
 * Time: 8:36 AM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class WalletKitInstance {

    //Prefix filename used in saving the wallet and blockchain in WalletAppkit
    //This is our forwarding service wallet
    //private static final String PREFIXFILENAME = "forwarding-service";
    //fortesting... uncomment above for production and comment this line
    private static final String PREFIXFILENAME = "forwarding-service-testnet";


    //Prefix filename used in saving the wallet and blockchain in WalletAppkit
    //This is our forwarding service wallet
    //private static final String PREFIXFILENAMEFEE = "fee-wallet";
    //fortesting... uncomment above for production and comment this line
    private static final String PREFIXFILENAMEFEE = "fee-wallet-testnet";

    //Used the MainNetParams for BTC... See the source for details
    //private static NetworkParameters params = MainNetParams.get();
    //fortesting... uncomment above for production and comment this line
    private static NetworkParameters params = TestNet3Params.get();

    //fortesting
    private static final String PREFIXFILENAMERECEIVER = "receiver-testnet";
    private static WalletAppKit receiverKit = new WalletAppKit(params, new File("."), PREFIXFILENAMERECEIVER);

    //WalletAppkit is used for forwarding services, to and from our forwarding wallet
    private static WalletAppKit kit = new WalletAppKit(params, new File("."), PREFIXFILENAME);

    //WalletAppkit is used for forwarding services, to and from our forwarding wallet
    private static WalletAppKit feeKit = new WalletAppKit(params, new File("."), PREFIXFILENAMEFEE);


    //This is our forwarding wallet.....
    public static WalletAppKit getForwardingKit() {
        return kit;
    }

    //This is our testing receiver wallet.....
    public static WalletAppKit getReceiverKit() {
        return receiverKit;
    }

    //This is our fee wallet
    public static WalletAppKit getFeeKit() {
        return feeKit;
    }

    //Production network
    public static NetworkParameters getParams() {
        return params;
    }

    //filename prefix for the forwarding wallet
    public static String getPrefixfilename() {
        return PREFIXFILENAME;
    }

    //filename prefix for the fee wallet
    public static String getPrefixfilenamefee() {
        return PREFIXFILENAMEFEE;
    }

    private WalletKitInstance() {
    }
}
