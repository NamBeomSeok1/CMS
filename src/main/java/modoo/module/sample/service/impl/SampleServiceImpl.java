package modoo.module.sample.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import modoo.module.sample.service.SampleService;
import modoo.module.sample.service.SampleVO;

@Service("sampleService")
public class SampleServiceImpl extends EgovAbstractServiceImpl implements SampleService {
	
	@Resource(name = "sampleMapper")
	private SampleMapper sampleMapper;
	
	@Resource(name = "sampleIdGnrService")
	private EgovIdGnrService sampleIdGnrService; // 설정 : src/main/resources/egovframework/spring/com/idgn/context-idgn-Sample.xml

	/**
	 * 샘플 목록
	 */
	@Override
	public List<SampleVO> selectSampleList(SampleVO searchVO) throws Exception {
		return sampleMapper.selectSampleList(searchVO);
	}

	/**
	 * 샘플 목록 카운트
	 */
	@Override
	public int selectSampleListCnt(SampleVO searchVO) throws Exception {
		return sampleMapper.selectSampleListCnt(searchVO);
	}

	/**
	 * 샘플 저장
	 */
	@Override
	public void insertSample(SampleVO sample) throws Exception {
		String sampleId = sampleIdGnrService.getNextStringId();
		sample.setSampleId(sampleId);
		sampleMapper.insertSample(sample);
	}

	/**
	 * 샘플 상세
	 */
	@Override
	public SampleVO selectSample(SampleVO sample) throws Exception {
		return sampleMapper.selectSample(sample);
	}

	/**
	 * 샘플 수정
	 */
	@Override
	public void updateSample(SampleVO sample) throws Exception {
		sampleMapper.updateSample(sample);
	}

	/**
	 * 샘플 삭제
	 */
	@Override
	public void deleteSample(SampleVO sample) throws Exception {
		sampleMapper.deleteSample(sample);
	}

}
