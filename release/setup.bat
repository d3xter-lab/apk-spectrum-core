@echo off
cd
set APP_PATH=C:\Program Files\APKScanner
set APP_FILE=ApkScanner.exe
set SRC_PATH=.

if not exist "%SRC_PATH%\%APP_FILE%" (
    set SRC_PATH=C:
    if not exist "%SRC_PATH%\%APP_FILE%" (
        echo Fail : No such %APP_FILE% file
        echo Info : Please copy the APKScanner folder to the C:\ path.
        echo Info : And run setup.bat as administrator.
        goto exit
    )
)

rem --- Java 버전확인 ---
java -version > javaver.txt 2>&1
set /p java_ver=<javaver.txt
del javaver.txt

if "%java_ver%" == "%java_ver:java version =%" (
    goto nosuch_java
)
set java_ver=%java_ver:java version =%
set java_ver=%java_ver:"=%
set java_ver=%java_ver:~,3%

rem Java 1.7버전 이상필요
if not "%java_ver%" GEQ "1.7" (
    echo Need JDK7...
    echo current version : %java_ver%
    goto nosuch_java
)

rmdir /s /q "%APP_PATH%"

rem --- 폴더 생성 ---
if not exist "%APP_PATH%" (
    echo Create folder : %APP_PATH%
    mkdir "%APP_PATH%"
    rem mkdir "%APP_PATH%\tool"
)
if not exist "%APP_PATH%\tool" (
     echo Create folder : %APP_PATH%\tool
     mkdir "%APP_PATH%\tool"
)
if not exist "%APP_PATH%\data" (
     echo Create folder : %APP_PATH%\data
     mkdir "%APP_PATH%\data"
)
if not exist "%APP_PATH%\lib" (
     echo Create folder : %APP_PATH%\lib
     mkdir "%APP_PATH%\lib"
)
if not exist "%APP_PATH%" (
    echo Fail : Not create the folder : %APP_PATH%
    goto exit
)

rem --- 파일 복사 ---
copy /Y %SRC_PATH%\ApkScanner.exe "%APP_PATH%"
copy /Y %SRC_PATH%\APKInfoDlg.jar "%APP_PATH%"
copy /Y %SRC_PATH%\lib\apktool.jar "%APP_PATH%\lib"
copy /Y %SRC_PATH%\lib\commons-cli-1.3.1.jar "%APP_PATH%\lib"
copy /Y %SRC_PATH%\lib\json-simple-1.1.1.jar "%APP_PATH%\lib"
copy /Y %SRC_PATH%\tool\adb.exe "%APP_PATH%\tool"
copy /Y %SRC_PATH%\tool\AdbWinApi.dll "%APP_PATH%\tool"
copy /Y %SRC_PATH%\tool\AdbWinUsbApi.dll "%APP_PATH%\tool"
copy /Y %SRC_PATH%\tool\aapt.exe "%APP_PATH%\tool"
rem copy /Y %SRC_PATH%\data\strings-ko.xml "%APP_PATH%\data"
rem copy /Y .\tool\* "%APP_PATH%\tool\"


rem --- 연결프로그램 지정 ---
ftype vnd.android.package-archive="%APP_PATH%\%APP_FILE%" "%%1"
vnd.android.package-archive.reg
assoc .apk=vnd.android.package-archive
assoc .ppk=vnd.android.package-archive
rem ftype vnd.android.package-archive=javaw -jar "-Dfile.encoding=utf-8" "%APP_PATH%\%APP_FILE%" %%1 %%*
rem reg add "HKCR\vnd.android.package-archive\DefaultIcon" /t REG_SZ /d "%APP_PATH%\%APP_FILE%,1" /f


rem attrib -h %USERPROFILE%\AppData\Local\IconCache.db
rem del %USERPROFILE%\AppData\Local\IconCache.db

echo Complete

goto exit

:nosuch_java
set java_ver=
echo Please retry after setup JDK7..
echo You can download the JDK7 from http://www.oracle.com/technetwork/java/javase/downloads/index.html
goto exit

:exit
pause