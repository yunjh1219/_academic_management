$(function() {
    var tabs = $("#tabs").tabs();

    // 메뉴 항목을 클릭할 때 해당 탭 생성 및 활성화
    $("#menu .snb_depth3 a").on("click", function(event) {
        event.preventDefault();  // 기본 링크 동작 방지
        var tabName = $(this).data("tab"); // data-tab 값을 사용하여 탭 이름 생성
        var tabId = "tabs-" + tabName.replace(/\s+/g, ''); // 공백 제거하여 tabId 생성
        var url = $(this).attr("href");

        var existingTab = $("#" + tabId);

        // 🔥 최대 8개 탭 제한 추가
        if ($("#tabs ul li").length >= 8) {
            alert("최대 8개의 탭만 열 수 있습니다.");
            return;  // 탭 추가 방지
        }

        if (existingTab.length === 0) {
            // 탭 생성
            $("#tabs ul").append("<li><a href='#" + tabId + "'>" + tabName + "</a><span class='ui-icon ui-icon-close' role='presentation'></span></li>");
            $("#tabs").append("<div id='" + tabId + "'><p>로딩 중...</p></div>");
            tabs.tabs("refresh");

            // AJAX로 콘텐츠 로드
            $.ajax({
                url: url,
                method: 'GET',
                success: function(data) {
                    $("#" + tabId).html(data);  // 콘텐츠 삽입
                },
                error: function() {
                    $("#" + tabId).html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                }
            });
        }

        // 새로 생성된 탭을 활성화
        tabs.tabs("option", "active", $("#tabs").find("a[href='#" + tabId + "']").parent().index());
    });

    // 다른 <a> 태그 클릭 시 콘텐츠 로드만 처리
    $("a.load-content").on("click", function(event) {
        event.preventDefault();  // 기본 링크 이동 방지

        // 클릭된 a 태그의 href 속성 값 가져오기
        var url = $(this).attr("href");
        // 콘텐츠만 로드
        $.ajax({
            url: url,
            method: 'GET',
            success: function(data) {
                console.log(data)
                $("#tabs").html(data);  // #tabs에 콘텐츠만 삽입
            },
            error: function() {
                $("#tabs").html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
            }
        });
    });
});
