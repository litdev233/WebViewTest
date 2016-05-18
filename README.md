## 新手练习 ##
主要是联系WebView控件的使用，除此还顺便练习了DrawerLayout的使用

分两块内容：

 - 打开Web地址
	 - 自定义添加Http报文头字段
	 - 修改访问的User-Agent
	 - 实现WebChromeClient接口，监听目标URL的Title、载入进度以及处理网页的Alert
	 - 实现WebViewClient接口，修改在网页中的跳转方式、开始载入、完成载入以及载入失败的处理逻辑
	 - 处理back键事件(返回上一次请求的URL或finish Activity)
 - 打开本地Html内容
	- JS调用JAVA函数
	- 处理JS的Alert事件

项目采取**Android Studio2.1.1**开发,移除系统模板生成的无用代码，干净、清爽