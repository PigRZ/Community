community-back为后端项目

community-front为前端项目

# 本地运行流程

本地部署在8081端口

### 进入前端项目

```
cd community-front

npm install

npm run build
```

将dist文件夹的文件复制到community-back\src\main\resources\static文件夹下

### 创建数据库

创建名为community的数据库，并执行sql文件夹下的community.sql文件。

### 后端

在community-back\src\main\resources\文件夹下的application.yaml文件修改数据库账号密码

打包

```
mvn clean

mvn package
```

把community-back\target下的community-back-0.0.1-SNAPSHOT.jar文件复制到上级目录community-back（上级目录有个static文件夹，此步骤也可不执行，直接运行jar包，但是当maven重新打包时，target目录下的这个static文件夹会被重置，建议执行此步骤）

运行jar包

```
java -jar community-back-0.0.1-SNAPSHOT.jar
```

访问loaclhost:8081即可

# 远程服务器部署/本地服务器修改端口注意事项

application.yaml文件中修改端口

community-front\src下的main.js修改Vue.prototype.serverHost=  "localhost:8081/"

修改community-front\src下Post.vue、GoodList.vue和AccountMenu.vue文件中的localhost:8081为部署地址和端口