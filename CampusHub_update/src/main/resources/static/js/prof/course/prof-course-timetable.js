$(function () {
    // 페이지가 로드되면 교수 강의 시간표 데이터를 가져옴
    loadCourseSchedule();

    // 교수 강의 시간표 목록을 가져오는 함수
    function loadCourseSchedule() {
        var url = '/api/professor/course/calender'; // 교수 강의 시간표 API URL
        var token = localStorage.getItem('jwtToken'); // JWT 토큰을 로컬 스토리지에서 가져옴

        $.ajax({
            url: url,
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // JWT 토큰을 Authorization 헤더에 추가
                'Content-Type': 'application/json',
            },
            success: function (response) {
                console.log('서버 응답:', response); // 응답을 콘솔에 출력하여 확인
                // 요청 성공 시, 데이터를 받아서 시간표에 채우기
                if (response.status === 200) {
                    var tableBody = $('#stu-course-time-TableBody'); // id가 stu-course-time-TableBody인 tbody
                    tableBody.empty(); // 기존 내용을 비움

                    // 시간표 데이터를 처리
                    for (let hour = 1; hour <= 9; hour++) {
                        const row = $('<tr></tr>'); // 새로운 행 생성
                        row.append(`<td>${hour}교시</td>`); // 시간대 열 추가

                        // 각 요일 칼럼 초기화
                        const days = ['월', '화', '수', '목', '금']; // 요일 배열
                        days.forEach(day => {
                            // "금요일"과 같이 날 수를 맞추기 위해 요일 이름을 "금"으로 변환
                            const course = response.data.find(schedule =>
                                schedule.courseDay === day + '요일' && schedule.startPeriod <= hour && schedule.endPeriod >= hour
                            );
                            row.append(`<td>${course ? course.courseName : ''}</td>`); // 수업명 추가
                        });

                        tableBody.append(row); // 테이블에 추가
                    }
                } else {
                    console.error('강의 시간표 조회 실패:', response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error('시간표 데이터를 가져오는 중 오류 발생:', error);
            },
        });
    }
});