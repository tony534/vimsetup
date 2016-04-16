rm -rf ~/.vim
rm -rf ~/.vimrc
cp -R ./.vim ~/
cp ./.vimrc ~/
chmod 777 ~/.vim
chmod 777 ~/.vimrc

git config --global user.name "Wei Gao"
git config --global user.email "gw8310@gmail.com"
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global push.default simple
git config --global pull.rebase true
git config --global rerere.enabled true
git config --global alias.ss "status –s"
git config --global alias.lg "log –oneline –decorate --all --graph"
git config ­­--global alias.mylog "log ­­pretty=format:'%h %s [%an]' –graph"
git config --global core.editor "mvim -f"
git config --­­global merge.tool vimdiff
git config credential.helper store

