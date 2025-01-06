$(function() {
    var tabs = $("#tabs").tabs();

    // 메뉴 항목을 클릭할 때 해당 탭 생성 및 활성화 (snb_depth3 a 요소에만 적용)
    $("#menu .snb_depth3 a").on("click", function(event) {
        event.preventDefault();
        var tabName = $(this).data("tab");  // 클릭한 메뉴 항목의 data-tab 값
        var tabId = "tabs-" + tabName.replace(/\s+/g, '');  // 공백 제거 후 탭 ID 생성
        var url = $(this).attr("href");  // 링크의 href 값을 가져옴

        // 이미 존재하는 탭인지 확인
        var existingTab = $("#" + tabId);

        if (existingTab.length === 0) {
            // 새로운 탭 추가
            $("#tabs ul").append("<li><a href='#" + tabId + "'>" + tabName + "</a><span class='ui-icon ui-icon-close' role='presentation'></span></li>");
            $("#tabs").append("<div id='" + tabId + "'><p>로딩 중...</p></div>");
            tabs.tabs("refresh");

            // Ajax로 외부 콘텐츠 로드
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

        // 해당 탭 활성화
        tabs.tabs("option", "active", $("#tabs").find("a[href='#" + tabId + "']").parent().index());
    });

    // 닫기 아이콘 클릭 시 탭 삭제
    tabs.on("click", "span.ui-icon-close", function() {
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });

    // 페이지 로드 시 첫 번째 탭 자동 클릭
    var firstTab = $("#menu .snb_depth3 a").first();
    if (firstTab.length > 0) {
        firstTab.trigger("click");
    }
});

// DOM이 로드된 후에 실행
document.addEventListener("DOMContentLoaded", function() {
    // 모든 tab-link를 선택
    const tabLinks = document.querySelectorAll('.tab-link');

    // 각 tab-link에 클릭 이벤트 리스너 추가
    tabLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            // 클릭한 tab-link가 이미 active 상태인지를 확인
            const activeLink = document.querySelector('.tab-link.active');
            if (activeLink && activeLink !== link) {
                activeLink.classList.remove('active');
                const activeSubMenu = activeLink.nextElementSibling;
                if (activeSubMenu && activeSubMenu.classList.contains('snb_depth2')) {
                    activeSubMenu.style.display = 'none';
                }
            }

            // 클릭한 링크에 active 클래스 추가
            link.classList.toggle('active');

            // 하위 메뉴를 토글하기
            const subMenu = link.nextElementSibling;
            if (subMenu && subMenu.classList.contains('snb_depth2')) {
                // 하위 메뉴가 보이면 숨기고, 아니면 보이게 처리
                if (subMenu.style.display === 'block') {
                    subMenu.style.display = 'none';
                } else {
                    subMenu.style.display = 'block';
                }
            }
        });
    });
});