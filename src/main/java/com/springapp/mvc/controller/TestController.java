package com.springapp.mvc.controller;

import com.springapp.mvc.result.Result;
import com.springapp.mvc.result.ResultSupport;
import com.springapp.mvc.services.ImageService;
import com.springapp.mvc.services.ImageServiceImpl;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcdalao on 2017/4/19.
 */
@Controller
@RequestMapping("/upload")
public class TestController {
    private ImageService imageService;

    public TestController() throws IOException {
        imageService=new ImageServiceImpl();
    }

    @RequestMapping(value="/test",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody
    String test() {
        return "hello";
    }

    @RequestMapping(value = "/image",method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
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
     * 字节流返回退选哪个数据
     * @param path
     * @param request
     * @param response
     */
    @RequestMapping(value="/path",method = RequestMethod.GET)
    public void getFile(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileInputStream input = this.imageService.read(path);
        response.setContentType("image/png");
        byte[] data = new byte[input.available()];
        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }

}




