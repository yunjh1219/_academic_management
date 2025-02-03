document.getElementById('stu-MyInCourses-SearchBtn').addEventListener('click', function() {
    const url = '/api/student/course';
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
            console.log(data);  // 데이터를 콘솔에 출력해서 확인

            // data가 배열인지 객체인지 확인
            if (Array.isArray(data)) {
                // data가 배열일 경우 처리
                const tableBody = document.getElementById('stu-Registered-Courses-TableBody');
                if (tableBody) {  // tableBody가 null이 아닌지 확인
                    tableBody.innerHTML = '';  // 기존 데이터 제거

                    data.forEach((course, index) => {
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
                        <td data-field="courseTime">${course.startPeriod}교시 ~ ${course.endPeriod}교시</td>
                    `;
                        tableBody.appendChild(row);
                    });
                } else {
                    console.error('tableBody 요소를 찾을 수 없습니다.');
                }
            } else if (typeof data === 'object') {
                // data가 객체일 경우 처리 (예: { data: [...] })
                console.log('data는 객체입니다. 객체 내부의 데이터를 처리할 수 있습니다.');
                // 객체 내부에 있는 data 배열을 사용
                const tableBody = document.getElementById('stu-Registered-Courses-TableBody');
                if (tableBody) {  // tableBody가 null이 아닌지 확인
                    tableBody.innerHTML = '';  // 기존 데이터 제거

                    if (data.data && Array.isArray(data.data)) {
                        data.data.forEach((course, index) => {
                            const row = document.createElement('tr');
                            row.innerHTML = `
                            <td data-field="index">${index + 1}</td>
                            <td data-field="courseGrade">${course.courseGrade}</td>  <!-- 학년 -->       
                            <td data-field="courseName">${course.courseName}</td> <!-- 강의명 --> 
                            <td data-field="courseDivision">${course.courseDivision}</td> <!-- 이수구분 --> 
                            <td data-field="creditScore">${course.creditScore}</td> <!-- 학점 --> 
                            <td data-field="professorName">${course.professorName}</td> <!-- 담당교수 --> 
                            <td data-field="courseRoom">${course.courseRoom}</td> <!-- 강의실 --> 
                            <td data-field="courseDay">${course.courseDay}</td> <!-- 요일 -->  
                            <td data-field="courseTime">${course.startPeriod}교시 ~ ${course.endPeriod}교시</td>
                        `;
                            tableBody.appendChild(row);
                        });
                    } else {
                        console.error('객체 내부에 data 배열이 없습니다.');
                    }
                } else {
                    console.error('tableBody 요소를 찾을 수 없습니다.');
                }
            } else {
                console.error('알 수 없는 데이터 형식입니다.', data);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('데이터를 불러오는 데 실패했습니다.');
        });
});
