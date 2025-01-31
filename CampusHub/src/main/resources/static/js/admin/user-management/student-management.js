// '조회' 버튼 클릭 시 학생 정보 목록을 불러오는 함수
document.getElementById('admin-stuinfo-searchBtn').addEventListener('click', function () {
    const url = '/api/admin/students/condition'; // 학생 정보 목록을 가져올 URL
    console.log("학생 정보 목록을 불러오는 중...");

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
            const tableBody = document.getElementById('admin-stuinfo-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((student, index) => {
                const row = document.createElement('tr');
                row.dataset.id = student.userNum;
                row.innerHTML = `
                <td><input type="checkbox" class="student-checkbox"></td>
                <td>${index + 1}</td>
                <td data-field="name" style="text-align: left;">${student.username}</td>
                <td data-field="studentId">${student.userNum}</td>
                <td data-field="department">${student.deptName}</td>
                <td data-field="status">${student.status}</td>
                <td data-field="remarks"></td>
            `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학생 정보를 불러오는 중 오류가 발생했습니다.');
        });
});

// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('admin-stuinfo-newBtn').addEventListener('click', function() {
    const tableBody = document.getElementById('admin-stuinfo-TableBody');
    const newNoticeId = `new+${tableBody.rows.length + 1}`;
    const displayId = newNoticeId.replace('new+', '');
    const newRow = document.createElement('tr');
    newRow.dataset.id = newNoticeId;

    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td>
        <td data-field="name"></td>
        <td data-field="studentId"></td>
        <td data-field="department"></td>
        <td data-field="phone"></td>
        <td data-field="remark"></td>
        <td data-field="address"></td>
    `;

    tableBody.appendChild(newRow);
    newRow.classList.add('student-row', 'selected');
    alert('새로운 학생 정보를 추가했습니다. 내용을 확인하세요.');
});

// 저장 버튼 클릭 시 데이터 저장
document.getElementById('admin-stuinfo-savBtn').addEventListener('click', function() {
    const userName = document.getElementById('name').value;
    const birthday = document.getElementById('birthdate').value + "T00:00:00";
    const deptName = document.getElementById('department').value;
    const userNum = document.getElementById('studentId').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;

    const selectedRow = document.querySelector('#admin-stuinfo-TableBody tr.selected');
    let url = '';
    let method = '';
    let studentIdInRow = null;

    if(selectedRow) {
        studentIdInRow = selectedRow.dataset.id;
        if (studentIdInRow.startsWith("new+")) {
            url = '/api/join/student';
            method = 'POST';
        } else {
            url = `/admin-portal/students/${studentIdInRow}`;
            method = 'PUT';
        }
    } else {
        url = `/api/join/student`;
        method = 'POST';
    }

    const studentData = {
        userName, birthday, deptName, userNum, phone, email, address, type: "STUDENT"
    };

    const token = localStorage.getItem('jwtToken');

    fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(studentData)
    })
        .then(response => {
            if (response.ok) {
                alert(method === 'PUT' ? '학생 정보가 성공적으로 수정되었습니다.' : '새로운 학생 정보가 성공적으로 저장되었습니다.');
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학생 정보를 저장하는 중 오류가 발생했습니다.');
        });
});

// 테이블 행 선택 시 selected 클래스 추가 및 데이터 편집 창에 반영
document.getElementById('admin-stuinfo-TableBody').addEventListener('click', function (event) {
    const clickedRow = event.target.closest('tr');
    if (clickedRow) {
        const previouslySelectedRow = document.querySelector('#admin-stuinfo-TableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }
        clickedRow.classList.add('selected');

        // 선택된 행의 userNum 가져오기
        const userNum = clickedRow.querySelector('[data-field="studentId"]')?.textContent || '';

        // AJAX 요청으로 데이터 가져오기
        fetch(`/api/admin/student/${userNum}`) // userNum을 사용하여 API 호출
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
                    document.getElementById('name').value = student.username || '';
                    document.getElementById('studentId').value = student.userNum || '';
                    document.getElementById('department').value = student.deptName || '';
                    document.getElementById('phone').value = student.phone || '';
                    document.getElementById('email').value = student.email || '';
                    document.getElementById('address').value = student.address || '';
                    document.getElementById('birthdate').value = student.birthdate || '';
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


// 삭제 버튼 클릭 시 학생 정보 삭제
document.getElementById('admin-stuinfo-delBtn').addEventListener('click', function () {
    const selectedRow = document.querySelector('#admin-stuinfo-TableBody tr.selected');
    if (!selectedRow) {
        alert('삭제할 학생을 선택해주세요.');
        return;
    }

    const studentIdInRow = selectedRow.dataset.id;
    if (!studentIdInRow) {
        alert('삭제할 학생 ID를 찾을 수 없습니다.');
        return;
    }

    if (studentIdInRow.startsWith('new+')) {
        selectedRow.remove();
        alert('새로 추가된 학생 정보가 삭제되었습니다.');
        return;
    }

    const url = `/api/admin/user/${studentIdInRow}`;
    const token = localStorage.getItem('jwtToken');

    if (!confirm('정말로 이 학생 정보를 삭제하시겠습니까?')) return;

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
                alert('학생 정보가 성공적으로 삭제되었습니다.');
            } else {
                throw new Error('삭제 요청 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학생 정보를 삭제하는 중 오류가 발생했습니다.');
        });
});
