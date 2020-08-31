FROM seataio/seata-server:1.3.0

COPY ./file.conf /seata-server/resources
COPY ./registry.conf /seata-server/resources

CMD ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -server -Xmx2048m -Xms2048m -Xmn1024m -Xss512k -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -XX:SurvivorRatio=10 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:MaxDirectMemorySize=1024m -XX:-OmitStackTraceInFastThrow -XX:-UseAdaptiveSizePolicy -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/log/seata_heapdump.hprof -XX:+DisableExplicitGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 -Xloggc:/var/log/seata_gc.log -verbose:gc -Dio.netty.leakDetectionLevel=advanced -cp /seata-server/resources:/seata-server/classes:/seata-server/libs/* io.seata.server.Server"]