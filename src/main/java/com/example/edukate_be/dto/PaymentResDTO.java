package com.example.edukate_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentResDTO implements Serializable {

    private String paymentUrl;
    private String vnpTxnRef;

    public PaymentResDTO(String paymentUrl, String vnpTxnRef) {
        this.paymentUrl = paymentUrl;
        this.vnpTxnRef = vnpTxnRef;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public String getVnpTxnRef() {
        return vnpTxnRef;
    }
}
