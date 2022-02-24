package modoo.module.mber.author.service.impl;

import java.util.List;

import modoo.module.mber.author.service.MberAuthorVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mberAuthorMapper")
public interface MberAuthorMapper {

	/**
	 * 권한 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<MberAuthorVO> selectMberAuthorList(MberAuthorVO searchVO) throws Exception;
	
	/**
	 * 권한 사용된 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<MberAuthorVO> selectUsedAuthorList(MberAuthorVO searchVO) throws Exception;
}
