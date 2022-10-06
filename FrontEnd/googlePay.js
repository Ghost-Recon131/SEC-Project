

const tokenizationSpecification = {
    type: 'PAYMENT_GATEWAY',
    parameters: {
      'gateway': 'example',
      'gatewayMerchantId': 'exampleGatewayMerchantId'
    }
  };

const cardPaymentMethod = {
    type: 'CARD',
    tokenizationSpecification: tokenizationSpecification,
    parameters: {
        allowedCardNetworks: ["AMEX", "DISCOVER", "INTERAC", "JCB", "MASTERCARD", "VISA"],
        allowedAuthMethods: ["PAN_ONLY", "CRYPTOGRAM_3DS"]
    }
};

const googlePayConfiguration = {
    apiVersion: 2,
    apiVersionMinor: 0,
    allowedPaymentMethods: [cardPaymentMethod],
  };

let googlePayClient;

function onGooglePayLoaded(){
    googlePayClient = new googlePayClient.payment.api.PaymentsClient({
        enviroment: "TEST",
    });

    googlePayClient.isReadyToPay(googlePayConfiguration)
        .then(respone => {
            if(respone.result) {
                createAndAddButton();
            } else {
                // if the customers dont have google pay
            }
        })

        .catch(error => console.error('isReadyToPay error: ', error));
}


function createAndAddButton() {
    const googlePayButton = googlePayClient.createButton({
        onClick: onGooglePaymentButtonClicked,
    });

    document.getElementById('buy-now').appendChild(googlePayButton);
}

function onGooglePaymentButtonClicked() {

    const paymentDataRequest = { ...googlePayConfiguration};
    paymentDataRequest.merchantInfo = {
        merchantId: 'BCR2DN4T4CKYNHTJ',
        merchantName: 'MFH',
    };

    paymentDataRequest.transactionInfo = {
        totalPriceStatus: 'FINAL',
        totalPrice: selectedItem.price,
        currencyCode: 'AUD',
        countryCode: 'AU',
    };

    googlePayClient.loadPaymentData(paymentDataRequest)
        .then(paymentData => processPaymentData(paymentData))
        .catch(error => console.error('loadPaymentData error: ', error));
}

function processPaymentData(paymentData) {
    fetch(ordersEndPointUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },

        body: paymentData
    })
}
