<%--
  Created by IntelliJ IDEA.
  User: Augie
  Date: 11/22/13
  Time: 12:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
    <title>My App</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <style>
        body {
            font-family: Arial, san-serif;
            font-size: 9pt;
        }

        #parenttable {
            width: 890;
        }

        .selllabel {
            float: right;
            overflow: hidden;
        }

        .buylabel {
            display: block;
            overflow: hidden;
        }

        .dynBtn {
            width: 80;
        }

        #buytabledata, #selltabledata{
            display: block;
            height: 210px;
            overflow-y: scroll;
        }

    </style>
    <script type="text/javascript">
        $ (document).ready (function () {
            $ ("#parenttable").hide ();
        });
        var marketDataArray = [];
        var selectedCoinObj = [];
        var coinForNow = ['FRK/BTC', 'BTB/BTC', 'RED/LTC', 'CSC/BTC', 'ASC/XPM', 'TIX/LTC'  ];
        $.subscribe ('handleJsonResult', function (event, data) {
            marketDataArray = [];
            $ ('#marketdata_id').html ("<ul id='buttonlist'></ul>");
            var list = $ ('#buttonlist');
            var sellorderrows = $ ('#sellorders');
            var buyorderrows = $ ('#buyorders');
            var marketDataJson = event.originalEvent.data.marketData;
            //console.log (marketDataJson);
            var jsonData = JSON.parse (marketDataJson);
            var parseData = jsonData.return.markets;
            $ ("#listedcoins").find ("button").remove ();
            $.each (parseData, function (index, obj) {
                marketDataArray.push (obj);
                //if (coinForNow.indexOf (obj.label) >= 0) {
                    var coinLabel = obj.label;
                    //list.append ('<li id="' + coinLabel + '" onClick="createTable(this.id)">' + coinLabel + ' - ' + obj.lasttradeprice + '</li>\n');
                    selectedCoinObj.push (obj);

                    var $button = $ ('<button/>', {
                        type   : 'button',
                        'class': 'dynBtn',
                        id     : obj.label,
                        text   : coinLabel + ' ' + obj.lasttradeprice,
                        click  : function () {
                            cTable (this.id);
                        }
                    });

                    $button.appendTo ('#listedcoins');

                    /* var sellorders = obj.sellorders;
                     var buyorders = obj.buyorders;
                     $.each (sellorders, function (sIndex, sObj) {
                     sellorderrows.append ('<tr><td>' + sObj.price + '</td><td>' + sObj.quantity + '</td><td>' + sObj.total + '</td></tr>\n');
                     });
                     $.each (buyorders, function (bIndex, bObj) {
                     buyorderrows.append ('<tr><td>' + bObj.price + '</td><td>' + bObj.quantity + '</td><td>' + bObj.total + '</td></tr>\n');
                     });*/
                //}
            });

        });

        var cTable = function createTable (clickID) {
            var sellorderrows = $ ('#sellorders');
            var buyorderrows = $ ('#buyorders');
            $ ("#sellorders").find ("tr:gt(0)").remove ();
            $ ("#buyorders").find ("tr:gt(0)").remove ();

            $.each (selectedCoinObj, function (index, obj) {
                if (obj.label === clickID) {
                    var sellorders = obj.sellorders;
                    var buyorders = obj.buyorders;
                    $.each (sellorders, function (sIndex, sObj) {
                        sellorderrows.append ('<tr><td>' + sObj.price + '</td><td>' + sObj.quantity + '</td><td>' + sObj.total + '</td></tr>\n');
                    });
                    $.each (buyorders, function (bIndex, bObj) {
                        buyorderrows.append ('<tr><td>' + bObj.price + '</td><td>' + bObj.quantity + '</td><td>' + bObj.total + '</td></tr>\n');
                    });
                }
            });
            $ ("#cointransaction").find ("hr, h2").remove ();
            $ ('#cointransaction').append ("<hr/><h2>" + clickID + ' Order Transaction</h2>');
            $ ("#parenttable").show ();
            var selwidth = $ ('#sellorders').width ();
            var buywidth = $ ('#buyorders').width ();
            $ ('#parenttable').width (selwidth + buywidth + 40);
        }


    </script>
</head>
<body>
<div>
    <s:url id="ajaxTest" value="exchangerates"/>
    <sj:a id="link1" href="%{ajaxTest}" indicator="indicator" targets="exchangerates_id" button="true"> EXCHANGE RATES </sj:a>
    <s:url id="ajaxTest01" value="marketdata"/> <sj:a
        id="link2"
        href="%{ajaxTest01}"
        dataType="json"
        onSuccessTopics="handleJsonResult"
        button="true"
        indicator="indicator"
        > MARKET DATA </sj:a>
    <img id="indicator" src="assets/images/indicator.gif" height="15px" width="15px" alt="Loading..." style="display:none"/>
</div>
<div id="exchangerates_id"></div>
<br/> <br/>

<div id="marketdata_id"></div>
<br/><br/>

<div id='listedcoins'></div>
<div id='cointransaction'></div>
<div id='parenttable'>
    <div class="selllabel"><h2>SELL ORDERS</h2>

        <div id="selltabledata">
            <table id="sellorders" border="1">
                <thead>
                <tr>
                    <th>PRICE</th>
                    <th>QUANTITY</th>
                    <th>TOTAL</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div class="buylabel"><h2>BUY ORDERS</h2>
        <div id="buytabledata">
            <table id="buyorders" border="1">
                <thead>
                <tr>
                    <th>PRICE</th>
                    <th>QUANTITY</th>
                    <th>TOTAL</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
</body>
</html>