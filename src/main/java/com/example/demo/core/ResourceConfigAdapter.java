package com.example.demo.core;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourceConfigAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取文件的真实路径 work_project代表项目工程名 需要更改
        String path = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator;
        //String path =File.separator+"usr"+File.separator+"local"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"Orz"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"static"+File.separator+"picture"+File.separator;
        System.out.println(path);
        //String path = "D:/fileUpload/";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/**").
                    addResourceLocations("file:"+path);
        }else{//linux和mac系统 可以根据逻辑再做处理
            registry.addResourceHandler("/**").
                    addResourceLocations("file:"+path);
        }
        super.addResourceHandlers(registry);
    }
}