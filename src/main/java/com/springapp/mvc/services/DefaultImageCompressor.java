package com.springapp.mvc.services;

import org.apache.commons.io.IOUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Created by pcdalao on 2017/4/20.
 */
public class DefaultImageCompressor implements ImageCompressor {

    private float quality;

    public DefaultImageCompressor(float quality) {
        this.quality = quality;
    }

    @Override
    public void compressImage(File srcFile, File targetFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            BufferedImage srcImage = ImageIO.read(fis);
            fos = new FileOutputStream(targetFile);
            compressImage(srcImage,fos,this.quality);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
        }

    }

    //压缩图片
    private void compressImage(BufferedImage srcImage, OutputStream outputStream, float quality) throws IOException {
        //构建图片对象
        final int imageType = srcImage.isAlphaPremultiplied() ? BufferedImage.TRANSLUCENT : BufferedImage.TYPE_INT_RGB;
        final int width=srcImage.getWidth();
        final int height=srcImage.getHeight();
        BufferedImage bufferedImage=new BufferedImage(width,height,imageType);
        //绘制图片
        Image scaledInstance=srcImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        bufferedImage.getGraphics().drawImage(scaledInstance,0,0,width,height,Color.WHITE,null);//白色背景

        Iterator<ImageWriter> iterator=ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter=iterator.next();
        ImageWriteParam imageWriteParam=imageWriter.getDefaultWriteParam();
        //压缩
        if(imageWriteParam.canWriteCompressed()){
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(quality);
        }

        ImageOutputStream imageOutputStream=new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);//设置写入的目标流

        IIOImage iioImage=new IIOImage(bufferedImage,null,null);
        imageWriter.write(null,iioImage,imageWriteParam);//写图像
        imageOutputStream.flush();//刷新缓冲


    }
}
