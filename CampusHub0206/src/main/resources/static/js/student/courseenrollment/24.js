document.getElementById('stu-MyCourses-SearchBtn').addEventListener('click', function() {
    const url = '/api/student/course/all';
    const token = localStorage.getItem('jwtToken');

    // 요청 URL을 콘솔에 출력
    console.log(`Sending request to: ${url}`);

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('데이터를 보자==========:', data);  // 데이터를 콘솔에 출력해서 확인

            const tableBody = document.getElementById('stu-cousreAllTableBody');
            if (tableBody) {  // tableBody가 null이 아닌지 확인
                tableBody.innerHTML = '';  // 기존 데이터 제거

                // 'data' 배열을 직접 처리
                if (Array.isArray(data.data)) {  // data.data 배열이 있는지 확인
                    data.data.forEach((course, index) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td><input type="checkbox" class="student-checkbox" data-id="${course.id}"></td>
                        <td data-field="index">${index + 1}</td>
                        <td data-field="courseGrade">${course.courseGrade}</td>  <!-- 학년 -->       
                        <td data-field="courseName">${course.courseName}</td> <!-- 강의명 --> 
                        <td data-field="courseDivision">${course.courseDivision}</td> <!-- 이수구분 --> 
                        <td data-field="creditScore">${course.creditScore}</td> <!-- 학점 --> 
                        <td data-field="professorName">${course.professorName}</td> <!-- 담당교수 --> 
                        <td data-field="courseRoom">${course.courseRoom}</td> <!-- 강의실 --> 
                        <td data-field="courseDay">${course.courseDay}</td> <!-- 요일 -->  
                        <td data-field="courseTime">${course.startPeriod}교시 ~ ${course.endPeriod}교시</td>  <!-- 교시 범위 표시 -->
                    `;
                        tableBody.appendChild(row);
                    });
                } else {
                    console.error('courses 배열이 존재하지 않거나 형식이 잘못되었습니다.', data);
                    alert('데이터 형식에 문제가 있습니다.');
                }
            } else {
                console.error('tableBody 요소를 찾을 수 없습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('데이터를 불러오는 데 실패했습니다.');
        });
});



document.getElementById('AddCourse-NoticeBtn').addEventListener('click', function() {
    const selectedCourses = document.querySelectorAll('.course-checkbox:checked');  // 체크된 강의들

    const courseRegistrationsTableBody = document.getElementById('stu-cousreAllTableBody');
    if (courseRegistrationsTableBody) {
        selectedCourses.forEach(checkbox => {
            const courseId = checkbox.getAttribute('data-course-id');  // 체크된 강의의 ID
            const courseRow = checkbox.closest('tr');  // 체크박스를 포함한 행

            const row = document.createElement('tr');
            row.innerHTML = `
                <td><button class="registration-btn" data-course-id="${courseId}">신청</button></td> <!-- 신청 버튼 -->
                <td data-field="index">${courseRegistrationsTableBody.rows.length + 1}</td> <!-- 순차적인 번호 -->
                 <td data-field="schoolYear">${courseRow.querySelector('[data-field="schoolYear"]').textContent}</td>  <!-- 학년 -->
                <td data-field="courseName">${courseRow.querySelector('[data-field="courseName"]').textContent}</td>  <!-- 강의명 -->
                <td data-field="division">${courseRow.querySelector('[data-field="division"]').textContent}</td>  <!-- 이수구분 -->
                <td data-field="credits">${courseRow.querySelector('[data-field="credits"]').textContent}</td>  <!-- 학점 -->
                <td data-field="professorName">${courseRow.querySelector('[data-field="professorName"]').textContent}</td>  <!-- 담당교수 -->
                <td data-field="room">${courseRow.querySelector('[data-field="room"]').textContent}</td>  <!-- 강의실 -->
                <td data-field="courseDay">${courseRow.querySelector('[data-field="courseDay"]').textContent}</td>  <!-- 요일 -->
                <td data-field="courseTime">${courseRow.querySelector('[data-field="courseTime"]').textContent}</td>  <!-- 시간 -->
                <td><button class="cancel-btn">취소</button></td> <!-- 취소 버튼 추가 -->
            `;
            courseRegistrationsTableBody.appendChild(row);
        });
    } else {
        console.error('Course-Registrations-TableBody 요소를 찾을 수 없습니다.');
    }
});

// 취소 버튼 클릭 시 해당 행 삭제
document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('cancel-btn')) {
        const row = event.target.closest('tr');  // 취소 버튼이 있는 행
        if (row) {
            row.remove();  // 해당 행 삭제
        }
    }
});

// 수강신청 버튼 클릭 시 서버에 저장 요청
document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('registration-btn')) {
        const courseId = event.target.getAttribute('data-course-id');  // 클릭된 강의의 ID
        const courseRow = event.target.closest('tr');  // 해당 버튼이 속한 tr

        const courseToRegister = {
            id: courseId,
            courseName: courseRow.querySelector('[data-field="courseName"]').textContent,
            schoolYear: courseRow.querySelector('[data-field="schoolYear"]').textContent,
            division: courseRow.querySelector('[data-field="division"]').textContent,
            credits: courseRow.querySelector('[data-field="credits"]').textContent,
            professorName: courseRow.querySelector('[data-field="professorName"]').textContent,
            room: courseRow.querySelector('[data-field="room"]').textContent,
            courseDay: courseRow.querySelector('[data-field="courseDay"]').textContent,
            start: courseRow.querySelector('[data-field="courseTime"]').textContent.split('~')[0].trim(),
            end: courseRow.querySelector('[data-field="courseTime"]').textContent.split('~')[1].trim(),
        };

        // POST 요청을 통해 수강신청 데이터 서버로 전송
        fetch('/api/course/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(courseToRegister),  // 강의 정보 전송
        })
            .then(response => response.json())
            .then(data => {
                console.log('수강신청이 완료되었습니다.', data);
                alert('수강신청이 완료되었습니다.');

                // 신청 버튼을 신청완료로 변경
                event.target.textContent = '완료';  // 버튼 텍스트 변경
                event.target.disabled = true;  // 버튼 비활성화 (선택 사항)

                // 취소 버튼 비활성화
                const cancelButton = event.target.closest('tr').querySelector('.cancel-btn');
                if (cancelButton) {
                    cancelButton.disabled = true;  // 취소 버튼 비활성화
                }

            })
            .catch(error => {
                console.error('수강신청 중 오류 발생:', error);
                alert('수강신청 중 오류가 발생했습니다.');
            });
    }




});

