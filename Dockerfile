FROM openjdk:19
EXPOSE 2207

ADD /target/library-management.jar library-management.jar 
ENTRYPOINT [ "java","-jar","/library-management.jar" ]
