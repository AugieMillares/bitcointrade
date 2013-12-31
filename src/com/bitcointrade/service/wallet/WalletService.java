
package com.bitcointrade.service.wallet;

import java.math.BigInteger;

import com.google.bitcoin.core.AbstractWalletEventListener;
import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.Utils;
import com.google.bitcoin.core.Wallet;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.utils.BriefLogFormatter;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 12/22/13
 * Time: 8:44 AM
 * <p/>
 * Modification:
 * ----------------------------
 */

public class WalletService {



    //Run the forwarding wallet and the feek wallet o the background
    //NOTE:
    //     RUN THIS FIRST
    public void forwardingWallet(){
        WalletAppKit forwardingKit = WalletKitInstance.getForwardingKit();
        WalletAppKit feeKit = WalletKitInstance.getFeeKit();
        processKit(forwardingKit, true);
        processKit(feeKit, false);
        WalletAppKit receivingKit = WalletKitInstance.getReceiverKit();
        processKit(receivingKit,false);
    }

    private void processKit(WalletAppKit kit, final boolean isForwardingKit){
        // This line makes the log output more compact and easily read, especially when using the JDK log adapter.
        BriefLogFormatter.init();

        // Download the block chain and wait until it's done.
        kit.startAndWait();

        // We want to know when we receive money.
        kit.wallet().addEventListener(new AbstractWalletEventListener() {
            @Override
            public void onCoinsReceived(Wallet w, Transaction tx, BigInteger prevBalance, BigInteger newBalance) {
                // Runs in the dedicated "user thread" (see bitcoinj docs for more info on this).
                //
                // The transaction "tx" can either be pending, or included into a block (we didn't see the broadcast).
                BigInteger value = tx.getValueSentToMe(w);
                System.out.println("Received tx for " + Utils.bitcoinValueToFriendlyString(value) + ": " + tx);

                Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<Transaction>() {
                    @Override
                    public void onSuccess(Transaction result) {
                        new WalletUtils().displayForwardingWalletInfoFromMemory();
                        //[2013-12-22]
                        if(isForwardingKit){
                            //TODO -- add logic to save in DB the USER info (User's Info, Transaction)
                            TransactionRecordTest transactionRecordTest = TransactionRecordTest.getInstance();
                            transactionRecordTest.addTranssaction(result);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // This kind of future can't fail, just rethrow in case something weird happens.
                        throw new RuntimeException(t);
                    }
                });
            }
        });

        System.out.println("Waiting for coins to arrive. Press Ctrl-C to quit.");
    }


}
