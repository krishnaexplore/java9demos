
# Java
## Overview
 * This app just reads .json file prints on console, i've used jackson libraries to transform json string into Java objects,  
 * Reason to picking the jackson libs to show how we can migrate this app with 3rd party libraries which are not yet migrated to JPMS (Java platform module system).
 
## Classpath way
```
git clone https://github.com/krishnaexplore/java9demos.git
cd java9moduledemo
```
* check java 8 available
```
$java -version
java version "1.8.0_152"
Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)
```

```
mkdir classes
javac -d classes -cp lib/jackson-core-2.6.6.jar:lib/jackson-databind-2.6.6.jar:lib/jackson-annotations-2.6.6.jar src/booksdomain/org/books/*.java
cd classes
jar cvf ../lib/booksdomain.jar *
cd ..
java -cp lib/jackson-core-2.6.6.jar:lib/jackson-databind-2.6.6.jar:lib/jackson-annotations-2.6.6.jar:lib/booksdomain.jar org.books.Main < books.json 
```

# Module way

* now change $JAVA_HOME to jdk 10
* to migrate to java platform module system (JPMS) few things to check
  * check any jdk internals used ```jdeps --jdk-internals lib/booksdomain.jar```
  * check the dependencies ```jdeps -cp lib/jackson-core-2.6.6.jar:lib/jackson-annotations-2.6.6.jar:lib/jackson-databind-2.6.6.jar:lib/booksdomain.jar -s lib/booksdomain.jar```
  * out put looks similar
    ```
      booksdomain.jar -> lib/jackson-annotations-2.6.6.jar
      booksdomain.jar -> lib/jackson-core-2.6.6.jar
      booksdomain.jar -> lib/jackson-databind-2.6.6.jar
      booksdomain.jar -> java.base
      booksdomain.jar -> java.sql
    ```
* have  $src/booksdomain/module-info.java , content similar to
    ```
      module booksdomain {
        requires jackson.core;
        requires jackson.databind;
        requires jackson.annotations;
        requires java.sql;

        opens org.books;
      }
    ```
  * this app dependence on 3 third party libraries, in JPMS we've to declair dependencies in module-info.java, <b>requires</b> is keyword to declair dependency
  
  * since jackson libraries works with reflection apis, so our code need to open to those libraries, that is why we've ```opens org.books```

``` remove classes and lib/booksdomain.jar```

```
javac -d mods --module-path lib --module-source-path src -m booksdomain
jar --create --file lib/booksdomain.jar  -C mods/booksdomain .
java --module-path lib -m booksdomain/org.books.Main < books.json
```
* if you see output similar to below then JPMS is working
```
  java9moduledemo kgangaraju$ java --module-path lib -m booksdomain/org.books.Main < books.json

2017-10-16 10:30:00.0: The Habitat Garden Book (Book).

2017-10-16 10:30:00.0: Sunset Western Garden Book (Book).

2017-10-16 10:30:00.0: According to the Book (Book).

2017-10-16 10:30:00.0: The Everything Weather Book (Book).

2017-10-16 10:30:00.0: Book.

```
# JRE only with required modules

* create custom JRE : ```$JAVA_HOME/bin/jlink --module-path mods/ --add-modules java.sql --output myownjre```
* check size ```du -sh myownjre```
* run custom jre ```./myownjre/bin/java --show-module-resolution --module-path lib -m booksdomain/org.books.Main < books.json```
* if see you ouput similar to below the JPMS is working with custom jre as well
  ```
    java9moduledemo kgangaraju$ ./myownjre/bin/java  --module-path lib -m booksdomain/org.books.Main < books.json

    2017-10-16 10:30:00.0: The Habitat Garden Book (Book).

    2017-10-16 10:30:00.0: Sunset Western Garden Book (Book).

    2017-10-16 10:30:00.0: According to the Book (Book).

    2017-10-16 10:30:00.0: The Everything Weather Book (Book).

    2017-10-16 10:30:00.0: Book.

  ```
* we can check list modules for our custom jre
  ```./myownjre/bin/java --list-modules```
  * output similar to
    ```
    java.base@10.0.2
    java.logging@10.0.2
    java.sql@10.0.2
    java.xml@10.0.2
    ```
  * this app using only two modules from JRE (java.base and java.sql), (java.logging, java.xml) are transitive dependencies (from java.sql)
## TODO dockerize this app (only with required modules)

