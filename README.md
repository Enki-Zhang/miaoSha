
## 使用说明
2. 运行sql文件夹下的sql文件
3. 到src/main/resources下的application.properties下修改你的数据库链接用户名与密码
4. 安装redis、mysql、rabbitmq、maven等环境
5. 启动前，检查配置 application.properties 中相关redis、mysql、rabbitmq地址
6. 在数据库秒杀商品表里面设置合理的秒杀开始时间与结束时间
7. 登录地址：http://localhost:8080/login/to_login
8. 商品秒杀列表地址：http://localhost:8080/goods/to_list

## 其它说明
1. 数据库共有一千个用户左右（手机号：15200000000~15200000997 密码为：123456），为压测准备的。（使用 com.ljs.miaosha.util.UserUtil.java该类生成的，生成token做压测的方法也是在此类里面）

2. 邮箱只实现了前端格式验证，只需输入一个正确的邮箱格式即可（例如：yys@qq.com）

## 项目描述
1. 使用分布式Seesion，让多台服务器可以响应。
2. 使用redis做缓存提高访问速度和并发量，减少数据库压力。
3. 使用页面静态化，缓存页面至浏览器，前后端分离降低服务器压力。
4. 使用消息队列完成异步下单，提升用户体验，削峰和降流。
5. 安全性优化：双重md5密码校验，秒杀接口地址的隐藏，接口限流防刷，数学公式验证码。


## 学习项目非原创