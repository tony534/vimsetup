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
