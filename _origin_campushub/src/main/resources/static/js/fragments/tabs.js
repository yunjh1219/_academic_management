$(function() {
    var tabs = $("#tabs").tabs();

    // 메뉴 항목 클릭 이벤트
    $("#menu .snb_depth3 a").off("click").on("click", function (event) {
        event.preventDefault();
        var tabName = $(this).data("tab");
        var tabId = $(this).data("id");
        var url = $(this).attr("href");
        openTab(tabName, url, tabId);
    });

    // 탭 생성 및 활성화 로직을 함수로 분리
    function openTab(tabName, url, tabId) {

        var existingTab = $("#" + tabId);

        // 🔥 최대 8개 탭 제한 추가
        if ($("#tabs ul li").length >= 8 && existingTab.length === 0) {
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
                success: function (data) {
                    $("#" + tabId).html(data);  // 콘텐츠 삽입

                },
                error: function () {
                    $("#" + tabId).html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                }
            });
        }
        // 새로 생성된 또는 기존 탭을 활성화
        tabs.tabs("option", "active", $("#tabs").find("a[href='#" + tabId + "']").parent().index());
    }


    // 바로가기 링크 클릭 이벤트
    $("a.shortcut-link").on("click", function (event) {
        event.preventDefault();  // 기본 링크 동작 방지
        var tabName = $(this).data("tab");
        var url = $(this).attr("href");
        openTab(tabName, url);
    });

    // load-content 링크 클릭 이벤트
    $("a.load-content").on("click", function (event) {
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
                    success: function (data) {
                        activeTabContent.html(data); // 현재 활성화된 탭에 콘텐츠 로드
                        initializeTabScripts("#" + activeTabId);  // 새로 로드된 탭의 스크립트 초기화
                    },
                    error: function () {
                        activeTabContent.html("<p>콘텐츠를 불러오는 데 실패했습니다.</p>");
                    }
                });
            }
        }
    });

    // 닫기 아이콘 클릭 시 탭 삭제
    tabs.on("click", "span.ui-icon-close", function () {
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });

    // 전체 탭 닫기 버튼 클릭 이벤트
    $(".close-btn").on("click", function () {
        // 모든 탭 삭제
        $("#tabs ul li").remove();  // 탭 목록 제거
        $("#tabs .ui-tabs-panel").remove();  // 탭 내용 제거
        tabs.tabs("refresh");  // 탭 리프레시
    });

    // var tabToActivate = $('a[data-tab=학사공지사항]');

    // 현재 열린 탭이 없을 때만 기본 탭을 활성화
    // if ($("#tabs ul li").length === 0) {  // 탭 목록이 비어있다면
    //     if (tabToActivate.length > 0) {
    //         // 기본 탭을 활성화
    //         var tabName = tabToActivate.data("tab");
    //         var url = tabToActivate.attr("href");
    //         openTab(tabName, url);
    //     }
    // }


