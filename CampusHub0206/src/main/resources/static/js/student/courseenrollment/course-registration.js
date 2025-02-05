// 강의 조회
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
                        <td><input type="checkbox" class="course-checkbox" data-id="${course.id}" data-course-grade="${course.courseGrade}" data-course-name="${course.courseName}" data-course-division="${course.courseDivision}" data-credit-score="${course.creditScore}" data-professor-name="${course.professorName}" data-course-room="${course.courseRoom}" data-course-day="${course.courseDay}" data-course-time="${course.startPeriod}교시 ~ ${course.endPeriod}교시"></td>
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

// 강의 추가 버튼 클릭
document.getElementById('stu-AddCourse-CoursesBtn').addEventListener('click', function() {
    const selectedCourses = document.querySelectorAll('.course-checkbox:checked');  // 체크된 강의들
    const courseRegistrationsTableBody = document.getElementById('stu-Course-Registrations-TableBody');

    if (courseRegistrationsTableBody) {
        // 이미 등록된 강의 ID를 추적하기 위한 Set
        const registeredCourseIds = new Set();  // registeredCourseIds를 이곳에서 정의합니다.

        // 현재 등록된 강의들을 Set에 추가하여 중복을 피함
        const existingRows = courseRegistrationsTableBody.querySelectorAll('tr');
        existingRows.forEach(row => {
            const courseId = row.querySelector('.registration-btn')?.getAttribute('data-course-id');
            if (courseId) {
                registeredCourseIds.add(courseId);
            }
        });

        selectedCourses.forEach((checkbox, index) => {  // index를 추가하여 순번 계산
            const courseId = checkbox.getAttribute('data-id');  // 체크된 강의의 ID
            const courseName = checkbox.getAttribute('data-course-name');  // 강의명
            const courseGrade = checkbox.getAttribute('data-course-grade');  // 학년
            const courseDivision = checkbox.getAttribute('data-course-division');  // 이수구분
            const creditScore = checkbox.getAttribute('data-credit-score');  // 학점
            const professorName = checkbox.getAttribute('data-professor-name');  // 담당교수
            const courseRoom = checkbox.getAttribute('data-course-room');  // 강의실
            const courseDay = checkbox.getAttribute('data-course-day');  // 요일
            const courseTime = checkbox.getAttribute('data-course-time');  // 교시 시간대

            // 이미 등록된 강의는 중복으로 추가하지 않음
            if (registeredCourseIds.has(courseId)) {
                console.log(`강의 ${courseName}는 이미 등록되어 있습니다.`);
                return;  // 중복된 강의는 추가하지 않음
            }

            // 새 행 생성
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><button class="registration-btn" data-course-id="${courseId}">신청</button></td> <!-- 신청 버튼 -->
                <td data-field="index">${index + 1}</td>  <!-- 순번 -->
                <td data-field="courseGrade">${courseGrade}</td>  <!-- 학년 -->
                <td data-field="courseName">${courseName}</td>  <!-- 강의명 -->
                <td data-field="courseDivision">${courseDivision}</td>  <!-- 이수구분 -->
                <td data-field="creditScore">${creditScore}</td>  <!-- 학점 -->
                <td data-field="professorName">${professorName}</td>  <!-- 담당교수 -->
                <td data-field="courseRoom">${courseRoom}</td>  <!-- 강의실 -->
                <td data-field="courseDay">${courseDay}</td>  <!-- 요일 -->
                <td data-field="courseTime">${courseTime}</td>  <!-- 시간 -->
                <td><button class="cancel-btn">취소</button></td> <!-- 취소 버튼 -->
            `;

            // 취소 버튼 클릭 시 해당 행 제거
            row.querySelector('.cancel-btn').addEventListener('click', function() {
                row.remove();  // 해당 강의를 등록 테이블에서 제거
            });

            // 테이블에 행 추가
            courseRegistrationsTableBody.appendChild(row);

            // 등록된 강의 ID를 Set에 추가
            registeredCourseIds.add(courseId);
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

document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('registration-btn')) {
        const courseId = event.target.getAttribute('data-course-id');  // 클릭된 강의의 ID

        // 수강 신청할 강의 ID 목록 (현재 하나의 강의만 신청하므로 배열로 전달)
        const courseIdsToRegister = [courseId];  // List<Long> 형식에 맞게 배열로 감싸서 전송

        console.log('Selected course ID:', courseId);
        console.log('Course IDs to register:', courseIdsToRegister);

        const token = localStorage.getItem('jwtToken');  // JWT 토큰 가져오기
        console.log('JWT Token:', token);

        // POST 요청을 통해 수강신청 데이터 서버로 전송
        fetch('/api/student/course/join', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(courseIdsToRegister)  // 배열 형태로 전송
        })
            .then(response => {
                console.log('Response Status:', response.status);
                return response.json();  // JSON 응답 반환
            })
            .then(data => {
                console.log('Response Data:', data);

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
