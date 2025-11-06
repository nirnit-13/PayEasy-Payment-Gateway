package com.paymentgateway.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeService {

    public String generateQRCodeBase64(String data) {
        try {
            System.out.println("=== QR CODE GENERATION ===");
            System.out.println("Input data: " + data);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            byte[] qrBytes = outputStream.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(qrBytes);

            System.out.println("QR Code generated successfully");
            System.out.println("Base64 length: " + base64.length());
            System.out.println("========================");

            return base64;
        } catch (WriterException | IOException e) {
            System.err.println("QR Code generation failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage());
        }
    }
}