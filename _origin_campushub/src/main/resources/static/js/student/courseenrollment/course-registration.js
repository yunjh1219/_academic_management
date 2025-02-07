// 강의 조회
document.getElementById('stu-MyCourses-SearchBtn').addEventListener('click', function () {

    // 선택한 강의 테이블 본문 초기화
    const tableBody = document.getElementById('stu-Course-Registrations-TableBody');
    if (tableBody) {
        tableBody.innerHTML = '';  // 기존 데이터 제거
    }

    const url = '/api/student/course/all';
    const myCourseUrl = '/api/student/course';
    const token = localStorage.getItem('jwtToken');
    const division = document.getElementById('stu-course-division').value;
    const deptName = document.getElementById('stu-stu-courion-deptName').value;
    const courseName = null;

    const params = new URLSearchParams();
    if (division) params.append('division', division);
    if (deptName) params.append('deptNam',deptNam);
    if (courseName) params.append('courseName',courseName);

    // 요청 URL 및 파라미터 로그 찍기
    const requestUrl = `${url}?${params.toString()}`;

    Promise.all([
        fetch(requestUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        }).then(response => response.json()),
        fetch(myCourseUrl, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
    ]).then(([allCoursesData, myCourseData]) => {

        console.log("allCoursesData",allCoursesData);

        const tableBody = document.getElementById('stu-cousreAllTableBody');
        if (tableBody) {
            tableBody.innerHTML = '';  // 기존 데이터 제거

            // 본인 강의의 id들을 배열로 저장
            const myCourseIds = new Set(myCourseData.data.map(course => course.id));

            // 'allCoursesData.data' 배열을 순회하여 행 추가
            allCoursesData.data.forEach((course, index) => {
                const row = document.createElement('tr');

                // 본인 강의는 회색 배경 설정
                const isMyCourse = myCourseIds.has(course.id);
                // 본인 강의는 회색 배경 설정
                row.style.backgroundColor = isMyCourse ? ' #f0f0f0' : '';  // 본인 강의는 회색 배경 (#d3d3d3)

                row.innerHTML = `
                    <td>
                        <input type="checkbox" class="course-checkbox" data-id="${course.id}" data-course-grade="${course.courseGrade}" data-course-name="${course.courseName}" data-course-division="${course.courseDivision}" data-credit-score="${course.creditScore}" data-professor-name="${course.professorName}" data-course-room="${course.courseRoom}" data-course-day="${course.courseDay}" data-course-time="${course.startPeriod}교시 ~ ${course.endPeriod}교시" 
                        ${isMyCourse ? 'disabled' : ''}>
                    </td>
                    <td data-field="index">${index + 1}</td>
                    <td data-field="courseGrade">${course.courseGrade}</td>
                    <td data-field="courseName">${course.courseName}</td>
                    <td data-field="courseDivision">${course.courseDivision}</td>
                    <td data-field="creditScore">${course.creditScore}</td>
                    <td data-field="professorName">${course.professorName}</td>
                    <td data-field="courseRoom">${course.courseRoom}</td>
                    <td data-field="courseDay">${course.courseDay}</td>
                    <td data-field="courseTime">${course.startPeriod}교시 ~ ${course.endPeriod}교시</td>
                `;

                tableBody.appendChild(row);
            });

            // 전체 선택/해제 체크박스 처리
            const selectAllCheckbox = document.getElementById('selectAll');
            if (selectAllCheckbox) {
                selectAllCheckbox.addEventListener('change', function () {
                    const checkboxes = tableBody.querySelectorAll('.course-checkbox');
                    checkboxes.forEach(checkbox => {
                        if (!checkbox.disabled) {
                            checkbox.checked = selectAllCheckbox.checked;  // 전체 체크/해제
                        }
                    });
                });
            }
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('데이터를 불러오는 데 문제가 발생했습니다.');
    });
});


// 강의 추가 버튼 클릭
document.getElementById('stu-AddCourse-CoursesBtn').addEventListener('click', function() {
    const selectedCourses = document.querySelectorAll('.course-checkbox:checked');
    const courseRegistrationsTableBody = document.getElementById('stu-Course-Registrations-TableBody');
    const totalCreditsElement = document.querySelector('.addNum'); // 총 학점 표시할 요소

    if (courseRegistrationsTableBody && totalCreditsElement) {

        // 이미 등록된 강의 ID를 추적하기 위한 Set
        const registeredCourseIds = new Set();

        // 현재 등록된 강의들의 학점을 합산하기 위한 변수
        let totalCredits = 0;

        // 기존 등록된 강의들의 정보를 Set에 추가하고, 학점 합산
        const existingRows = courseRegistrationsTableBody.querySelectorAll('tr');
        existingRows.forEach(row => {
            const courseId = row.querySelector('.registration-btn')?.getAttribute('data-course-id');
            const credit = parseFloat(row.querySelector('[data-field="creditScore"]')?.textContent) || 0;
            if (courseId) {
                registeredCourseIds.add(courseId);
                totalCredits += credit;
            }
        });

        selectedCourses.forEach((checkbox, index) => {
            const courseId = checkbox.getAttribute('data-id');
            const courseName = checkbox.getAttribute('data-course-name');
            const courseGrade = checkbox.getAttribute('data-course-grade');
            const courseDivision = checkbox.getAttribute('data-course-division');
            const creditScore = parseFloat(checkbox.getAttribute('data-credit-score')) || 0;
            const professorName = checkbox.getAttribute('data-professor-name');
            const courseRoom = checkbox.getAttribute('data-course-room');
            const courseDay = checkbox.getAttribute('data-course-day');
            const courseTime = checkbox.getAttribute('data-course-time');

            if (registeredCourseIds.has(courseId)) {
                alert(`강의 ${courseName}는 이미 등록되어 있습니다.`);
                return;
            }

            // 새 행 생성
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><button class="registration-btn" data-course-id="${courseId}">신청</button></td>
                <td data-field="index">${index + 1}</td>
                <td data-field="courseGrade">${courseGrade}</td>
                <td data-field="courseName">${courseName}</td>
                <td data-field="courseDivision">${courseDivision}</td>
                <td data-field="creditScore">${creditScore}</td>
                <td data-field="professorName">${professorName}</td>
                <td data-field="courseRoom">${courseRoom}</td>
                <td data-field="courseDay">${courseDay}</td>
                <td data-field="courseTime">${courseTime}</td>
                <td><button class="cancel-btn">해제</button></td>
            `;

            // 취소 버튼 클릭 시 해당 강의 제거 및 학점 차감
            row.querySelector('.cancel-btn').addEventListener('click', function() {
                totalCredits -= creditScore;
                updateTotalCredits(totalCredits); // 학점 업데이트
                row.remove();
            });

            // 테이블에 행 추가
            courseRegistrationsTableBody.appendChild(row);

            // 총 학점 업데이트
            totalCredits += creditScore;
            registeredCourseIds.add(courseId);

        });

        // 학점 표시 업데이트
        updateTotalCredits(totalCredits);
    } else {
        console.error('Course-Registrations-TableBody 요소 또는 .addNum 요소를 찾을 수 없습니다.');
    }
});

// 학점 합계를 업데이트하는 함수
function updateTotalCredits(credits) {
    const totalCreditsElement = document.querySelector('.addNum');
    if (totalCreditsElement) {
        totalCreditsElement.textContent = `[총 학점: ${credits}]`; // 학점 표시
    }
}

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

