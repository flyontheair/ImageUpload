package com.springapp.mvc.services;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.xml.ws.spi.http.HttpContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Max on 2017/3/15.
 */
@Service("imageServiceImpl")
public class ImageServiceImpl implements ImageService {
    private final File root;
    private String rootUri="/file/image";//子目录路径
    //private String rootUri="G:\\github\\ImageUpload\\src\\main\\java\\com\\springapp\\mvc\\file\\image";


    public ImageServiceImpl(){

        String configUri=getClass().getResource("/").getFile().toString();//当前项目地址，实际应该配置的

        root=new File(configUri+rootUri);
        System.out.println(root.getAbsolutePath());
    }

    @Override
    public String save(String name, InputStream inputStream) throws IOException {
        String suffix = getSuffix(name);
        String uri = "";
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = dateFormat.format(date);
        uri=format+"/"+date.getTime();//按时间分目录
        File srcFile=new File(this.root,uri+suffix);//完整地址路径
        File dir=srcFile.getParentFile();

        dir.mkdirs();
        OutputStream output = null;
        try {//???注释解读
            output = new FileOutputStream(srcFile);
            IOUtils.copy(inputStream, output);
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(inputStream);
        }
        //File targetFile=new File(this.root,uri+suffix);

        return rootUri+uri+suffix;
    }
    private String getSuffix(String name) {
        if (name != null) {
            int i = name.lastIndexOf('.');
            if (i >= 0) {
                return name.substring(i);
            }
        }

        return "";
    }
}
