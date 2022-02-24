package modoo.module.thema.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.thema.service.GoodsThemaService;

@Service("goodsThemaService")
public class GoodsThemaServiceImpl implements GoodsThemaService{

	@Resource(name="goodsThemaMapper")
	private GoodsThemaMapper goodsThemaMapper;

	@Resource(name="goodsThemaMapngMapper")
	private GoodsThemaMapngMapper goodsThemaMapngMapper;

	/**
	 * 테마상세
	 */
	@Override
	public GoodsThemaVO selectGoodsThema(GoodsThemaVO searchVO) throws Exception {
		return goodsThemaMapper.selectGoodsThema(searchVO);
	}
	/**
	 * 테마개수
	 */
	@Override
	public int selectGoodsThemaListCnt(GoodsThemaVO searchVO) throws Exception {
		return goodsThemaMapper.selectGoodsThemaListCnt(searchVO);
	}
	/**
	 * 테마다음순서
	 */
	@Override
	public int selectNextThemaSn(GoodsThemaVO searchVO) throws Exception {
		return goodsThemaMapper.selectNextThemaSn(searchVO);
	}
	/**
	 * 테마다음번호
	 */
	@Override
	public int selectNextThemaNo() throws Exception {
		return goodsThemaMapper.selectNextThemaNo();
	}
	
	/**
	 * 테마목록
	 */
	@Override
	public List<GoodsThemaVO> selectGoodsThemaList(GoodsThemaVO searchVO) throws Exception {
		return goodsThemaMapper.selectGoodsThemaList(searchVO);
	}/**
	 * 테마저장
	 */
	@Override
	public void insertGoodsThema(GoodsThemaVO goodsThema) throws Exception {
		goodsThemaMapper.insertGoodsThema(goodsThema);
	}/**
	 * 테마수정
	 */
	@Override
	public void updateGoodsThema(GoodsThemaVO goodsThema) throws Exception {
		goodsThemaMapper.updateGoodsThema(goodsThema);
	}
	/**
	 * 테마삭제
	 */
	@Override
	public void deleteGoodsThema(GoodsThemaVO goodsThema) throws Exception {
		goodsThemaMapper.deleteGoodsThema(goodsThema);
		GoodsThemaMapngVO goodsThemaMapng = new GoodsThemaMapngVO();
		goodsThemaMapngMapper.deleteGoodsThemaMapngList(goodsThemaMapng);
	}

	/**
	 * 테마이미지삭제
	 */
	@Override
	public void deleteThemaImg(GoodsThemaVO goodsThema) {
		goodsThemaMapper.deleteThemaImg(goodsThema);
	}
	
	
}
