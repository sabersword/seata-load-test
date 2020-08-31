# Docker image for multi stage build
# VERSION 0.1
# Author: sabersword

### 第一阶段，用maven镜像进行编译
FROM maven:3.6.1 AS compile_stage

####################定义环境变量 start####################
#定义工作目录
ENV WORK_PATH /root/seata-load-test/
####################定义环境变量 start####################

#作者
MAINTAINER sabersword <godigmh@gmail.com>

#将源码复制到当前目录
COPY ./ $WORK_PATH

#如果前面您已经准备好了repository目录，就可以用来替换镜像中的repository目录了，先删除镜像中已有的repository
# RUN rm -rf /root/.m2/repository

#将准备好的repository文件夹复制进来，这样相当于镜像环境中已经有了java工程所需的jar，可以避免去maven中央仓库下载
# COPY ./repository /root/.m2/repository

COPY settings.xml /usr/share/maven/ref/
#编译构建
RUN cd $WORK_PATH && mvn -s /usr/share/maven/ref/settings.xml clean package

### 第二阶段，用第一阶段的jar和jre镜像合成一个小体积的镜像
FROM java:8-jre-alpine

####################定义环境变量 start####################
#定义工程名称，也是源文件的文件夹名称
ENV PROJECT_NAME order
#定义工程版本
ENV PROJECT_VERSION 0.1-SNAPSHOT
#定义工作目录
ENV WORK_PATH /root/seata-load-test/
####################定义环境变量 start####################

#安全起见不用root账号，新建用户admin
#RUN adduser -Dh /home/admin admin

#工作目录是/app
WORKDIR /root

#从名为compile_stage的stage复制构建结果到工作目录
COPY --from=compile_stage $WORK_PATH/${PROJECT_NAME}/target/${PROJECT_NAME}-${PROJECT_VERSION}.jar .

#启动应用
CMD ["sh", "-c", "java -server -Xmx2048m -Xms2048m -Xmn1024m -Xss512k -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -XX:SurvivorRatio=10 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:MaxDirectMemorySize=1024m -XX:-OmitStackTraceInFastThrow -XX:-UseAdaptiveSizePolicy -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/log/heapdump.hprof -XX:+DisableExplicitGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 -jar /root/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]
