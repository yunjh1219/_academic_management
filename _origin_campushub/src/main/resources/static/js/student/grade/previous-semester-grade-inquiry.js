// Mock 데이터
const previousSemesterGrades = [
    {
        year: "2024",
        semester: "1",
        lectureName: "자바 프로그래밍",
        credits: 3,
        grade: "A+",
        gpa: 4.5
    },
    {
        year: "2024",
        semester: "1",
        lectureName: "머신러닝",
        credits: 3,
        grade: "A+",
        gpa: 4.5
    },
    {
        year: "2024",
        semester: "1",
        lectureName: "운영체제",
        credits: 3,
        grade: "A+",
        gpa: 4.5
    },
    {
        year: "2024",
        semester: "1",
        lectureName: "소프트웨어 공학",
        credits: 3,
        grade: "A+",
        gpa: 4.5
    }
];

const semesterDetails = [
    {
        year:"2024",
        semester:"1",
        studentName: "전영욱",
        department: "컴퓨터공학과",
        grade: "A+",
        gpa: 4.5
    }
];

// 조회 버튼 클릭 시 데이터 업데이트
document.getElementById('previous_semester_grade_inquiry_searchBtn').addEventListener('click', function () {
    updatePreviousSemesterGrades();
    updateSemesterDetails();
});

// 기이수 성적 데이터 테이블 업데이트
function updatePreviousSemesterGrades() {
    const tableBody = document.getElementById('previous_current_semester_grade_inquiry_tablebody');
    tableBody.innerHTML = ''; // 기존 데이터 초기화

    previousSemesterGrades.forEach((data, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${data.year}</td>
            <td>${data.semester}</td>
            <td>${data.lectureName}</td>
            <td>${data.credits}</td>
            <td>${data.grade}</td>
            <td>${data.gpa}</td>
        `;
        tableBody.appendChild(row);
    });
}

// 학기별 성적 내역 데이터 테이블 업데이트
function updateSemesterDetails() {
    const tableBody = document.getElementById('previous_semester_grade_inquiry_tablebody');
    tableBody.innerHTML = ''; // 기존 데이터 초기화

    semesterDetails.forEach((data, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${data.year}</td>
            <td>${data.semester}</td>
            <td>${data.studentName}</td>
            <td>${data.department}</td>
            <td>${data.grade}</td>
            <td>${data.gpa}</td>
        `;
        tableBody.appendChild(row);
    });
}
