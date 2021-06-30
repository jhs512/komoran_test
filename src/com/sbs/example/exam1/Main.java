package com.sbs.example.exam1;

import java.util.ArrayList;
import java.util.List;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class Main {
	public static void main(String[] args) {
		String body = "안녕하세요. 저는 사과를 참 좋아하는 대학생입니다.\n";
		body += "저는 어제부터 코딩을 공부하고 있습니다.\n";
		body += "여러가지를 동시에 배우고 있는데 그 중에서 C++이 가장 재밌습니다.\n";

		System.out.println(Util.getKeywordsStrFromStr(body));
	}
}

class Util {
	static String getKeywordsStrFromStr(String str) {
		String keywordsStr = "";

		List<String> keywords = getKeywordsFromStr(str);

		for (String keyword : keywords) {
			keywordsStr += " #" + keyword;
		}

		keywordsStr = keywordsStr.trim();

		return keywordsStr;
	}

	static List<String> getKeywordsFromStr(String str) {
		List<String> keywords = new ArrayList<>();

		Komoran komoran = new Komoran(DEFAULT_MODEL.EXPERIMENT);

		KomoranResult analyzeResultList = komoran.analyze(str);

		List<Token> tokenList = analyzeResultList.getTokenList();
		for (Token token : tokenList) {
			String keyword = token.getMorph();
			String pos = token.getPos();

			switch (keyword) {
			case "어제":
			case "동시":
			case "이":
				continue;
			}

			switch (pos) {
			case "NNP":
			case "NNG":
				keywords.add(keyword);
				break;
			}
		}

		return keywords;
	}
}