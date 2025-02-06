document.getElementById('prof-assign-check-searchBtn').addEventListener('click', function() {
    // 선택된 강의명과 주차 가져오기
    const courseName = document.getElementById('prof-prof-assch-courseName').value;  // 강의명 셀렉트박스에서 선택된 값
    const week = document.getElementById('prof-prof-assch-week').value;  // 주차 셀렉트박스에서 선택된 값


    // 강의명과 주차가 비어있지 않으면 요청을 보내고, 비어있으면 경고 메시지를 표시
    if (courseName === '' || week === '') {
        alert('강의명과 주차를 모두 선택해주세요.');
    } else {
        // status=null로 설정
        const url = `/api/professor/assignment/condition?courseName=${encodeURIComponent(courseName)}&week=${encodeURIComponent(week)}&status=null`;

        const token = localStorage.getItem('jwtToken');

        fetch(url, {
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
                console.log("서버 응답 데이터:", data);
                const tableBody = document.getElementById('prof-assign-stu-search-TableBody');
                tableBody.innerHTML = '';  // 테이블 초기화

                data.data.forEach((assignment, index) => {
                    const row = document.createElement('tr');
                    row.dataset.id = assignment.id;  // 각 행의 데이터 ID
                    // 상태 매핑 (null 또는 빈 값 처리)


                    row.innerHTML = `
                    <td><input type="checkbox" class="assignment-checkbox"></td>
                    <td>${index + 1}</td>
                    <td data-field="courseName" style="text-align: left;">${assignment.userName}</td> <!-- 이름 -->
                    <td data-field="userNum">${assignment.userNum}</td> <!-- 학번 -->
                    <td data-field="deptName">${assignment.deptName}</td> <!-- 학과 -->
                    <td data-field="submitDate">${assignment.submitDate}</td> <!-- 제출 날짜 --> 
                     <td data-field="status">${assignment.status}</td>
                `;
                    tableBody.appendChild(row);

                });
            })
            .catch(error => {
                console.error('Error:', error);
                alert('과제 정보를 불러오는 중 오류가 발생했습니다.');
            });
    }
});
