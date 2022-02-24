package modoo.module.shop.goods.ctgry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@Service("goodsCtgryService")
public class GoodsCtgryServiceImpl extends EgovAbstractServiceImpl implements GoodsCtgryService {
	
	private static final String ROOT_CTGRY_ID = "GCTGRY_0000000000000"; //최상위 카타고리ID
	
	@Resource(name = "goodsCtgryMapper")
	private GoodsCtgryMapper goodsCtgryMapper;
	
	@Resource(name = "goodsCtgryIdGnrService")
	private EgovIdGnrService goodsCtgryIdGnrService;
	
	private List<GoodsCtgryVO> goodsCtgryList = new ArrayList<GoodsCtgryVO>();

	/**
	 * 상품카테고리 트리 목록 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	private List<GoodsCtgryVO> getGoodsCtgryTreeAllList() throws Exception {
		List<GoodsCtgryVO> allList = goodsCtgryMapper.selectGoodsCtgryList(new GoodsCtgryVO());
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("expanded", true);
		
		List<GoodsCtgryVO> dp1List = new ArrayList<GoodsCtgryVO>();
		for(GoodsCtgryVO dp1 : allList) {
			if(dp1.getUpperGoodsCtgryId().equals(ROOT_CTGRY_ID)) {

				List<GoodsCtgryVO> dp2List = new ArrayList<GoodsCtgryVO>();
				for(GoodsCtgryVO dp2 : allList) {
					if(dp2.getUpperGoodsCtgryId().equals(dp1.getGoodsCtgryId())) {
						
						List<GoodsCtgryVO> dp3List = new ArrayList<GoodsCtgryVO>();
						for(GoodsCtgryVO dp3 : allList) {
							if(dp3.getUpperGoodsCtgryId().equals(dp2.getGoodsCtgryId())) {
								
								List<GoodsCtgryVO> dp4List = new ArrayList<GoodsCtgryVO>();
								for(GoodsCtgryVO dp4 : allList) {
									if(dp4.getUpperGoodsCtgryId().equals(dp3.getGoodsCtgryId())) {
										dp4List.add(dp4);
									}
								}
								if(dp4List.size() > 0) {
									dp3.set_attributes(attributes);
									dp3.set_children(dp4List);
								}else {
									dp3.set_children(null);
								}
								
								dp3List.add(dp3);
							}
						}
						if(dp3List.size() > 0) {
							dp2.set_attributes(attributes);
							dp2.set_children(dp3List);
						}else {
							dp2.set_children(null);
						}
						
						dp2List.add(dp2);
					}
					
				}
				if(dp2List.size() > 0) {
					dp1.set_attributes(attributes);
					dp1.set_children(dp2List);
				}else {
					dp1.set_children(null);
				}

				dp1List.add(dp1);
			}
		}
		
		return dp1List;
	}

	/**
	 * 상품카테고리 트리 목록
	 */
	@Override
	public List<GoodsCtgryVO> selectGoodsCtgryTreeList(GoodsCtgryVO searchVO) throws Exception {
		if(goodsCtgryList.size() == 0) {
			goodsCtgryList = getGoodsCtgryTreeAllList();
		}
		
		String cesCode = "ALL";
		//if(StringUtils.isNotEmpty(searchVO.getSearchPrtnrId()) && "PRTNR_0001".equals(searchVO.getSearchPrtnrId())) {
		if(StringUtils.isNotEmpty(searchVO.getSearchPrtnrId())) {
			if("PRTNR_0000".equals(searchVO.getSearchPrtnrId())) {
				cesCode = "B2C";
			}else {
				cesCode = "B2B";
			}
		}
		
		List<GoodsCtgryVO> ctgryList1 = new ArrayList<GoodsCtgryVO>();
		for(GoodsCtgryVO dp1 : goodsCtgryList) {
			if(dp1.get_children() != null) {
				List<GoodsCtgryVO> ctgryList2 = new ArrayList<GoodsCtgryVO>();
				for(GoodsCtgryVO dp2 : dp1.get_children()) {
					if(dp2.get_children() != null) {
						List<GoodsCtgryVO> ctgryList3 = new ArrayList<GoodsCtgryVO>();
						for(GoodsCtgryVO dp3 : dp2.get_children()) {
							if("ALL".equals(cesCode) || cesCode.equals(dp3.getCtgryExpsrSeCode()) || "ALL".equals(dp3.getCtgryExpsrSeCode())) {
								ctgryList3.add(dp3);
							}
						}
					}
					if("ALL".equals(cesCode) || cesCode.equals(dp2.getCtgryExpsrSeCode()) || "ALL".equals(dp2.getCtgryExpsrSeCode())) {
						ctgryList2.add(dp2);
					}
				}
			}
			if("ALL".equals(cesCode) || cesCode.equals(dp1.getCtgryExpsrSeCode()) || "ALL".equals(dp1.getCtgryExpsrSeCode())) {
				ctgryList1.add(dp1);
			}
		}
		
		return ctgryList1;
		//return goodsCtgryList;
	}
	
