package com.example.finalprojectpm.db.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;



/**
 * This class consists exclusively of static methods that operate on or return
 * images.  It contains methods that can transform image to byte array,string base64 and back
 * @author Pavlo Manuilenko
 * @see BufferedImage
 * @see ByteArrayOutputStream
 * @see ByteArrayInputStream
 * @see Base64
 */
public class ImageUtil {
    private ImageUtil(){ }
    private static final Logger LOGGER = LogManager.getLogger(ImageUtil.class);

    /**
     * Converts or transforms image to byte array using ImageIo
     * @param image the image to be converted
     * @see ImageIO
     * @return byte array representation of images
     */
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

    /**
     * Converts or transform byte array to image using ImageIo
     * @param bytes the byte array to be converted
     * @see ImageIO
     * @return BufferedImage from given byte array
     */
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

    /**
     * @param bytes byte array to be converted
     * @see Base64
     * @return String Base64 representation of byte array usin Base64Encoder
     */
    public static String getBase64String(byte[] bytes){
        byte[] encoded = Base64.getEncoder().encode(bytes);
        return new String(encoded);
    }

}
