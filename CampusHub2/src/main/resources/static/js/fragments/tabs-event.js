$(function() {
    var tabs = $("#tabs").tabs();

    // 탭 생성 및 활성화 로직을 함수로 분리
    function openTab(tabName, url) {
        var tabId = "tabs-" + tabName.replace(/\s+/g, ''); // 공백 제거하여 tabId 생성
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

        // 새로 생성된 또는 기존 탭을 활성화
        tabs.tabs("option", "active", $("#tabs").find("a[href='#" + tabId + "']").parent().index());
    }

    // 메뉴 항목 클릭 이벤트
    $("#menu .snb_depth3 a").on("click", function(event) {
        event.preventDefault();  // 기본 링크 동작 방지
        var tabName = $(this).data("tab");
        var url = $(this).attr("href");
        openTab(tabName, url);
    });

    // 바로가기 링크 클릭 이벤트
    $("a.shortcut-link").on("click", function(event) {
        event.preventDefault();  // 기본 링크 동작 방지
        var tabName = $(this).data("tab");
        var url = $(this).attr("href");
        openTab(tabName, url);
    });

    // load-content 링크 클릭 이벤트
    $("a.load-content").on("click", function(event) {
        event.preventDefault();  // 기본 링크 이동 방지
        var tabName = $(this).data("tab"); // data-tab 속성으로 탭 이름 가져오기
        var url = $(this).attr("href");

        if (tabName) {
            // 탭이름이 있다면 탭 생성 및 활성화
            openTab(tabName, url);
        } else {
            // 탭 이름이 없는 경우 현재 활성화된 탭에 콘텐츠 로드
            var activeTabId = $("#tabs .ui-tabs-panel:visible").attr("id"); // 현재 활성화된 탭 ID
            if (activeTabId) {
                var activeTabContent = $("#" + activeTabId);
                $.ajax({
                    url: url,
                    method: 'GET',
                    success: function(data) {
                        activeTabContent.html(data); // 현재 활성화된 탭에 콘텐츠 로드
                    },
                    error: function() {
                        activeTabContent.html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                    }
                });
            }
        }
    });

});
