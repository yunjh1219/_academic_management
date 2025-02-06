document.getElementById('stu-atten-searchBtn').addEventListener('click', function () {
    const token = localStorage.getItem('jwtToken');
    const url = '/api/student/attendance/condition'; // API 경로
    const courseName = document.getElementById('stu_course').value; // 선택된 강의명

    // URL 인코딩된 값을 디코딩
    const decodedCourseName = decodeURIComponent(courseName);

    const params = new URLSearchParams();
    if (decodedCourseName) params.append('courseName', decodedCourseName);

    // 요청 URL 및 파라미터 로그 찍기
    const requestUrl = `${url}?${params.toString()}`;
    console.log('보낸 요청 URL:', requestUrl);  // 요청 URL 로그
    console.log('디코딩된 강의명:', decodedCourseName);  // 디코딩된 강의명 로그

    fetch(requestUrl, {
        method: 'GET',  // GET 방식으로 요청
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("서버 응답 데이터:", data);
            const tableBody = document.getElementById('stu-atten-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((atten, index) => {
                const row = document.createElement('tr');
                row.dataset.id = atten.userNum;
                row.innerHTML = `
                   <td>${index + 1}</td>
                   <td data-field="studentId">${atten.week}</td>
                   <td data-field="department">${atten.status}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('출석 정보를 불러오는 중 오류가 발생했습니다.');
        });
});
