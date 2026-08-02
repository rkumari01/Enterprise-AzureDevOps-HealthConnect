package com.healthconnect.payment.service;

import com.healthconnect.payment.dto.request.CreatePaymentRequest;
import com.healthconnect.payment.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);
}