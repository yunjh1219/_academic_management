//조회 cond 안되는거
document.getElementById('stu_assign_serachBtn').addEventListener('click',function (){

    const token = localStorage.getItem('jwtToken');


    // 입력된 학과와 학번 가져오기
    const courseName = document.getElementById('stu_assign_course').value;
    const week = document.getElementById('stu_assign_week').value;

    console.log('강의명courseName:',courseName);
    console.log('주차week:',week);

    const url = `/api/student/assignment/condition?courseName=${encodeURIComponent(courseName)}&week=${encodeURIComponent(week)}`;


    fetch(url,  {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('요청 URL:', url);
            console.log("서버 응답 데이터:", data);
            const tableBody = document.getElementById('stu_assign_TableBody');
            tableBody.innerHTML = '';


            data.data.forEach((assignment, index) => {
                const row = document.createElement('tr');
                row.dataset.id = assignment.id;
                row.innerHTML = `
                   <td><input type="checkbox" class="student-checkbox" data-id="${assignment.id}"></td>
                   <td>${index + 1}</td>
                   <td data-field="stu_assign_week" style="text-align: left;">${assignment.week}</td>
                   <td data-field="stu_assign_courseName">${assignment.courseName}</td>
                   <td data-field="stu_assign_userName">${assignment.userName}</td>
                   <td data-field="stu_assign_status">${assignment.submitStatus}</td> <!-- 제출 여부 -->
                   <td data-field="stu_assign_createDate">${assignment.createDate}</td>
         
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('학생 정보를 불러오는 중 오류가 발생했습니다.');
        });
});