document.getElementById('prof-assign-searchBtn').addEventListener('click', function() {
    // 강좌명과 주차 선택 값 가져오기
    const courseName = document.getElementById('course-name-select').value;
    const week = document.getElementById('week-select').value;

    // URL에 필터를 쿼리 파라미터로 추가
    let url = '/api/assignments/filter';
    let params = [];

    if (courseName) {
        params.push('courseName=' + encodeURIComponent(courseName));
    }
    if (week) {
        params.push('week=' + encodeURIComponent(week));
    }

    // 강좌명과 주차가 모두 비어 있으면 파라미터 없이 전체 조회
    if (params.length > 0) {
        url += '?' + params.join('&');
    }

    // fetch로 API 호출
    fetch(url)
        .then(response => response.json())  // 응답을 JSON으로 파싱
        .then(data => {
            console.log(data);  // 데이터를 콘솔에 출력 (실제 데이터 처리 로직 추가)
            const tableBody = document.getElementById('prof-assign-search-TableBody');
            tableBody.innerHTML = '';  // 기존 데이터 제거

            data.forEach((assign, index) => {
                const row = document.createElement('tr');
                const deadline = new Date(assign.dueDate);  // "yyyy-MM-dd" 형식의 문자열을 Date로 변환
                row.innerHTML = `
                    <td><input type="checkbox" class="assign-checkbox" data-id="${assign.id}"></td>
                    <td>${index + 1}</td>
                    <td data-field="courseName">${assign.courseName}</td>
                    <td data-field="author">${assign.professorName}</td>
                    <td data-field="week">${assign.week}</td>
                    <td data-field="deadline">${deadline.toLocaleDateString()}</td>  <!-- 날짜 출력 -->
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('과제 목록을 불러오는 데 실패했습니다.');
        });
});


$(document).ready(function() {
    // 강좌명 목록을 가져와서 <select>에 추가
    $.ajax({
        url: '/api/assignments/course-names',
        method: 'GET',
        success: function(courseNames) {
            const $courseSelect = $('#course-name-select');  // 강좌명 select 요소
            courseNames.forEach(function(courseName) {
                $courseSelect.append(`<option value="${courseName}">${courseName}</option>`);
            });
        },
        error: function() {
            alert('강좌명을 불러오는 데 실패했습니다.');
        }
    });

    // 주차 목록을 가져와서 <select>에 추가
    $.ajax({
        url: '/api/assignments/weeks',
        method: 'GET',
        success: function(weeks) {
            const $weekSelect = $('#week-select');  // 주차 select 요소
            weeks.forEach(function(week) {
                $weekSelect.append(`<option value="${week}">${week}</option>`);
            });
        },
        error: function() {
            alert('주차를 불러오는 데 실패했습니다.');
        }
    });
});


// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('prof-assign-newBtn').addEventListener('click', function() {
    const tableBody = document.getElementById('prof-assign-search-TableBody');

    const newAssignId = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성 (현재 행의 개수를 기반으로)
    const displayId = newAssignId.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = newAssignId;  // 고유한 data-id 부여

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="assign-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="courseName"></td>
        <td data-field="week"></td>
        <td data-field="content"></td>
        <td data-field="dueDate"></td>
        <td data-field="author">작성자</td>
    `;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#prof-assign-search-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('assign-row', 'selected');

    // 강의명, 주차, 내용, 교수명, 제출기한 등을 초기화
    document.getElementById('lectureTitle').value = ''; // 강의명
    document.getElementById('week').value = ''; // 주차
    document.getElementById('content').value = ''; // 내용
    document.getElementById('author').value = '작성자'; // 교수명
    document.getElementById('Deadline').value = ''; // 제출기한

    // 실시간 반영 이벤트 추가
    addRealTimeEditing(newRow);  // 여기에서 새로운 행을 전달하여 실시간 반영 기능을 실행

    alert('새로운 과제를 추가했습니다. 내용을 입력하세요.');
});

// 실시간 반영 이벤트 처리 함수 (예시: 강의명 수정 시 테이블에 반영)
function addRealTimeEditing(row) {
    // 강의명 수정 시 실시간 반영
    const courseNameCell = row.querySelector('td[data-field="courseName"]');
    const dueDateCell = row.querySelector('td[data-field="dueDate"]');

    // 강의명 수정 시 테이블에 반영
    document.getElementById('lectureTitle').addEventListener('input', function() {
        if (courseNameCell) {
            courseNameCell.textContent = this.value;
        }
    });


    // 제출기한 수정 시 테이블에 반영
    document.getElementById('Deadline').addEventListener('input', function() {
        if (dueDateCell) {
            dueDateCell.textContent = this.value;
        }
    });
}


document.getElementById('prof-assign-search-TableBody').addEventListener('click', function(event) {
    // 클릭된 곳이 tr 요소인지 확인
    if (event.target.tagName === 'TD') {
        const row = event.target.closest('tr');  // 클릭된 tr 요소 찾기
        const assignId = row.querySelector('.assign-checkbox').getAttribute('data-id');  // 과제 ID

        // 해당 ID로 과제 정보를 가져오기 (API 호출)
        fetch(`/api/assignments/assignment-detail/${assignId}`)
            .then(response => response.json())
            .then(assign => {
                // 폼에 값 채우기
                document.getElementById('lectureTitle').value = assign.courseName;
                document.getElementById('week').value = assign.week;
                document.getElementById('content').value = assign.content;
                document.getElementById('file').value = assign.file || '';  // 첨부파일 (없으면 빈 문자열)
                document.getElementById('Deadline').value = assign.dueDate;  // 'yyyy-MM-dd' 형식
                document.getElementById('author').value = assign.professorName;  // 작성자는 수정 불가

                // 실시간 제출기한 수정
                document.getElementById('Deadline').addEventListener('input', function() {
                    // 제출기한을 수정하면 테이블의 제출기한 셀을 업데이트
                    const selectedRow = row;  // 클릭한 행을 선택
                    const deadlineCell = selectedRow.querySelector('td[data-field="deadline"]');  // data-field="deadline"인 셀 찾기
                    if (deadlineCell) {
                        deadlineCell.textContent = this.value;  // 제출기한 수정 반영
                    }
                });

                // 실시간 내용 수정
                document.getElementById('content').addEventListener('input', function() {
                    // 내용을 수정하면 테이블의 내용 셀을 업데이트
                    const selectedRow = row;  // 클릭한 행을 선택
                    const contentCell = selectedRow.querySelector('td[data-field="content"]');  // data-field="content"인 셀 찾기
                    if (contentCell) {
                        contentCell.textContent = this.value;  // 내용 수정 반영
                    }
                });
            })
            .catch(error => {
                console.error('Error:', error);  // 에러 발생 시 콘솔에 에러 메시지 출력
                alert('공지사항 정보를 불러오는 중 오류가 발생했습니다.');  // 사용자에게 오류 메시지 표시
            });
    }
});

document.getElementById('prof-assign-saveBtn').addEventListener('click', function() {
    const courseName = document.getElementById('lectureTitle').value;
    const week = document.getElementById('week').value;
    const content = document.getElementById('content').value;
    const author = document.getElementById('author').value;
    const dueDate = document.getElementById('Deadline').value;

    // 과제 ID가 있는지 확인 (수정일 경우 ID가 존재)
    const selectedRow = document.querySelector('#prof-assign-search-TableBody tr.selected');  // 선택된 행 찾기
    let url = '';
    let method = '';
    let assignId = null;

    if (selectedRow) {
        // 선택된 행에서 ID를 가져옴
        assignId = selectedRow.dataset.id;  // 선택된 과제의 ID

        if (assignId.startsWith('new+')) {
            // 신규 과제 (new+ 로 시작하는 ID)
            url = '/api/assignments';
            method = 'POST';  // 신규 추가: POST 요청
        } else {
            // 기존 과제 (실제 DB ID가 존재하는 경우)
            url = `/api/assignments/${assignId}`;
            method = 'PUT';  // 수정: PUT 요청
        }
    } else {
        // 선택된 행이 없으면 신규로 처리
        url = '/api/assignments';
        method = 'POST';  // 신규 추가: POST 요청
    }

    // 과제 데이터 객체 생성
    const assignData = {
        courseName: courseName,
        week: week,
        content: content,
        professorName: author,
        dueDate: dueDate
    };

    // 요청 보내기
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(assignData)
    })
        .then(response => {
            if (response.ok) {
                if (method === 'PUT') {
                    alert('과제가 성공적으로 수정되었습니다.');
                } else {
                    alert('새로운 과제가 성공적으로 저장되었습니다.');
                }
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('과제를 저장하는 중 오류가 발생했습니다.');
        });
});
