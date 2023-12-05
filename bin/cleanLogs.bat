chcp 65001
@echo off
echo.
echo [信息] 清空日志。
echo.

echo %~d0
cd %~dp0

cd ..
call rmdir /s /q .\logs\*

pause