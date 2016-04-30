rm -rf %HOME%\vimfiles
rm -rf %HOME%\_vimrc
rm -rf %HOME%\.gitconfig
cp -R .\_vim %HOME%\
cp .\_vimrc %HOME%\
cp .\.gitconfig_template %HOME%\.gitconfig

Powershell.exe -executionpolicy remotesigned -File  %HOME%\vimsetup\ps.ps1

git config credential.helper store
