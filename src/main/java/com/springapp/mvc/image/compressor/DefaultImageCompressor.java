package com.springapp.mvc.image.compressor;


import com.springapp.mvc.image.ImageSize;
import com.springapp.mvc.image.filter.GaussianFilter;
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
            compressImage(srcImage, fos, this.quality);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
        }
    }
    
    private void compressImage(BufferedImage srcImage, OutputStream outputStream, float quality) throws IOException {
        //构建图片对象
        final int imageType = srcImage.isAlphaPremultiplied() ? BufferedImage.TRANSLUCENT : BufferedImage.TYPE_INT_RGB;
        BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), imageType);
        //绘制缩放后的图
        Image scaledInstance = srcImage.getScaledInstance(srcImage.getWidth(), srcImage.getHeight(), Image.SCALE_SMOOTH);
        bufferedImage.getGraphics().drawImage(scaledInstance, 0, 0, srcImage.getWidth(), srcImage.getHeight(), Color.WHITE, null);
        
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        // 压缩设置
        if (imageWriteParam.canWriteCompressed()) {
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(quality);
        }
        ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        IIOImage iioimage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioimage, imageWriteParam);
        imageOutputStream.flush();
    }
    
    public void compressImageToVague(File srcFile, File targetFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            BufferedImage srcImage = ImageIO.read(fis);
            fos = new FileOutputStream(targetFile);
            compressImageToVague(srcImage, fos, this.quality);
        } finally {
            IOUtils.closeQuietly(fos);

            IOUtils.closeQuietly(fis);
        }
    }
    
    private void compressImageToVague(BufferedImage srcImage, OutputStream outputStream, float quality) throws IOException {
        //构建图片对象
    	GaussianFilter boxBlurFilter = new GaussianFilter(30);
    	BufferedImage srcImageVague = boxBlurFilter.filter(srcImage, srcImage);
    	
        final int imageType = srcImageVague.isAlphaPremultiplied() ? BufferedImage.TRANSLUCENT : BufferedImage.TYPE_INT_RGB;
        BufferedImage bufferedImage = new BufferedImage(srcImageVague.getWidth(), srcImageVague.getHeight(), imageType);
        //绘制缩放后的图
        Image scaledInstance = srcImageVague.getScaledInstance(srcImageVague.getWidth(), srcImageVague.getHeight(), Image.SCALE_SMOOTH);
        bufferedImage.getGraphics().drawImage(scaledInstance, 0, 0, srcImageVague.getWidth(), srcImageVague.getHeight(), Color.WHITE, null);
        
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        // 压缩设置
        if (imageWriteParam.canWriteCompressed()) {
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(quality);
        }
        ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);
        
        IIOImage iioimage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioimage, imageWriteParam);
        imageOutputStream.flush();
    }
    
    
	@Override
	public void compressImage(File srcFile, File targetFile, ImageSize imageSize)
			throws IOException {
		FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            BufferedImage srcImage = ImageIO.read(fis);
            fos = new FileOutputStream(targetFile);
            compressImage(srcImage, fos, this.quality, imageSize);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
        }
	}
	
	private void compressImage(BufferedImage srcImage, OutputStream outputStream, float quality, ImageSize imageSize) throws IOException {
        //构建图片对象
        final int imageType = srcImage.isAlphaPremultiplied() ? BufferedImage.TRANSLUCENT : BufferedImage.TYPE_INT_RGB;
        BufferedImage bufferedImage = new BufferedImage(imageSize.width, imageSize.height, imageType);
        //绘制缩放后的图
        Image scaledInstance = srcImage.getScaledInstance(imageSize.width, imageSize.height, Image.SCALE_SMOOTH);
        bufferedImage.getGraphics().drawImage(scaledInstance, 0, 0, imageSize.width, imageSize.height, Color.WHITE, null);
        
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        // 压缩设置
        if (imageWriteParam.canWriteCompressed()) {
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(quality);
        }
        ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        IIOImage iioimage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioimage, imageWriteParam);
        imageOutputStream.flush();
    }

}
