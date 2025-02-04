//신규
document.getElementById('admin-deptfinfo-newBtn').addEventListener('click',function (){

    //폼 초기화 (입력된 데이터 초기화)
    document.getElementById('admin_deptfinfo_deptname').value = '';

    alert('새로운 학과 정보를 입력해주세요');

    const tableBody = document.getElementById('admin-deptfinfo-TableBody');
    const newdeptId = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성 (현재 행의 개수를 기반으로)
    const displayId = newdeptId.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = newdeptId;  // 고유한 data-id 부여

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    const deptName = document.getElementById('admin_deptfinfo_deptname').value;

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="deptname">${deptName}</td> <!-- 이름 -->
    `;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-deptfinfo-TableBody tr.selected');
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
    const admin_deptfinfo_deptnameCell = row.querySelector('td[data-field="deptname"]');


    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('admin_deptfinfo_deptname').addEventListener('input', function() {
        if (admin_deptfinfo_deptnameCell) {
            admin_deptfinfo_deptnameCell.textContent = this.value;  // 이름 수정 반영
        }
    });
}

//저장
document.getElementById('admin-deptfinfo-savBtn').addEventListener('click',function (){
    const deptName = document.getElementById('admin_deptfinfo_deptname').value; //학과이름

    // 선택된 행이 있는지 확인 (수정일 경우 ID가 존재)
    const selectedRow = document.querySelector('#admin-deptfinfo-TableBody tr.selected');  // 선택된 행 찾기
    let url = '';
    let method = '';
    let deptIdInRow = null;

    if (selectedRow) {
        // 선택된 행에서 ID 가져오기
        deptIdInRow = selectedRow.dataset.id;  // 선택된 학생의 ID

        if (deptIdInRow.startsWith('new+')) {
            // 신규 학생 (new+로 시작하는 ID)
            url = '/api/admin/dept';
            method = 'POST';  // 신규 추가: POST 요청
        } else {
            // 기존 학생 (실제 DB ID가 존재하는 경우)
            url = `/api/admin/dept/${deptIdInRow}`;
            method = 'PUT';  // 수정: PUT 요청
        }
    } else {
        // 선택된 행이 없으면 신규로 처리
        url = '/api/admin/dept';
        method = 'POST';  // 신규 추가: POST 요청
    }

    //학과정보등록
    const deptDate = {
        deptName: deptName
    };

    const token = localStorage.getItem('jwtToken');

    // 요청 보내기
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`  // JWT 토큰을 Authorization 헤더에 추가
        },
        body: JSON.stringify(deptDate)
    })
        .then(response => {
            if (response.ok) {
                if (method === 'PUT') {
                    alert('학과 정보가 성공적으로 수정되었습니다.');
                } else {
                    alert('새로운 학과 정보가 성공적으로 저장되었습니다.');
                }
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학과 정보를 저장하는 중 오류가 발생했습니다.');
        });
});

document.getElementById('admin-deptfinfo-searchBtn').addEventListener('click',function (){
    const  url = '/api/admin/dept/all'
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
            const tableBody = document.getElementById('admin-deptfinfo-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((dept, index) => {
                const row = document.createElement('tr');
                row.dataset.id = dept.userNum;
                row.innerHTML = `
                    <td><input type="checkbox" class="dept-checkbox"></td>
                    <td>${index + 1}</td>
                    <td data-field="deptName" style="text-align: left;">${dept.deptName}</td>
            
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학과 정보를 불러오는 중 오류가 발생했습니다.');
        });
});

//단건조회
document.getElementById('admin-deptfinfo-TableBody').addEventListener('click', function (event) {
    const clickedRow = event.target.closest('tr');
    if (clickedRow) {
        const previouslySelectedRow = document.querySelector('#admin-deptfinfo-TableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }
        clickedRow.classList.add('selected');

        // 선택된 행의 userNum 가져오기
        const userNum = clickedRow.querySelector('[data-field="studentId"]')?.textContent || '';

        // JWT 토큰을 로컬 스토리지에서 가져오기
        const token = localStorage.getItem('jwtToken');

        // AJAX 요청으로 데이터 가져오기 (토큰을 Authorization 헤더에 추가)
        fetch(`/api/admin/student/${userNum}`, {
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
                // 데이터 편집 창에 반영
                if (data && data.data) { // 데이터가 존재하는지 확인
                    const student = data.data; // 학생 정보 객체
                    document.getElementById('name').value = student.userName || '이름없음';
                    document.getElementById('studentId').value = student.userNum || '학번없음';
                    document.getElementById('department').value = student.deptName || '학과없음';
                    document.getElementById('phone').value = student.phone || '폰없음';
                    document.getElementById('email').value = student.email || '메일없음';
                    document.getElementById('address').value = student.address || '주소없음';
                    document.getElementById('birthdate').value = student.birthdate || '생일없음';
                } else {
                    alert('학생 정보를 찾을 수 없습니다.');
                }
            })
            .catch(error => {
                console.error('AJAX 요청 중 오류가 발생했습니다:', error);
                alert('학생 정보를 불러오는 중 오류가 발생했습니다.');
            });
    }
});