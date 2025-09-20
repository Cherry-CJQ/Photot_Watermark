package com.photowatermark.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件工具类
 */
public class FileUtils {

    // 支持的图片格式
    private static final String[] SUPPORTED_IMAGE_EXTENSIONS = {
        ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff", ".tif"
    };

    /**
     * 获取目录中的所有图片文件
     */
    public static List<File> getImageFiles(File directory) {
        List<File> imageFiles = new ArrayList<>();

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isImageFile(file)) {
                        imageFiles.add(file);
                    }
                }
            }
        }

        return imageFiles;
    }

    /**
     * 检查文件是否为支持的图片格式
     */
    public static boolean isImageFile(File file) {
        if (file.isFile()) {
            String fileName = file.getName().toLowerCase();
            for (String extension : SUPPORTED_IMAGE_EXTENSIONS) {
                if (fileName.endsWith(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前日期时间字符串
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 创建输出目录
     */
    public static File createOutputDirectory(File inputFile) {
        File parentDir = inputFile.getParentFile();
        String outputDirName = parentDir.getName() + "_watermark";
        File outputDir = new File(parentDir, outputDirName);

        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new RuntimeException("无法创建输出目录: " + outputDir.getAbsolutePath());
            }
        }

        return outputDir;
    }

    /**
     * 生成输出文件名
     */
    public static String generateOutputFilename(String originalName) {
        // 保持原文件名，避免覆盖原文件
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            String name = originalName.substring(0, dotIndex);
            String extension = originalName.substring(dotIndex);
            return name + "_watermark" + extension;
        }
        return originalName + "_watermark";
    }
}
