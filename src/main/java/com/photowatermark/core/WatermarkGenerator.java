package com.photowatermark.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.photowatermark.utils.ConfigParser;
import com.photowatermark.utils.FileUtils;

/**
 * 水印生成器
 */
public class WatermarkGenerator {

    /**
     * 为图片添加水印
     */
    public static void addWatermark(File inputFile, String watermarkText, ConfigParser.Config config) {
        try {
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(inputFile);
            if (originalImage == null) {
                throw new IOException("无法读取图片文件: " + inputFile.getName());
            }

            // 创建带水印的新图片
            BufferedImage watermarkedImage = createWatermarkedImage(originalImage, watermarkText, config);

            // 创建输出目录
            File outputDir = FileUtils.createOutputDirectory(inputFile);
            String outputFilename = FileUtils.generateOutputFilename(inputFile.getName());
            File outputFile = new File(outputDir, outputFilename);

            // 保存图片
            String formatName = getFormatName(inputFile.getName());
            ImageIO.write(watermarkedImage, formatName, outputFile);

            System.out.println("保存到: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("处理图片失败: " + inputFile.getName(), e);
        }
    }

    /**
     * 创建带水印的图片
     */
    private static BufferedImage createWatermarkedImage(BufferedImage originalImage, String watermarkText, ConfigParser.Config config) {
        // 创建新的图片，保持原图类型
        BufferedImage watermarkedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                originalImage.getType()
        );

        // 绘制原图
        Graphics2D g2d = watermarkedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);

        // 设置水印样式
        g2d.setFont(new Font("Arial", Font.BOLD, config.getFontSize()));
        g2d.setColor(hexToColor(config.getColor()));

        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 计算水印位置
        Point position = calculateWatermarkPosition(g2d, watermarkText, originalImage.getWidth(), originalImage.getHeight(), config.getPosition());

        // 绘制水印
        g2d.drawString(watermarkText, position.x, position.y);
        g2d.dispose();

        return watermarkedImage;
    }

    /**
     * 计算水印位置
     */
    private static Point calculateWatermarkPosition(Graphics2D g2d, String text, int imageWidth, int imageHeight, String position) {
        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        int margin = 20; // 边距

        switch (position) {
            case "top-left":
                return new Point(margin, margin + textHeight);

            case "center":
                int centerX = (imageWidth - textWidth) / 2;
                int centerY = (imageHeight + textHeight) / 2;
                return new Point(centerX, centerY);

            case "bottom-right":
            default:
                int bottomRightX = imageWidth - textWidth - margin;
                int bottomRightY = imageHeight - margin;
                return new Point(bottomRightX, bottomRightY);
        }
    }

    /**
     * 十六进制颜色转换为Color对象
     */
    private static Color hexToColor(String hex) {
        try {
            return new Color(
                    Integer.parseInt(hex.substring(0, 2), 16),
                    Integer.parseInt(hex.substring(2, 4), 16),
                    Integer.parseInt(hex.substring(4, 6), 16)
            );
        } catch (Exception e) {
            return Color.WHITE; // 默认白色
        }
    }

    /**
     * 根据文件名获取图片格式
     */
    private static String getFormatName(String filename) {
        String lowerName = filename.toLowerCase();
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            return "JPEG";
        } else if (lowerName.endsWith(".png")) {
            return "PNG";
        } else if (lowerName.endsWith(".gif")) {
            return "GIF";
        } else if (lowerName.endsWith(".bmp")) {
            return "BMP";
        } else {
            return "JPEG"; // 默认格式
        }
    }
}
