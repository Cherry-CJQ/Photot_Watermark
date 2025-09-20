@echo off
echo ========================================
echo 修复Maven配置脚本
echo ========================================

REM 设置临时Maven仓库目录
set TEMP_REPO=%CD%\.m2_temp
set MAVEN_HOME=D:\app_rosd\0HHU_program\apache-maven-3.9.3

echo.
echo 1. 创建临时Maven仓库目录...
if not exist %TEMP_REPO% mkdir %TEMP_REPO%

echo.
echo 2. 备份原始Maven配置...
if exist "%MAVEN_HOME%\conf\settings.xml" (
    copy "%MAVEN_HOME%\conf\settings.xml" "%MAVEN_HOME%\conf\settings.xml.backup"
)

echo.
echo 3. 创建新的Maven配置...
(
echo ^<?xml version="1.0" encoding="UTF-8"?^>
echo ^<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"^>
echo           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"^>
echo           xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0^>
echo           http://maven.apache.org/xsd/settings-1.0.0.xsd"^>
echo     ^<localRepository^>%TEMP_REPO%^</localRepository^>
echo     ^<interactiveMode^>true^</interactiveMode^>
echo     ^<offline^>false^</offline^>
echo     ^<mirrors^>
echo         ^<mirror^>
echo             ^<id^>aliyunmaven^</id^>
echo             ^<mirrorOf^>*^</mirrorOf^>
echo             ^<name^>阿里云公共仓库^</name^>
echo             ^<url^>https://maven.aliyun.com/repository/public^</url^>
echo         ^</mirror^>
echo     ^</mirrors^>
echo ^</settings^>
) > "%MAVEN_HOME%\conf\settings.xml"

echo.
echo 4. 设置环境变量...
set MAVEN_OPTS=-Dmaven.repo.local=%TEMP_REPO%

echo.
echo 5. 尝试编译项目...
call "%MAVEN_HOME%\bin\mvn.cmd" clean compile

if errorlevel 1 (
    echo.
    echo 编译失败! 尝试备用方案...
    goto :manual_download
) else (
    echo.
    echo 编译成功! 继续打包...
    call "%MAVEN_HOME%\bin\mvn.cmd" package
    goto :success
)

:manual_download
echo.
echo 6. 备用方案: 手动下载依赖...
if not exist lib mkdir lib
echo 请手动下载以下依赖:
echo https://repo1.maven.org/maven2/org/apache/commons/commons-imaging/1.0-alpha3/commons-imaging-1.0-alpha3.jar
echo 下载后保存到 lib/ 目录

:success
echo.
echo ========================================
echo 修复完成!
echo 临时Maven仓库: %TEMP_REPO%
echo 
echo 后续使用:
echo   "%MAVEN_HOME%\bin\mvn.cmd" clean compile
echo   "%MAVEN_HOME%\bin\mvn.cmd" package
echo ========================================

pause