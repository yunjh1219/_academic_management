// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('admin-stuinfo-newBtn').addEventListener('click', function() {
    const tableBody = document.getElementById('admin-stuinfo-TableBody');

    const newNoticeId = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성 (현재 행의 개수를 기반으로)
    const displayId = newNoticeId.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = newNoticeId;  // 고유한 data-id 부여

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="name"></td>
        <td data-field="studentId"></td>
        <td data-field="department"></td>
        <td data-field="status"></td>
        <td data-field="remark"></td>
    `;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-stuinfo-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('student-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTimeEditing(newRow);

    alert('새로운 학생 정보를 추가했습니다. 내용을 입력하세요.');
});

// 실시간으로 수정 내용 반영
function addRealTimeEditing(row) {
    const nameCell = row.querySelector('td[data-field="name"]');
    const studentIdCell = row.querySelector('td[data-field="studentId"]');
    const departmentCell = row.querySelector('td[data-field="department"]');
    const statusCell = row.querySelector('td[data-field="status"]');
    const remarkCell = row.querySelector('td[data-field="remark"]');

    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('name').addEventListener('input', function() {
        if (nameCell) {
            nameCell.textContent = this.value;  // 이름 수정 반영
        }
    });

    document.getElementById('studentId').addEventListener('input', function() {
        if (studentIdCell) {
            studentIdCell.textContent = this.value;  // 학번 수정 반영
        }
    });

    document.getElementById('department').addEventListener('input', function() {
        if (departmentCell) {
            departmentCell.textContent = this.value;  // 학과 수정 반영
        }
    });

    document.getElementById('status').addEventListener('input', function() {
        if (statusCell) {
            statusCell.textContent = this.value;  // 상태 수정 반영
        }
    });

    document.getElementById('remark').addEventListener('input', function() {
        if (remarkCell) {
            remarkCell.textContent = this.value;  // 비고 수정 반영
        }
    });
}

document.getElementById('admin-stuinfo-savBtn').addEventListener('click', function() {
    const userName = document.getElementById('name').value; // 학생의 이름을 가져옵니다
    const birthday = document.getElementById('birthdate').value;  // 생년월일을 가져옵니다. LocalDateTime 형식으로 변환이 필요합니다.
    const deptName = document.getElementById('department').value; // 학생의 학과명을 가져옵니다
    const userNum = document.getElementById('studentId').value; // 학생의 학번을 가져옵니다
    const phone = document.getElementById('phone').value; // 학생의 연락처를 가져옵니다
    const email = document.getElementById('email').value; // 학생의 이메일을 가져옵니다
    const address = document.getElementById('address').value; // 학생의 주소를 가져옵니다
    const password = document.getElementById('password').value; // 학생의 비밀번호를 가져옵니다


    // 생년월일 값 처리 (예: "2000-01-01" 형식으로 전달)
    const formattedBirthday = birthday + "T00:00:00";  // 시간 부분 추가 (필요 시 수정 가능)

    // 선택된 행이 있는지 확인 (수정일 경우 ID가 존재)
    const selectedRow = document.querySelector('#admin-stuinfo-TableBody tr.selected');  // 선택된 행 찾기
    let url = '';
    let method = '';
    let studentIdInRow = null;

    if (selectedRow) {
        // 선택된 행에서 ID 가져오기
        studentIdInRow = selectedRow.dataset.id;  // 선택된 학생의 ID

        if (studentIdInRow.startsWith('new+')) {
            // 신규 학생 (new+로 시작하는 ID)
            url = '/api/join/student';
            method = 'POST';  // 신규 추가: POST 요청
        } else {
            // 기존 학생 (실제 DB ID가 존재하는 경우)
            url = `/admin-potal/students/${studentIdInRow}`;
            method = 'PUT';  // 수정: PUT 요청
        }
    } else {
        // 선택된 행이 없으면 신규로 처리
        url = '/api/join/student';
        method = 'POST';  // 신규 추가: POST 요청
    }

    // 학생 등록 요청
    const studentData = {
        userName: userName, // 학생의 이름
        birthday: formattedBirthday, // 학생의 생년월일 (LocalDateTime 형식으로 전달)
        deptName: deptName, // 학생의 학과명
        userNum: userNum, // 학생의 학번
        phone: phone, // 학생의 연락처
        email: email, // 학생의 이메일
        address: address, // 학생의 주소
        password: password, // 학생의 비밀번호
        type: "STUDENT"  // "STUDENT" 타입 고정
    };

    // 요청 보내기
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(studentData)
    })
        .then(response => {
            if (response.ok) {
                if (method === 'PUT') {
                    alert('학생 정보가 성공적으로 수정되었습니다.');
                } else {
                    alert('새로운 학생 정보가 성공적으로 저장되었습니다.');
                }
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학생 정보를 저장하는 중 오류가 발생했습니다.');
        });
});
