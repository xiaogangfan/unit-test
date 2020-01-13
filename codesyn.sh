rm -rf *
cp -rf ~/code_git/private/unit-test/* .
git pull --rebase
git add .
./git.sh 'code syn'