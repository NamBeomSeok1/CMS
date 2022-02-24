# 딥레이서

## Git URL
```
https://github.com/NamBeomSeok1/DEEPRACER.git

Dataeum YONA server
http://office.dataeum.kr:9900/
```


## 프로젝트 설정


### Tomcat context.xml
```xml

 <Resource name="jdbc/homeDB" auth="Container"  type="javax.sql.DataSource"
              driverClassName="net.sf.log4jdbc.sql.jdbcapi.DriverSpy"
              url="jdbc:log4jdbc:mariadb://gne.edueum.com:3316/racer_db"
              username="racer_user"
              password="racer_pass!2#"
              maxActive="20" maxIdle="10" maxWait="-1" />
```




## 실도메인
```
http://racer.foxedu.kr
```
