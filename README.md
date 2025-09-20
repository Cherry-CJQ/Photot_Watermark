# Photo Watermark Tool

一个基于Java的命令行照片水印工具，用于为图片添加基于EXIF拍摄时间的文字水印。

## 功能特性

- 📸 **EXIF信息读取**: 自动读取图片的EXIF元数据，提取拍摄时间
- 🖼️ **智能水印**: 使用拍摄日期作为水印文本
- 🎨 **自定义样式**: 支持设置字体大小、颜色和位置
- 📁 **批量处理**: 支持处理目录中的所有图片
- 💾 **自动保存**: 在原目录创建_watermark子目录保存处理结果

## 快速开始

### 系统要求
- Java 8 或更高版本
- Maven 3.6+

### 安装依赖
```bash
mvn clean install
```

### 基本用法
```bash
# 处理单个图片文件
java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar /path/to/image.jpg

# 处理目录中的所有图片
java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar /path/to/image/directory

# 自定义水印参数
java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar /path/to/image.jpg -fontSize 32 -color FF0000 -position center
```

### 命令行参数
- `inputPath`: 图片文件或目录路径 (必需)
- `-fontSize <size>`: 字体大小，默认24
- `-color <hex>`: 字体颜色，十六进制格式，默认FFFFFF (白色)
- `-position <pos>`: 水印位置，可选: top-left, center, bottom-right，默认bottom-right

## 项目结构

```
photot_watermark/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── photowatermark/
│                   ├── Main.java                 # 主入口类
│                   ├── core/
│                   │   ├── ExifReader.java       # EXIF信息读取
│                   │   └── WatermarkGenerator.java # 水印生成
│                   └── utils/
│                       ├── FileUtils.java        # 文件工具
│                       └── ConfigParser.java     # 配置解析
├── target/
│   └── photo-watermark-tool-1.0.0-jar-with-dependencies.jar
├── pom.xml
├── README.md
└── LICENSE
```

## 技术栈

- **Java 8+**: 核心编程语言
- **Apache Commons Imaging**: EXIF元数据读取
- **Java ImageIO**: 图片处理和保存
- **Maven**: 项目构建和依赖管理

## 开发计划

项目按照PRT.md中的计划分阶段实现：

1. **Phase 1**: 项目搭建和基础框架
2. **Phase 2**: 核心功能开发 (EXIF读取、图片处理)
3. **Phase 3**: 配置管理和输出处理
4. **Phase 4**: 测试和性能优化

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目！

## 联系方式

项目维护者: [Cherry-CJQ](https://github.com/Cherry-CJQ)
