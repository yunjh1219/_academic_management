// 조회 버튼 클릭 시 공지사항 목록을 불러오는 함수
document.getElementById('noticeSearchBtn').addEventListener('click', function () {
    const tableBody = document.getElementById('noticeTableBody');
    tableBody.innerHTML = ''; // 기존 데이터를 제거

    // 모의 데이터로부터 공지사항 목록을 테이블에 추가
    mockNotices.forEach((notice, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td><input type="checkbox" class="notice-checkbox"></td>
            <td>${index + 1}</td>
            <td>${notice.title}</td>
            <td>${notice.author}</td>
            <td>${new Date(notice.createdate).toLocaleString()}</td>
        `;
        tableBody.appendChild(row); // 테이블에 행 추가
    });
});

// 공지사항 정보를 저장하는 버튼 클릭 이벤트
document.getElementById('saveBtn').addEventListener('click', function () {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const file = document.getElementById('file').value;
    const author = document.getElementById('author').value; // 작성자
    const createdate = new Date().toISOString(); // 현재 시간
    const updatedate = createdate; // 처음 저장 시 수정일시도 동일

    // 새로운 공지사항 객체 생성
    const newNotice = {
        title: title,
        content: content,
        file: file,
        author: author,
        createdate: createdate,
        updatedate: updatedate
    };

    // 모의 데이터에 추가 (이 부분은 나중에 실제 백엔드 API로 변경)
    mockNotices.push(newNotice);
    alert('공지사항이 저장되었습니다.');

    // 입력 필드 초기화
    document.getElementById('editNoticeForm').reset();
});



// 삭제 버튼 클릭 시 선택된 공지사항 삭제
document.getElementById('deleteBtn').addEventListener('click', function () {
    const checkboxes = document.querySelectorAll('.notice-checkbox:checked');
    checkboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const rowIndex = Array.from(row.parentNode.children).indexOf(row);
        mockNotices.splice(rowIndex, 1); // 모의 데이터에서 삭제
        row.remove(); // 테이블에서 행 제거
    });
    alert('선택된 공지사항이 삭제되었습니다.');
});
