package modoo.module.shop.goods.brand.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import modoo.module.shop.goods.brand.service.GoodsBrandGroup;
import modoo.module.shop.goods.brand.service.GoodsBrandImageVO;
import modoo.module.shop.goods.brand.service.GoodsBrandService;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;
import modoo.module.shop.goods.image.service.GoodsImageService;
import modoo.module.shop.goods.image.service.GoodsImageVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Service("goodsBrandService")
public class GoodsBrandServiceImpl extends EgovAbstractServiceImpl implements GoodsBrandService {

	@Resource(name = "goodsBrandMapper")
	private GoodsBrandMapper goodsBrandMapper;
	
	@Resource(name = "goodsBrandImageMapper")
	private GoodsBrandImageMapper goodsBrandImageMapper;
	
	@Resource(name = "goodsBrandIdGnrService")
	private EgovIdGnrService goodsBrandIdGnrService;
	
	@Resource(name = "goodsBrandImageIdGnrService")
	private EgovIdGnrService goodsBrandImageIdGnrService;
	
	@Resource(name="goodsService")
	private GoodsService goodsService;

	
	//Cached List
	private List<GoodsBrandGroup> goodsBrandGroupList = new ArrayList<GoodsBrandGroup>();

	/**
	 * 상품브랜드 목록
	 */
	@Override
	public List<GoodsBrandVO> selectGoodsBrandList(GoodsBrandVO searchVO) throws Exception {
		return goodsBrandMapper.selectGoodsBrandList(searchVO);
	}

	/**
	 * 상품브랜드 목록 카운트
	 */
	@Override
	public int selectGoodsBrandListCnt(GoodsBrandVO searchVO) throws Exception {
		return goodsBrandMapper.selectGoodsBrandListCnt(searchVO);
	}

	/**
	 * 상품브랜드 저장
	 */
	@Override
	public void insertGoodsBrand(GoodsBrandVO goodsBrand) throws Exception {
		String brandId = goodsBrandIdGnrService.getNextStringId();
		goodsBrand.setBrandId(brandId);
		goodsBrandMapper.insertGoodsBrand(goodsBrand);
		
		GoodsBrandImageVO bImg = new GoodsBrandImageVO();
		bImg.setBrandId(goodsBrand.getBrandId());
		
		//대표이미지
		if(goodsBrand.getRepBrandImageList() != null && goodsBrand.getRepBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "REP");
			/*List<GoodsBrandImageVO> brandImageList = goodsBrand.getRepBrandImageList();
			bImg.setBrandImageSeCode("REP");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImageList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
		//모바일대표이미지
		if(goodsBrand.getRepMobBrandImageList() != null && goodsBrand.getRepMobBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "REPMOB");
			/*
			List<GoodsBrandImageVO> brandImageList = goodsBrand.getRepBrandImageList();
			bImg.setBrandImageSeCode("REP");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImageList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}
			 */
		}
		
