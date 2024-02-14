package ru.job4j.serialization.bealdung;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class DeserializationUtility {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        AppleProduct macBook = new AppleProduct();
        macBook.headphonePort = "headphonePort2020";
        macBook.thunderboltPort = "thunderboltPort2020";

        String serializedObj =
                "rO0ABXNyACxydS5qb2I0ai5zZXJpYWxpemF0aW9uLmJlYWxkdW5nLkFwcGxl"
                        + "UHJvZHVjdAAAAAAAEtaHAgADTAANaGVhZHBob25lUG9ydHQAEkxqYXZhL2xhbm"
                        + "cvU3RyaW5nO0wADWxpZ2h0bmluZ1BvcnRxAH4AAUwAD3RodW5kZXJib2x0UG9ydHEAfgAB"
                        + "eHB0ABFoZWFkcGhvbmVQb3J0MjAyMHQAAzEyM3QAE3RodW5kZXJib2x0UG9ydDIwMjA";


                        System.out.println(
                "Deserializing AppleProduct...");

        AppleProduct deserializedObj = (AppleProduct) deSerializeObjectFromString(
                serializedObj);

        System.out.println(
                "Headphone port of AppleProduct:"
                        + deserializedObj.getHeadphonePort());
        System.out.println(
                "Thunderbolt port of AppleProduct:"
                        + deserializedObj.getThunderboltPort());
        System.out.println("LightningPort port of AppleProduct:"
                + deserializedObj.getLightningPort());
    }

    public static Object deSerializeObjectFromString(String s)
            throws IOException, ClassNotFoundException {

        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }
}