# JavGo-Lion 在线教育

## 1.运行手册

> TIP：以下均以 Mac 环境为例

### 1.1 基础环境搭建

首先，我们需要安装 Homebrew，如果你已经安装了 Homebrew，可以跳过这一步。在终端中输入以下命令来安装 Homebrew：

```shell
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
输入以下命令来更新 Homebrew 到最新版本：

```shell
brew update
```

#### 1.1.1 安装 JDK

使用 Homebrew 来安装 JDK 1.8。在终端中输入以下命令：

```shell
brew tap AdoptOpenJDK/openjdk
brew install --cask adoptopenjdk8
```

安装完成后，我们可以使用以下命令来检查 JDK 是否安装成功：

```shell
java -version
```

如果安装成功，会显示以下信息：

```shell
openjdk version "1.8.0_292"
OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_292-b10)
OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.292-b10, mixed mode)
```

#### 1.1.2 安装 Maven

访问 Maven 官网，下载 Maven 的二进制包，下载完成后，解压到指定目录，然后配置环境变量。

下载地址：https://maven.apache.org/download.cgi

配置环境变量，打开终端，输入以下命令：

> TIP：如果是 Mac Apple Silicon 版本，可以在 .zshrc 文件中配置环境变量。

```shell
vim ~/.bash_profile
```

在打开的文件中，输入以下内容：

```shell
export M2_HOME=/Users/xxx/xxx/apache-maven-3.8.1
export PATH=$PATH:$M2_HOME/bin
```

保存后，输入以下命令使配置生效：

```shell
source ~/.bash_profile
```

输入以下命令来检查 Maven 是否安装成功：

```shell
mvn -v
```

如果安装成功，会显示以下信息：

```shell
Apache Maven 3.8.1 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /Users/xxx/xxx/apache-maven-3.8.1
Java version: 1.8.0_292, vendor: AdoptOpenJDK, runtime: /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre
Default locale: zh_CN, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
```

#### 1.1.3 安装 MySQL

使用 Homebrew 来安装 MySQL。在终端中输入以下命令：

```shell
brew install mysql
```

安装完成后，我们可以使用以下命令来检查 MySQL 是否安装成功：

```shell
mysql --version
```

如果安装成功，会显示以下信息：

```shell
mysql  Ver 8.0.25 for osx10.16 on x86_64 (Homebrew)
```

安装完成后，我们需要启动 MySQL 服务，输入以下命令：

```shell
brew services start mysql
```

启动完成后，我们可以使用以下命令来检查 MySQL 服务是否启动成功：

```shell
brew services list
```

如果启动成功，会显示以下信息：

```shell
Name    Status  User Plist
mysql   started xxx  /Users/xxx/Library/LaunchAgents/homebrew.mxcl.mysql.plist
```

启动成功后，我们需要设置 MySQL 的 root 用户的密码，输入以下命令：

```shell
# 进入 MySQL (密码为空)
mysql -uroot -p

# 设置密码
ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';
```

设置完成后，我们可以使用以下命令来检查 MySQL 的 root 用户密码是否设置成功：

```shell
# 退出 MySQL
exit

