// Mock 데이터 저장소
const mockData = [
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


// 조회 버튼 클릭 시 성적 목록을 불러오는 함수
document.getElementById('current_semester_grade_searchBtn').addEventListener('click', function () {
    updateTable();
});

// 데이터 테이블 업데이트 함수
function updateTable() {
    const tableBody = document.getElementById('current_semester_grade_tablebody');
    tableBody.innerHTML = ''; // 기존 데이터 초기화

    mockData.forEach((data, index) => {
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

    // 콘솔에 현재 mockData 출력
    console.log('조회된 데이터:', mockData);
}
