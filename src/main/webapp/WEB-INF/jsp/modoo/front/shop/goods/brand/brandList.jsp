<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="브랜드관"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
    <div class="sub-contents">
        <h2 class="sub-tit m-none">브랜드관</h2>
        <nav class="brand-nav">
            <div>
                <a href="#brand01">ㄱ</a>
                <a href="#brand02">ㄴ</a>
                <a href="#brand03">ㄷ</a>
                <a href="#brand04">ㄹ</a>
                <a href="#brand05">ㅁ</a>
                <a href="#brand06">ㅂ</a>
                <a href="#brand07">ㅅ</a>
                <a href="#brand08">ㅇ</a>
                <a href="#brand09">ㅈ</a>
                <a href="#brand10">ㅊ</a>
                <a href="#brand11">ㅋ</a>
                <a href="#brand12">ㅌ</a>
                <a href="#brand13">ㅍ</a>
                <a href="#brand14">ㅎ</a>
            </div>
            <div class="ff-din">
                <a href="#brandA">A</a>
                <a href="#brandB">B</a>
                <a href="#brandC">C</a>
                <a href="#brandD">D</a>
                <a href="#brandE">E</a>
                <a href="#brandF">F</a>
                <a href="#brandG">G</a>
                <a href="#brandH">H</a>
                <a href="#brandI">I</a>
                <a href="#brandJ">J</a>
                <a href="#brandK">K</a>
                <a href="#brandL">L</a>
                <a href="#brandM">M</a>
                <a href="#brandN">N</a>
                <a href="#brandO">O</a>
                <a href="#brandP">P</a>
                <a href="#brandQ">Q</a>
                <a href="#brandR">R</a>
                <a href="#brandS">S</a>
                <a href="#brandT">T</a>
                <a href="#brandU">U</a>
                <a href="#brandV">V</a>
                <a href="#brandW">W</a>
                <a href="#brandX">X</a>
                <a href="#brandY">Y</a>
                <a href="#brandZ">Z</a>
                <a href="#brandEtc">etc.</a>
            </div>
        </nav>
        <div class="brand" id="brandList">

        </div>
    </div>
</div>

	<javascript>
		<script src="${CTX_ROOT}/resources/front/shop/goods/brand/js/brandList.js"></script>
	</javascript>
</body>
</html>