# srb-project
hfb:模拟付费宝
srb：后台程序
srb-admin：尚荣宝后台管理程序
srb-site：前端网站

重置、体现、投资都有

还款的页面没有，但后台代码基本很清晰

流程：
用户绑定提供基本信息到付费宝绑定账户信息，然后绑定账户协议号

后面所有与付费宝的操作都是通过 改bindcode操作的

充值：
生成表单数据-》前往付费宝充值-》付费宝异步通知平台，并且同步返回平台