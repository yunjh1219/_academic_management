// 교수 정보 조회
document.getElementById('admin-profinfo-searchBtn').addEventListener('click',function () {
    const url = '/api/admin/professors/condition'

        const token = localStorage.getItem('jwtToken');

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
            const tableBody = document.getElementById('admin-profinfo-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((professor, index) => {
                const row = document.createElement('tr');
                row.dataset.id = professor.userNum;
                row.innerHTML = `
                <td><input type="checkbox" class="professor-checkbox"></td>
                <td>${index + 1}</td>
                <td data-field="name" style="text-align: left;">${professor.username}</td>
                <td data-field="profId">${professor.userNum}</td>
                <td data-field="department">${professor.deptName}</td>
                <td data-field="type">${professor.type}</td>
                <td data-field="status">${professor.status}</td>
                <td data-field="remarks"></td>
            `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('교수 정보를 불러오는 중 오류가 발생했습니다.');
        });
});

// 신규 교수 추가
// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('admin-profinfo-newBtn').addEventListener('click', function() {

    // 폼 초기화 (입력된 데이터 초기화)
    document.getElementById('name').value = '';
    document.getElementById('professorId').value = '';
    document.getElementById('department').value = '';
    document.getElementById('phone').value = '';
    document.getElementById('email').value = '';
    document.getElementById('address').value = '';
    document.getElementById('birthdate').value = '';

    alert('새로운 교수 정보를 추가했습니다. 정보를 입력해주세요.');

    const tableBody = document.getElementById('admin-profinfo-TableBody');
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
    const professorId = document.getElementById('professorId').value; // 교수 ID
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="name">${name}</td> <!-- 이름 -->
        <td data-field="professorId">${professorId}</td> <!-- 교수 ID -->
        <td data-field="department">${department}</td> <!-- 학과 -->
        <td data-field="status">${phone}</td> <!-- 연락처 -->
        <td data-field="remark">${email}</td> <!-- 이메일 -->
    `;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('prof-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTimeProfEditing(newRow);
});

// 실시간으로 수정 내용 반영
function addRealTimeProfEditing(row) {
    const nameCell = row.querySelector('td[data-field="name"]');
    const studentIdCell = row.querySelector('td[data-field="professorId"]');
    const departmentCell = row.querySelector('td[data-field="department"]');
    const statusCell = row.querySelector('td[data-field="status"]');
    const remarkCell = row.querySelector('td[data-field="remark"]');

    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('name').addEventListener('input', function() {
        if (nameCell) {
            nameCell.textContent = this.value;  // 이름 수정 반영
        }
    });

    document.getElementById('professorId').addEventListener('input', function() {
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
document.getElementById('admin-profinfo-savBtn').addEventListener('click', function() {
    const userName = document.getElementById('name').value; // 학생의 이름을 가져옵니다
    const birthday = document.getElementById('birthdate').value;  // 생년월일을 가져옵니다. LocalDateTime 형식으로 변환이 필요합니다.
    const deptName = document.getElementById('department').value; // 학생의 학과명을 가져옵니다
    const userNum = document.getElementById('professorId').value; // 학생의 학번을 가져옵니다
    const phone = document.getElementById('phone').value; // 학생의 연락처를 가져옵니다
    const email = document.getElementById('email').value; // 학생의 이메일을 가져옵니다
    const address = document.getElementById('address').value; // 학생의 주소를 가져옵니다

    // 생년월일 값 처리 (예: "2000-01-01" 형식으로 전달)
    const formattedBirthday = birthday + "T00:00:00";  // 시간 부분 추가 (필요 시 수정 가능)

    // 선택된 행이 있는지 확인 (수정일 경우 ID가 존재)
    const selectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');  // 선택된 행 찾기
    let url = '';
    let method = '';
    let professorIdInRow = null;

    if (selectedRow) {
        // 선택된 행에서 ID 가져오기
        professorIdInRow = selectedRow.dataset.id;  // 선택된 학생의 ID

        if (professorIdInRow.startsWith('new+')) {
            // 신규 학생 (new+로 시작하는 ID)
            url = '/api/join/professor';
            method = 'POST';  // 신규 추가: POST 요청
        } else {
            // 기존 학생 (실제 DB ID가 존재하는 경우)
            url = `/admin-potal/professors/${professorIdInRow}`;
            method = 'PUT';  // 수정: PUT 요청
        }
    } else {
        // 선택된 행이 없으면 신규로 처리
        url = '/api/join/professor';
        method = 'POST';  // 신규 추가: POST 요청
    }

    // 학생 등록 요청
    const professorData = {
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
        body: JSON.stringify(professorData)
    })
        .then(response => {
            if (response.ok) {
                if (method === 'PUT') {
                    alert('교수 정보가 성공적으로 수정되었습니다.');
                } else {
                    alert('새로운 교수 정보가 성공적으로 저장되었습니다.');
                }
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('교수 정보를 저장하는 중 오류가 발생했습니다.');
        });
});


// 행 클릭시 단건 조회 (교수)
document.getElementById('admin-profinfo-TableBody').addEventListener('click', function (event) {
    const clickedRow = event.target.closest('tr');
    if (clickedRow) {
        const previouslySelectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }
        clickedRow.classList.add('selected');

        // 선택된 행의 userNum 가져오기
        const userNum = clickedRow.querySelector('[data-field="profId"]')?.textContent || '';

        // JWT 토큰을 로컬 스토리지에서 가져오기
        const token = localStorage.getItem('jwtToken');

        // AJAX 요청으로 데이터 가져오기 (토큰을 Authorization 헤더에 추가)
        fetch(`/api/admin/professor/${userNum}`, {
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
                // 데이터 콘솔 출력
                console.log("서버 응답 데이터:", data);  // 데이터를 콘솔에 출력
                // 데이터 편집 창에 반영
                if (data && data.data) { // 데이터가 존재하는지 확인
                    const professor = data.data; // 교수 정보 객체
                    document.getElementById('name').value = professor.userName || '이름없음';
                    document.getElementById('professorId').value = professor.userNum || '교수 ID없음';
                    document.getElementById('department').value = professor.deptName || '학과없음';
                    document.getElementById('phone').value = professor.phone || '폰없음';
                    document.getElementById('email').value = professor.email || '메일없음';
                    document.getElementById('address').value = professor.address || '주소없음';
                    document.getElementById('birthdate').value = professor.birthdate || '생일없음';
                } else {
                    alert('교수 정보를 찾을 수 없습니다.');
                }
            })
            .catch(error => {
                console.error('AJAX 요청 중 오류가 발생했습니다:', error);
                alert('교수 정보를 불러오는 중 오류가 발생했습니다.');
            });
    }
});

//삭제
// 삭제 버튼 클릭 이벤트 핸들러
document.getElementById('admin-profinfo-delBtn').addEventListener('click', function () {
    const selectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');  // 선택된 행 찾기

    if (!selectedRow) {
        alert('삭제할 교수를 선택하세요.');
        return;
    }

    const userNum = selectedRow.querySelector('[data-field="profId"]')?.textContent;

    if (!userNum) {
        alert('올바른 교수 ID를 찾을 수 없습니다.');
        return;
    }

    const url = `/api/admin/user/${userNum}`;  // 삭제 API 경로
    const token = localStorage.getItem('jwtToken');  // JWT 토큰 가져오기

    if (confirm('정말로 이 교수 정보를 삭제하시겠습니까?')) {
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('교수 정보가 성공적으로 삭제되었습니다.');
                    selectedRow.remove();  // 테이블에서 선택된 행 삭제
                } else {
                    throw new Error('삭제 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('교수 정보를 삭제하는 중 오류가 발생했습니다.');
            });
    }
});
