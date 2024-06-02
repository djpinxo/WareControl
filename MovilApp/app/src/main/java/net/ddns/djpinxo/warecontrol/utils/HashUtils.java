package net.ddns.djpinxo.warecontrol.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String hashString(String input) {
        try {
            // Crear una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Hashear el texto de entrada
            byte[] hashBytes = digest.digest(input.getBytes());
            // Convertir los bytes del hash a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear el texto", e);
        }
    }
}

