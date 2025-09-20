@echo off
echo ========================================
echo 手动构建照片水印工具
echo ========================================

REM 创建临时目录用于编译
if not exist temp mkdir temp
if not exist temp\classes mkdir temp\classes

echo.
echo 1. 下载必要的依赖库...
echo    请手动下载以下JAR文件到 lib/ 目录:
echo    - commons-imaging-1.0-alpha3.jar
echo    - 从Maven中央仓库下载: https://mvnrepository.com/artifact/org.apache.commons/commons-imaging/1.0-alpha3

echo.
echo 2. 创建lib目录...
if not exist lib mkdir lib

echo.
echo 3. 检查依赖库...
if not exist lib\commons-imaging-1.0-alpha3.jar (
    echo 错误: 请先下载 commons-imaging-1.0-alpha3.jar 到 lib/ 目录
    echo 下载地址: https://repo1.maven.org/maven2/org/apache/commons/commons-imaging/1.0-alpha3/commons-imaging-1.0-alpha3.jar
    pause
    exit /b 1
)

echo.
echo 4. 编译Java源代码...
javac -cp "lib/*" -d temp/classes src/main/java/com/photowatermark/*.java src/main/java/com/photowatermark/core/*.java src/main/java/com/photowatermark/utils/*.java

if errorlevel 1 (
    echo 编译失败!
    pause
    exit /b 1
)

echo.
echo 5. 创建可执行JAR...
jar cfe photowatermark.jar com.photowatermark.Main -C temp/classes .

if errorlevel 1 (
    echo 创建JAR失败!
    pause
    exit /b 1
)

echo.
echo 6. 清理临时文件...
rmdir /s /q temp

echo.
echo ========================================
echo 构建成功!
echo 生成的可执行文件: photowatermark.jar
echo 
echo 使用方法:
echo   java -jar photowatermark.jar 图片路径 [选项]
echo ========================================

pause