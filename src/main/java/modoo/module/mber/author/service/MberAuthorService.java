package modoo.module.mber.author.service;

import java.util.List;

public interface MberAuthorService {

	/**
	 * 권한 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<MberAuthorVO> selectMberAuthorList(MberAuthorVO searchVO) throws Exception;

	/**
	 * 사용된 권한 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<MberAuthorVO> selectUsedAuthorList(MberAuthorVO searchVO) throws Exception;
}
