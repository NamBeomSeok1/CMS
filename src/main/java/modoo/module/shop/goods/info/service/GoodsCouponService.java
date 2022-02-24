package modoo.module.shop.goods.info.service;

import java.io.InputStream;
import java.util.List;

public interface GoodsCouponService {
	
	/**
	 * 임시 상품쿠폰 저장
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void insertTmpCoupon(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 업로드 쿠폰엑셀
	 * @param goodsCoupon
	 * @param fis
	 * @return
	 * @throws Exception
	 */
	int uploadCouponExcel(GoodsCouponVO goodsCoupon, InputStream fis) throws Exception;

	/**
	 * 주문시 쿠폰 저장
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void insertGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 쿠폰상태 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateGoodsCouponSttus(GoodsCouponVO goodsCoupon) throws Exception;


	/**
	 * 쿠폰상세
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	GoodsCouponVO selectGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 임비 쿠폰 목록
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	List<GoodsCouponVO> selectTmpUploadCouponExcelList(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 임시 쿠폰데이터 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateTmpUploadCouponExcel(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰 체크 카운트
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	int selectTmpCouponCheckCnt(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 임시쿠폰 목록 삭제
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void deleteTmpCouponList(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰목록
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	List<GoodsCouponVO> selectGoodsCouponList(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 판매가능쿠폰수
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	int selectGoodsCouponCnt(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰번호 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateGoodsCouponNo(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰 주문번호 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateGoodsCouponOrderNo(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰 삭제
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void deleteGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰 발급
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	List<GoodsCouponVO> selectSleGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;
}
