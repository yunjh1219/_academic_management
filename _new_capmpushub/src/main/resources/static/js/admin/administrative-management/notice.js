//저장
document.getElementById('admin-notice-saveBtn').addEventListener('click',function (){

    const token = localStorage.getItem('jwtToken');
    const urlType = document.getElementById('noticeType').value;

    if(urlType === "school"){

        const url = '/api/admin/notice'
        const noticeType = document.getElementById('admin_noticeType').value;
        const noticeTitle = document.getElementById('admin_notice_title').value;
        const noticeContent = document.getElementById('admin_notice_content').value;

        const NoticeData = {
            noticeType: noticeType,
            noticeTitle: noticeTitle,
            noticeContent:noticeContent
        };

        fetch(url, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(NoticeData)
        })
            .then(response => {
                if (response.ok) {
                    alert('새로운 공지사항이 성공적으로 저장되었습니다.');
                } else {
                    throw new Error('저장 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('공지사항 정보를 저장하는 중 오류가 발생했습니다.');
            });

    }else {

        const deptNoticeCreateUrl = '/api/admin/deptnotice'
        const deptNoticeType = document.getElementById('admin_noticeType').value;
        const deptName = document.getElementById('admin_noticeDept').value;
        const noticeTitle = document.getElementById('admin_notice_title').value;
        const noticeContent = document.getElementById('admin_notice_content').value;

        const DeptNoticeData = {
            deptNoticeType: deptNoticeType,
            deptName: deptName,
            noticeTitle: noticeTitle,
            noticeContent: noticeContent
        };

        fetch(deptNoticeCreateUrl, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(DeptNoticeData)
        })
            .then(response => {
                if (response.ok) {
                    alert('새로운 학과 공지사항이 성공적으로 저장되었습니다.');
                } else {
                    throw new Error('학과 공지사항 저장 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('학과 공지사항 정보를 저장하는 중 오류가 발생했습니다.');
            });
    }
})

//조회
document.getElementById('admin-notice-searchBtn').addEventListener('click', function () {
    const token = localStorage.getItem('jwtToken');
    const noticeType = '학사';
    const url = `/api/notice/condition?noticeType=${encodeURIComponent(noticeType)}`;

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("서버 응답 데이터:", data);
            const tableBody = document.getElementById('admin-notice-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((admin_notice, index) => {
                const createdAtTime = formatTime(admin_notice.createdAt);


                const row = document.createElement('tr');
                row.dataset.id = admin_notice.id;
                row.innerHTML = `
                    <td><input type="checkbox" class="admin_notice-checkbox"></td>
                    <td>${index + 1}</td>
                    <td data-field="noticeTitle">${admin_notice.noticeTitle}</td>
                    <td data-field="userName" style="text-align: left;">${admin_notice.userName}</td>
                    <td data-field="createdAt">${createdAtTime}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항 정보를 불러오는 중 오류가 발생했습니다.');
        });
});


//단건조회
document.getElementById('admin-notice-TableBody').addEventListener('click', function (event) {
    const clickedRow = event.target.closest('tr');
    if (clickedRow) {
        const previouslySelectedRow = document.querySelector('#admin-notice-TableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }
        clickedRow.classList.add('selected');

        // 선택된 행의 noticeId 가져오기
        const noticeId = clickedRow.dataset.id; // data-id 속성에서 가져오기
        console.log('선택된 공지사항 ID:', noticeId);

        const token = localStorage.getItem('jwtToken');

        fetch(`/api/notice/condition/${noticeId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답에 문제가 발생했습니다.');
                }
                return response.json();
            })
            .then(data => {
                console.log('공지사항 상세 정보:', data);


                // 데이터 편집 창에 반영
                if (data && data.data) { // 데이터가 존재하는지 확인
                    const admin_notice = data.data; // 학생 정보 객체
                    document.getElementById('admin_notice_title').value = admin_notice.noticeTitle || '제목없음';
                    document.getElementById('admin_notice_content').value = admin_notice.noticeContent || '내용없음';
                    document.getElementById('admin_notice_createdate').value = formatTime(admin_notice.createdAt) || '생성일자없음';
                    document.getElementById('admin_notice_updatedate').value = formatTime(admin_notice.updatedAt)|| '';
                    document.getElementById('admin_notice_author').value = admin_notice.userName || '작성자없음';
                } else {
                    alert('학생 정보를 찾을 수 없습니다.');
                }
            })
            .catch(error => {
                console.error('AJAX 요청 중 오류가 발생했습니다:', error);
                alert('공지사항 정보를 불러오는 중 오류가 발생했습니다.');
            });
    }
});

