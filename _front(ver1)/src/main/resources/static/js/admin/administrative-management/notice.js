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

                // 각 공지사항 데이터를 테이블 행에 삽입
                row.dataset.id = notice.id;  // 실제 DB에서 받아온 고유 ID를 dataset.id로 설정
                row.innerHTML = `
                    <td><input type="checkbox" class="notice-checkbox"></td>  <!-- 체크박스 -->
                     <td data-field="index">${index + 1}</td>  <!-- 순차적인 번호 (1부터 시작) -->
                    <td data-field="title" style="text-align: left;">${notice.title}</td>  <!-- 제목 -->
                    <td data-field="author">${notice.author}</td>  <!-- 작성자 -->
                   <td data-field="createdAt">${formattedDate}</td>  <!-- 작성일자 -->
                `;
                tableBody.appendChild(row);  // 테이블에 새로운 행 추가
            });
        })
        .catch(error => {
            console.error('Error:', error);  // 에러 발생 시 콘솔에 에러 메시지 출력
            alert('공지사항을 불러오는 중 오류가 발생했습니다.');  // 사용자에게 오류 메시지 표시
        });
});


// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('newNoticeBtn').addEventListener('click', function() {
    const tableBody = document.getElementById('noticeTableBody');

    // 신규 공지사항 ID 관리 (단순히 increment하는 방식 사용)
    const newNoticeId = `new-${Date.now()}`; // 고유한 ID 생성 (시간을 기반으로)

    // 새로운 행(tr) 생성
    const newRow = document.createElement('tr');
    newRow.dataset.id = newNoticeId;  // 고유한 data-id 부여

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="notice-checkbox"></td>
        <td>${newNoticeId}</td> <!-- 신규 ID 표시 -->
        <td contenteditable="true" style="text-align: left;">제목을 입력하세요</td>
        <td contenteditable="true">작성자</td>
        <td>${today}</td>
    `;

    // 테이블에 행 추가
    tableBody.prepend(newRow); // 가장 위에 새로운 행을 추가

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


// 공지사항 테이블 행 클릭 시 발생하는 이벤트
// 공지사항 테이블에서 특정 행을 클릭했을 때 데이터 표시 및 실시간 업데이트 처리
document.getElementById('noticeTableBody').addEventListener('click', function(event) {
    if (event.target.tagName === 'TD') {  // 클릭한 요소가 'TD'일 경우
        const row = event.target.parentElement;  // 클릭한 행을 가져옴
        const noticeId = row.dataset.id;  // 해당 행의 dataset.id를 noticeId로 사용 (실제 DB의 고유 ID)

        // 기존에 선택된 행에서 "selected" 클래스 제거
        const previouslySelectedRow = document.querySelector('#noticeTableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }

        // 현재 클릭한 행에 "selected" 클래스 추가
        row.classList.add('selected');

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
                // 제목 수정 시, 테이블의 제목 셀 실시간으로 반영
                document.getElementById('title').addEventListener('input', function() {
                    // 제목을 수정하면 테이블의 제목 셀을 업데이트
                    const selectedRow = document.querySelector('#noticeTableBody tr.selected');  // 선택된 행 찾기
                    if (selectedRow) {
                        const titleCell = selectedRow.querySelector('td[data-field="title"]');  // data-field="title"인 셀 찾기
                        if (titleCell) {
                            titleCell.textContent = this.value;  // 제목 수정 반영
                        }
                    }
                });

                // 내용 입력란을 수정하면 테이블의 내용도 실시간으로 반영
                document.getElementById('content').addEventListener('input', function() {
                    // 내용을 수정하면 테이블의 내용 셀을 업데이트
                    const selectedRow = document.querySelector('#noticeTableBody tr.selected');  // 선택된 행 찾기
                    if (selectedRow) {
                        const contentCell = selectedRow.querySelector('td[data-field="content"]');  // data-field="content"인 셀 찾기
                        if (contentCell) {
                            contentCell.textContent = this.value;  // 내용 수정 반영
                        }
                    }
                });
            })
            .catch(error => {
                console.error('Error:', error);  // 에러 발생 시 콘솔에 에러 메시지 출력
                alert('공지사항 정보를 불러오는 중 오류가 발생했습니다.');  // 사용자에게 오류 메시지 표시
            });
    }
});



document.getElementById('saveBtn').addEventListener('click', function() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const author = document.getElementById('author').value;

    // 공지사항 ID가 있는지 확인 (수정일 경우 ID가 존재)
    const selectedRow = document.querySelector('#noticeTableBody tr.selected');  // 선택된 행 찾기
    let url = '';
    let method = '';
    let noticeId = null;

    if (selectedRow) {
        // 수정: PUT 요청
        noticeId = selectedRow.dataset.id;  // 선택된 공지사항의 ID
        url = `/admin-potal/notices/${noticeId}`;
        method = 'PUT';
    } else {
        // 신규: POST 요청
        url = '/admin-potal/notices';
        method = 'POST';
    }

    // 공지사항 데이터 객체 생성
    const noticeData = {
        title: title,
        content: content,
        author: author
    };

    // 요청 보내기
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noticeData)
    })
        .then(response => {
            if (response.ok) {
                if (method === 'PUT') {
                    alert('공지사항이 성공적으로 수정되었습니다.');
                } else {
                    alert('새로운 공지사항이 성공적으로 저장되었습니다.');
                }
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항을 저장하는 중 오류가 발생했습니다.');
        });
});

//
// // '저장' 버튼 클릭 시, 수정된 내용을 서버로 전송하여 저장
// document.getElementById('saveBtn').addEventListener('click', function() {
//     // 선택된 행에서 data-id를 사용하여 공지사항 ID 가져오기
//     const selectedRow = document.querySelector('#noticeTableBody tr[data-id]');
//     if (!selectedRow) {
//         alert('저장할 공지사항을 선택해주세요.');
//         return;
//     }
//
//     const noticeId = selectedRow.dataset.id;  // data-id 속성에서 ID 가져오기
//
//     // 수정된 공지사항 데이터를 객체로 생성
//     const updatedNotice = {
//         title: document.getElementById('title').value,
//         content: document.getElementById('content').value,
//         author: document.getElementById('author').value
//     };
//
//     // PUT 요청을 사용하여 서버에 수정 데이터 전송
//     fetch(`/admin-potal/notices/${noticeId}`, {
//         method: 'PUT',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(updatedNotice)
//     })
//         .then(response => {
//             if (response.ok) {
//                 alert('공지사항이 성공적으로 저장되었습니다.');
//             } else {
//                 throw new Error('저장 중 오류가 발생했습니다.');
//             }
//         })
//         .catch(error => {
//             console.error('Error:', error);
//             alert('공지사항을 저장하는 중 오류가 발생했습니다.');
//         });
// });
//
//
//
// // '저장' 버튼 클릭 시, 신규 공지사항을 서버로 전송하여 저장
// document.getElementById('saveBtn').addEventListener('click', function() {
//     // 새로운 공지사항 ID는 data-id로 관리
//     const newRow = document.querySelector('#noticeTableBody tr[data-id]');
//     if (!newRow) {
//         alert('저장할 공지사항을 선택해주세요.');
//         return;
//     }
//
//     const newNoticeId = newRow.dataset.id;  // 신규 ID 가져오기 (new-<timestamp>)
//
//     // 수정된 공지사항 데이터를 객체로 생성
//     const newNotice = {
//         title: document.getElementById('title').value,
//         content: document.getElementById('content').value,
//         author: document.getElementById('author').value
//     };
//
//     // POST 요청을 사용하여 서버에 새로운 공지사항 데이터 전송
//     fetch('/admin-potal/notices', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(newNotice)
//     })
//         .then(response => {
//             if (response.ok) {
//                 alert('새로운 공지사항이 성공적으로 저장되었습니다.');
//             } else {
//                 throw new Error('저장 중 오류가 발생했습니다.');
//             }
//         })
//         .catch(error => {
//             console.error('Error:', error);
//             alert('공지사항을 저장하는 중 오류가 발생했습니다.');
//         });
// });
