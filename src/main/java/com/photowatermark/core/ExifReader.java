package com.photowatermark.core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

/**
 * EXIF信息读取器
 */
public class ExifReader {

    /**
     * 读取图片的EXIF拍摄时间信息
     */
    public static String readExifDateTime(File imageFile) {
        try {
            // 使用更简单的方法获取所有EXIF信息
            ImageMetadata metadata = Imaging.getMetadata(imageFile);

            if (metadata instanceof JpegImageMetadata) {
                JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

                // 打印所有EXIF信息用于调试
                System.out.println("EXIF信息: " + jpegMetadata.toString());

                // 这里简化处理，实际项目中需要根据具体的EXIF标签结构来解析
                // 暂时返回null，后续可以根据实际需求完善
                return extractDateTimeFromMetadata(jpegMetadata.toString());
            }

        } catch (ImageReadException | IOException e) {
            System.err.println("读取EXIF信息失败: " + imageFile.getName() + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("处理EXIF信息时发生错误: " + imageFile.getName() + " - " + e.getMessage());
        }

        return null;
    }

    /**
     * 从元数据字符串中提取日期时间信息（简化实现）
     */
    private static String extractDateTimeFromMetadata(String metadata) {
        // 这里是一个简化的实现，实际项目中需要根据具体的EXIF结构来解析
        // 查找包含日期时间的信息
        if (metadata.contains("DateTimeOriginal")) {
            return extractFormattedDateTime(metadata, "DateTimeOriginal");
        } else if (metadata.contains("DateTime")) {
            return extractFormattedDateTime(metadata, "DateTime");
        }
        return null;
    }

    /**
     * 提取并格式化日期时间
     */
    private static String extractFormattedDateTime(String metadata, String tagName) {
        try {
            // 简化的提取逻辑，实际需要更复杂的解析
            int startIndex = metadata.indexOf(tagName);
            if (startIndex != -1) {
                // 尝试提取类似 "2023:12:25 14:30:45" 的格式
                String remaining = metadata.substring(startIndex);
                for (int i = 0; i < remaining.length() - 10; i++) {
                    String potentialDate = remaining.substring(i, i + 10);
                    if (potentialDate.matches("\\d{4}:\\d{2}:\\d{2}")) {
                        String timePart = remaining.substring(i + 11, i + 19);
                        if (timePart.matches("\\d{2}:\\d{2}:\\d{2}")) {
                            String exifDateTime = potentialDate + " " + timePart;
                            return formatExifDateTime(exifDateTime);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("提取日期时间失败: " + e.getMessage());
        }
        return null;
    }

    /**
     * 格式化EXIF日期时间字符串 EXIF格式: "yyyy:MM:dd HH:mm:ss" -> "yyyy-MM-dd HH:mm:ss"
     */
    private static String formatExifDateTime(String exifDateTime) {
        try {
            // EXIF格式: "2023:12:25 14:30:45"
            SimpleDateFormat exifFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            Date date = exifFormat.parse(exifDateTime);

            // 转换为标准格式: "2023-12-25 14:30:45"
            SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return standardFormat.format(date);

        } catch (Exception e) {
            System.err.println("格式化EXIF时间失败: " + exifDateTime);
            return exifDateTime.replace(':', '-'); // 简单替换冒号为连字符
        }
    }

    /**
     * 检查图片是否包含EXIF信息
     */
    public static boolean hasExifData(File imageFile) {
        try {
            ImageMetadata metadata = Imaging.getMetadata(imageFile);
            return metadata != null;
        } catch (Exception e) {
            return false;
        }
    }
}
