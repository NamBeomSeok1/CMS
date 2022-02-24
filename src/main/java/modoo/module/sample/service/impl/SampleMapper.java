package modoo.module.sample.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.sample.service.SampleVO;

@Mapper("sampleMapper")
public interface SampleMapper {

	/**
	 * 샘플 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<SampleVO> selectSampleList(SampleVO searchVO) throws Exception;
	
	/**
	 * 샘플 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectSampleListCnt(SampleVO searchVO) throws Exception;
	
	/**
	 * 샘플 저장
	 * @param sample
	 * @throws Exception
	 */
	void insertSample(SampleVO sample) throws Exception;
	
	/**
	 * 샘플 상세
	 * @param sample
	 * @return
	 * @throws Exception
	 */
	SampleVO selectSample(SampleVO sample) throws Exception;
	
	/**
	 * 샘플 수정
	 * @param sample
	 * @throws Exception
	 */
	void updateSample(SampleVO sample) throws Exception;
	
	/**
	 * 샘플 삭제
	 * @param sample
	 * @throws Exception
	 */
	void deleteSample(SampleVO sample) throws Exception;
}
