package modoo.module.biling.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.icu.text.SimpleDateFormat;;

public class CreditCardVaild {


	Logger LOGGER = LoggerFactory.getLogger(CreditCardVaild.class);
	/*// Luhn Algorithm
	public Integer[] numbers;
	public Integer checksum;
	public String cardNumberString;

	public CreditCardVaild(String cardNumberString) {
	        this.cardNumberString = cardNumberString;
	        this.numbers = convertArray() ;
	        this.checksum = numbers[numbers.length - 1];

	    }

	Integer[] getTargets() {
		Integer[] sub = (Integer[]) ArrayUtils.subarray(ArrayUtils.clone(numbers), 0, numbers.length - 1);
		ArrayUtils.reverse(sub);
		return sub;
	}

	Integer[] convertArray() {
		Integer[] array = new Integer[cardNumberString.length()];
		for (int i = 0; i < cardNumberString.length(); ++i) {
			array[i] = Integer.parseInt(cardNumberString.charAt(i) + "");
		}
		return array;
	}

	int checksum() {
		return checksum;
	}

	int sumvalue() {
		int sum = 0;

		Integer[] targets = getTargets();
		for (int i = 0; i < targets.length; i++) {
			int current = targets[i];
			if (0 == i % 2) {
				int doubles = (current * 2);
				// System.out.println(current+ "X2 = "+doubles+" : ("+ (doubles
				// % 10) +" "+ (doubles / 10)+")");
				sum = sum + (doubles % 10) + (doubles / 10);
			} else {
				// System.out.println(current);
				sum = sum + current;
			}
		}

		return sum;
	}

	static Integer nextTenNumber(int i) {
		return 10 * ((i / 10) + 1);
	}

	Integer calculate() {
		return nextTenNumber(sumvalue()) - sumvalue();
	}

	public Boolean isValid() {
		return checksum() == calculate();
	}
	*/
	
	

	
}
