@ECHO OFF&PUSHD %~DP0 &TITLE Ϊ�˲���
mode con cols=36 linnes =100
color 2C
:menu
cls
echo.
echo �������Ӧ����ָ��
echo ========================
echo.
echo ����1�����ܻ������ƽ�
echo.
echo ����2�����ܻ����»�ԭ
echo.
echo ========================

set /p user_input=������ָ�
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
echo "���Կ��ֵ���ˣ�ˣ�"
)
pause