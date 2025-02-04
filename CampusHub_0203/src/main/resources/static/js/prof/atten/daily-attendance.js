document.getElementById('prof-daily-att-searchBtn').addEventListener('click', function() {
    // 선택된 강의명과 주차 가져오기
    const courseName = document.getElementById('prof_att_courseName').value;  // 강의명 셀렉트박스에서 선택된 값
    const week = document.getElementById('prof_att_week').value;  // 주차 셀렉트박스에서 선택된 값

    // 강의명과 주차가 비어있지 않으면 요청을 보내고, 비어있으면 경고 메시지를 표시
    if (courseName === '' || week === '') {
        alert('강의명과 주차를 모두 선택해주세요.');
    } else {
        // 쿼리 파라미터로 강의명과 주차를 전달
        const url = `/api/professor/attendance/condition?courseName=${encodeURIComponent(courseName)}&week=${encodeURIComponent(week)}`;

        console.log("보내는 URL:", url);  // 보내는 URL 로그로 출력
        console.log("보내는 강의명:", courseName);
        console.log("주차:", week);

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
                const tableBody = document.getElementById('prof-daily-att-TableBody');
                tableBody.innerHTML = '';  // 테이블 초기화

                data.data.forEach((prof_daily_att, index) => {
                    const row = document.createElement('tr');
                    row.dataset.id = prof_daily_att.id;  // 각 행의 데이터 ID
                    row.innerHTML = `
                    <td>${index + 1}</td>
                    <td data-field="userName" style="text-align: left;">${prof_daily_att.userName}</td> <!-- 이름 -->
                    <td data-field="userNum">${prof_daily_att.userNum}</td> <!-- 학번 -->
                    <!-- 드롭다운 추가 -->
                    <td>
                       <select class="attendance-status">
                            <option value="present">출석</option>
                            <option value="late">지각</option>
                            <option value="absent">결석</option>
                        </select>
                   </td>
                    <td><input type="checkbox" class="scholarship-checkbox"></td>
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