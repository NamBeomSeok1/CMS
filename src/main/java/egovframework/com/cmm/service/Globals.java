package egovframework.com.cmm.service;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class Globals {
	//OS 유형
    public static final String OS_TYPE = EgovProperties.getProperty("Globals.OsType");
    //DB 유형
    public static final String DB_TYPE = EgovProperties.getProperty("Globals.DbType");
    //메인 페이지
    public static final String MAIN_PAGE = EgovProperties.getProperty("Globals.MainPage");
    //ShellFile 경로
    public static final String SHELL_FILE_PATH = EgovProperties.getPathProperty("Globals.ShellFilePath");
    //퍼로퍼티 파일 위치
    public static final String CONF_PATH = EgovProperties.getPathProperty("Globals.ConfPath");
    //Server정보 프로퍼티 위치
    public static final String SERVER_CONF_PATH = EgovProperties.getPathProperty("Globals.ServerConfPath");
    //Client정보 프로퍼티 위치
    public static final String CLIENT_CONF_PATH = EgovProperties.getPathProperty("Globals.ClientConfPath");
    //파일포맷 정보 프로퍼티 위치
    public static final String FILE_FORMAT_PATH = EgovProperties.getPathProperty("Globals.FileFormatPath");

    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";

	//메일발송요청 XML파일경로
	public static final String MAIL_REQUEST_PATH = EgovProperties.getPathProperty("Globals.MailRequestPath");
	//메일발송응답 XML파일경로
	public static final String MAIL_RESPONSE_PATH = EgovProperties.getPathProperty("Globals.MailRResponsePath");

	// G4C 연결용 IP (localhost)
	public static final String LOCAL_IP = EgovProperties.getProperty("Globals.LocalIp");

	//SMS 정보 프로퍼티 위치
	public static final String SMSDB_CONF_PATH = EgovProperties.getPathProperty("Globals.SmsDbConfPath");

	//서비스 모드 (DEV, REAL)
	public static final String CMS_MODE = EgovProperties.getProperty("CMS.mode");
	
	//파일 업로드 경로
	public static final String FILE_STORE_PATH = EgovProperties.getProperty("Globals.fileStorePath");
	//콘텐츠파일 경로
	public static final String CONTENTS_STORE_PATH = EgovProperties.getProperty("CMS.contents.fileStorePath");

	
	//KAKAO KEY
	public static final String KAKAO_KEY = EgovProperties.getProperty("kakao.appkey");
	
	//APPLE
	public static final String APPLE_AUTH_TOKEN_URL = EgovProperties.getProperty("APPLE.AUTH.TOKEN.URL");
	public static final String APPLE_PUBLICKEY_URL = EgovProperties.getProperty("APPLE.PUBLICKEY.URL");
	public static final String APPLE_WEBSITE_URL = EgovProperties.getProperty("APPLE.WEBSITE.URL");
	public static final String APPLE_ISS = EgovProperties.getProperty("APPLE.ISS");
	public static final String APPLE_AUD = EgovProperties.getProperty("APPLE.AUD");
	public static final String APPLE_TEAM_ID = EgovProperties.getProperty("APPLE.TEAM.ID");
	public static final String APPLE_KEY_ID = EgovProperties.getProperty("APPLE.KEY.ID");
	public static final String APPLE_KEY_PATH = EgovProperties.getProperty("APPLE.KEY.PATH");
	
	//BIZTALK
	public static final String BIZTALK_APIURL = EgovProperties.getProperty("BIZTALK.apiUrl");
	public static final String BIZTALK_SENDERKEY = EgovProperties.getProperty("BIZTALK.senderKey");
	public static final String BIZTALK_BSID = EgovProperties.getProperty("BIZTALK.bsid");
	public static final String BIZTALK_PASSWD = EgovProperties.getProperty("BIZTALK.passwd");
	public static final String BIZTALK_COUNTRYCODE = EgovProperties.getProperty("BIZTALK.countryCode");
	public static final String BIZTALK_SERVERIP = EgovProperties.getProperty("BIZTALK.serverIp");
	
	//네이버
	public static final String NAVER_CLIENT_ID = EgovProperties.getProperty("NAVER.clientId");
	public static final String NAVER_CLIENT_SECRET = EgovProperties.getProperty("NAVER.clientSecret");
	public static final String NAVER_REDIRECT_URI = EgovProperties.getProperty("NAVER.redirectUri");
	public static final String NAVER_SESSION_STATE = EgovProperties.getProperty("NAVER.sessionState");
	public static final String NAVER_PROFILE_API_URL = EgovProperties.getProperty("NAVER.profileApiUrl");
	
	//SSL사용여부
	public static final String SSL_AT = EgovProperties.getProperty("Globals.SslAt");
	
	//이니시스
	public static final String INICIS_JS = EgovProperties.getProperty("INICIS.js");
	
	//FOX통합회원
	public static final String FOX_MEMBER_LOGINURL = EgovProperties.getProperty("FOX.member.loginUrl");
	public static final String FOX_PORTALURL = EgovProperties.getProperty("FOX.portalUrl");
	
	
}
