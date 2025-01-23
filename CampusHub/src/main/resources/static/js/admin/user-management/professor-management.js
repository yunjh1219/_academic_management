// Mock 데이터 저장소 (빈 배열)
let mockProfessors = [];

// 조회 버튼 클릭 시 교수 정보 목록을 불러오는 함수
document.getElementById('searchBtn').addEventListener('click', function () {
    console.log("교수 정보 목록을 불러오는 중...");

    const tableBody = document.getElementById('professorTableBody');
    tableBody.innerHTML = ''; // 기존 데이터를 제거

    // Mock 데이터를 기반으로 테이블에 행 추가
    mockProfessors.forEach((professor, index) => {
        const row = document.createElement('tr');
        row.classList.add('professor-row');
        row.dataset.id = professor.id;

        row.innerHTML = `
            <td><input type="checkbox" class="professor-checkbox"></td>
            <td>${index + 1}</td>
            <td>${professor.name}</td>
            <td>${professor.professorId}</td>
            <td>${professor.department}</td>
            <td>${professor.type}</td>
            <td>${professor.status}</td>
            <td>${professor.remarks || ''}</td>
        `;

        tableBody.appendChild(row); // 테이블에 행 추가
    });
});

// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('newProfessorBtn').addEventListener('click', function () {
    const tableBody = document.getElementById('professorTableBody');

    const newRowId = `new+${tableBody.rows.length + 1}`;
    const displayId = newRowId.replace('new+', '');
    const newRow = document.createElement('tr');
    newRow.dataset.id = newRowId;

    newRow.innerHTML = `
        <td><input type="checkbox" class="professor-checkbox"></td>
        <td>${displayId}</td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
        <td contenteditable="true"></td>
    `;

    tableBody.appendChild(newRow);
    alert('새로운 교수 정보를 추가했습니다. 내용을 입력하세요.');
});

// 저장 버튼 클릭 시 교수 정보 저장
document.getElementById('saveBtn').addEventListener('click', function () {
    const selectedRow = document.querySelector('#professorTableBody tr.selected');
    let professorIdInRow = null;

    const name = document.getElementById('professor-name').value;
    const birthdate = document.getElementById('professor-birthdate').value + "T00:00:00";
    const department = document.getElementById('professor-department').value;
    const professorId = document.getElementById('professorId').value;
    const phone = document.getElementById('professor-phone').value;
    const email = document.getElementById('professor-email').value;
    const address = document.getElementById('professor-address').value;
    const password = document.getElementById('professor-password').value;

    if (selectedRow) {
        professorIdInRow = selectedRow.dataset.id;

        if (professorIdInRow.startsWith('new+')) {
            // 새로운 교수 정보 추가
            const newId = mockProfessors.length + 1; // ID 자동 생성
            mockProfessors.push({
                id: newId,
                name: name,
                professorId: professorId,
                department: department,
                type: 'PROFESSOR',
                status: 'ACTIVE',
                remarks: '',
            });
            alert('새로운 교수 정보가 저장되었습니다.');
        } else {
            // 기존 교수 정보 수정
            const professor = mockProfessors.find(p => p.id === parseInt(professorIdInRow));
            if (professor) {
                professor.name = name;
                professor.professorId = professorId;
                professor.department = department;
                alert('교수 정보가 수정되었습니다.');
            }
        }
    } else {
        // 새로운 교수 정보 추가
        const newId = mockProfessors.length + 1; // ID 자동 생성
        mockProfessors.push({
            id: newId,
            name: name,
            professorId: professorId,
            department: department,
            type: 'PROFESSOR',
            status: 'ACTIVE',
            remarks: '',
        });
        alert('새로운 교수 정보가 저장되었습니다.');
    }

    document.getElementById('editNoticeForm').reset(); // 폼 초기화
});

// 삭제 버튼 클릭 시 선택된 교수 정보 삭제
document.getElementById('deleteBtn').addEventListener('click', function () {
    const selectedRows = document.querySelectorAll('#professorTableBody tr.selected');

    if (selectedRows.length === 0) {
        alert('삭제할 교수 정보를 선택하세요.');
        return;
    }

    selectedRows.forEach(row => {
        const professorIdInRow = row.dataset.id;
        // Mock 데이터에서 삭제
        mockProfessors = mockProfessors.filter(p => p.id !== parseInt(professorIdInRow));
    });

    alert('선택한 교수 정보가 삭제되었습니다.');

    // 삭제 후 테이블 갱신: 조회 버튼 클릭 이벤트 호출
    document.getElementById('searchBtn').click();
});


// 테이블 행 클릭 시 선택 상태 변경
document.getElementById('professorTableBody').addEventListener('click', function (event) {
    const row = event.target.closest('tr');
    if (row) {
        row.classList.toggle('selected');
    }
});

// 검색 버튼 클릭 시 이름 또는 교번으로 교수 정보를 검색
document.querySelector('.search-container button').addEventListener('click', function () {
    const searchInput = document.querySelector('.search-container input[type="text"]').value.trim();

    console.log("ddd");

    if (!searchInput) {
        alert("이름 또는 교번을 입력하세요.");
        return;
    }

    const filteredProfessors = mockProfessors.filter(professor =>
        professor.name.includes(searchInput) || professor.professorId.includes(searchInput)
    );

    const tableBody = document.getElementById('professorTableBody');
    tableBody.innerHTML = ''; // 기존 데이터를 제거

    if (filteredProfessors.length === 0) {
        alert("검색 결과가 없습니다.");
        return;
    }

    // 필터링된 데이터를 기반으로 테이블 업데이트
    filteredProfessors.forEach((professor, index) => {
        const row = document.createElement('tr');
        row.classList.add('professor-row');
        row.dataset.id = professor.id;

        row.innerHTML = `
            <td><input type="checkbox" class="professor-checkbox"></td>
            <td>${index + 1}</td>
            <td>${professor.name}</td>
            <td>${professor.professorId}</td>
            <td>${professor.department}</td>
            <td>${professor.type}</td>
            <td>${professor.status}</td>
            <td>${professor.remarks || ''}</td>
        `;

        tableBody.appendChild(row);
    });
});

