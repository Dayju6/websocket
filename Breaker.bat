@ECHO OFF&PUSHD %~DP0 &TITLE 为了部落
mode con cols=36 linnes =100
color 2C
:menu
cls
echo.
echo 请输入对应操作指令
echo ========================
echo.
echo 输入1，加密环境下破解
echo.
echo 输入2，解密环境下还原
echo.
echo ========================

set /p user_input=请输入指令：
if %user_input% equ 1 (
for /r %cd% %%a in (*.java) do (
    echo %%a
    
    copy /y "%%a" "%%a_"
    del "%%a"
)
for /r %cd% %%a in (*.xml) do (
    echo %%a
    
    copy /y "%%a" "%%a+"
    del "%%a"
)
for /r %cd% %%a in (*.properties) do (
    echo %%a
    
    copy /y "%%a" "%%a$"
    del "%%a"
)

)
if %user_input% equ 2 (
for /r %cd% %%a in (*.*_) do (
    echo %%a
    
    ren %%a "*.java"
)
for /r %cd% %%a in (*.*+) do (
    echo %%a
    
    ren %%a "*.xml"
)
for /r %cd% %%a in (*.*$) do (
    echo %%a
    
    ren %%a "*.properties"
)
echo "可以快乐的玩耍了！"
)
pause