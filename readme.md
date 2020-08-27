1. 确认好Nacos的IP,配置到应用和Seata的配置文件里
    - 应用
        - spring.cloud.nacos.discovery.server-addr=${nacos-ip}:8848
        - seata.registry.nacos.server-addr=${nacos-ip}
    - Seata
        - registry.nacos.serverAddr=${nacos-ip}

2. 改好数据库的配置

3. 已经将项目上传到阿里云镜像服务管理,自行构建可以使用下面命令构建
    - docker build -f account.dockerfile -t org/ypq/account:0.1-SNAPSHOT .
    - docker build -f business.dockerfile -t org/ypq/business:0.1-SNAPSHOT .
    - docker build -f order.dockerfile -t org/ypq/order:0.1-SNAPSHOT .
    - docker build -f storage.dockerfile -t org/ypq/storage:0.1-SNAPSHOT .
    - cd seata && docker build -t org/ypq/seata:0.1-SNAPSHOT .

4. 启动nacos
    - docker run --name nacos-standalone -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:latest

5. 启动seata,并且选好注册到注册中心的IP
    - docker run -d --name seata-ypq -p 8091:8091 -e SEATA_IP=${seata-ip} org/ypq/storage:0.1-SNAPSHOT 
    
6. 启动应用
    - docker run -d -p 9773:9773 org/ypq/account:0.1-SNAPSHOT
    - docker run -d -p 9770:9770 org/ypq/business:0.1-SNAPSHOT
    - docker run -d -p 9771:9771 org/ypq/storage:0.1-SNAPSHOT
    - docker run -d -p 9772:9772 org/ypq/order:0.1-SNAPSHOT
    
    

