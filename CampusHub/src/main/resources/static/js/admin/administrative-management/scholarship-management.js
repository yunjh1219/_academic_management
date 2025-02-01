// 조회
document.getElementById('admin-shipinfo-searchBtn').addEventListener('click', function () {
    const url = '/api/admin/scholarship/condition';

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
            const tableBody = document.getElementById('admin-stushipinfo-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((scholarship, index) => {
                const row = document.createElement('tr');
                row.dataset.id = scholarship.userNum;
                row.innerHTML = `
                    <td><input type="checkbox" class="scholarship-checkbox"></td>
                    <td>${index + 1}</td>
                    <td data-field="name" style="text-align: left;">${scholarship.username}</td>
                    <td data-field="studentId">${scholarship.userNum}</td>
                    <td data-field="department">${scholarship.deptName}</td>
                    <td data-field="year">${scholarship.year ? scholarship.year.year : ''}</td>
                    <td data-field="semester">${scholarship.semester}</td>
                    <td data-field="scholarshipName">${scholarship.scholarshipName}</td>
                    <td data-field="type">${scholarship.type}</td>
                    <td data-field="amount">${scholarship.amount}</td>
                    <td data-field="confDate">${scholarship.confDate ? scholarship.confDate : ''}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('장학금 정보를 불러오는 중 오류가 발생했습니다.');
        });
});

// 신규
// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('admin-shipinfo-newBtn').addEventListener('click', function() {

    // 폼 초기화 (입력된 데이터 초기화)
    document.getElementById('admin_shipinfo_name').value = '';
    document.getElementById('admin_shipinfo_studentId').value = '';
    document.getElementById('admin_shipinfo_department').value = '';
    document.getElementById('admin_shipinfo_year').value = '';
    document.getElementById('admin_shipinfo_semester').value = '';
    document.getElementById('admin_shipinfo_scholarshipName').value = '';
    document.getElementById('admin_shipinfo_paymentType').value = '';
    document.getElementById('admin_shipinfo_amount').value = '';

    alert('새로운 장학 정보를 추가했습니다. 정보를 입력해주세요.');

    const tableBody = document.getElementById('admin-stushipinfo-TableBody');
    const newshipinfoId = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성 (현재 행의 개수를 기반으로)
    const displayId = newshipinfoId.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = newshipinfoId;  // 고유한 data-id 부여

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    // 입력된 폼 데이터 가져오기
    const name = document.getElementById('admin_shipinfo_name').value;
    const studentId = document.getElementById('admin_shipinfo_studentId').value;
    const department = document.getElementById('admin_shipinfo_department').value;
    const year = document.getElementById('admin_shipinfo_year').value;
    const semester = document.getElementById('admin_shipinfo_semester').value;
    const scholarshipName = document.getElementById('admin_shipinfo_scholarshipName').value;
    const paymentType = document.getElementById('admin_shipinfo_paymentType').value;
    const amount = document.getElementById('admin_shipinfo_amount').value;
    const confDate = document.getElementById('admin_shipinfo_confDate').value;

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="name">${name}</td> <!-- 이름 -->
        <td data-field="studentId">${studentId}</td> <!-- 학번 -->
        <td data-field="department">${department}</td> <!-- 학과 -->
        <td data-field="year">${year}</td> <!-- 학년도 -->
        <td data-field="semester">${semester}</td> <!-- 학기 -->
        <td data-field="scholarshipName">${scholarshipName}</td> <!-- 장학금명 -->
        <td data-field="type">${paymentType}</td> <!-- 지급구분 -->
        <td data-field="amount">${amount}</td>  <!-- 장학금액 -->
        <td data-field="confDate">${confDate}</td> <!-- 확정일자 -->

    `;


    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-stushipinfo-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('stushipinfo-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTimeShipEditing(newRow);
});

// 실시간으로 수정 내용 반영
function addRealTimeShipEditing(row) {
    const admin_shipinfo_nameCell = row.querySelector('td[data-field="name"]');
    const admin_shipinfo_studentIdCell = row.querySelector('td[data-field="studentId"]');
    const admin_shipinfo_departmentCell = row.querySelector('td[data-field="department"]');
    const admin_shipinfo_yearCell = row.querySelector('td[data-field="year"]');
    const admin_shipinfo_semesterCell = row.querySelector('td[data-field="semester"]');
    const admin_shipinfo_scholarshipNameCell = row.querySelector('td[data-field="scholarshipName"]')
    const admin_shipinfo_paymentTypeCell = row.querySelector('td[data-field="type"]')
    const admin_shipinfo_amountCell = row.querySelector('td[data-field="amount"]')
    const admin_shipinfo_confDateCell = row.querySelector('td[data-field="confDate"]')

    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('admin_shipinfo_name').addEventListener('input', function() {
        if (admin_shipinfo_nameCell) {
            admin_shipinfo_nameCell.textContent = this.value;  // 이름 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_studentId').addEventListener('input', function() {
        if (admin_shipinfo_studentIdCell) {
            admin_shipinfo_studentIdCell.textContent = this.value;  // 학번 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_department').addEventListener('input', function() {
        if (admin_shipinfo_departmentCell) {
            admin_shipinfo_departmentCell.textContent = this.value;  // 학과 수정 반영
        }
    });


    document.getElementById('admin_shipinfo_year').addEventListener('input', function() {
        if (admin_shipinfo_yearCell) {
            admin_shipinfo_yearCell.textContent = this.value;  // 상태 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_semester').addEventListener('input', function() {
        if (admin_shipinfo_semesterCell) {
            admin_shipinfo_semesterCell.textContent = this.value;  // 상태 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_scholarshipName').addEventListener('input', function() {
        if (admin_shipinfo_scholarshipNameCell) {
            admin_shipinfo_scholarshipNameCell.textContent = this.value;  // 상태 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_paymentType').addEventListener('input', function() {
        if (admin_shipinfo_paymentTypeCell) {
            admin_shipinfo_paymentTypeCell.textContent = this.value;  // 상태 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_amount').addEventListener('input', function() {
        if (admin_shipinfo_amountCell) {
            admin_shipinfo_amountCell.textContent = this.value;  // 상태 수정 반영
        }
    });

    document.getElementById('admin_shipinfo_confDate').addEventListener('input', function() {
        if (admin_shipinfo_confDateCell) {
            admin_shipinfo_confDateCell.textContent = this.value;  // 상태 수정 반영
        }
    });

}