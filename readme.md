# easy-security

![](doc/logo.png)

> 官网查看详细教程： https://easy-security.aizuda.com/

easy-security 基于过滤器实现的一款配合spring快速开发的安全认证框架，思想是希望通过简单的配置，并且实现核心的方法达到认证和鉴权的目的。

easy-security 不限制存取token方式，无论是保存到服务端还是使用JWT等都可以，因为这部分是由开发者自己来定义的，只需要告诉 easy-security 该如何获取用户信息即可。

如果你使用了 easy-security 自身所带的 Req 请求封装，那么所有的接口请求均以POST方式，Req 会把认证后的用户所携带在每次请求中，当需要获取用户的时候可以通过 Req 直接获取，解耦开发者获取认证用户的

easy-security 结合了 Yapi 的使用，如果你使用 Yapi 需要在自己的项目中描述规则

### 功能列举
* 认证拦截
* 权限校验
* 用户获取
* 黑名单
* 密文传输(内置AES加密算法)

### 加入社区
![](doc/code.png) 
![](doc/code1.png)



