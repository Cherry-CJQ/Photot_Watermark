# 照片水印工具安装指南

## 问题说明

由于Maven仓库目录权限问题，自动编译可能失败。以下是手动安装和使用的解决方案。

## 方案一：使用预配置的Maven（推荐）

1. **修改Maven配置**：
   - 打开Maven安装目录：`D:\app_rosd\0HHU_program\apache-maven-3.9.3\conf\settings.xml`
   - 修改 `<localRepository>` 指向一个有写入权限的目录，例如：
     ```xml
     <localRepository>C:\Users\您的用户名\.m2\repository</localRepository>
     ```

2. **重新编译项目**：
   ```bash
   mvn clean compile
   mvn package
   ```

## 方案二：手动下载依赖（备用）

1. **创建lib目录**：
   ```bash
   mkdir lib
   ```

2. **下载依赖库**：
   - 访问：https://repo1.maven.org/maven2/org/apache/commons/commons-imaging/1.0-alpha3/
   - 下载 `commons-imaging-1.0-alpha3.jar`
   - 将下载的JAR文件放入 `lib/` 目录

3. **手动编译**：
   ```bash
   javac -cp "lib/*" -d temp/classes src/main/java/com/photowatermark/*.java src/main/java/com/photowatermark/core/*.java src/main/java/com/photowatermark/utils/*.java
   ```

4. **创建可执行JAR**：
   ```bash
   jar cfe photowatermark.jar com.photowatermark.Main -C temp/classes .
   ```

## 方案三：使用提供的构建脚本

运行 `build_manual.bat` 脚本，按照提示手动下载依赖库。

## 项目文件结构

```
photot_watermark/
├── src/                    # 源代码
│   └── main/java/com/photowatermark/
│       ├── Main.java              # 主入口类
│       ├── core/                  # 核心功能
│       │   ├── ExifReader.java    # EXIF读取
│       │   └── WatermarkGenerator.java # 水印生成
│       └── utils/                 # 工具类
│           ├── ConfigParser.java  # 配置解析
│           └── FileUtils.java     # 文件操作
├── lib/                    # 依赖库目录（需要手动创建）
├── pom.xml                # Maven配置
├── build_manual.bat       # 手动构建脚本
├── run_example.bat        # 运行示例脚本
├── USAGE.md              # 使用说明
└── INSTALL.md            # 本安装指南
```

## 快速开始

1. **确保Java环境**：
   ```bash
   java -version
   ```

2. **按照上述方案之一解决依赖问题**

3. **运行工具**：
   ```bash
   java -jar photowatermark.jar 图片路径 [选项]
   ```

## 使用示例

```bash
# 处理单张图片
java -jar photowatermark.jar photo.jpg

# 处理整个目录
java -jar photowatermark.jar /path/to/photos

# 自定义参数
java -jar photowatermark.jar photo.jpg -fontSize 32 -color FF0000 -position center
```

## 参数说明

- `inputPath`: 图片文件或目录路径（必需）
- `-fontSize <size>`: 字体大小，默认24
- `-color <hex>`: 字体颜色（十六进制），默认FFFFFF（白色）
- `-position <pos>`: 水印位置（top-left, center, bottom-right），默认bottom-right

## 输出说明

处理后的图片会保存在原目录下的 `原目录名_watermark` 子目录中，文件名会添加 `_watermark` 后缀。

## 技术支持

如果遇到问题，请检查：
1. Java版本是否 >= 1.8
2. 依赖库是否下载正确
3. 文件权限是否足够

项目采用MIT许可证，欢迎贡献代码！