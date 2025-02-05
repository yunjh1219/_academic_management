window.addEventListener('load', function () {
    let currentYear = 2025;  // 초기 연도
    let currentMonth = 1;    // 초기 월 (1월)

    // 초기 학사일정 및 공지사항 로딩
    loadAcademicCalendar(currentYear, currentMonth);

    // 장학금 정보 로딩
    loadScholarshipInfo();

    // 장학금 정보를 로드하는 함수
    function loadScholarshipInfo() {
        // 장학금 샘플 데이터
        const scholarships = [
            {
                name: "2025년 학기 장학금",
                type: "사전감면",
                amount: "500,000원"
            },
            {
                name: "학과 우수 장학금",
                type: "사후감면",
                amount: "300,000원"
            },
            {
                name: "성적 향상 장학금",
                type: "사후감면",
                amount: "200,000원"
            }
        ];

        // 장학금 테이블을 동적으로 채울 tbody 요소
        const scholarshipTableBody = document.querySelector('#scholarship-table tbody');
        scholarshipTableBody.innerHTML = "";  // 기존 내용 비우기

        // 장학금 데이터 출력
        scholarships.forEach(scholarship => {
            const row = document.createElement('tr');

            // 장학금명
            const nameCell = document.createElement('td');
            nameCell.textContent = scholarship.name;
            row.appendChild(nameCell);

            // 지급구분
            const typeCell = document.createElement('td');
            typeCell.textContent = scholarship.type;
            row.appendChild(typeCell);

            // 장학금액
            const amountCell = document.createElement('td');
            amountCell.textContent = scholarship.amount;
            row.appendChild(amountCell);

            // 테이블에 행 추가
            scholarshipTableBody.appendChild(row);
        });
    }

    // 이전 달 버튼 클릭 시
    document.getElementById('prev-month').addEventListener('click', function () {
        if (currentMonth === 1) {
            currentMonth = 12;
            currentYear -= 1;  // 1월에서 이전으로 가면 연도가 바뀜
        } else {
            currentMonth -= 1;
        }
        loadAcademicCalendar(currentYear, currentMonth);
    });

    // 다음 달 버튼 클릭 시
    document.getElementById('next-month').addEventListener('click', function () {
        if (currentMonth === 12) {
            currentMonth = 1;
            currentYear += 1;  // 12월에서 다음으로 가면 연도가 바뀜
        } else {
            currentMonth += 1;
        }
        loadAcademicCalendar(currentYear, currentMonth);
    });

    // 학사일정을 로드하는 함수
    function loadAcademicCalendar(year, month) {
        // 월 제목 업데이트
        document.getElementById('month-title').textContent = `${year}년 ${month}월`;

        // 샘플 데이터
        const data = {
            "departmentNotices": [
                {
                    "title": "2025학년도 학과 공지사항",
                    "date": "2025-01-20"
                },
                {
                    "title": "학과 세미나 일정",
                    "date": "2025-01-25"
                }
            ],
            "academicCalendar": [
                {
                    "title": "개강일",
                    "date": "2025-03-01"
                },
                {
                    "title": "중간고사 기간",
                    "date": "2025-04-15"
                }
            ]
        };

        // 해당 월의 학사 일정만 필터링
        const filteredEvents = data.academicCalendar.filter(event => {
            const eventDate = new Date(event.date);
            return eventDate.getFullYear() === year && eventDate.getMonth() + 1 === month;
        });

        // 공지사항 최신 5개 가져오기 (날짜 상관없이)
        const filteredNotices = data.departmentNotices.slice(0, 5);  // 최신 5개 선택

        // 학사 일정 테이블 채우기
        const academicCalendarTable = document.querySelector("#academic-calendar tbody");
        academicCalendarTable.innerHTML = "";  // 기존 테이블 데이터 비우기

        filteredEvents.forEach(event => {
            const row = document.createElement("tr");

            // 날짜와 제목을 결합하여 하나의 셀에 넣기
            const dateAndTitleCell = document.createElement("td");
            const date = new Date(event.date);
            dateAndTitleCell.textContent = `${date.getDate()}일 - ${event.title}`;  // "날짜 - 제목" 형식
            row.appendChild(dateAndTitleCell);

            academicCalendarTable.appendChild(row);
        });

        // 공지사항 테이블 채우기
        const departmentNoticesTable = document.querySelector("#department-notices tbody");
        departmentNoticesTable.innerHTML = "";  // 기존 공지사항 데이터 비우기

        filteredNotices.forEach(notice => {
            const row = document.createElement("tr");

            // 공지사항 제목만 표시
            const titleCell = document.createElement("td");
            titleCell.textContent = notice.title;

            // 제목에 스타일 적용
            titleCell.classList.add("notice-title");

            row.appendChild(titleCell);

            departmentNoticesTable.appendChild(row);
        });
    }
});
