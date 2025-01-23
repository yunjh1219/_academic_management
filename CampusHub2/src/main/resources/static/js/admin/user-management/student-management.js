// '조회' 버튼 클릭 시 학생 정보 목록을 불러오는 함수
document.getElementById('admin-stuinfo-searchBtn').addEventListener('click', function() {
    const url = '/api/admin/students/condition';  // 학생 정보 목록을 가져올 URL

    console.log("학생 정보 목록을 불러오는 중...");

    fetch(url)  // fetch API를 사용하여 서버에서 학생 정보를 요청
        .then(response => response.json())  // 응답을 JSON 형식으로 변환
        .then(data => {
            const tableBody = document.getElementById('admin-stuinfo-TableBody');  // 학생 정보 테이블의 tbody 요소
            tableBody.innerHTML = ''; // 기존 데이터를 제거 (새로고침)

            // 서버에서 받은 데이터를 기반으로 테이블에 새로운 행 추가
            data.forEach((student, index) => {  // index 값을 사용하여 순차적인 번호를 할당
                const row = document.createElement('tr');  // 새로운 행 추가

                // 학생 정보 데이터 (학과, 이름, 학번 등)
                row.classList.add('student-row');  // CSS 클래스 추가
                row.dataset.id = student.id;  // 실제 DB에서 받아온 고유 ID를 dataset.id로 설정
                row.innerHTML = `
                    <td><input type="checkbox" class="student-checkbox"></td>  <!-- 체크박스 -->
                    <td data-field="index">${index + 1}</td>  <!-- 순차적인 번호 (1부터 시작) -->
                    <td data-field="name" style="text-align: left;">${student.name}</td>  <!-- 이름 -->
                    <td data-field="studentId">${student.studentId}</td>  <!-- 학번 -->
                    <td data-field="department">${student.department}</td>  <!-- 학과 -->
                    <td data-field="status">${student.status}</td>  <!-- 상태 -->
                    <td data-field="remarks">${student.remarks}</td>  <!-- 비고 -->
                `;
                tableBody.appendChild(row);  // 테이블에 새로운 행 추가
            });
        })
        .catch(error => {
            console.error('Error:', error);  // 에러 발생 시 콘솔에 에러 메시지 출력
            alert('학생 정보를 불러오는 중 오류가 발생했습니다.');  // 사용자에게 오류 메시지 표시
        });
});




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

    // 입력된 폼 데이터 가져오기
    const name = document.getElementById('name').value;
    const birthdate = document.getElementById('birthdate').value;
    const department = document.getElementById('department').value;
    const studentId = document.getElementById('studentId').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="name">${name}</td> <!-- 이름 -->
        <td data-field="studentId">${studentId}</td> <!-- 학번 -->
        <td data-field="department">${department}</td> <!-- 학과 -->
        <td data-field="status">${phone}</td> <!-- 연락처 -->
        <td data-field="remark">${email}</td> <!-- 이메일 -->
        <td data-field="address">${address}</td> <!-- 주소 -->
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

    alert('새로운 학생 정보를 추가했습니다. 내용을 확인하세요.');
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

//저장버튼
document.getElementById('admin-stuinfo-savBtn').addEventListener('click', function() {
    const userName = document.getElementById('name').value; // 학생의 이름을 가져옵니다
    const birthday = document.getElementById('birthdate').value;  // 생년월일을 가져옵니다. LocalDateTime 형식으로 변환이 필요합니다.
    const deptName = document.getElementById('department').value; // 학생의 학과명을 가져옵니다
    const userNum = document.getElementById('studentId').value; // 학생의 학번을 가져옵니다
    const phone = document.getElementById('phone').value; // 학생의 연락처를 가져옵니다
    const email = document.getElementById('email').value; // 학생의 이메일을 가져옵니다
    const address = document.getElementById('address').value; // 학생의 주소를 가져옵니다

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
        type: "STUDENT"  // "STUDENT" 타입 고정
    };

    // 로컬 스토리지에서 JWT 토큰 가져오기
    const token = localStorage.getItem('jwtToken');

    // 요청 보내기
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`  // JWT 토큰을 Authorization 헤더에 추가
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
