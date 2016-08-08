rmdir %HOME%\vimfiles /S /Q
rmdir %HOME%\_vimrc /S /Q
rmdir %HOME%\.gitconfig /Q
xcopy .\vimfiles %HOME%\vimfiles /E /H /I /Y
xcopy .\_vimrc %HOME%\ /E /H /I /Y
copy .\.gitconfig_template %HOME%\.gitconfig /Y

Powershell.exe -executionpolicy remotesigned -File  %HOME%\vimsetup\ps.ps1

git config credential.helper store
