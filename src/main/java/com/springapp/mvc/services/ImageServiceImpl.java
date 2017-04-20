package com.springapp.mvc.services;
import com.springapp.mvc.image.ImageConfig;
import com.springapp.mvc.image.ImageSize;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by pcdalao on 2017/4/19.
 */
public class ImageServiceImpl implements ImageService {

    private final File root;
    private static final String ORIGIN = "origin";//原图名称
    public static final String DEFAULT_IMAGE_SIZE = "0x0";//原图尺寸的压缩结果文件名
    private final ImageCompressor imageCompressor;
    private final ImageConfig imageConfig;
    private static final Pattern IMAGE_SIZE_PATTERN = Pattern.compile("\\dx\\d");//图片宽高格式，也是名字

    private final List<ImageSize> imageSizes = new ArrayList<ImageSize>();

    public ImageServiceImpl() throws IOException {
        String root=getClass().getResource("/").getFile().toString();//当前项目地址，实际应该配置的
        this.root = new File(root, "image");//图片的存储路径

        this.imageConfig=ImageConfig.load();
        this.imageCompressor = new DefaultImageCompressor(0.7f);
        for(String size:imageConfig.getImageSizes()){
            this.imageSizes.add(new ImageSize(size));
        }
    }

    /**
     *保存图片流到本地
     * @param name
     * @param inputStream
     * @param userId
     * @return
     * @throws IOException
     */
    @Override
    public String save(String name, InputStream inputStream, String userId) throws IOException {
        String suffix=getSuffix(name);
        String uri=buildUri(userId);
        File srcFile=new File(this.root,uri+ORIGIN+suffix);
        File dir=srcFile.getParentFile();
        dir.mkdirs();//创建多级文件目录

        OutputStream outputStream=null;
        //写入文件
        try{
            outputStream= new FileOutputStream(srcFile);
            IOUtils.copy(inputStream,outputStream);

        }finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }

        //格式化图片（压缩到默认质量）文件名末：0x0
        File targetFile=new File(this.root,uri+DEFAULT_IMAGE_SIZE+suffix);
        compressImage(srcFile, targetFile);//压缩文件

