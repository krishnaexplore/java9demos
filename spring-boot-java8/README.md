
# Spring boot 2.0.4 & jdk 8

* build & run with jdk 8
```
  git clone https://github.com/krishnaexplore/java9demos.git
  cd spring-boot-java8
  ./gradlew clean build
  java -jar build/libs/*.jar
  curl "http://localhost:8080/welcome"
```


* with Java 9+ 
  * change JAVA_HOME to java 9+, better to have java 10 now 
  ```
    ./gradlew clean build
  ```
  * hopefully compiles and builds
  ```
    java -jar build/libs/*.jar
    curl "http://localhost:8080/welcome"
  ```
  * modulirize
    * change source/target compitibility with java 10 $build.gradle
      ```
        sourceCompatibility = 1.10
        targetCompatibility = 1.10
      ```
    * add module-info.java $src/main/java
    ```
        module com.example.demo{
        requires spring.boot.starter.web;
        requires spring.boot.starter.json;
        requires spring.boot.starter;
        requires spring.boot.starter.tomcat;
        requires org.hibernate.validator;
        requires spring.webmvc;
        requires spring.web;
        requires spring.boot.autoconfigure;
        requires spring.boot;
        requires spring.boot.starter.logging;
        requires java.annotation;
        requires spring.context;
        requires java.validation;
        requires spring.aop;
        requires spring.beans;
        requires spring.expression;
        requires spring.core;
        requires com.fasterxml.jackson.datatype.jdk8;
        requires com.fasterxml.jackson.datatype.jsr310;
        requires com.fasterxml.jackson.module.paramnames;
        requires com.fasterxml.jackson.databind;
        requires org.jboss.logging;
        requires com.fasterxml.classmate;
        requires org.apache.logging.log4j;
        requires spring.jcl;
        requires com.fasterxml.jackson.core;

        //Legacy jars doesn't have module-info.java or Automatic-Module-Name in $jar/META-INF/MANIFEST.MF
        requires tomcat.embed.websocket;
        requires tomcat.embed.core;
        requires logback.classic;
        requires jul.to.slf4j;
        requires jackson.annotations;
        requires logback.core;
        requires slf4j.api;
        requires snakeyaml;
        requires tomcat.embed.el;
       }

    ```
      * above dependencies are resolved manually looking to each of jar's $jar/META-INF/MANIFEST.MF, (look for Automatic-Module-Name)
   * classpath as Module in $build.gradle
    ```
      compileJava {    
        doFirst {
          options.compilerArgs = [
              '--module-path', classpath.asPath,
          ]
          classpath = files()
        } 
      }
    ```
    
    * now build and run
    ```
       ./gradlew clean build
       java -jar build/libs/*.jar
    ```
    
    
  ### TODO
  
  * next want to do dockerize the app and with <b><i>jlink</b></i> have only required modules for this app

