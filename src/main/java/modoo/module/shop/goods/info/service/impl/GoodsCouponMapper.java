package modoo.module.shop.goods.info.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.info.service.GoodsCouponVO;

@Mapper("goodsCouponMapper")
public interface GoodsCouponMapper {
	
	/**
	 * 상품쿠론업로드 저장(임시)
	 * @param goodsCouponVO
	 * @throws Exception
	 */
	void insertUploadGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 임시 상품쿠폰 저장
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void insertTmpCoupon(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 삭제 : 업로드 임시 엑셀데이터 
	 * @throws Exception
	 */
	void deleteTmpUploadCouponExcel(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 임시 쿠폰 목록
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
	 * 임시 쿠폰 체크 카운트
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
	 * 임시쿠폰데이터를 상품푸콘데이터로 저장 
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void insertTmpCouponToGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 주문시 쿠폰 저장
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void insertGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 쿠폰상세
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	GoodsCouponVO selectGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;

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
	 * 중복쿠폰 목록
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	List<?> selectGoodsCouponDuplList(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰번호 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateGoodsCouponNo(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 쿠폰상태 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateGoodsCouponSttus(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 쿠폰 주문번호 수정
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void updateGoodsCouponOrderNo(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰상세
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	GoodsCouponVO selectGoodsCouponDetail(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰 삭제
	 * @param goodsCoupon
	 * @throws Exception
	 */
	void deleteGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;
	
	/**
	 * 쿠폰 판매목록
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	List<?> selectGoodsCouponSleCheckList(GoodsCouponVO goodsCoupon) throws Exception;

	/**
	 * 쿠폰 발급
	 * @param goodsCoupon
	 * @return
	 * @throws Exception
	 */
	List<GoodsCouponVO> selectSleGoodsCoupon(GoodsCouponVO goodsCoupon) throws Exception;
}
