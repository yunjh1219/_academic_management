// Mock 데이터 저장소 (빈 배열)
const mockData = [];

// 신규 버튼 클릭 시 입력 필드 초기화 및 빈 행 추가
document.getElementById('prof-enter-grade-newBtn').addEventListener('click', function () {
    // 입력 필드 초기화
    document.getElementById('enter_grade_name').value = '';
    document.getElementById('enter_grade_dept').value = '';
    document.getElementById('enter_grade_stuId').value = '';
    document.getElementById('midterm').value = '';
    document.getElementById('final').value = '';
    document.getElementById('totalScore').value = '';

    // 테이블에 빈 행 추가
    const tableBody = document.getElementById('enter-grade-tablebody');
    const row = document.createElement('tr');
    row.innerHTML = `
        <td><input type="checkbox" class="course-checkbox"></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    `;
    tableBody.appendChild(row);
});

document.getElementById('prof-enter-grade-savBtn').addEventListener('click', function () {
    const name = document.getElementById('enter_grade_name').value;
    const department = document.getElementById('enter_grade_dept').value;
    const studentId = document.getElementById('enter_grade_stuId').value;
    const midterm = parseFloat(document.getElementById('midterm').value) || 0;
    const final = parseFloat(document.getElementById('final').value) || 0;
    const totalScore = midterm + final;

    console.log('입력된 데이터:', { name, department, studentId, midterm, final, totalScore }); // 추가

    // 목데이터에 추가
    const data = { name, department, studentId, midterm, final, totalScore };
    mockData.push(data);
    alert('저장되었습니다.');

    // 폼 초기화
    document.getElementById('editNoticeForm').reset();
});


// 조회 버튼 클릭 시 성적 목록을 불러오는 함수
document.getElementById('prof-enter-grade-searchBtn').addEventListener('click', function () {
    const tableBody = document.getElementById('enter-grade-tablebody');
    tableBody.innerHTML = ''; // 기존 데이터 초기화

    // 목데이터를 테이블에 추가
    mockData.forEach((data, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td><input type="checkbox" class="course-checkbox"></td>
            <td>${index + 1}</td>
            <td>${data.name}</td>
            <td>${data.studentId}</td>
            <td>${data.department}</td>
            <td>${data.midterm}</td>
            <td>${data.final}</td>
            <td>${data.totalScore}</td>
        `;
        tableBody.appendChild(row);
    });

    // 콘솔에 현재 mockData 출력
    console.log('조회된 데이터:', mockData);
});

// 삭제 버튼 클릭 시 선택된 성적 정보 삭제
document.getElementById('prof-enter-grade-delBtn').addEventListener('click', function () {
    const selectedCheckboxes = document.querySelectorAll('.course-checkbox:checked');

    if (selectedCheckboxes.length === 0) {
        alert('삭제할 성적 정보를 선택하세요.');
        return;
    }

    selectedCheckboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const index = Array.from(row.parentNode.children).indexOf(row);
        mockData.splice(index, 1); // 목데이터에서 삭제
        row.remove(); // 테이블에서 행 제거
    });

    // 콘솔에 현재 mockData 출력
    console.log('삭제 후 데이터:', mockData);

    alert('선택한 성적 정보가 삭제되었습니다.');
});
