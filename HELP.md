# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.5/maven-plugin/reference/html/#build-image)

### Commands
- curl -w "@curl_format.txt" -s -v "http://localhost:8080/io/fasted/file/size"
- curl -w "@curl_format.txt" -s -v "http://localhost:8080/io/fasted/file/target/exists"
- curl -w "@curl_format.txt" -s -v -X DELETE "http://localhost:8080/io/fasted/file/target"
- curl -w "@curl_format.txt" -s -v -X PATCH "http://localhost:8080/io/fasted/file/target"