		//소개이미지
		if(goodsBrand.getIntBrandImageList() != null && goodsBrand.getIntBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "INT");
			/*List<GoodsBrandImageVO> brandImgaeList = goodsBrand.getIntBrandImageList();
			bImg.setBrandImageSeCode("INT");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImgaeList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
		//이벤트 PC 이미지
		if(goodsBrand.getEvtBrandImageList()!= null && goodsBrand.getEvtBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "EVT");
			/*List<GoodsBrandImageVO> brandImgaeList = goodsBrand.getIntBrandImageList();
			bImg.setBrandImageSeCode("INT");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImgaeList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
		//이벤트 모바일 이미지
		if(goodsBrand.getEvtMobBrandImageList() != null && goodsBrand.getEvtMobBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "EVTMOB");
			/*List<GoodsBrandImageVO> brandImgaeList = goodsBrand.getIntBrandImageList();
			bImg.setBrandImageSeCode("INT");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImgaeList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
	}

	/**
	 * 상품브랜드 상세
	 */
	@Override
	public GoodsBrandVO selectGoodsBrand(GoodsBrandVO goodsBrand) throws Exception {
		GoodsBrandVO vo = goodsBrandMapper.selectGoodsBrand(goodsBrand);
		GoodsVO goods = new GoodsVO();
		GoodsVO btbGoods =  new GoodsVO();
		GoodsVO btcGoods =  new GoodsVO();
		
		if(vo!=null){
			if(vo.getBrandBtbGoodsId()!=null){
				goods.setGoodsId(vo.getBrandBtbGoodsId());
				btbGoods=goodsService.selectGoods(goods);
				vo.setBrandBtbGoods(btbGoods);
			}
			
			if(vo.getBrandBtcGoodsId()!=null){
				goods.setGoodsId(vo.getBrandBtcGoodsId());
				btcGoods=goodsService.selectGoods(goods);
				vo.setBrandBtcGoods(btcGoods);
			}
			GoodsBrandImageVO brandImage = new GoodsBrandImageVO();
			brandImage.setBrandId(goodsBrand.getBrandId());
			/*brandImage.setBrandImageSeCode("DEK");
			List<GoodsBrandImageVO> dekBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setDekBrandImageList(dekBrandImageList);
	
			brandImage.setBrandImageSeCode("MOB");
			List<GoodsBrandImageVO> mobBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setMobBrandImageList(mobBrandImageList);*/
	
			brandImage.setBrandImageSeCode("REP");
			List<GoodsBrandImageVO> repBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setRepBrandImageList(repBrandImageList);
	
			brandImage.setBrandImageSeCode("REPMOB");
			List<GoodsBrandImageVO> repMobBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setRepMobBrandImageList(repMobBrandImageList);
			
			brandImage.setBrandImageSeCode("INT");
			List<GoodsBrandImageVO> intBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setIntBrandImageList(intBrandImageList);
			
			brandImage.setBrandImageSeCode("EVT");
			List<GoodsBrandImageVO> evtBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setEvtBrandImageList(evtBrandImageList);
			
			brandImage.setBrandImageSeCode("EVTMOB");
			List<GoodsBrandImageVO> evtMobBrandImageList = goodsBrandImageMapper.selectGoodsBrandImageList(brandImage);
			vo.setEvtMobBrandImageList(evtMobBrandImageList);
		
		}
		return vo;
	}

	/**
	 * 상품브랜드 수정
	 */
	@Override
	public void updateGoodsBrand(GoodsBrandVO goodsBrand) throws Exception {
		GoodsBrandImageVO bImg = new GoodsBrandImageVO();
		bImg.setBrandId(goodsBrand.getBrandId());

		//컴퓨터대표이미지
		if(goodsBrand.getRepBrandImageList() != null && goodsBrand.getRepBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "REP");
			/*
			List<GoodsBrandImageVO> brandImageList = goodsBrand.getRepBrandImageList();
			bImg.setBrandImageSeCode("REP");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImageList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}
			*/
		}
		
