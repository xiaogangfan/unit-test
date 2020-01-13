echo "请确保已经安装了Gradle"
gradle assemble
cp build/distributions/* .
echo "插件生成成功：unit.test-0.0.2.zip"