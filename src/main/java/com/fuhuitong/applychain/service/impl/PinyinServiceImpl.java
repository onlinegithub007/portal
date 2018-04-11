package com.fuhuitong.applychain.service.impl;

import com.fuhuitong.applychain.service.IPinyinService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class PinyinServiceImpl extends BaseService implements IPinyinService {

	@Override
	public String getPinyin(HttpServletRequest request, HttpSession session, String chineseChars) {
		
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(chineseChars))
		{
			return getRetCodeWithJson(1, "");
		}
		
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

		try {
			String pinyin = PinyinHelper.toHanYuPinyinString(chineseChars, format, "", false);
			
			return getRetCodeWithJson(0, pinyin);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error(e.toString());
			return getRetCodeWithJson(1, "");
		}
	}

}