# 进入 MySQL (密码为 123456)
mysql -uroot -p
```

如果设置成功，会显示以下信息：

```shell
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 8
Server version: 8.0.25 Homebrew
```

#### 1.1.4 安装 Redis

使用 Homebrew 来安装 Redis。在终端中输入以下命令：

```shell
brew install redis
```

安装完成后，我们可以使用以下命令来检查 Redis 是否安装成功：

```shell
redis-server --version
```

如果安装成功，会显示以下信息：

```shell
Redis server v=6.2.4 sha=00000000:0 malloc=libc bits=64 build=6c7c2c9c5c6c5c6c
```

安装完成后，我们需要启动 Redis 服务，输入以下命令：

```shell
brew services start redis
```

启动完成后，我们可以使用以下命令来检查 Redis 服务是否启动成功：

```shell
brew services list
```

如果启动成功，会显示以下信息：

```shell
Name    Status  User Plist
redis   started xxx  /Users/xxx/Library/LaunchAgents/homebrew.mxcl.redis.plist
```

#### 1.1.5 安装 Nginx

使用 Homebrew 来安装 Nginx。在终端中输入以下命令：

```shell
brew install nginx
```

安装完成后，我们可以使用以下命令来检查 Nginx 是否安装成功：

```shell
nginx -v
```

如果安装成功，会显示以下信息：

```shell
nginx version: nginx/1.21.0
```

安装完成后，我们需要启动 Nginx 服务，输入以下命令：

```shell
brew services start nginx
```

启动完成后，我们可以使用以下命令来检查 Nginx 服务是否启动成功：

```shell
brew services list
```

如果启动成功，会显示以下信息：

```shell
Name    Status  User Plist
nginx   started xxx  /Users/xxx/Library/LaunchAgents/homebrew.mxcl.nginx.plist
```

#### 1.1.6 安装 Elasticsearch

#### 1.1.7 安装 Kibana

#### 1.1.8 安装 Logstash

#### 1.1.10 安装 RabbitMQ

使用 Homebrew 来安装 RabbitMQ。在终端中输入以下命令：

```shell
brew install rabbitmq
```

安装完成后，我们可以使用以下命令来检查 RabbitMQ 是否安装成功：

```shell
rabbitmqctl status
```

如果安装成功，会显示以下信息：

```shell
Status of node rabbit@xxx ...
[{pid,xxx},
 {running_applications,
     [{rabbitmq_management,"RabbitMQ Management Console","3.8.17"},
      {rabbitmq_web_dispatch,"RabbitMQ Web Dispatcher","3.8.17"},
      {rabbitmq_management_agent,"RabbitMQ Management Agent","3.8.17"},
      {rabbitmq_management_visualiser,
          "RabbitMQ Management Visualiser","3.8.17"},
      {rabbitmq_web_stomp,"RabbitMQ STOMP Web Stomp","3.8.17"},
      {rabbitmq_web_stomp_examples,
          "RabbitMQ STOMP Web Stomp examples","3.8.17"},
      {rabbitmq_web_mqtt,"RabbitMQ MQTT over WebSockets","3.8.17"},
      {rabbitmq_mqtt,"RabbitMQ MQTT Adapter","3.8.17"},
      {rabbitmq_amqp1_0,"AMQP 1.0 support plugin","3.8.17"},
      {rabbitmq_auth_backend_ldap,
          "RabbitMQ LDAP Authentication Backend","3.8.17"},
      {rabbitmq_auth_mechanism_ssl,
          "RabbitMQ SSL Authentication Mechanism","3.8.17"},
      {rabbitmq_auth_mechanism_oauth2,
          "RabbitMQ OAuth 2.0 Authentication Mechanism","3.8.17"},
      {rabbitmq_auth_mechanism_http,
          "RabbitMQ HTTP Authentication Mechanism","3.8.17"},
      {rabbitmq_auth_backend_cache,
          "Caching RabbitMQ Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_http,
          "HTTP-based RabbitMQ Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_ldap,
          "RabbitMQ LDAP Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_cache,
          "Caching RabbitMQ Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_http,
          "HTTP-based RabbitMQ Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_ldap,
          "RabbitMQ LDAP Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_cache,
          "Caching RabbitMQ Authentication Backend","3.8.17"},
      {rabbitmq_auth_backend_http,
          "HTTP-based RabbitMQ Authentication Backend
```

安装完成后，我们需要启动 RabbitMQ 服务，输入以下命令：

```shell
brew services start rabbitmq
```

#### 1.1.13 安装 Kafka

#### 1.1.14 安装 RocketMQ

#### 1.1.15 安装 Jenkins

#### 1.1.16 安装 Docker

#### 1.1.23 安装 Sentinel

#### 1.1.24 安装 Node.js

> TIP：本项目以 Node.js 14.17.0 版本为例

使用 Homebrew 来安装 Node.js。在终端中输入以下命令：

```shell
brew install node
```

安装完成后，我们可以使用以下命令来检查 Node.js 是否安装成功：

```shell
node -v
```

如果安装成功，会显示以下信息：

```shell
v14.17.0
```

#### 1.1.24 安装 NVM

使用 Homebrew 来安装 NVM。在终端中输入以下命令：

```shell
brew install nvm
```

安装完成后，我们可以使用以下命令来检查 NVM 是否安装成功：

```shell
nvm --version
```

如果安装成功，会显示以下信息：

```shell
0.38.0
```

安装完成后，我们需要配置 NVM，输入以下命令：

> TIP：如果是 Mac Apple Silicon 版本，可以在 .zshrc 文件中配置环境变量。

```shell
vim ~/.bash_profile
```

在打开的文件中，输入以下内容：

```shell
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
```

保存后，输入以下命令使配置生效：

```shell
source ~/.bash_profile
```

安装完成后，我们可以使用以下命令来检查 NVM 是否配置成功：

```shell
nvm --version
```

如果配置成功，会显示以下信息：

```shell
0.38.0
```

安装完成后，我们可以使用 NVM 方便的管理 Node.js 的版本，输入以下命令：

```shell
# 查看 Node.js 的版本
nvm ls

# 安装 Node.js 的版本
nvm install v14.17.0

# 切换 Node.js 的版本
nvm use v14.17.0
```

#### 1.1.24 安装 Nacos

### 1.2 项目环境搭建

#### 1.2.1 安装 IDEA

#### 1.2.2 安装 Git

使用 Homebrew 来安装 Git。在终端中输入以下命令：

```shell
brew install git
```

安装完成后，我们可以使用以下命令来检查 Git 是否安装成功：

```shell
git --version
```

如果安装成功，会显示以下信息：

```shell
git version 2.32.0
```

如果 git 操作远程库失败，可以尝试执行以下命令取消代理：

```shell
git config --global http.proxy http://127.0.0.1:1080
git config --global https.proxy http://127.0.0.1:1080

git config --global --unset http.proxy
git config --global --unset https.proxy
```

#### 1.2.3 安装 IDEA 插件

### 1.3 项目运行

#### 1.3.1 导入项目

#### 1.3.2 启动项目

#### 1.3.3 访问项目

### 1.4 项目部署

#### 1.4.1 项目打包

#### 1.4.2 项目部署

#### 1.4.3 项目访问

## 2.技术栈

### 2.1 前端技术栈

### 2.2 后端技术栈

## 3.系统模块

### 3.1 用户模块

### 3.2 课程模块

### 3.3 订单模块

### 3.4 支付模块

### 3.5 短信模块

### 3.6 邮件模块

### 3.7 网关模块

### 3.8 监控模块

### 3.9 日志模块

### 3.10 搜索模块

### 3.11 消息模块

### 3.12 配置模块

### 3.13 任务模块

### 3.14 定时任务模块

### 3.15 代码生成模块

## 4.技术架构

### 4.1 架构图

### 4.2 架构说明

## 5.数据库设计

### 5.1 数据库表

### 5.2 数据库设计

## 6.项目结构

### 6.1 项目结构图

### 6.2 项目结构说明

## 7.项目配置

### 7.1 项目配置文件

### 7.2 项目配置说明


