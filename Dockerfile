FROM openjdk:17
EXPOSE 2207

ADD target/library-management.jar . 
ENTRYPOINT [ "java","-jar","/library-management.jar" ]
