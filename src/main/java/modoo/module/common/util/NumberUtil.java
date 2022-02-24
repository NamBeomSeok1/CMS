package modoo.module.common.util;

import java.util.Random;

public class NumberUtil {
	/*
	 *결제 테이블 임시 키 발급 
	 */
	public static String numberGen(int len) {
	       
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
       
        for(int i=0;i<len;i++) {           
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));           
            numStr += ran;
        }
        return numStr;
	}

    /*
     *쿠폰 넘버
     */
    public static String couponGen() {

        Random rnd =new Random();
        String coupon = "";

        StringBuffer buf =new StringBuffer();

        for(int i=0;i<=18;i++){
            // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.

            if(i==4 || i==9 || i==14){
                buf.append("-");
            }else{
                if(rnd.nextBoolean()){
                    buf.append((char)((int)(rnd.nextInt(26))+97));
                }else{
                    buf.append((rnd.nextInt(10)));
                }
            }
            coupon = buf.toString();
        }
        return coupon;
    }
}
