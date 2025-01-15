// '검색' 버튼 클릭 시 공지사항 목록을 불러오는 함수
document.getElementById('searchBtn').addEventListener('click', function() {
    const url = '/admin-potal/notices';  // 공지사항 목록을 가져올 URL

    fetch(url)  // fetch API를 사용하여 서버에서 공지사항 데이터를 요청
        .then(response => response.json())  // 응답을 JSON 형식으로 변환
        .then(data => {
            const tableBody = document.getElementById('noticeTableBody');  // 공지사항 테이블의 tbody 요소
            tableBody.innerHTML = ''; // 기존 데이터를 제거 (새로고침)

            // 서버에서 받은 데이터를 기반으로 테이블에 새로운 행 추가
            data.forEach((notice, index) => {  // index 값을 사용하여 순차적인 번호를 할당
                const row = document.createElement('tr');  // 새로운 행 추가

                const formattedDate = new Date(notice.createdAt).toLocaleDateString('ko-KR', {  // 날짜 포맷팅
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                });

                // 각 공지사항 데이터를 테이블 행에 삽입 (순차적인 번호 포함)
                row.innerHTML = `
                    <td><input type="checkbox" class="notice-checkbox"></td>  <!-- 체크박스 -->
                    <td>${index + 1}</td>  <!-- 순차적인 번호 (1부터 시작) -->
                    <td style="text-align: left;">${notice.title}</td>  <!-- 제목 -->
                    <td>${notice.author}</td>  <!-- 작성자 -->
                    <td>${formattedDate}</td>  <!-- 작성일자 -->
                `;
                tableBody.appendChild(row);  // 테이블에 새로운 행 추가
            });
        })
        .catch(error => {
            console.error('Error:', error);  // 에러가 발생하면 콘솔에 에러 메시지 출력
            alert('공지사항을 불러오는 중 오류가 발생했습니다.');  // 사용자에게 오류 메시지 표시
        });
});

// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('newNoticeBtn').addEventListener('click', function() {
    const tableBody = document.getElementById('noticeTableBody');

    // 현재 테이블에서 각 행에 순차적인 번호를 부여
    const rows = tableBody.querySelectorAll('tr');
    const newNoticeNo = rows.length + 1;  // 새로운 번호는 현재 테이블 행 수에 1을 더한 값

    // 새로운 행(tr) 생성
    const newRow = document.createElement('tr');
    newRow.dataset.id = newNoticeNo;  // 고유한 data-id 부여 (순차적인 번호)

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="notice-checkbox"></td>
        <td>${newNoticeNo}</td> <!-- 신규 번호 표시 -->
        <td contenteditable="true" style="text-align: left;">제목을 입력하세요</td>
        <td contenteditable="true">작성자</td>
        <td>${today}</td>
    `;

    // 테이블에 행 추가 (기존 목록의 아래에 추가)
    tableBody.appendChild(newRow); // 기존 목록의 아래에 새로운 행을 추가

    // 선택된 행 강조
    newRow.classList.add('selected');

    // 제목, 내용 등 폼 필드 초기화
    document.getElementById('title').value = '제목을 입력하세요';
    document.getElementById('content').value = '';
    document.getElementById('author').value = '작성자';
    document.getElementById('createdate').value = today;
    document.getElementById('updatedate').value = today;

    alert('새로운 공지사항을 추가했습니다. 내용을 입력하세요.');
});



// 공지사항 테이블에서 특정 행을 클릭했을 때 데이터 표시 및 실시간 업데이트 처리
document.getElementById('noticeTableBody').addEventListener('click', function(event) {
    if (event.target.tagName === 'TD') {  // 클릭한 요소가 'TD'일 경우
        const row = event.target.parentElement;  // 클릭한 행을 가져옴
        const cells = row.getElementsByTagName('td');  // 해당 행의 모든 셀 가져오기
        const noticeId = cells[1].textContent;  // 두 번째 셀의 텍스트(content)인 공지사항 ID 가져오기 (ID는 두 번째 칸에 있다고 가정)

        // 해당 공지사항의 상세 정보를 불러옴
        fetch(`/admin-potal/notices/${noticeId}`)
            .then(response => response.json())  // 응답을 JSON 형식으로 변환
            .then(notice => {
                // 데이터 폼에 채우기
                document.getElementById('title').value = notice.title;
                document.getElementById('content').value = notice.content;
                document.getElementById('file').value = '';  // 파일 첨부 필드는 비워둠
                document.getElementById('createdate').value = new Date(notice.createdAt).toLocaleDateString();  // 작성일자
                document.getElementById('updatedate').value = new Date().toLocaleDateString();  // 수정일자 (현재 날짜로 표시)
                document.getElementById('author').value = notice.author;  // 작성자

                // **실시간으로 테이블 업데이트**
                // 제목을 수정하면 테이블의 제목도 실시간으로 반영
                document.getElementById('title').addEventListener('input', function() {
                    cells[2].textContent = this.value;  // 제목 필드 수정 시, 테이블의 제목 셀 내용 변경
                });

                // 내용 입력란을 수정하면 테이블의 내용도 실시간으로 반영
                document.getElementById('content').addEventListener('input', function() {
                    cells[3].textContent = this.value;  // 내용 필드 수정 시, 테이블의 내용 셀 내용 변경
                });
            })
            .catch(error => {
                console.error('Error:', error);  // 에러 발생 시 콘솔에 에러 메시지 출력
                alert('공지사항 정보를 불러오는 중 오류가 발생했습니다.');  // 사용자에게 오류 메시지 표시
            });
    }
});


// '저장' 버튼 클릭 시, 수정된 내용을 서버로 전송하여 저장
document.getElementById('saveBtn').addEventListener('click', function() {
    // 선택된 행에서 data-id를 사용하여 공지사항 ID 가져오기
    const selectedRow = document.querySelector('#noticeTableBody tr[data-id]');
    if (!selectedRow) {
        alert('저장할 공지사항을 선택해주세요.');
        return;
    }

    const noticeId = selectedRow.dataset.id;  // data-id 속성에서 ID 가져오기

    // 수정된 공지사항 데이터를 객체로 생성
    const updatedNotice = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        author: document.getElementById('author').value
    };

    // PUT 요청을 사용하여 서버에 수정 데이터 전송
    fetch(`/admin-potal/notices/${noticeId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedNotice)
    })
        .then(response => {
            if (response.ok) {
                alert('공지사항이 성공적으로 저장되었습니다.');
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항을 저장하는 중 오류가 발생했습니다.');
        });
});


// 테이블에서 클릭한 행을 강조 표시하고 선택된 것으로 간주
document.getElementById('noticeTableBody').addEventListener('click', function(event) {
    if (event.target.tagName === 'TD') {
        const rows = document.querySelectorAll('#noticeTableBody tr');
        rows.forEach(row => row.classList.remove('selected'));  // 모든 행의 선택 해제
        event.target.parentElement.classList.add('selected');  // 선택한 행 강조
    }
});