package com.springapp.mvc.controller;

import com.mongodb.DBObject;
import com.springapp.mvc.services.Mongo.MongoDaoImpl;
import com.springapp.mvc.services.zip.ZipUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * Created by pcdalao on 2017/5/15.
 */
@Controller
@RequestMapping("/zip")
public class ZipController {
    private MongoDaoImpl mongoDao;
    private ZipUtils zipUtils;

    public ZipController(){
        mongoDao=MongoDaoImpl.getMongoDaoImplInstance();
        zipUtils=new ZipUtils();
    }

    @RequestMapping(value="/down")
    public void downZip(List<String> ids, String fileName, HttpServletResponse response) throws IOException{
        String zipName = fileName+".zip";
        File destFile=new File("D:/apache/apache-tomcat-7.0.72/file/zip/"+zipName);
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        List<File> files=new ArrayList<File>();
        try{
            String[] keys={"_id"};
            Object[] ary=ids.toArray();
            List<Object[]> values=new ArrayList<Object[]>();
            values.add(ary);
            ArrayList<DBObject> list=mongoDao.findByInQuery("Demo","Image",keys,values,-1);
            for(DBObject item : list){
                String filePath=item.get("root").toString()+item.get("path");
                //files.add(new File(filePath));
                zipUtils.doCompress(new File(filePath),out);
                response.flushBuffer();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @RequestMapping(value="test")
    public @ResponseBody String Test(){
        return "Max";
    }
}
