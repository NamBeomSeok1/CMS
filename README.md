# 폭스스토어

## Git URL
```
http://YOUR_ID@office.dataeum.kr:9900/dymoon/%ED%8F%AD%EC%8A%A4%EB%AA%B0

Dataeum YONA server
http://office.dataeum.kr:9900/
```


## 프로젝트 설정

### globals.properties

```
/src/main/resources/egovframework/egovProps/globals.properties
Globals.fileStorePath =  C:/JAVA_PROJECTS/store/AttachFiles/
CMS.contents.fileStorePath = C:/JAVA_PROJECTS/store/AttachFiles/contents/
```

### Tomcat server.xml
```xml
<Host>
	...
	<Context docBase="D:/JAVA_PROJECTS/foxmall/workspace/AttachFiles/contents" path="/contents"/>
	...
</Host>
```

### Tomcat context.xml
```xml

 <Resource name="jdbc/homeDB" auth="Container"  type="javax.sql.DataSource"
              driverClassName="net.sf.log4jdbc.sql.jdbcapi.DriverSpy"
              url="jdbc:log4jdbc:mariadb://gne.edueum.com:3316/foxmall_dev_db"
              username="foxmall_user"
              password="foxedu!2#"
              maxActive="20" maxIdle="10" maxWait="-1" />
```


## 개발도메인
```
로컬개발 : http://devstore.foxedu.kr
개발서버 : http://dev-store.foxedu.kr
```

## 실도메인
```
http://store.foxedu.kr
```
