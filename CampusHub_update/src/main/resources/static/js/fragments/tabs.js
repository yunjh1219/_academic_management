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

            $.ajax({
                url: url,
                method: 'GET',
                cache: false,  // 캐시 방지
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

    // 강의명과 주차를 가져오는 함수
    function loadCourseAndWeek() {
// 로컬 스토리지에서 JWT 토큰 가져오기
        const token = localStorage.getItem('jwtToken');
        const url = "/api/professor/course";

// 강의명 정보 가져오기
        fetch(url, {
            method: 'GET',  // GET 방식으로 요청
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        })
            .then(response => response.json())  // 응답을 JSON 형식으로 파싱
            .then(data => {
                console.log('받은 데이터:', data);  // 받아온 데이터를 콘솔에 출력하여 확인

                const courseNameSelect = document.getElementById('courseName');  // courseName 셀렉트 박스 ID
                const weekSelect = document.getElementById('week');  // week 셀렉트 박스 ID
                courseNameSelect.innerHTML = '';  // 셀렉트 박스 초기화
                weekSelect.innerHTML = '';  // 주차 셀렉트 박스 초기화

                // 기본 옵션 유지
                const defaultOption = document.createElement('option');
                defaultOption.value = '';
                defaultOption.textContent = '강의명을 선택하세요';
                defaultOption.disabled = true;  // 선택 불가능한 옵션으로 설정
                defaultOption.selected = true;  // 기본 선택되도록 설정
                courseNameSelect.appendChild(defaultOption);

                // 강의명 데이터를 셀렉트 박스에 추가
                const courses = data.data || [];  // data.data가 배열 형태로 존재하는지 확인
                courses.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course.courseName;  // 강의명으로 value 설정
                    option.textContent = course.courseName;  // 강의명 표시
                    courseNameSelect.appendChild(option);  // courseName 셀렉트 박스에 옵션 추가
                });

                // 주차 옵션 추가 (1부터 19까지)
                const defaultWeekOption = document.createElement('option');
                defaultWeekOption.value = '';
                defaultWeekOption.textContent = '주차를 선택하세요';
                defaultWeekOption.disabled = true;
                defaultWeekOption.selected = true;
                weekSelect.appendChild(defaultWeekOption);

                for (let i = 1; i <= 19; i++) {
                    const weekOption = document.createElement('option');
                    weekOption.value = i;  // 주차 값 설정
                    weekOption.textContent = `${i}주차`;  // 주차 표시
                    weekSelect.appendChild(weekOption);  // week 셀렉트 박스에 옵션 추가
                }
            })
            .catch(error => {
                console.error('Error:', error);  // 에러 발생 시 출력
            });

    }

    // 금일출석관리 -> 강의명과 주차를 가져오는 함수
    function att_loadCourseAndWeek() {
// 로컬 스토리지에서 JWT 토큰 가져오기
        const token = localStorage.getItem('jwtToken');
        const url = "/api/professor/course";

// 강의명 정보 가져오기
        fetch(url, {
            method: 'GET',  // GET 방식으로 요청
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        })
            .then(response => response.json())  // 응답을 JSON 형식으로 파싱
            .then(data => {
                console.log('받은 데이터:', data);  // 받아온 데이터를 콘솔에 출력하여 확인

                const courseNameSelect = document.getElementById('prof_att_courseName');  // courseName 셀렉트 박스 ID
                const weekSelect = document.getElementById('prof_att_week');  // week 셀렉트 박스 ID
                courseNameSelect.innerHTML = '';  // 셀렉트 박스 초기화
                weekSelect.innerHTML = '';  // 주차 셀렉트 박스 초기화

                // 기본 옵션 유지
                const defaultOption = document.createElement('option');
                defaultOption.value = '';
                defaultOption.textContent = '강의명을 선택하세요';
                defaultOption.disabled = true;  // 선택 불가능한 옵션으로 설정
                defaultOption.selected = true;  // 기본 선택되도록 설정
                courseNameSelect.appendChild(defaultOption);

                // 강의명 데이터를 셀렉트 박스에 추가
                const courses = data.data || [];  // data.data가 배열 형태로 존재하는지 확인
                courses.forEach(course => {
                    const option = document.createElement('option');
                    option.value = course.courseName;  // 강의명으로 value 설정
                    option.textContent = course.courseName;  // 강의명 표시
                    courseNameSelect.appendChild(option);  // courseName 셀렉트 박스에 옵션 추가
                });

                // 주차 옵션 추가 (1부터 19까지)
                const defaultWeekOption = document.createElement('option');
                defaultWeekOption.value = '';
                defaultWeekOption.textContent = '주차를 선택하세요';
                defaultWeekOption.disabled = true;
                defaultWeekOption.selected = true;
                weekSelect.appendChild(defaultWeekOption);

                for (let i = 1; i <= 19; i++) {
                    const weekOption = document.createElement('option');
                    weekOption.value = i;  // 주차 값 설정
                    weekOption.textContent = `${i}주차`;  // 주차 표시
                    weekSelect.appendChild(weekOption);  // week 셀렉트 박스에 옵션 추가
                }
            })
            .catch(error => {
                console.error('Error:', error);  // 에러 발생 시 출력
            });

    }

    // 탭이 활성화될 때마다 강의명과 주차를 불러오도록 설정
    tabs.on("tabsactivate", function(event, ui) {
        var activeTabId = ui.newPanel.attr('id'); // 활성화된 탭의 ID 확인

        // 과제확인 탭일 경우에만 강의명과 주차를 불러옴
        if (activeTabId === "tabs-과제확인") {
            loadCourseAndWeek();
        }
        if (activeTabId == "tabs-금일출석관리"){
            att_loadCourseAndWeek()
        }

    });

    // 메뉴 항목 클릭 이벤트
    $("#menu .snb_depth3 a").off("click").on("click", function(event) {
        event.preventDefault();
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
                        initializeTabScripts("#" + activeTabId);  // 새로 로드된 탭의 스크립트 초기화
                    },
                    error: function() {
                        activeTabContent.html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                    }
                });
            }
        }
    });

    // 닫기 아이콘 클릭 시 탭 삭제
    tabs.on("click", "span.ui-icon-close", function() {
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });

    // 전체 탭 닫기 버튼 클릭 이벤트
    $(".close-btn").on("click", function() {
        // 모든 탭 삭제
        $("#tabs ul li").remove();  // 탭 목록 제거
        $("#tabs .ui-tabs-panel").remove();  // 탭 내용 제거
        tabs.tabs("refresh");  // 탭 리프레시
    });

    var tabToActivate = $('a[data-tab=공지사항]');

    // 현재 열린 탭이 없을 때만 기본 탭을 활성화
    if ($("#tabs ul li").length === 0) {  // 탭 목록이 비어있다면
        if (tabToActivate.length > 0) {
            // 기본 탭을 활성화
            var tabName = tabToActivate.data("tab");
            var url = tabToActivate.attr("href");
            openTab(tabName, url);
        }
    }
});
