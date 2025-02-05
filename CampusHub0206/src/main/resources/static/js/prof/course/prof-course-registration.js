// 교수 강의 목록을 가져오는 함수
document.getElementById('prof_course_registration_searchBtn').addEventListener('click', function () {
    const url = '/api/professor/course';
    const token = localStorage.getItem('jwtToken');

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('가져온 강의 데이터:', data);
            const tableBody = document.getElementById('prof_course_registration_TableBody');
            tableBody.innerHTML = '';
            data.data.forEach((course, index) => {
                const row = document.createElement('tr');
                row.dataset.id = course.id;
                row.innerHTML = `
                    <td><input type="checkbox" class="course-checkbox"></td>
                    <td>${index + 1}</td>
                    <td>${course.courseGrade}</td>
                    <td>${course.courseName}</td>
                    <td>${course.courseDivision}</td>
                    <td>${course.creditScore}</td>
                    <td>${course.professorName}</td>
                    <td>${course.courseRoom}</td>
                    <td>${course.courseDay}</td>
                    <td>${course.startPeriod} - ${course.endPeriod}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('강의 목록을 불러오는 중 오류가 발생했습니다.');
        });
});

// 신규 버튼 클릭 시 새로운 강의 추가
document.getElementById('prof_course_registration_newBtn').addEventListener('click', function () {
    document.getElementById('prof_course_registration_name').value = '';
    document.getElementById('prof_course_registration_grade').value = '';
    document.getElementById('prof_course_registration_credits').value = '';
    document.getElementById('prof_course_registration_classroom').value = '';
    document.getElementById('courseType').value = '';
    document.getElementById('dayOfWeek').value = '';
    document.getElementById('classTime').value = '';
    document.getElementById('endClassTime').value = '';

    const tableBody = document.getElementById('prof_course_registration_TableBody');
    const newCourseId = `new+${tableBody.rows.length + 1}`;
    const newRow = document.createElement('tr');
    newRow.dataset.id = newCourseId;

    newRow.innerHTML = `
        <td><input type="checkbox" class="course-checkbox"></td>
        <td>${tableBody.rows.length + 1}</td>
        <td>${document.getElementById('prof_course_registration_grade').value}</td>
        <td>${document.getElementById('prof_course_registration_name').value}</td>
        <td>${document.getElementById('courseType').value}</td>
        <td>${document.getElementById('prof_course_registration_credits').value}</td>
        <td><!-- 교수 이름 --></td>
        <td>${document.getElementById('prof_course_registration_classroom').value}</td>
        <td>${document.getElementById('dayOfWeek').value}</td>
        <td>${document.getElementById('classTime').value} - ${document.getElementById('endClassTime').value}</td>
    `;

    tableBody.appendChild(newRow);
});


// 저장 버튼
document.getElementById('prof_course_registration_savBtn').addEventListener('click', function () {
    const courseName = document.getElementById('prof_course_registration_name').value;
    const courseGrade = document.getElementById('prof_course_registration_grade').value;
    const credits = parseInt(document.getElementById('prof_course_registration_credits').value, 10);
    const room = document.getElementById('prof_course_registration_classroom').value;
    const courseDay = document.getElementById('dayOfWeek').value;
    const startPeriod = parseInt(document.getElementById('classTime').value, 10);
    const endPeriod = parseInt(document.getElementById('endClassTime').value, 10);

    // 한글 → 숫자 변환 매핑
    const divisionMapping = {
        "전공필수": 0,
        "전공선택": 1,
        "교양": 2
    };

    // 숫자 → 한글 변환 매핑 (신규 저장 시 사용)
    const reverseDivisionMapping = {
        0: "전공필수",
        1: "전공선택",
        2: "교양"
    };

    const divisionText = document.getElementById('courseType').value;
    const selectedRow = document.querySelector('#prof_course_registration_TableBody tr.selected');

    let url = '';
    let method = '';
    let division;

    if (selectedRow) {
        const courseIdInRow = selectedRow.dataset.id;
        if (courseIdInRow.startsWith('new+')) {
            url = '/api/professor/course';
            method = 'POST';
            division = divisionText;  // 신규 저장 시 한글로 보냄
        } else {
            url = `/api/professor/course/${courseIdInRow}`;
            method = 'PATCH';
            division = divisionMapping[divisionText];  // 수정 시 숫자로 변환
        }
    } else {
        url = '/api/professor/course';
        method = 'POST';
        division = divisionText;  // 신규 저장 시 한글로 보냄
    }

    // 강의 데이터 객체 생성
    const courseData = {
        courseName: courseName,
        room: room,
        division: division, // POST는 한글, PATCH는 숫자로 설정
        courseDay: courseDay,
        courseGrade: courseGrade,
        startPeriod: startPeriod,
        endPeriod: endPeriod,
        credits: credits
    };

    console.log('전송할 강의 데이터:', JSON.stringify(courseData, null, 2));

    const token = localStorage.getItem('jwtToken');

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(courseData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(errData => {
                    throw new Error(`오류 발생: ${errData.message}`);
                });
            }
        })
        .then(data => {
            alert(method === 'PATCH' ? '강의 정보가 성공적으로 수정되었습니다.' : '새로운 강의 정보가 성공적으로 저장되었습니다.');
            document.getElementById('prof_course_registration_searchBtn').click();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('강의 정보를 저장하는 중 오류가 발생했습니다: ' + error.message);
        });
});





// 단건 선택 및 폼에 데이터 표시
document.getElementById('prof_course_registration_TableBody').addEventListener('click', function (event) {
    const clickedRow = event.target.closest('tr');
    if (clickedRow) {
        const previouslySelectedRow = document.querySelector('#prof_course_registration_TableBody tr.selected');
        if (previouslySelectedRow) {
            previouslySelectedRow.classList.remove('selected');
        }
        clickedRow.classList.add('selected');

        // 선택된 행의 데이터로 폼 채우기
        document.getElementById('prof_course_registration_name').value = clickedRow.children[3].textContent;
        document.getElementById('prof_course_registration_grade').value = clickedRow.children[2].textContent;
        document.getElementById('prof_course_registration_credits').value = clickedRow.children[5].textContent;
        document.getElementById('prof_course_registration_classroom').value = clickedRow.children[7].textContent;
        document.getElementById('courseType').value = clickedRow.children[4].textContent;
        document.getElementById('dayOfWeek').value = clickedRow.children[8].textContent;
        document.getElementById('classTime').value = clickedRow.children[9].textContent.split(' - ')[0]; // 시작 시간
        document.getElementById('endClassTime').value = clickedRow.children[9].textContent.split(' - ')[1]; // 종료 시간
    }
});

// 실시간으로 수정 내용 반영
function addRealTimeEditing(row) {
    const courseNameCell = row.querySelector('td[data-field="courseName"]');
    const gradeCell = row.querySelector('td[data-field="grade"]');
    const creditsCell = row.querySelector('td[data-field="credits"]');
    const classroomCell = row.querySelector('td[data-field="classroom"]');
    const courseTypeCell = row.querySelector('td[data-field="courseType"]');
    const dayOfWeekCell = row.querySelector('td[data-field="dayOfWeek"]');
    const startPeriodCell = row.querySelector('td[data-field="startPeriod"]'); // 시작 시간 셀
    const endPeriodCell = row.querySelector('td[data-field="endPeriod"]'); // 종료 시간 셀

    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('prof_course_registration_name').addEventListener('input', function() {
        if (courseNameCell) {
            courseNameCell.textContent = this.value;  // 강의명 수정 반영
        }
    });

    document.getElementById('prof_course_registration_grade').addEventListener('input', function() {
        if (gradeCell) {
            gradeCell.textContent = this.value;  // 학년 수정 반영
        }
    });

    document.getElementById('prof_course_registration_credits').addEventListener('input', function() {
        if (creditsCell) {
            creditsCell.textContent = this.value;  // 학점 수정 반영
        }
    });

    document.getElementById('prof_course_registration_classroom').addEventListener('input', function() {
        if (classroomCell) {
            classroomCell.textContent = this.value;  // 강의실 수정 반영
        }
    });

    document.getElementById('courseType').addEventListener('input', function() {
        if (courseTypeCell) {
            courseTypeCell.textContent = this.value;  // 이수구분 수정 반영
        }
    });

    document.getElementById('dayOfWeek').addEventListener('input', function() {
        if (dayOfWeekCell) {
            dayOfWeekCell.textContent = this.value;  // 요일 수정 반영
        }
    });

    document.getElementById('classTime').addEventListener('input', function() {
        if (startPeriodCell) {
            startPeriodCell.textContent = this.value;  // 시작 시간 수정 반영
        }
    });

    document.getElementById('endClassTime').addEventListener('input', function() {
        if (endPeriodCell) {
            endPeriodCell.textContent = this.value;  // 종료 시간 수정 반영
        }
    });
}