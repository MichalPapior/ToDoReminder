\
    @echo off
    set DIR=%~dp0
    set WRAPPER_JAR=%DIR%gradle\wrapper\gradle-wrapper.jar

    if not exist "%WRAPPER_JAR%" (
      echo Missing gradle-wrapper.jar (Android Studio zwykle go nie wymaga do importu, ale CLI tak).
      echo Zaimportuj projekt w Android Studio (File ^> Open).
      exit /b 1
    )

    java -jar "%WRAPPER_JAR%" %*
