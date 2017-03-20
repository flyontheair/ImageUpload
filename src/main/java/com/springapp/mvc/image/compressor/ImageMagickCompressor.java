package com.springapp.mvc.image.compressor;

import com.springapp.mvc.image.ImageSize;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.IOException;

public class ImageMagickCompressor implements ImageCompressor {
    private static final String DEFAULT_SEARCH_PATH = "/usr/bin/";
    private String searchPath;
    private String quality;
    
    public ImageMagickCompressor(String searchPath, float quality) {
        this.searchPath = searchPath == null ? DEFAULT_SEARCH_PATH : searchPath;
        this.quality = (int)(quality * 100) + "%";
    }
    
    @Override
    public void compressImage(File srcFile, File targetFile) throws IOException {
        // create command
        ConvertCmd cmd = new ConvertCmd();
        cmd.setSearchPath(this.searchPath);
        // create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        op.addRawArgs("-quality", quality);
        op.addImage();
        op.addImage();

        try {
            cmd.run(op, srcFile.getAbsolutePath(), targetFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void compressImageToVague(File srcFile, File targetFile) throws IOException {
    	
    }

	@Override
	public void compressImage(File srcFile, File targetFile, ImageSize imageSize)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
}
