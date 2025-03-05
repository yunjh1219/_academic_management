// Mock 데이터 저장소 (빈 배열)
const processingMockData = [];

// 신규 버튼 클릭 시 입력 필드 초기화 및 빈 행 추가
document.getElementById('grade_processing_newBtn').addEventListener('click', function () {
    // 입력 필드 초기화
    document.getElementById('grade_processing_name').value = '';
    document.getElementById('grade_processing_dept').value = '';
    document.getElementById('grade_processing_stuId').value = '';
    document.getElementById('grade_processing_att').value = '';
    document.getElementById('grade_processing_ass').value = '';
    document.getElementById('grade_processing_examScore').value = '';
    document.getElementById('grade_processing_totalScore').value = '';
    document.getElementById('grade_processing_grade').value = '';
    document.getElementById('grade_processing_credits').value = '';
    document.getElementById('grade_processing_percentage').value = '';

    // 테이블에 빈 행 추가
    const tableBody = document.getElementById('grade_processing_tablebody');
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
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    `;
    tableBody.appendChild(row);
});

// 저장 버튼 클릭 시 데이터 저장
document.getElementById('grade_processing_savBtn').addEventListener('click', function () {
    const name = document.getElementById('grade_processing_name').value.trim();
    const department = document.getElementById('grade_processing_dept').value.trim();
    const studentId = document.getElementById('grade_processing_stuId').value.trim();
    const attendance = parseFloat(document.getElementById('grade_processing_att').value) || 0;
    const assignment = parseFloat(document.getElementById('grade_processing_ass').value) || 0;
    const examScore = parseFloat(document.getElementById('grade_processing_examScore').value) || 0;

    // 총점수 계산
    const totalScore = attendance + assignment + examScore;
    document.getElementById('grade_processing_totalScore').value = totalScore;

    // 등급 및 학점 계산
    let grade = '';
    let credits = 0;
    if (totalScore >= 95) {
        grade = 'A+';
        credits = 4.5;
    } else if (totalScore >= 90) {
        grade = 'A';
        credits = 4.0;
    } else if (totalScore >= 85) {
        grade = 'B+';
        credits = 3.5;
    } else if (totalScore >= 80) {
        grade = 'B';
        credits = 3.0;
    } else if (totalScore >= 75) {
        grade = 'C+';
        credits = 2.5;
    } else if (totalScore >= 70) {
        grade = 'C';
        credits = 2.0;
    } else if (totalScore >= 65) {
        grade = 'D+';
        credits = 1.5;
    } else if (totalScore >= 60) {
        grade = 'D';
        credits = 1.0;
    } else {
        grade = 'F';
        credits = 0.0;
    }

    const percentage = (totalScore / (attendance + assignment + examScore)) * 100;

    const data = { name, department, studentId, attendance, assignment, examScore, totalScore, grade, credits, percentage };
    processingMockData.push(data);
    alert('저장되었습니다.');

    // 테이블 갱신
    updateTable();
    // 폼 초기화
    document.getElementById('editNoticeForm').reset();
});

// 조회 버튼 클릭 시 성적 목록을 불러오는 함수
document.getElementById('grade_processing_searchBtn').addEventListener('click', function () {
    updateTable();
});

// 데이터 테이블 업데이트 함수
function updateTable() {
    const tableBody = document.getElementById('grade_processing_tablebody');
    tableBody.innerHTML = ''; // 기존 데이터 초기화

    processingMockData.forEach((data, index) => { // 변경됨
        const row = document.createElement('tr');
        row.innerHTML = `
            <td><input type="checkbox" class="course-checkbox"></td>
            <td>${index + 1}</td>
            <td>${data.name}</td>
            <td>${data.studentId}</td>
            <td>${data.department}</td>
            <td>${data.attendance}</td>
            <td>${data.assignment}</td>
            <td>${data.examScore}</td>
            <td>${data.totalScore}</td>
            <td>${data.grade}</td>
            <td>${data.credits}</td>
            <td>${data.percentage.toFixed(2)}%</td>
        `;
        tableBody.appendChild(row);
    });

    // 콘솔에 현재 processingMockData 출력
    console.log('조회된 데이터:', processingMockData);
}

// 삭제 버튼 클릭 시 선택된 성적 정보 삭제
document.getElementById('grade_processing_delBtn').addEventListener('click', function () {
    const selectedCheckboxes = document.querySelectorAll('.course-checkbox:checked');

    if (selectedCheckboxes.length === 0) {
        alert('삭제할 성적 정보를 선택하세요.');
        return;
    }

    selectedCheckboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const index = Array.from(row.parentNode.children).indexOf(row);
        processingMockData.splice(index, 1); // 변경됨
        row.remove(); // 테이블에서 행 제거
    });

    alert('선택한 성적 정보가 삭제되었습니다.');
    // 콘솔에 현재 processingMockData 출력
    console.log('삭제 후 데이터:', processingMockData);
});
