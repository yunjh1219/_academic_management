window.addEventListener('load', function () {

    const token = localStorage.getItem('jwtToken');


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




        fetch(url=`/api/notice/condition?noticeType=${encodeURIComponent('학사')}`,{
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        }) .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
            .then(data => {
                console.log("서버 응답 데이터:", data);
                const tableBody = document.getElementById('stu-main-acadnotice-TabelBody');
                tableBody.innerHTML = '';

                data.data.forEach((admin_notice, index) => {

                    const formatDate = (dateString) => {
                        const date = new Date(dateString);
                        const year = date.getFullYear();
                        const month = String(date.getMonth() + 1).padStart(2, '0');
                        const day = String(date.getDate()).padStart(2, '0');
                        return `${year}.${month}.${day}`;
                    };

                    const noticeTypeMapping = {
                        BACHELOR: "학사",
                        PROFESSOR: "교수"
                    };

                    const row = document.createElement('tr');
                    row.dataset.id = admin_notice.id;
                    row.innerHTML = `
            <td class="notice-title" data-field="noticeTitle">${admin_notice.noticeTitle}</td>
            <td class="notice-createdAt" data-field="noticeCreated" style="text-align: right;">${formatDate(admin_notice.createdAt)}</td>
        `;

                    // 제목 클릭 시 모달 표시
                    row.querySelector('.notice-title').addEventListener('click', function () {
                        // noticeType 매핑에 따른 한글 설정
                        const noticeTypeText = noticeTypeMapping[admin_notice.noticeType] || "알 수 없는 유형";
                        document.getElementById('stu-modal-noticeType').textContent = `[${noticeTypeText}]`;
                        document.getElementById('stu-modal-noticeTitle').textContent = admin_notice.noticeTitle;
                        document.getElementById('stu-modal-noticeAuth').textContent = admin_notice.userName;
                        document.getElementById('stu-modal-noticeCreatedAt').textContent = formatDate(admin_notice.createdAt);
                        document.getElementById('modal-content').textContent = admin_notice.noticeContent || '내용이 없습니다.';
                        document.getElementById('notice-modal').style.display = 'block';

                    });

                    tableBody.appendChild(row);
                });

                // 모달 닫기
                document.getElementById('close-modal').addEventListener('click', function () {
                    document.getElementById('notice-modal').style.display = 'none';
                });

                // 모달 바깥 클릭 시 닫기
                window.addEventListener('click', function (event) {
                    const modal = document.getElementById('notice-modal');
                    if (event.target === modal) {
                        modal.style.display = 'none';
                    }
                });
            })

    }
});
