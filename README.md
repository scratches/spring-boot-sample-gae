Simple Spring Boot app that runs on Google AppEngine. No attempt has been made to use the Google APIs - just a minimal Spring app that works.

Depends on [spring-boot-legacy](https://github.com/scratches/spring-boot-legacy) (which you will need to build and install locally):

```
$ git clone https://github.com/scratches/spring-boot-legacy
$ (cd spring-boot-legacy; mvn install)
$ git clone https://github.com/scratches/spring-boot-sample-gae
$ cd spring-boot-sample-gae
$ mvn appengine:update
```

Also runs as a deployed jar in a Jetty container. The `main()` app (normal Spring Boot launcher) should also work.