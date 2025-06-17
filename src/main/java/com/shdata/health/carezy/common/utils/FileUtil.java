package com.shdata.health.carezy.common.utils;

import cn.hutool.core.util.IdcardUtil;

import java.io.File;

public class FileUtil extends cn.hutool.core.io.FileUtil {


    /**
     * 判断文件是否PDF文件
     */
    public static boolean isPDF(File file) {
        return file.getName().endsWith(".pdf");
    }

    public static void main(String[] args) { //189799197312253282
        System.out.println(IdcardUtil.isValidCard18("189799197312253282", true));
    }
}
