package com.example.BikerMice;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

public class CodificadorAES
{
    private static final String ALGORITMO = "AES";
    private static final byte[] valor_clave = "0000000000000000".getBytes();
    // key 16 bytes lenght (what you want and of course, valid characters)

    public static String encriptar (String texto_a_encriptar) throws Exception
    {

        SecretKeySpec key = new SecretKeySpec(valor_clave, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key );

        byte[] encrypted = cipher.doFinal(texto_a_encriptar.getBytes("UTF-8"));
        String texto_encriptado = Base64.encodeToString(encrypted, Base64.DEFAULT);//new String(encrypted, "UTF-8");

        return texto_encriptado;


    }

    public static String desencriptar(String texto_encriptado) throws Exception
    {

        SecretKeySpec key = new SecretKeySpec(valor_clave, ALGORITMO);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decodificar_texto = Base64.decode(texto_encriptado.getBytes("UTF-8"), Base64.DEFAULT);
        byte[] desencriptado = cipher.doFinal(decodificar_texto);

        return new String(desencriptado, "UTF-8");
    }
}
