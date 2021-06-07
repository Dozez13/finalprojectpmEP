package com.example.finalprojectpm.db.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageUtil {
    private ImageUtil(){ }
    private static final Logger LOGGER = LogManager.getLogger(ImageUtil.class);
    public static byte[] imageToByte(BufferedImage image){
        byte[] bytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return bytes;
    }
    public static BufferedImage byteToImage(byte[] bytes){
        BufferedImage newBi = null;
        try{
            InputStream is = new ByteArrayInputStream(bytes);
            newBi = ImageIO.read(is);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return newBi;
    }
    public static String getBase64String(byte[] bytes){
        byte[] encoded = Base64.getEncoder().encode(bytes);
        return new String(encoded);  // Outputs "SGVsbG8="
    }
}
