# light-registry

基于Eureka实现的服务注册中心

启动命令:
````
java -Xmx2048m -Xms2048m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=2048m -Xss256k -jar light-registry.jar --spring.profiles.active=node01
java -Xmx2048m -Xms2048m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=2048m -Xss256k -jar light-registry.jar --spring.profiles.active=node02
java -Xmx2048m -Xms2048m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=2048m -Xss256k -jar light-registry.jar --spring.profiles.active=node03
````