	/**
	 * 상품카테고리 목록
	 */
	@Override
	public List<GoodsCtgryVO> selectGoodsCtgryList(GoodsCtgryVO searchVO) throws Exception {
		return  goodsCtgryMapper.selectGoodsCtgryList(searchVO);
	}
	
	/**
	 * 활성 상품 카테고리 목록
	 */
	@Override
	public List<GoodsCtgryVO> selectActGoodsCtgryList(GoodsCtgryVO searchVO) throws Exception{
		return goodsCtgryMapper.selectActGoodsCtgryList(searchVO);
	}

	/**
	 * 상품카테고리 목록 카운트
	 */
	@Override
	public int selectGoodsCtgryListCnt(GoodsCtgryVO searchVO) throws Exception {
		return goodsCtgryMapper.selectGoodsCtgryListCnt(searchVO);
	}

	/**
	 * 상품케테고리 저장
	 */
	@Override
	public void insertGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception {
		String goodsCtgryId = goodsCtgryIdGnrService.getNextStringId();
		goodsCtgry.setGoodsCtgryId(goodsCtgryId);
		goodsCtgryMapper.insertGoodsCtgry(goodsCtgry);
		
		goodsCtgryList = getGoodsCtgryTreeAllList();;
	}

	/**
	 * 상품케테고리 상세
	 */
	@Override
	public GoodsCtgryVO selectGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception {
		return goodsCtgryMapper.selectGoodsCtgry(goodsCtgry);
	}

	/**
	 * 상품카테고리 수정
	 */
	@Override
	public void updateGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception {
		//서브 카테고리 비활성화
		goodsCtgryMapper.updateSubGoddsCtgryAct(goodsCtgry);
		
		goodsCtgryMapper.updateGoodsCtgry(goodsCtgry);
		
		goodsCtgryList = getGoodsCtgryTreeAllList();
	}

	
	/**
	 * 상품카테고리 삭제
	 */
	@Override
	public void deleteGoodsCtgry(GoodsCtgryVO goodsCtgry) throws Exception {
		
		//서브 상품카테고리 삭제
		goodsCtgryMapper.deleteSubGoddsCtgry(goodsCtgry);

		// 카테고리 삭제
		goodsCtgryMapper.deleteGoodsCtgry(goodsCtgry);
		
		goodsCtgryList = getGoodsCtgryTreeAllList();
	}
	
