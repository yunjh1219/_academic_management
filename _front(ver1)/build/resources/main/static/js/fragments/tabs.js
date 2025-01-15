$(function() {
    var tabs = $("#tabs").tabs();

    // 메뉴 항목을 클릭할 때 해당 탭 생성 및 활성화
    $("#menu .snb_depth3 a").on("click", function(event) {
        event.preventDefault();
        var tabName = $(this).data("tab");
        var tabId = "tabs-" + tabName.replace(/\s+/g, '');
        var url = $(this).attr("href");

        var existingTab = $("#" + tabId);

        // 🔥 최대 8개 탭 제한 추가
        if ($("#tabs ul li").length >= 8) {
            alert("최대 8개의 탭만 열 수 있습니다.");
            return;  // 탭 추가 방지
        }

        if (existingTab.length === 0) {
            $("#tabs ul").append("<li><a href='#" + tabId + "'>" + tabName + "</a><span class='ui-icon ui-icon-close' role='presentation'></span></li>");

            $("#tabs").append("<div id='" + tabId + "'><p>로딩 중...</p></div>");
            tabs.tabs("refresh");

            $.ajax({
                url: url,
                method: 'GET',
                success: function(data) {
                    $("#" + tabId).html(data);
                },
                error: function() {
                    $("#" + tabId).html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                }
            });
        }

        tabs.tabs("option", "active", $("#tabs").find("a[href='#" + tabId + "']").parent().index());
    });



    // ❗️ X 버튼 클릭 시 모든 탭 삭제 기능 추가
    $("#tabs").on("click", ".close-btn", function () {
        $("#tabs ul li").remove();  // 모든 탭 제목 삭제
        $("#tabs > div").remove();  // 모든 탭 콘텐츠 삭제
        tabs.tabs("refresh");       // 탭 UI 갱신
    });

    // 닫기 아이콘 클릭 시 탭 삭제
    tabs.on("click", "span.ui-icon-close", function() {
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });

    // 예를 들어, 'course2'라는 탭을 자동으로 클릭하고 싶을 때
    var tabToActivate = $("#menu .snb_depth3 a[data-tab='공지사항']");

    if (tabToActivate.length > 0) {
        tabToActivate.trigger("click");
    }

    // 첫 번째 탭 자동 클릭
    // var firstTab = $("#menu .snb_depth3 a").first();
    // if (firstTab.length > 0) {
    //     firstTab.trigger("click");
    // }

    // depth1의 a 태그 클릭 시 depth2 메뉴 토글
    $(".snb_depth1 li > a").click(function() {
        var depth2 = $(this).next("ul");

        // depth2 메뉴가 화면상에 보일 때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
        if (depth2.is(":visible")) {
            depth2.slideUp(100);  // 100ms 동안 슬라이드 업
            $(this).parent().removeClass("open");
        } else {
            depth2.slideDown(100);  // 100ms 동안 슬라이드 다운
            $(this).parent().addClass("open");
        }

        // 모든 다른 li 요소에서 active 클래스 제거
        $(".snb_depth1 li").removeClass("active");
        // 클릭된 요소에 active 클래스 추가
        $(this).parent().addClass("active");

        // 활성화된 메뉴를 sessionStorage에 저장
        sessionStorage.setItem('activeDepth1', $(this).parent()[0].outerHTML);

    });

    // depth2의 a 태그 클릭 시, 페이지 이동 전에 상태 유지
    $(".snb_depth2 li > a").click(function() {
        var depth1 = $(this).closest(".snb_depth1 > li");
        // 클릭된 depth1 항목에 active 클래스 유지
        depth1.addClass("active");

        // 이동 시에도 sessionStorage에 저장
        sessionStorage.setItem('activeDepth1', depth1[0].outerHTML);
    });

    // depth3의 a 태그 클릭 시 페이지 이동 허용
    $(".snb_depth3 li > a").click(function() {
        return true; // 페이지 이동을 허용
    });
});
