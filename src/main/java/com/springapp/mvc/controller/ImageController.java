package com.springapp.mvc.controller;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.springapp.mvc.result.Result;
import com.springapp.mvc.result.ResultSupport;
import com.springapp.mvc.services.ImageService;
import com.springapp.mvc.services.ImageServiceImpl;
import com.springapp.mvc.services.Mongo.MongoDaoImpl;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcdalao on 2017/4/19.
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;

    public ImageController() throws IOException {
        imageService=new ImageServiceImpl();
    }

    @RequestMapping(value = "/",method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public
    @ResponseBody
    Result<List<String>> uploadImage(HttpServletRequest request) throws IOException, FileUploadException {
        List<String> list = new ArrayList<String>();
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator fileIterator = upload.getItemIterator(request);
        while (fileIterator.hasNext()) {
            FileItemStream item = fileIterator.next();
            String path = this.imageService.save(item.getName(), item.openStream(),"max");
            list.add(path);
        }
        return ResultSupport.ok(list);
    }

    /**
     * 字节流返回图片（自动生成尺寸图片）
     * @param path
     * @param request
     * @param response
     */
    @RequestMapping(value="/path/**",method = RequestMethod.GET)
    public void getFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String path=restOfTheUrl.substring(restOfTheUrl.indexOf("/image/path")+11);
        FileInputStream input = this.imageService.read(path);
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();

        int read;
        byte b[]=new byte[1024];
        read=input.read(b);
        while(read!=-1)
        {
            stream.write(b,0,read);
            read=input.read(b);
            //read=fis.read();
        }
        stream.flush();
        stream.close();

        /*File file=new File("D:\\a-Max\\git-own\\ImageUpload\\target\\ImageUpload\\WEB-INF\\classes\\image\\max\\20170421\\8658c9c7-357e-49c6-9b80-c073a95a9e77\\320x180.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        int length = inputStream.read(data);
        inputStream.close();

        response.setContentType("image/png");

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();*/
    }

    @RequestMapping(value="/test")
    public @ResponseBody String Test(){
        MongoDaoImpl mongoDao=MongoDaoImpl.getMongoDaoImplInstance();
        DBCollection collection= mongoDao.getCollection("Demo", "user");
//        String[] keys={};
//        Object[] values={};
//        List<DBObject> list=mongoDao.find("Demo","user",keys,values,-1);

        String[] keys={"name","public","price"};
        Object[] values={"Core Java","Max",12.00};
        boolean a=mongoDao.inSert("Demo","Book",keys,values);

        return "Max is ok";
    }

}




