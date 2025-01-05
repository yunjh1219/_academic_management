$(function() {
    var tabTitle = $("#tab_title"),
        tabContent = $("#tab_content"),
        tabTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
        tabCounter = 2;

    var tabs = $("#tabs").tabs();

    // 탭을 추가하는 다이얼로그 설정
    var dialog = $("#dialog").dialog({
        autoOpen: false,
        modal: true,
        buttons: {
            Add: function() {
                addTab();
                $(this).dialog("close");
            },
            Cancel: function() {
                $(this).dialog("close");
            }
        },
        close: function() {
            form[0].reset();
        }
    });

    var form = dialog.find("form").on("submit", function(event) {
        addTab();
        dialog.dialog("close");
        event.preventDefault();
    });

    // 탭을 추가하는 함수
    function addTab() {
        var label = tabTitle.val() || "Tab " + tabCounter,
            id = "tabs-" + tabCounter,
            li = $(tabTemplate.replace( /#\{href\}/g, "#" + id ).replace( /#\{label\}/g, label )),
            tabContentHtml = tabContent.val() || "Tab " + tabCounter + " content.";

        // 새로운 탭을 탭 목록에 추가
        tabs.find(".ui-tabs-nav").append(li);
        // 새로운 탭의 내용을 추가
        tabs.append("<div id='" + id + "'><p>" + tabContentHtml + "</p></div>");
        tabs.tabs("refresh");
        tabCounter++;
    }

    // 메뉴 항목을 클릭할 때 해당 탭 생성 및 활성화
    $("#menu a").on("click", function(event) {
        event.preventDefault();
        var tabName = $(this).data("tab");  // 클릭한 메뉴 항목의 data-tab 값
        var tabId = "tabs-" + tabName;  // 탭 ID 설정
        var url = $(this).attr("href");  // 링크의 href 값을 가져옴

        var existingTab = $("#" + tabId);

        // 이미 탭이 존재하면 해당 탭으로 이동
        if (existingTab.length === 0) {
            // 새로운 탭을 탭 목록에 추가
            var tabLabel = tabName;
            var tabContentHtml = "<p>로딩 중...</p>";  // 기본 텍스트 설정

            // 새로운 탭을 탭 목록에 추가
            $("#tabs ul").append("<li><a href='#" + tabId + "'>" + tabLabel + "</a><span class='ui-icon ui-icon-close' role='presentation'></span></li>");
            $("#tabs").append("<div id='" + tabId + "'><p>" + tabContentHtml + "</p></div>");
            $("#tabs").tabs("refresh");

            // Ajax로 외부 콘텐츠를 로드하여 해당 탭에 삽입
            $.ajax({
                url: url, // href 값을 url로 사용
                method: 'GET',
                success: function(data) {
                    $("#" + tabId).html(data);  // 외부 콘텐츠를 해당 탭에 삽입
                },
                error: function() {
                    $("#" + tabId).html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                }
            });
        }

        // 해당 탭을 활성화
        $("#tabs").tabs("option", "active", $("#tabs").find("a[href='#" + tabId + "']").parent().index());
    });


// 닫기 아이콘 클릭 시 해당 탭 삭제
    tabs.on("click", "span.ui-icon-close", function() {
        $(this).text('');  // 텍스트 제거
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });

    // 백스페이스 키를 누를 때 활성 탭을 삭제
    tabs.on("keyup", function(event) {
        if (event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE) {
            var panelId = tabs.find(".ui-tabs-active").remove().attr("aria-controls");
            $("#" + panelId).remove();
            tabs.tabs("refresh");
        }
    });
});