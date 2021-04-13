echo "请确保已经安装了Gradle"
gradle assemble
rm -rf unit.test-*
cp build/distributions/* .
echo "插件生成成功，在项目根目录下"