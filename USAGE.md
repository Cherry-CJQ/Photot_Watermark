# 照片水印工具使用指南

## 项目概述

这是一个基于Java的命令行照片水印工具，可以为图片添加基于EXIF拍摄时间的文字水印。

## 项目结构

```
photot_watermark/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── photowatermark/
│   │   │           ├── Main.java                 # 主入口类
│   │   │           ├── core/
│   │   │           │   ├── ExifReader.java       # EXIF信息读取
│   │   │           │   └── WatermarkGenerator.java # 水印生成
│   │   │           └── utils/
│   │   │               ├── FileUtils.java        # 文件工具
│   │   │               └── ConfigParser.java     # 配置解析
│   │   └── resources/
│   └── test/
│       └── java/
├── pom.xml
├── README.md
├── PRT.md
└── LICENSE
```

## 核心功能

### 1. EXIF信息读取
- 自动读取图片的EXIF元数据
- 提取拍摄时间信息
- 格式化为年月日格式作为水印文本

### 2. 水印配置
- 字体大小设置（默认24pt）
- 字体颜色设置（默认白色）
- 水印位置选择（左上角、居中、右下角）

### 3. 图片处理
- 在原图上绘制文字水印
- 保持原始图片质量
- 生成新的水印图片文件

### 4. 批量处理
- 支持处理目录中的所有图片
- 自动创建输出目录
- 保持原始文件名结构

## 使用方法

### 编译项目
```bash
mvn clean compile
```

### 打包可执行JAR
```bash
mvn clean package
```

### 运行工具
```bash
# 处理单个图片文件
java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar photo.jpg

# 处理目录中的所有图片
java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar /path/to/photos

# 自定义水印参数
java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar photo.jpg -fontSize 32 -color FF0000 -position center
```

### 命令行参数
- `inputPath`: 图片文件或目录路径 (必需)
- `-fontSize <size>`: 字体大小，默认24
- `-color <hex>`: 字体颜色，十六进制格式，默认FFFFFF (白色)
- `-position <pos>`: 水印位置，可选: top-left, center, bottom-right，默认bottom-right

## 输出说明

处理后的图片会保存在原目录下的 `原目录名_watermark` 子目录中，文件名会添加 `_watermark` 后缀以避免覆盖原文件。

## 技术依赖

- **Java 8+**: 核心编程语言
- **Apache Commons Imaging**: EXIF元数据读取
- **Java ImageIO**: 图片处理和保存
- **Maven**: 项目构建和依赖管理

## 注意事项

1. 如果图片没有EXIF信息，会使用当前时间作为水印文本
2. 支持常见图片格式：JPG, JPEG, PNG, GIF, BMP, TIFF
3. 输出图片质量与原图保持一致
4. 水印位置支持三种预设位置

## 故障排除

如果遇到Maven依赖问题，可以尝试：
1. 清理Maven本地仓库：`mvn clean`
2. 重新下载依赖：`mvn dependency:resolve`
3. 或者手动下载依赖包到本地仓库

## 开发计划

后续版本计划添加：
- 更多水印样式选项
- 批量处理性能优化  
- GUI界面版本
- 更多图片格式支持