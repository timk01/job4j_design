package ru.job4j.serialization.bealdung;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

public class SerializationUtility {

    public static void main(String[] args) throws IOException {
        AppleProduct macBook = new AppleProduct();
        byte[] bytes = macBook.toString().getBytes();
        System.out.println(Arrays.toString(bytes));
        macBook.headphonePort = "headphonePort2020";
        macBook.thunderboltPort = "thunderboltPort2020";
        macBook.setLightningPort("123");

        String serializedObj = serializeObjectToString(macBook);
 
        System.out.println("Serialized AppleProduct object to string:");
        System.out.println(serializedObj);
    }

    /**
     * oos.close(); doesn't affect data in ByteArrayOutputStream (even after it's closed it had data copy)
     * and since it's actually USED in oos, it has this data. Even if we don't work with baos directly!
     * @param o
     * @return
     * @throws IOException
     */

    public static String serializeObjectToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}