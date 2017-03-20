package com.springapp.mvc.image.compressor;

import com.springapp.mvc.image.ImageSize;

import java.io.File;
import java.io.IOException;

public interface ImageCompressor {

	public abstract void compressImageToVague(File srcFile, File targetFile) throws IOException;
	
    public abstract void compressImage(File srcFile, File targetFile, ImageSize imageSize) throws IOException;
    
    public abstract void compressImage(File srcFile, File targetFile) throws IOException;

}