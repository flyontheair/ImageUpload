package com.springapp.mvc.web;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcdalao on 2017/3/15.
 */

@Controller
@RequestMapping("/image")
public class ImageController {
    @RequestMapping(method = RequestMethod.POST,produces = "application/json",consumes = "multipart/form-data")
    @ResponseBody
    public List<String> uploadImage(HttpServletRequest request) throws IOException, FileUploadException {
        List<String> list=new ArrayList<String>();
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator fileIterator = upload.getItemIterator(request);
        while(fileIterator.hasNext()){
            FileItemStream item=fileIterator.next();
            String path="test";
            list.add(path);
        }
        list.add("max test ok");
        return list;
    }
}
