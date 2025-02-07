//조회 cond 되는거
document.getElementById('prof-daily-att-searchBtn').addEventListener('click', function() {
    // 선택된 강의명과 주차 가져오기
    const courseName = document.getElementById('prof-prof-attda-courseName').value;  // 강의명 셀렉트박스에서 선택된 값
    const week = document.getElementById('prof-prof-attda-week').value;  // 주차 셀렉트박스에서 선택된 값

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
                       <select id="attendance-status" class="attendance-status">
                            <option value="출석">출석</option>
                            <option value="지각">지각</option>
                            <option value="결석">결석</option>
                        </select>
                   </td>
            
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


//저장
document.getElementById('prof-daily-att-saveBtn').addEventListener('click', function() {
    const token = localStorage.getItem('jwtToken');
    const tableBody = document.getElementById('prof-daily-att-TableBody');
    const rows = tableBody.querySelectorAll('tr');  // 테이블 행들 가져오기
    const prof_stu_atten_Data = [];  // 데이터 저장할 배열
    const courseName = document.getElementById('prof-prof-attda-courseName').value;
    const week = document.getElementById('prof-prof-attda-week').value;

    const params = new URLSearchParams();
    if (courseName) params.append('courseName', courseName);
    if (week) params.append('week',week);

    const url = '/api/professor/attendance/condition';

    // 요청 URL 및 파라미터 로그 찍기
    const requestUrl = `${url}?${params.toString()}`;

    rows.forEach(row => {
        const userName = row.querySelector('[data-field="userName"]').textContent;  // 이름
        const userNum = row.querySelector('[data-field="userNum"]').textContent;  // 학번
        const status = row.querySelector('.attendance-status').value;  // 출석 상태 드롭다운 값



        // 출석 상태 데이터를 객체로 저장
        const studentData = {
            userName: userName,
            userNum: userNum,
            status: status
        };

        prof_stu_atten_Data.push(studentData);  // 배열에 추가
    });

    console.log('내가 보낸 요청:', requestUrl);
    console.log('내가 보낸 강의명:', courseName);

    fetch(requestUrl,  {
        method: 'POST',  // POST 방식으로 요청
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
        },
        body: JSON.stringify(prof_stu_atten_Data)  // 데이터 배열을 JSON 형식으로 전송
    })
        .then(response => response.json())  // 서버 응답 처리
        .then(data => {
            console.log('서버 응답:', data);  // 응답 데이터 확인

            console.log('내가 보낸 요청:', requestUrl);
            console.log('내가 보낸 강의명:', courseName);
            alert('출석 정보가 저장되었습니다.');
        })
        .catch(error => {
            console.error('Error:', error);  // 에러 발생 시 출력
            alert('출석 정보를 저장하는 중 오류가 발생했습니다.');
        });
});
