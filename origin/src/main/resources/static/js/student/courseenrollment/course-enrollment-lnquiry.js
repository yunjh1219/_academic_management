document.getElementById('MyCourses-SearchBtn').addEventListener('click', function() {
    const url = '/api/course';  // 경로변경: 목록을 가져올 URL (엔드포인트)

    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data);  // 데이터를 콘솔에 출력해서 확인            //┌ 출력변경 : 이 값을 출력할 div class
            const tableBody = document.getElementById('Registered-Courses-TableBody');
            if (tableBody) {  // tableBody가 null이 아닌지 확인
                tableBody.innerHTML = '';  // 기존 데이터 제거

                data.forEach((course, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                    <td data-field="index">${index + 1}</td>
                    <td data-field="schoolYear">${course.schoolYear}</td>              
                    <td data-field="courseName">${course.courseName}</td>
                    <td data-field="division">${course.division}</td>
                    <td data-field="credits">${course.credits}</td>
                    <td data-field="professorName">${course.professorName}</td>
                    <td data-field="room">${course.room}</td>
                    <td data-field="courseDay">${course.courseDay}</td>
                    <td data-field="courseTime">${course.start}교시 ~ ${course.end}교시</td>  <!-- 교시 범위 표시 -->
                `;
                    tableBody.appendChild(row);
                });
            } else {
                console.error('tableBody 요소를 찾을 수 없습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('데이터를 불러오는 데 실패했습니다.');
        });
});