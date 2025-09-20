@echo off
echo ========================================
echo 照片水印工具示例脚本
echo ========================================

REM 检查Maven是否可用
mvn --version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Maven，请先安装Maven
    pause
    exit /b 1
)

echo.
echo 1. 清理项目...
mvn clean

echo.
echo 2. 编译项目...
mvn compile

echo.
echo 3. 打包可执行JAR...
mvn package

echo.
echo 4. 创建测试目录结构...
if not exist test_data mkdir test_data
if not exist test_data\input mkdir test_data\input
if not exist test_data\output mkdir test_data\output

echo.
echo 5. 复制一些测试图片到input目录(请手动添加图片文件)
echo    将JPG/PNG图片复制到 test_data\input\ 目录中

echo.
echo 6. 运行示例命令:
echo    java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar test_data\input
echo    java -jar target/photo-watermark-tool-1.0.0-jar-with-dependencies.jar test_data\input\your_image.jpg -fontSize 32 -color FF0000

echo.
echo ========================================
echo 使用说明:
echo 1. 将图片文件放入 test_data\input 目录
echo 2. 运行上述java命令处理图片
echo 3. 处理后的图片将保存在 test_data\input_watermark 目录
echo ========================================

pause