// 탭이 활성화될 때마다 강의명과 주차를 불러오도록 설정
    tabs.on("tabsactivate", function (event, ui) {
        var activeTabId = ui.newPanel.attr('id'); // 콘텐츠 영역 ID


        if (activeTabId === "stumana" || activeTabId === "profmana" || activeTabId === "adminiship") {
            admin_loadDeptName(activeTabId);  // 현재 탭의 ID를 전달
        }

        if(activeTabId === "prof-attda" || activeTabId === "prof-assch"){
            prof_loadCourseWeek(activeTabId)
        }

        if(activeTabId === "stu-courion") {
            stu_loadDeptName(activeTabId)
        }

        if(activeTabId === "stu-atteniry"){
            stu_loadCourse(activeTabId)
        }

        if(activeTabId === "stu-assiry"){
            stu_loadCourseWeek(activeTabId)
        }

    });

    function stu_loadCourseWeek(activeTabId) {
        const token = localStorage.getItem('jwtToken');
        const url = "/api/student/course";

        fetch(url, {
            method: 'GET',  // GET 방식으로 요청
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        }).then(response => response.json())  // 응답을 JSON 형식으로 파싱
            .then(data => {

                // data.data에서 학과 목록을 가져오기
                const course = data.data;  // 학과 정보는 data.data 안에 있음

                const courseNameSelect = document.getElementById('stu-'+activeTabId+'-courseName');
                const weekSelect = document.getElementById('stu-'+activeTabId+'-week');

                weekSelect.innerHTML = '';  // 주차 셀렉트 박스 초기화

                courseNameSelect.innerHTML = '<option value="">강의명 선택</option>';  // 셀렉트 박스 초기화
                // 받은 학과 데이터로 option 추가
                course.forEach(course => {
                    const option = document.createElement('option');
                    option.textContent = course.courseName;   // 학과 ID를 value로 설정
                    option.textContent = course.courseName;   // 학과 이름을 텍스트로 설정
                    courseNameSelect.appendChild(option);  // option을 셀렉트 박스에 추가
                });

                // 주차 옵션 추가 (1부터 19까지)
                const defaultWeekOption = document.createElement('option');
                defaultWeekOption.value = '';
                defaultWeekOption.textContent = '주차를 선택하세요';
                defaultWeekOption.disabled = true;
                defaultWeekOption.selected = true;
                weekSelect.appendChild(defaultWeekOption);

                for (let i = 1; i <= 16; i++) {
                    const weekOption = document.createElement('option');
                    weekOption.value = `${i}주차`;  // 주차 값 설정
                    weekOption.textContent = `${i}주차`;  // 주차 표시
                    weekSelect.appendChild(weekOption);  // week 셀렉트 박스에 옵션 추가
                }

            })
            .catch(error => {
                console.error('Error가 생겼어용:', error);  // 에러 발생 시 출력
            });
    }

    function stu_loadCourse(activeTabId){
        const token = localStorage.getItem('jwtToken');
        const url = "/api/student/course";

        fetch(url, {
            method: 'GET',  // GET 방식으로 요청
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        }).then(response => response.json())  // 응답을 JSON 형식으로 파싱
            .then(data => {
                // data.data에서 학과 목록을 가져오기
                const course = data.data;  // 학과 정보는 data.data 안에 있음

                const courseNameSelect = document.getElementById('stu-'+activeTabId+'-courseName');
                courseNameSelect.innerHTML = '<option value="">학과명 선택</option>';  // 셀렉트 박스 초기화
                // 받은 학과 데이터로 option 추가
                course.forEach(course => {
                    const option = document.createElement('option');
                    option.textContent = course.courseName;   // 학과 ID를 value로 설정
                    option.textContent = course.courseName;  // 학과 이름을 텍스트로 설정
                    courseNameSelect.appendChild(option);  // option을 셀렉트 박스에 추가
                });
            })
            .catch(error => {
                console.error('Error가 생겼어용:', error);  // 에러 발생 시 출력
            });
    }

    function stu_loadDeptName(activeTabId){
        const token = localStorage.getItem('jwtToken');
        const url = "/api/admin/dept/all";

        fetch(url, {
            method: 'GET',  // GET 방식으로 요청
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        }).then(response => response.json())  // 응답을 JSON 형식으로 파싱
            .then(data => {
                // data.data에서 학과 목록을 가져오기
                const departments = data.data;  // 학과 정보는 data.data 안에 있음

                const deptNameSelect = document.getElementById('stu-'+activeTabId+'-deptName');
                deptNameSelect.innerHTML = '<option value="">학과명 선택</option>';  // 셀렉트 박스 초기화
                // 받은 학과 데이터로 option 추가
                departments.forEach(department => {
                    const option = document.createElement('option');
                    option.textContent = department.deptName;   // 학과 ID를 value로 설정
                    option.textContent = department.deptName;  // 학과 이름을 텍스트로 설정
                    deptNameSelect.appendChild(option);  // option을 셀렉트 박스에 추가
                });
            })
            .catch(error => {
                console.error('Error가 생겼어용:', error);  // 에러 발생 시 출력
            });
    }



    function prof_loadCourseWeek(activeTabId){
        const token = localStorage.getItem('jwtToken');
        const url = "/api/professor/course";

        fetch(url, {
            method: 'GET',  // GET 방식으로 요청
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        }).then(response => response.json())  // 응답을 JSON 형식으로 파싱
          .then(data => {

                // data.data에서 학과 목록을 가져오기
                const course = data.data;  // 학과 정보는 data.data 안에 있음

                const courseNameSelect = document.getElementById('prof-'+activeTabId+'-courseName');
                const weekSelect = document.getElementById('prof-'+activeTabId+'-week');  // week 셀렉트 박스 ID

                weekSelect.innerHTML = '';  // 주차 셀렉트 박스 초기화

                courseNameSelect.innerHTML = '<option value="">강의명 선택</option>';  // 셀렉트 박스 초기화
                // 받은 학과 데이터로 option 추가
                course.forEach(course => {
                    const option = document.createElement('option');
                    option.textContent = course.courseName;   // 학과 ID를 value로 설정
                    option.textContent = course.courseName;   // 학과 이름을 텍스트로 설정
                    courseNameSelect.appendChild(option);  // option을 셀렉트 박스에 추가
                });

                // 주차 옵션 추가 (1부터 19까지)
                const defaultWeekOption = document.createElement('option');
                defaultWeekOption.value = '';
                defaultWeekOption.textContent = '주차를 선택하세요';
                defaultWeekOption.disabled = true;
                defaultWeekOption.selected = true;
                weekSelect.appendChild(defaultWeekOption);

                for (let i = 1; i <= 16; i++) {
                    const weekOption = document.createElement('option');
                    weekOption.value = `${i}주차`;  // 주차 값 설정
                    weekOption.textContent = `${i}주차`;  // 주차 표시
                    weekSelect.appendChild(weekOption);  // week 셀렉트 박스에 옵션 추가
                }

            })
            .catch(error => {
                console.error('Error가 생겼어용:', error);  // 에러 발생 시 출력
            });
    }

    function admin_loadDeptName(activeTabId) {
        const token = localStorage.getItem('jwtToken');
        const url = "/api/admin/dept/all";

        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
            }
        })
            .then(response => response.json())  // 응답을 JSON 형식으로 파싱
            .then(data => {

                // data.data에서 학과 목록을 가져오기
                const departments = data.data;  // 학과 정보는 data.data 안에 있음

                const deptNameSelect = document.getElementById('admin_'+activeTabId+'_deptName');
                deptNameSelect.innerHTML = '<option value="">학과명 선택</option>';  // 셀렉트 박스 초기화
                // 받은 학과 데이터로 option 추가
                departments.forEach(department => {
                    const option = document.createElement('option');
                    option.textContent = department.deptName;   // 학과 ID를 value로 설정
                    option.textContent = department.deptName;  // 학과 이름을 텍스트로 설정
                    deptNameSelect.appendChild(option);  // option을 셀렉트 박스에 추가
                });
            })
            .catch(error => {
                console.error('Error가 생겼어용:', error);  // 에러 발생 시 출력
            });
    }



})
