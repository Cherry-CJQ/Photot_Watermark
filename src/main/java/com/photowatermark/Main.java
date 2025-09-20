package com.photowatermark;

import java.io.File;
import java.util.List;

import com.photowatermark.core.ExifReader;
import com.photowatermark.core.WatermarkGenerator;
import com.photowatermark.utils.ConfigParser;
import com.photowatermark.utils.FileUtils;

/**
 * 照片水印工具主入口类
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        try {
            // 解析命令行参数
            ConfigParser.Config config = ConfigParser.parseArgs(args);

            // 获取输入路径的文件或目录
            File inputFile = new File(config.getInputPath());

            if (inputFile.isDirectory()) {
                // 处理目录中的所有图片
                processDirectory(inputFile, config);
            } else if (inputFile.isFile()) {
                // 处理单个图片文件
                processSingleFile(inputFile, config);
            } else {
                System.err.println("错误: 输入路径不存在 - " + config.getInputPath());
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("处理过程中发生错误: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 处理单个图片文件
     */
    private static void processSingleFile(File inputFile, ConfigParser.Config config) {
        try {
            System.out.println("正在处理: " + inputFile.getName());

            // 读取EXIF信息
            String timestamp = ExifReader.readExifDateTime(inputFile);
            if (timestamp == null) {
                System.out.println("警告: 无法读取 " + inputFile.getName() + " 的EXIF信息，使用当前时间");
                timestamp = FileUtils.getCurrentDateTime();
            }

            // 处理图片并添加水印
            WatermarkGenerator.addWatermark(inputFile, timestamp, config);

            System.out.println("完成: " + inputFile.getName());

        } catch (Exception e) {
            System.err.println("处理文件失败: " + inputFile.getName() + " - " + e.getMessage());
        }
    }

    /**
     * 处理目录中的所有图片文件
     */
    private static void processDirectory(File directory, ConfigParser.Config config) {
        List<File> imageFiles = FileUtils.getImageFiles(directory);

        if (imageFiles.isEmpty()) {
            System.out.println("目录中没有找到支持的图片文件");
            return;
        }

        System.out.println("找到 " + imageFiles.size() + " 个图片文件");

        for (File imageFile : imageFiles) {
            processSingleFile(imageFile, config);
        }

        System.out.println("批量处理完成");
    }

    /**
     * 打印使用说明
     */
    private static void printUsage() {
        System.out.println("照片水印工具 - 使用说明");
        System.out.println("用法: java -jar photowatermark.jar <inputPath> [options]");
        System.out.println();
        System.out.println("参数:");
        System.out.println("  inputPath         图片文件或目录路径 (必需)");
        System.out.println("  -fontSize <size>  字体大小，默认24");
        System.out.println("  -color <hex>      字体颜色，十六进制格式，默认FFFFFF (白色)");
        System.out.println("  -position <pos>   水印位置，可选: top-left, center, bottom-right，默认bottom-right");
        System.out.println();
        System.out.println("示例:");
        System.out.println("  java -jar photowatermark.jar photo.jpg");
        System.out.println("  java -jar photowatermark.jar /path/to/photos -fontSize 32 -color FF0000");
        System.out.println("  java -jar photowatermark.jar /path/to/directory -position center");
    }
}
