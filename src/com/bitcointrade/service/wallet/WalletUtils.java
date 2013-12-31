package com.bitcointrade.service.wallet;

import java.io.File;
import java.math.BigInteger;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.Wallet;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.store.UnreadableWalletException;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 12/22/13
 * Time: 8:44 AM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class WalletUtils {

    /**
     * Sending money to the user from our main BTC Wallet
     * NOTE:
     *   WalletApp Kit should already be running......
     *
     *
     * @param forwardingAddress     -   address to be forwarded
     * @param tx                    -   transaction of where to get the amount....
     */
    public void sendToUser(Address forwardingAddress, Transaction tx){
        WalletAppKit forwardingKit = WalletKitInstance.getForwardingKit();
        WalletAppKit feeKit = WalletKitInstance.getFeeKit();


        int feeKitKeySize = feeKit.wallet().getKeys().size();
        feeKit.wallet().addKey(new ECKey());
        Address feeAddress = feeKit.wallet().getKeys().get(feeKitKeySize).toAddress(WalletKitInstance.getParams());

        BigInteger value = tx.getValueSentToMe(forwardingKit.wallet());

        //InhouseFee... For now it's 50000 satoshis .....70000 - (10000 * 2) transaction forwarding Fee
         final BigInteger totalFee = BigInteger.valueOf(70000);



        //[2013-12-22]
        //TODO -- aside from the miners fee to ensure rapid confirmation. we have to add also our fee for our forwarding service
        //TODO -- our fee should be save in a different wallet which should be running in deamon thread.

        //Send with a small fee attached to ensure rapid confirmation. Fee is 10000 Satoshis
        final BigInteger amountToSend = value.subtract(totalFee);


        //[2013-12-22]
        //TODO -- add logic in DB if the user has enough money to send and be deducted in our wallet
        //TODO -- We should never try to send more coins than we have!

        final BigInteger inhouseForwardingFEE = totalFee.subtract(BigInteger.valueOf(20000));

        final Wallet.SendResult feeSendResult = forwardingKit.wallet().sendCoins(forwardingKit.peerGroup(),feeAddress,inhouseForwardingFEE);
        checkNotNull(feeSendResult);
        System.out.println("Sending Fee ...");
        feeSendResult.broadcastComplete.addListener(new Runnable() {
            @Override
            public void run() {
                // The wallet has changed now, it'll get auto saved shortly or when the app shuts down.
                System.out.println("Sent coins onwards! Transaction hash is " + feeSendResult.tx.getHashAsString());
            }
        }, MoreExecutors.sameThreadExecutor());


        final Wallet.SendResult sendResult = forwardingKit.wallet().sendCoins(forwardingKit.peerGroup(), forwardingAddress, amountToSend);
        checkNotNull(sendResult);
        System.out.println("Sending ...");

        // Register a callback that is invoked when the transaction has propagated across the network.
        // This shows a second style of registering ListenableFuture callbacks, it works when you don't
        // need access to the object the future returns.
        sendResult.broadcastComplete.addListener(new Runnable() {
            @Override
            public void run() {
                // The wallet has changed now, it'll get auto saved shortly or when the app shuts down.
                System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getHashAsString());
            }
        }, MoreExecutors.sameThreadExecutor());
    }

    @Test
    //Display the forwarding wallet info from memory
    public void displayForwardingWalletInfoFromMemory(){
        WalletAppKit forwardingKit = WalletKitInstance.getForwardingKit();
        System.out.println("=================================");
        System.out.println(forwardingKit.wallet().toString());
        System.out.println("=================================");
    }

    @Test
    //Display the fee wallet info from memory
    public void displayFeeWalletInfoFromMemory(){
        WalletAppKit feeKit = WalletKitInstance.getFeeKit();
        System.out.println("=================================");
        System.out.println(feeKit.wallet().toString());
        System.out.println("=================================");
    }


     @Test
    //Display the receiver wallet info from memory
    public void displayReceiverWalletInfoFromMemory(){
        WalletAppKit receiverKit = WalletKitInstance.getReceiverKit();
        System.out.println("=================================");
        System.out.println(receiverKit.wallet().toString());
        System.out.println("=================================");
    }



    //Return a new address for this wallet(forwarding kit wallet) for sending
    public Address getAnotherAddress(){
        WalletAppKit forwardingKit = WalletKitInstance.getForwardingKit();
        ECKey newKey  = new ECKey();
        forwardingKit.wallet().addKey(newKey);
        return newKey.toAddress(WalletKitInstance.getParams());
    }
}
