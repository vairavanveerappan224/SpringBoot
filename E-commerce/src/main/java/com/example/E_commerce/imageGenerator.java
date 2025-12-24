package com.example.E_commerce;

import java.io.File;

public class imageGenerator {
    public static String getImageName(String imageUrl){

        String uploadPath = "D:/user_image/" + imageUrl;
        File path=new File(uploadPath);

        return path.getName();
    }

}
