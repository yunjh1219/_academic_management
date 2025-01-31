// 교수 정보 목록을 불러오는 함수
document.getElementById('admin-profinfo-searchBtn').addEventListener('click', function () {
    const url = '/api/admin/professors/condition'; // 교수 정보 목록을 가져올 URL

    console.log("교수 정보 목록을 불러오는 중...");

    // 로컬 스토리지에서 JWT 토큰 가져오기
    const token = localStorage.getItem('jwtToken');

    // 서버에 fetch 요청
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
            const tableBody = document.querySelector('#admin-profinfo-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((professor, index) => {
                const row = document.createElement('tr');
                row.dataset.id = professor.userNum;
                row.innerHTML =
                    `<td><input type="checkbox" class="professor-checkbox"></td>
                     <td>${index + 1}</td>
                     <td data-field="name">${professor.username}</td>
                     <td data-field="professorId">${professor.userNum}</td>
                     <td data-field="department">${professor.deptName}</td>
                     <td data-field="status">${professor.status}</td>
                     <td data-field="remarks"></td>`;
                tableBody.appendChild(row);
            });

        })
        .catch(error => {
            console.error('Error:', error);
            alert('교수 정보를 불러오는 중 오류가 발생했습니다.');
        });
});

// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('admin-profinfo-newBtn').addEventListener('click', function () {
    const tableBody = document.getElementById('admin-profinfo-TableBody');

    const newNoticeId = `new+${tableBody.rows.length + 1}`;
    const displayId = newNoticeId.replace('new+', '');
    const newRow = document.createElement('tr');
    newRow.dataset.id = newNoticeId;

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
    const professorId = document.getElementById('professorId').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML =
        `<td><input type="checkbox" class="professor-checkbox"></td>             
         <td>${displayId}</td>
         <td style="text-align: left;" data-field="name">${name}</td>
         <td data-field="professorId">${professorId}</td>
         <td data-field="department">${department}</td>
         <td data-field="phone">${phone}</td>
         <td data-field="email">${email}</td>
         <td data-field="address">${address}</td>`;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('professor-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTimeEditing(newRow);

    alert('새로운 교수 정보를 추가했습니다. 내용을 확인하세요.');
});

function addRealTimeEditing(row) {
    const profNameCell = row.querySelector('td[data-field="name"]') || null;
    const professorIdCell = row.querySelector('td[data-field="professorId"]') || null;
    const profDepartmentCell = row.querySelector('td[data-field="department"]') || null;
    const profStatusCell = row.querySelector('td[data-field="status"]') || null;
    const profRemarkCell = row.querySelector('td[data-field="remarks"]') || null;

    if (profNameCell) {
        document.getElementById('name').addEventListener('input', function () {
            profNameCell.textContent = this.value;
        });
    }
    if (professorIdCell) {
        document.getElementById('professorId').addEventListener('input', function () {
            professorIdCell.textContent = this.value;
        });
    }
    if (profDepartmentCell) {
        document.getElementById('department').addEventListener('input', function () {
            profDepartmentCell.textContent = this.value;
        });
    }
    if (profStatusCell) {
        document.getElementById('status').addEventListener('input', function () {
            profStatusCell.textContent = this.value;
        });
    }
    if (profRemarkCell) {
        document.getElementById('remark').addEventListener('input', function () {
            profRemarkCell.textContent = this.value;
        });
    }
}

// 저장 버튼 클릭 시 데이터 저장
document.getElementById('admin-profinfo-savBtn').addEventListener('click', function() {
    const userName = document.getElementById('name').value;
    const birthday = document.getElementById('birthdate').value + "T00:00:00";
    const deptName = document.getElementById('department').value;
    const userNum = document.getElementById('professorId').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;

    const selectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');
    let url = '';
    let method = '';
    let professorIdInRow = null;

    if(selectedRow) {
        professorIdInRow = selectedRow.dataset.id;
        if (professorIdInRow.startsWith("new+")) {
            url = '/api/join/professor';
            method = 'POST';
        } else {
            url = `/admin-portal/professor/${professorIdInRow}`;
            method = 'PUT';
        }
    } else {
        url = `/api/join/professor`;
        method = 'POST';
    }

    const professorData = {
        userName, birthday, deptName, userNum, phone, email, address, type: "PROFESSOR"
    };

    const token = localStorage.getItem('jwtToken');

    fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(professorData)
    })
        .then(response => {
            if (response.ok) {
                alert(method === 'PUT' ? '교수 정보가 성공적으로 수정되었습니다.' : '새로운 교수 정보가 성공적으로 저장되었습니다.');
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('교수 정보를 저장하는 중 오류가 발생했습니다.');
        });
});

// 테이블 행 선택 시 selected 클래스 추가
document.getElementById('admin-profinfo-TableBody').addEventListener('click', function (event) {
    const clickedRow = event.target.closest('tr');
    if (clickedRow) {
        // 기존 선택된 행의 selected 클래스 제거
        const previouslySelectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }
        // 클릭한 행에 selected 클래스 추가
        clickedRow.classList.add('selected');
    }
});

// 삭제 버튼 클릭 시 학생 정보 삭제
document.getElementById('admin-profinfo-delBtn').addEventListener('click', function () {
    const selectedRow = document.querySelector('#admin-profinfo-TableBody tr.selected');
    if (!selectedRow) {
        alert('삭제할 교수를 선택해주세요.');
        return;
    }

    const professorIdInRow = selectedRow.dataset.id;
    if (!professorIdInRow) {
        alert('삭제할 교수 ID를 찾을 수 없습니다.');
        return;
    }

    if (professorIdInRow.startsWith('new+')) {
        selectedRow.remove();
        alert('새로 추가된 교수 정보가 삭제되었습니다.');
        return;
    }

    const url = `/api/admin/user/${professorIdInRow}`;
    const token = localStorage.getItem('jwtToken');

    if (!confirm('정말로 이 교수 정보를 삭제하시겠습니까?')) return;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                selectedRow.remove();
                alert('교수 정보가 성공적으로 삭제되었습니다.');
            } else {
                throw new Error('삭제 요청 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('교수 정보를 삭제하는 중 오류가 발생했습니다.');
        });
});
