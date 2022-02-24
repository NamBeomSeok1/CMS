# Javascript Functions
## Element Event
### CMS 영역 

---
#### data-event
|Event| Example|
|--|--|
|alert| &lt;a href="#" data-event="alert" data-message="안녕하세요"&gt; click &lt;/a&gt;|
|confirm| &lt;a href="#" data-event="confirm" data-message="안녕하세요"&gt; click &lt;/a&gt;|

---
#### modooAjax

example
```javascript
/** 
JsonResult 처리 

result.message 가 있을 때 alert(result.message) 호출
result.redirectUrl 페이지 이동 처리

위치 : /resources/decms/common/js/script.js
modooAjax = function(option) {}
*/

modooAjax({
    url: CTX_ROOT + '/sample/sampleList.json',
    method: 'get',
    param: {searchCondition: 'SJ', searchKeyword: '테스트'},
    callback: function(result) {
        console.log(result);
    }
});
```

---