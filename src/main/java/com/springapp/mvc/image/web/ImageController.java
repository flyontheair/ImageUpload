package com.springapp.mvc.image.web;

import com.springapp.mvc.image.services.ImageService;
import com.springapp.mvc.result.Result;
import com.springapp.mvc.result.ResultSupport;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    //@Qualifier("imageServiceImpl")
    private ImageService imageService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
    public
    @ResponseBody
    Result<List<String>> uploadImage(HttpServletRequest request) throws IOException, FileUploadException {
        List<String> list = new ArrayList<String>();
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator fileIterator = upload.getItemIterator(request);
        while (fileIterator.hasNext()) {
            FileItemStream item = fileIterator.next();
            String path = this.imageService.save(item.getName(), item.openStream());
            list.add(path);
        }
        return ResultSupport.ok(list);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Result<List<String>> test() {
        List<String> list = new ArrayList<String>();
        list.add("Max is ok");
        list.add("Max is ok2");
        list.add("Max is ok3");
        return ResultSupport.ok(list);
    }
}
