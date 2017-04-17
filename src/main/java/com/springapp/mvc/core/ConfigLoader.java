package com.springapp.mvc.core;

import com.springapp.mvc.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import org.apache.logging.log4j.core.util.Loader;

/**
 * Created by pcdalao on 2017/4/17.
 */
public abstract class ConfigLoader {

    private static final Yaml yaml=new Yaml();

    public static <T> T loadYamlAs(String pathname,Class<T> clazz){
        T obj=yaml.loadAs(ConfigLoader.load(pathname),clazz);

        String extFile=System.getProperty(pathname);
        if(!StringUtils.isBlank(extFile)){
            T obj1=yaml.loadAs(ConfigLoader.load(extFile),clazz);
            BeanUtils.CopyPropertiesExcludeNulls(obj1, obj);
        }
        return obj;
    }

    private static InputStream load(String path){
        try{
            //try to load absolute file
            File file=new File(path);
            if(file.isFile()){
                return new FileInputStream(file);
            }

            //load from class path
            URL url= Loader.getResource(path,ConfigLoader.class.getClassLoader());
            if(null==url){
                throw new FileNotFoundException("not Found");
            }
            return url.openStream();

        }catch (Exception e){
            //throw new ConfigLoadException(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