        return this.root.getPath()+uri + ORIGIN + suffix;//返回图片路径
    }

    @Override
    public FileInputStream read(String path) throws IOException {
        File file=new File(this.root,path);
        if(file.isFile()){
            if(file.canRead()){
                return new FileInputStream(file);
            }else{
                throw new IOException("file cannot be read: " + path);
            }
        }

        //没有指定的格式
        String fileName=file.getName().toLowerCase();//0x0格式
        final String size=IMAGE_SIZE_PATTERN.matcher(fileName).find()?fileName:DEFAULT_IMAGE_SIZE;
        final File dir=file.isDirectory()?file:file.getParentFile();

        file=this.loadSizedFile(dir,size);

        if(file!=null){
            return new FileInputStream(file);
        }

        return null;
    }

    private void compressImage(File srcFile, File targetFile) throws IOException {
        imageCompressor.compressImage(srcFile,targetFile);
    }

    //加载图片，不存在就生产
    private File loadSizedFile(File dir, String size) throws IOException{
        //目录存在检测
        if(!dir.isDirectory()){
            return null;
        }

        //检查尺寸是否存在
        File file=searchFile(dir,size);
        if(file!=null){
            return file;
        }
        File defaultImageFile= loadDefaultImageFile(dir);

        if(size.equals(DEFAULT_IMAGE_SIZE)){
            return defaultImageFile;
        }

        //仅仅生成特定格式
        ImageSize targetImageSize=new ImageSize(size);
        for(ImageSize imageSize:imageSizes){
            if(imageSize.equals(targetImageSize)){
                String suffix=getSuffix(defaultImageFile.getName());
                File targetFile = new File(dir, targetImageSize.size + suffix);
                resize(defaultImageFile, targetFile, targetImageSize.width, targetImageSize.height);

                return targetFile;
            }
        }

        return defaultImageFile;
    }

    //加载默认图片（0x0）
    public File loadDefaultImageFile(File dir) throws IOException {
        File targetfile=searchFile(dir,DEFAULT_IMAGE_SIZE);
        if(targetfile==null){//默认图一开始生成失败
            File srcFile=searchFile(dir,ORIGIN);
            if(srcFile==null){
                return null;
            }
            targetfile=new File(dir,DEFAULT_IMAGE_SIZE+getSuffix(srcFile.getName()));
            compressImage(srcFile,targetfile);
        }
        return targetfile;
    }

    //查找文件夹下是否存在此图片
    private File searchFile(File dir, final String searchName){
        FilenameFilter filter=new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith(searchName);
            }
        };

        File[] files=dir.listFiles(filter);//目录下的合法文件列表
        if(!ArrayUtils.isEmpty(files)){
            File file=files[0];
            if(file.isFile()&&file.canRead()){
                return file;
            }
        }
        return null;
    }



    /**
     * 组建图片路径
     * @param userId
     * @return
     */
    public String buildUri(String userId){
        final Date date=new Date();

        if(userId==null||userId=="") userId="01";
        SimpleDateFormat format=new SimpleDateFormat("/yyyyMMdd/");
        return "/"+userId+format.format(date)+ UUID.randomUUID().toString()+"/";
    }

    /**
     * 获取后缀文件名
     * @param name
     * @return
     */
    public String getSuffix(String name){
        if(name!=null){
            int i=name.lastIndexOf('.');
            if(i>=0){
                return name.substring(i);
            }
        }
        return ".jpg";//出错用jpg
    }

    /**
     * 强制压缩/放大图片到固定的大小
     * @param targetWidth int 新宽度
     * @param targetHeight, int 新高度
     * @param srcFile 源图片
     * @throws IOException
     */
    private void resize(File srcFile, File targetFile, int targetWidth, int targetHeight) throws IOException {
        // 若target是0x0，应该使用原图
        if (targetWidth == 0 && targetHeight == 0) {
            return;
        }

        FileInputStream input = new FileInputStream(srcFile);
        BufferedImage srcImage = ImageIO.read(input);
        IOUtils.closeQuietly(input);

        int srcWidth = srcImage.getWidth();
        int srcHeight = srcImage.getHeight();

        // 若宽为0，参考原高等比例缩放;
        if (targetWidth == 0) {
            targetWidth = (int) (1L * targetHeight * srcWidth / srcHeight);
        }
        // 若高为0，参考原宽等比例缩放;
        else if (targetHeight == 0) {
            targetHeight = (int) (1L * targetWidth * srcHeight / srcWidth);
        }

        // 等比例压缩；若目标尺寸比较原图大，则压缩为与目标尺寸等比例的小图
        double rate1 = ((double) srcWidth) / srcHeight;
        double rate2 = ((double) targetWidth) / targetHeight;

        int width = srcWidth, height = srcHeight;
        if (rate1 > rate2) {
            width = (int)(1L * srcHeight * targetWidth /  targetHeight);
        } else {
            height = (int)(1L * srcWidth * targetHeight / targetWidth);
        }

        // 先从原图截取与目标尺寸等比例最大图，然后再压缩
        int x = (srcWidth - width) / 2, y = (srcHeight - height) / 2;
        srcImage = srcImage.getSubimage(x, y, width, height);

        // 如目标尺寸比截图大，则采用截图尺寸
        if (targetWidth > width) {
            targetWidth = width;
            targetHeight = height;
        }

        writeImage(srcImage, targetFile, targetWidth, targetHeight);
    }

    private void writeImage(BufferedImage srcImage, File targetFile, int targetWidth, int targetHeight)
            throws IOException {
        //构建图片对象
        BufferedImage image = new BufferedImage(targetWidth, targetHeight, srcImage.getType());
        //绘制缩放后的图
        Image scaledInstance = srcImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        image.getGraphics().drawImage(scaledInstance, 0, 0, targetWidth, targetHeight, Color.WHITE, null);
        //输出到文件
        ImageIO.write(image, "jpg", targetFile);

        //logger.info("Created image: {}", targetFile);
    }


}
