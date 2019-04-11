# J2E-WHL企业级快速开发平台

## 引言

J2E-WHL 是一个 Java EE 企业级快速开发平台，基于经典技术组合（Spring Boot、Spring MVC、MyBatis、Bootstrap、Ace admin、Quartz、Activity），让初学者能够更快的入门并投入到团队开发中去；平台遵循 **MVC架构、模块化、微服务化、前后端分离** 设计，支持集群、分布式。

J2E-WHL 快速开发平台的主要目的是能够让初级的研发人员快速的开发出复杂的业务功能，让开发者专注于业务，其余由平台来封装技术细节，降低技术难度，从而节省人力成本，缩短项目周期，提高软件安全质量。

## 技术选型

* 主框架：Spring Boot 1.5.10、Spring Framework 4.3、Apache Shiro 1.4、Redis
* 持久层：Apache MyBatis 3.4、Alibaba Druid 1.1
* 视图层：Spring MVC 4.3、Bootstrap 3.3、Ace Admin
* 前端组件：jQuery 2.1、Bootstrap-Table 1.11、layer 3.1、zTree 3.5、jquery-validation
* 工具组件：Apache Commons、Logback 1.1、Jackson 2.8、POI 3.17、Quartz 2.2、Activity

## 模块说明

* **jee-core** 系统运行必须的通用配置及工具
 1. `anotation`：平台必须的自定义注解（如登录验证注解 `AuthorPermit`）
 2. `cache`：缓存配置
 3. `config`：通用的配置信息（Druid数据源、操作日志、Swagger API）
 4. `constant`：常量
 5. `exception`：自定义异常及全局异常处理类
 6. `generatorcode`：代码一键生成
 7. `init`：应用启动的初始化配置
 8. `page`：自定义分页类
 9. `util`:自定义工具类（Excel导入导出、Word文档导入导出、密码加密解密、Jedis操作等）
 10. `web`：包含基本CRUD的Entity、Dao、Service、Controller；跨域访问过滤器；MyBatis自定义分页插件；Spring MVC登录验证插件

---

* **jee-system** 一个应用最根本的用户、角色、字典、对用户、角色授权等系统功能的管理
 1. /java/`com.whli.jee.system` 包含用户、角色、系统日志等操作类
 2. /resources/mappers MyBatis ORM配置文件

---

* **jee-job** 集成Quatrz，实时配置定时任务、查看定时任务日志
 1. 如需扩展自定义任务，只需引用该模块并继承 `BaseAbstractJob.java` 抽象类、重写 `processJob` 方法

---
* **jee-oa** 集成Activity，实现在线流程编辑、布署流程、审批流程、查看待办、已办任务

### 演示地址
<http://47.102.119.205/page/login.html> 账号 admin 密码 123456

### 本地运行

1. 环境准备：`JDK 1.8`、`Maven 3.3`、`MySQL 5.7`
2. 下载源码：<https://github.com/whli745/JEEWHL.git>
3. 导入IDE Maven项目
4. 打开文件：/jee-boot/jee-web`/src/main/resources/application-dev.yml` 配置JDBC、Redis连接
5. 新建数据库jee(字符串UTF-8)，运行/docs/jee.sql脚本文件
6. 在IDE中运行`ApplicationStart.java` main方法
7. 使用浏览器打开/jee-ui`/page/login.html`  账号 admin 密码 123456

## 授权协议声明

1. 已开源的代码，授权协议采用 AGPL v3 + Apache Licence v2 进行发行。
2. 您可以免费使用、修改和衍生代码，但不允许修改后和衍生的代码做为闭源软件发布。
3. 修改后和衍生的代码必须也按照AGPL协议进行流通，对修改后和衍生的代码必须向社会公开。
4. 如果您修改了代码，需要在被修改的文件中进行说明，并遵守代码格式规范，帮助他人更好的理解您的用意。
5. 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议、版权声明和其他原作者规定需要包含的说明（请尊重原作者的著作权，不要删除或修改文件中的`@author`信息）。
6. 您可以应用于商业软件，但必须遵循以上条款原则。
