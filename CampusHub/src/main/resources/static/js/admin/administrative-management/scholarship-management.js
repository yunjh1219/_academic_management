// // 모달 열기
// document.getElementById('openAddModal').onclick = function() {
//     document.getElementById('studentaddModal').style.display = 'block';
// }
//
// // 모달 닫기
// document.getElementById('close-modal').onclick = function() {
//     document.getElementById('studentaddModal').style.display = 'none';
// }
//
// // 모달 외부 클릭 시 닫기
// window.onclick = function(event) {
//     if (event.target == document.getElementById('studentaddModal')) {
//         document.getElementById('studentaddModal').style.display = 'none';
//     }
// }

// Mock 데이터 저장소 (빈 배열)
let mockScholarships = [];

// 조회 버튼 클릭 시 장학금 목록을 불러오는 함수
document.getElementById('searchScolarshipBtn').addEventListener('click', function () {
    console.log("장학금 목록을 불러오는 중...");

    const tableBody = document.getElementById('scholarshipTableBody');
    tableBody.innerHTML = ''; // 기존 데이터를 제거

    // Mock 데이터를 기반으로 테이블에 행 추가
    mockScholarships.forEach((scholarship, index) => {
        const row = document.createElement('tr');
        row.classList.add('scholarship-row');
        row.dataset.id = scholarship.id;

        row.innerHTML = `
            <td><input type="checkbox" class="scholarship-checkbox"></td>
            <td>${scholarship.name}</td>
            <td>${scholarship.studentId}</td>
            <td>${scholarship.department}</td>
            <td>${scholarship.year}</td>
            <td>${scholarship.semester}</td>
            <td>${scholarship.scholarshipName}</td>
            <td>${scholarship.paymentType}</td>
            <td>${scholarship.amount}</td>
            <td>${scholarship.confirmationDate}</td>
        `;

        tableBody.appendChild(row); // 테이블에 행 추가
    });
});

// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('newScholarshipBtn').addEventListener('click', function () {
    const tableBody = document.getElementById('scholarshipTableBody');

    const newRowId = `new+${tableBody.rows.length + 1}`;
    const newRow = document.createElement('tr');
    newRow.dataset.id = newRowId;

    newRow.innerHTML = `
        <td><input type="checkbox" class="scholarship-checkbox"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
    `;

    tableBody.appendChild(newRow);
    alert('새로운 장학금 정보를 추가했습니다. 내용을 입력하세요.');
});

// 저장 버튼 클릭 시 장학금 정보 저장
document.getElementById('saveScholarshipBtn').addEventListener('click', function () {
    const name = document.getElementById('name').value;
    const studentId = document.getElementById('studentId').value;
    const department = document.getElementById('department').value;
    const semester = document.getElementById('semester').value;
    const scholarshipName = document.getElementById('scholarshipName').value;
    const paymentType = document.getElementById('paymentType').value;
    const amount = document.getElementById('amount').value;
    const confirmationDate = new Date().toISOString().split('T')[0];

    const newId = mockScholarships.length + 1; // ID 자동 생성
    mockScholarships.push({
        id: newId,
        name: name,
        studentId: studentId,
        department: department,
        year: new Date().getFullYear(),
        semester: semester,
        scholarshipName: scholarshipName,
        paymentType: paymentType,
        amount: amount,
        confirmationDate: confirmationDate,
    });

    alert('장학금 정보가 저장되었습니다.');
    document.getElementById('editNoticeForm').reset(); // 폼 초기화

    // 저장 후 테이블 갱신: 조회 버튼 클릭 이벤트 호출
    document.getElementById('searchScolarshipBtn').click();
});

// 삭제 버튼 클릭 시 선택된 장학금 정보 삭제
document.getElementById('deleteScholarshipBtn').addEventListener('click', function () {
    const selectedCheckboxes = document.querySelectorAll('.scholarship-checkbox:checked');

    if (selectedCheckboxes.length === 0) {
        alert('삭제할 장학금 정보를 선택하세요.');
        return;
    }

    selectedCheckboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const scholarshipId = row.dataset.id;
        mockScholarships = mockScholarships.filter(scholarship => scholarship.id !== parseInt(scholarshipId));
        row.remove(); // 테이블에서 행 제거
    });

    alert('선택한 장학금 정보가 삭제되었습니다.');
});

// 검색 버튼 클릭 시 필터링된 데이터 표시
document.querySelector('.search-container button').addEventListener('click', function () {
    const filterTypeElement = document.querySelector('.searchf-box select');
    const filterValueElement = document.querySelector('.search-box input');



    if (!filterTypeElement || !filterValueElement) {
        console.error("필터 타입 또는 검색 값 요소를 찾을 수 없습니다.");
        return;
    }

    const filterType = filterTypeElement.value.trim(); // 선택된 필터 타입 (학과/학번)
    const filterValue = filterValueElement.value.trim(); // 입력된 검색 값

    const tableBody = document.getElementById('scholarshipTableBody');
    if (!tableBody) {
        console.error("테이블 본문 요소를 찾을 수 없습니다.");
        return;
    }

    tableBody.innerHTML = ''; // 기존 테이블 초기화

    // 검색 조건에 따른 데이터 필터링
    const filteredScholarships = mockScholarships.filter((scholarship) => {
        if (!filterValue) return true; // 검색어가 비어 있으면 모든 데이터 반환
        if (filterType === "학과") {
            return scholarship.department.includes(filterValue);
        } else if (filterType === "학번") {
            return scholarship.studentId.includes(filterValue);
        }
        return false;
    });

    // 필터링된 데이터를 테이블에 추가
    filteredScholarships.forEach((scholarship) => {
        const row = document.createElement('tr');
        row.classList.add('scholarship-row');
        row.dataset.id = scholarship.id;

        row.innerHTML = `
            <td><input type="checkbox" class="scholarship-checkbox"></td>
            <td>${scholarship.name}</td>
            <td>${scholarship.studentId}</td>
            <td>${scholarship.department}</td>
            <td>${scholarship.year}</td>
            <td>${scholarship.semester}</td>
            <td>${scholarship.scholarshipName}</td>
            <td>${scholarship.paymentType}</td>
            <td>${scholarship.amount}</td>
            <td>${scholarship.confirmationDate}</td>
        `;

        tableBody.appendChild(row);
    });

    // 검색 결과가 없는 경우 메시지 표시
    if (filteredScholarships.length === 0) {
        const noDataRow = document.createElement('tr');
        noDataRow.innerHTML = `
            <td colspan="10" style="text-align: center;">검색 결과가 없습니다.</td>
        `;
        tableBody.appendChild(noDataRow);
    }
});
