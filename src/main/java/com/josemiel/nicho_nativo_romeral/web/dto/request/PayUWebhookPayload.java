package com.josemiel.nicho_nativo_romeral.web.dto.request;

// Agregar los campos que nos envíe PayU (esto si alcanzamos a integrarlo)
public record PayUWebhookPayload(
        String referenceCode,
        String transactionId,
        String state, // APPROVED/DECLINED/PENDING/...
        String authorizationCode,
        String responseCode,
        String responseMessage) {

}