// 신규 공지사항 추가
document.getElementById('admin-notice-newBtn').addEventListener('click', function () {
    // 입력 폼 초기화
    document.getElementById('admin_notice_title').value = '';
    document.getElementById('admin_notice_content').value = '';
    document.getElementById('admin_notice_createdate').value = '';
    document.getElementById('admin_notice_updatedate').value = '';
    document.getElementById('admin_notice_author').value = '';

    alert('새로운 공지사항을 추가하였습니다. 정보를 입력해주세요.');

    const tableBody = document.getElementById('admin-notice-TableBody');
    const newNoticeId = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성
    const displayId = newNoticeId.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = newNoticeId;  // 고유한 data-id 부여

    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="admin_notice-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="noticeTitle">제목없음</td>
        <td data-field="userName">작성자없음</td>
        <td data-field="createdAt">${today}</td>
    `;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-notice-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('admin-notice-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTimeNoticeEditing(newRow);
});

// 실시간 수정 반영
function addRealTimeNoticeEditing(row) {
    const admin_notice_titleCell = row.querySelector('td[data-field="noticeTitle"]');
    const admin_notice_contentCell = row.querySelector('td[data-field="content"]');
    const admin_notice_authorCell = row.querySelector('td[data-field="userName"]');

    // 실시간으로 입력되는 값 반영
    document.getElementById('admin_notice_title').addEventListener('input', function () {
        if (admin_notice_titleCell) {
            admin_notice_titleCell.textContent = this.value;  // 제목 수정 반영
        }
    });

    document.getElementById('admin_notice_content').addEventListener('input', function () {
        if (admin_notice_contentCell) {
            admin_notice_contentCell.textContent = this.value;  // 내용 수정 반영
        }
    });

    document.getElementById('admin_notice_author').addEventListener('input', function () {
        if (admin_notice_authorCell) {
            admin_notice_authorCell.textContent = this.value;  // 작성자 수정 반영
        }
    });
}


//타임포맷
function formatTime(dateTime) {
    const date = new Date(dateTime);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${year}.${month}.${day} ${hours}:${minutes}`;
}

const adminOptions = {
    school: [
        { value: "", text: "전체"},
        { value: "학사", text: "학사" },
        { value: "일반", text: "일반" },
        { value: "입찰", text: "입찰" },
        { value: "채용", text: "채용" }
    ],
    department: [
        { value: "", text: "전체"},
        { value: "학부", text: "학부" },
        { value: "장학", text: "장학" },
        { value: "공통", text: "공통" }
    ]
};

const noticeTypeSelect = document.getElementById("noticeType");
const adminNoticeTypeSelect = document.getElementById("admin_noticeType");
const adminNoticeDeptInput = document.getElementById("admin_noticeDept");

// 옵션 초기화 및 추가 함수
function updateAdminNoticeTypeOptions(selectedType) {
    adminNoticeTypeSelect.innerHTML = ""; // 기존 옵션 제거

    if (!selectedType || !adminOptions[selectedType]) return;

    adminOptions[selectedType].forEach(option => {
        const optElement = document.createElement("option");
        optElement.value = option.value;
        optElement.textContent = option.text;
        adminNoticeTypeSelect.appendChild(optElement);
    });
}

// 입력 필드 활성화/비활성화 함수
function toggleDeptInput(selectedType) {
    if (selectedType === "department") {
        adminNoticeDeptInput.disabled = false; // department 선택 시 입력 가능
    } else {
        adminNoticeDeptInput.disabled = true; // 다른 경우에는 입력 불가
    }
}

// 이벤트 리스너 등록
noticeTypeSelect.addEventListener("change", (event) => {
    updateAdminNoticeTypeOptions(event.target.value);
    toggleDeptInput(event.target.value); // noticeType 값에 따라 입력 필드 활성화/비활성화
});