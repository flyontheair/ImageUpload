package com.springapp.mvc.controller;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.springapp.mvc.result.Result;
import com.springapp.mvc.result.ResultSupport;
import com.springapp.mvc.services.ImageService;
import com.springapp.mvc.services.ImageServiceImpl;
import com.springapp.mvc.services.Mongo.MongoDaoImpl;
import com.springapp.mvc.services.SaveImage;
import com.springapp.mvc.services.SaveImageImpl;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.text.StrBuilder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pcdalao on 2017/4/19.
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;
    private SaveImage saveImage;

    public ImageController() throws IOException {
        imageService=new ImageServiceImpl();
        saveImage=new SaveImageImpl();
    }

    @RequestMapping(value = "/",method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public
    @ResponseBody
    Result<List<Map<String,String>>> uploadImage(HttpServletRequest request) throws IOException, FileUploadException {
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator fileIterator = upload.getItemIterator(request);
        String userId="Max";
        while (fileIterator.hasNext()) {
            FileItemStream item = fileIterator.next();
            Map<String,String> path = this.imageService.save(item.getName(), item.openStream(),userId);
            Map<String,String> img=new HashMap<String, String>();
            String id= saveImage.Save(path);
            if(id!=""){
                img.put("imgId",id);
                img.put("name",item.getName());
            }
            img.put("url",path.get("prefix")+path.get("path"));
            list.add(img);
        }
        return ResultSupport.ok(list);
    }

    /**
     * 字节流返回图片（自动生成尺寸图片）
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
    }

    @RequestMapping(value="/max",method = RequestMethod.POST)
    public @ResponseBody String Max(){
        return "Max is ok";
    }

}




