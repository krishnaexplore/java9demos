
# Java

## Classpath way
```
javac -d classes -cp lib/jackson-core-2.6.6.jar:lib/jackson-databind-2.6.6.jar:lib/jackson-annotations-2.6.6.jar src/booksdomain/org/books/*.java
jar --create --file lib/booksdomain.jar -C classes .
jar --list --file lib/booksdomain.jar
java -cp lib/jackson-annotations-2.6.6.jar:lib/jackson-core-2.6.6.jar:lib/jackson-databind-2.6.6.jar:lib/booksdomain.jar org.books.Main <books.json
```

# Module way

* check any jdk internals used ```jdeps --jdk-internals booksdomain.jar```
* check the dependencies ```jdeps -s lib/booksdomain.jar```
* check the dependencies ```jdeps -cp lib/jackson-annotations-2.6.6.jar:lib/jackson-core-2.6.6.jar:lib/jackson-databdatabind-2.6.6.jar:lib/booksdomain.jar -s lib/booksdomain.jar```

```
  module booksdomain {
  requires jackson.core;
  requires jackson.databind;
  requires jackson.annotations;
  requires java.sql;

  opens org.books;
}
```

```
javac -d mods --module-path lib --module-source-path src -m booksdomain
jar --create --file lib/booksdomain.jar  -C mods/booksdomain .
java --module-path lib -m booksdomain/org.books.Main < books.json
```
# Tiny JRE (Our Own JRE)

```$JAVA_HOME/bin/jlink --module-path mymodules/ --add-modules java.sql --output myownjre```

* check size ```du -sh myjre```
* run custom jre ```./myjre/bin/java --show-module-resolution --module-path lib -m booksdomain/org.books.Main < books.json```