	/**
	 * 상품카테고리 만들기
	 * @param allList
	 * @param emap
	 * @param upperGoodsCtgryId
	 * @return
	 */
	private String putGoodsCtgry(List<GoodsCtgryVO> allList, EgovMap emap, String upperGoodsCtgryId, GoodsVO goods) {
		List<GoodsCtgryVO> ctgryList = new ArrayList<GoodsCtgryVO>();
		String returnUpperId = "";
		int dp = 0;
		for(GoodsCtgryVO vo1: allList) {
			if(vo1.getGoodsCtgryId().equals(upperGoodsCtgryId)) {
				dp = vo1.getDp();
				if(dp == 1) {
					goods.setCateCode1(vo1.getGoodsCtgryId());
				}else if(dp == 2) {
					goods.setCateCode2(vo1.getGoodsCtgryId());
				}else if(dp == 3) {
					goods.setCateCode3(vo1.getGoodsCtgryId());
				}
				for(GoodsCtgryVO vo2: allList) {
					if(vo1.getUpperGoodsCtgryId().equals(vo2.getUpperGoodsCtgryId())) {
						ctgryList.add(vo2);
						returnUpperId = vo2.getUpperGoodsCtgryId();
					}
				}
			}
		}
		if(dp != 0) emap.put("cate"+dp+"List", ctgryList);

		return returnUpperId;
		
	}

	/**
	 * 상품카테고리 DEPTH별 목록
	 */
	@Override
	public EgovMap getGoodsCtgryDepthList(GoodsCtgryVO searchVO, GoodsVO goods) throws Exception {
		EgovMap emap = new EgovMap();
		List<GoodsCtgryVO> allList = goodsCtgryMapper.selectGoodsCtgryDepthList(searchVO);
		
		GoodsCtgryVO cc = new GoodsCtgryVO();
		for(GoodsCtgryVO vo: allList) {
			if(searchVO.getGoodsCtgryId().equals(vo.getGoodsCtgryId())) {
				cc = vo;
			}
		}
		
		List<GoodsCtgryVO> childList = new ArrayList<GoodsCtgryVO>();

		int dp = 0;
		for(GoodsCtgryVO vo1: allList) {
			if(vo1.getUpperGoodsCtgryId().equals(cc.getGoodsCtgryId())) {
				dp = vo1.getDp();
				childList.add(vo1);
			}
		}
		if(dp != 0) emap.put("cate"+dp+"List", childList);
		
		putGoodsCtgry(allList, emap, cc.getGoodsCtgryId(), goods);
		String upperId1 = putGoodsCtgry(allList, emap, cc.getUpperGoodsCtgryId(), goods);
		String upperId2 = putGoodsCtgry(allList, emap, upperId1, goods);
		putGoodsCtgry(allList, emap, upperId2, goods);

		return emap;
	}

	/**
	 * 상품카테고리 메뉴 목록
	 */
	@Override
	public List<GoodsCtgryVO> selectGoodsCtgryMenuList(GoodsCtgryVO searchVO) throws Exception {
		return goodsCtgryMapper.selectGoodsCtgryMenuList(searchVO);
	}
	/**
	 * 서브카테고리 목록
	 */
	@Override
	public List<GoodsCtgryVO> selectSubCtgryList(GoodsCtgryVO ctgry) {
		String cesCode = "ALL";
		if(StringUtils.isNotEmpty(ctgry.getSearchPrtnrId())) {
			if("PRTNR_0000".equals(ctgry.getSearchPrtnrId())) {
				cesCode = "B2C";
			}else {
				cesCode = "B2B";
			}
		}
		List<GoodsCtgryVO> subCtgryList = new ArrayList<GoodsCtgryVO>();
		
		for(GoodsCtgryVO dp1 : goodsCtgryList) {
			if(dp1.get_children() != null && dp1.getGoodsCtgryId().equals(ctgry.getSearchUpperGoodsCtgryId())) {
				for(GoodsCtgryVO dp2 : dp1.get_children()) {
					if("ALL".equals(cesCode) || cesCode.equals(dp2.getCtgryExpsrSeCode()) || "ALL".equals(dp2.getCtgryExpsrSeCode())) {
						subCtgryList.add(dp2);
					}
				}
			}
		}
		
		return subCtgryList;
		//return goodsCtgryMapper.selectSubCtgryList(ctgry);
	}

}