		//모바일대표이미지
		if(goodsBrand.getRepMobBrandImageList() != null && goodsBrand.getRepMobBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "REPMOB");
			/*
			List<GoodsBrandImageVO> brandImageList = goodsBrand.getRepBrandImageList();
			bImg.setBrandImageSeCode("REP");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImageList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}
			 */
		}
		
		// 소개이미지
		if(goodsBrand.getIntBrandImageList() != null && goodsBrand.getIntBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "INT");
			/*List<GoodsBrandImageVO> brandImgaeList = goodsBrand.getIntBrandImageList();
			bImg.setBrandImageSeCode("REP");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImgaeList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
		//이벤트 PC 이미지
		if(goodsBrand.getEvtBrandImageList()!= null && goodsBrand.getEvtBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "EVT");
			/*List<GoodsBrandImageVO> brandImgaeList = goodsBrand.getIntBrandImageList();
			bImg.setBrandImageSeCode("INT");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImgaeList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
		//이벤트 모바일 이미지
		if(goodsBrand.getEvtMobBrandImageList() != null && goodsBrand.getEvtMobBrandImageList().size() > 0) {
			saveGoodsBrandImage(goodsBrand, "EVTMOB");
			/*List<GoodsBrandImageVO> brandImgaeList = goodsBrand.getIntBrandImageList();
			bImg.setBrandImageSeCode("INT");
			int index = goodsBrandImageMapper.selectGoodsBrandImageMaxSn(bImg);
			for(GoodsBrandImageVO vo : brandImgaeList) {
				vo.setBrandImageSn(index++);
				vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
				vo.setBrandId(goodsBrand.getBrandId());
				goodsBrandImageMapper.insertGoodsBrandImage(vo);
			}*/
		}
		
		goodsBrandMapper.updateGoodsBrand(goodsBrand);
	}
	
	/**
	 * 상품브랜드 이미지 처리 (단일 이미지로 처리)
	 * @param goodsBrand
	 * @param brandImageSeCode
	 * @throws Exception
	 */
	private void saveGoodsBrandImage(GoodsBrandVO goodsBrand, String brandImageSeCode) throws Exception {
		
		GoodsBrandImageVO vo = new GoodsBrandImageVO();
		if("REP".equals(brandImageSeCode)) {
			vo = goodsBrand.getRepBrandImageList().get(0);
		}else if("INT".equals(brandImageSeCode)) {
			vo = goodsBrand.getIntBrandImageList().get(0);
		}else if("REPMOB".equals(brandImageSeCode)){
			vo = goodsBrand.getRepMobBrandImageList().get(0);
		}else if("EVT".equals(brandImageSeCode)){
			vo = goodsBrand.getEvtBrandImageList().get(0);
		}else if("EVTMOB".equals(brandImageSeCode)){
			vo = goodsBrand.getEvtMobBrandImageList().get(0);
		}

		vo.setBrandImageSeCode(brandImageSeCode);
		vo.setBrandId(goodsBrand.getBrandId());
		vo.setBrandImageSn(0); //파일 하나만 담기때문에 모두 0으로 처리
		vo.setBrandImageNo(goodsBrandImageIdGnrService.getNextBigDecimalId());
		
		//기존 이미지 삭제
		goodsBrandImageMapper.deleteGoodsBrandImageBrandId(vo);
		
		goodsBrandImageMapper.insertGoodsBrandImage(vo);
		
	}

	/**
	 * 상품브랜드 삭제
	 */
	@Override
	public void deleteGoodsBrand(GoodsBrandVO goodsBrand) throws Exception {
		// 기존 brandId 값 NULL 처리 (STN_GOODS)
		goodsBrandMapper.updateGoodsBrandIdNull(goodsBrand);
		
		// 브랜드 이미지 삭제
		GoodsBrandImageVO brandImage = new GoodsBrandImageVO();
		brandImage.setBrandId(goodsBrand.getBrandId());
		goodsBrandImageMapper.deleteGoodsBrandImageList(brandImage);
		
		// 삭제
		goodsBrandMapper.deleteGoodsBrand(goodsBrand);
	}
	
	@Override
	public void reloadGoodsBrandGroupList() throws Exception {
		String[] wrdArr = {"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"
				,"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","ETC"};
		
		List<GoodsBrandVO> goodsBrandList = goodsBrandMapper.selectGoodsBrandMenuList();
		List<GoodsBrandGroup> brandGroupList = new ArrayList<GoodsBrandGroup>();
		for(String wrd : wrdArr) {
			GoodsBrandGroup brandGroup = new GoodsBrandGroup();
			brandGroup.setWrd(wrd);
			List<GoodsBrandVO> list = new ArrayList<GoodsBrandVO>();
			for(GoodsBrandVO vo : goodsBrandList) {
				if(wrd.equals(vo.getWrd())) {
					list.add(vo);
				}
			}
			if(list.size() > 0) {
				brandGroup.setGoodsBrandList(list);
				brandGroupList.add(brandGroup);
			}
		}
		
		goodsBrandGroupList = brandGroupList;
	}

	/**
	 * 상품브랜드 메뉴 목록
	 */
	@Override
	public List<GoodsBrandGroup> selectGoodsBrandMenuList(GoodsBrandVO goodsBrand) throws Exception {
		//String[] wrdArr = {"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ","ETC"};
		/*
		String[] wrdArr = {"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"
				,"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","ETC"};
				*/
		
		if(goodsBrandGroupList.size() == 0) { // Cached 목록 없으면
			reloadGoodsBrandGroupList();
			/*
			List<GoodsBrandVO> goodsBrandList = goodsBrandMapper.selectGoodsBrandMenuList(goodsBrand);
			List<GoodsBrandGroup> brandGroupList = new ArrayList<GoodsBrandGroup>();
			for(String wrd : wrdArr) {
				GoodsBrandGroup brandGroup = new GoodsBrandGroup();
				brandGroup.setWrd(wrd);
				List<GoodsBrandVO> list = new ArrayList<GoodsBrandVO>();
				for(GoodsBrandVO vo : goodsBrandList) {
					if(wrd.equals(vo.getWrd())) {
						list.add(vo);
					}
				}
				if(list.size() > 0) {
					brandGroup.setGoodsBrandList(list);
					brandGroupList.add(brandGroup);
				}
			}
			
			goodsBrandGroupList = brandGroupList;
			*/
		}
		
		//List<GoodsBrandGroup> cloneGoodsBrandGroupList = goodsBrandGroupList;
		List<GoodsBrandGroup> resultList = new ArrayList<GoodsBrandGroup>();
		for(GoodsBrandGroup vo : goodsBrandGroupList) {
			List<GoodsBrandVO> blist = new ArrayList<GoodsBrandVO>();
			GoodsBrandGroup gbg = new GoodsBrandGroup();
			gbg.setWrd(vo.getWrd());
			for(GoodsBrandVO brand : vo.getGoodsBrandList()) {
				if(!"NONE".equals(brand.getBrandExpsrSeCode())) {
					if(StringUtils.isEmpty(goodsBrand.getSearchPrtnrId()) ) { 
						//전체를 뽑기위한 가상의 제휴사ID
						if( "PRTNR_XXXX".equals(brand.getPrtnrId())) blist.add(brand); 
					}else if(brand.getPrtnrId().equals(goodsBrand.getSearchPrtnrId())) {
						if("PRTNR_0000".equals(goodsBrand.getSearchPrtnrId())) { //B2C
							if("ALL".equals(brand.getBrandExpsrSeCode()) || "B2C".equals(brand.getBrandExpsrSeCode())) {
								blist.add(brand);
							}
						}else {  //B2B (이지웰)
							if("ALL".equals(brand.getBrandExpsrSeCode()) || "B2B".equals(brand.getBrandExpsrSeCode())) {
								blist.add(brand);
							}
						}
					}
				}
			}
			if(blist.size() > 0) {
				gbg.setGoodsBrandList(blist);
				resultList.add(gbg);
			}
			
		}
		
		return resultList;
		
	}
	/**
	 * 브랜드관 목록 조회 (ㄱ~ㅎ, A~Z)
	 * @throws Exception 
	 */
	@Override
	public List<GoodsBrandVO> selectGoodsBrandByChar(GoodsBrandVO searchVO) throws Exception {
		List<GoodsBrandVO> resultList = goodsBrandMapper.selectGoodsBrandByChar(searchVO);
		/*System.out.println( searchVO.toString()+"@@");
		GoodsVO btbGoods = new GoodsVO();
		GoodsVO btcGoods = new GoodsVO();
		for(GoodsBrandVO gb : resultList){
			if(gb.getBrandBtbGoodsId()!=null){
				btbGoods.setGoodsId(gb.getBrandBtbGoodsId());
				btbGoods=goodsService.selectGoods(btbGoods);
				gb.setBrandBtbGoods(btbGoods);
			}
			if(gb.getBrandBtcGoodsId()!=null){
				btcGoods.setGoodsId(gb.getBrandBtbGoodsId());
				btcGoods=goodsService.selectGoods(btcGoods);
				gb.setBrandBtcGoods(btcGoods);
				}
			}*/
			
		return resultList;
	}

}
