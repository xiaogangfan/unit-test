## Upload by Page
https://plugins.jetbrains.com/plugin/add#intellij


### Plugin upload by api
参考：https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html
例子：curl -i --header "Authorization: Bearer perm:eGlhb2dhbmdmYW4=.OTItMTY4OQ==.Yalrcn1Uj1AWok7H5sOSxDRiPvcagj" -F pluginId=org.xiaogang.unit.test -F file=@/Users/xiaogangfan/code_git/auto-unit-test/build/distributions/unit.test-0.0.2.zip  https://plugins.jetbrains.com/plugin/uploadPlugin

### Plugin download
参考：https://plugins.jetbrains.com/docs/marketplace/plugin-update-download.html
例子：https://plugins.jetbrains.com/plugin/download?pluginId=org.xiaogang.unit.test&version=0.0.2