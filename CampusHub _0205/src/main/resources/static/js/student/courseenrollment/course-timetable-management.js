$(function () {
    // 페이지가 로드되면 수강 시간표 데이터를 가져옴
    loadCourseSchedule();

    // 수강 시간표 목록을 가져오는 함수
    function loadCourseSchedule() {
        var url = '/api/course-schedule/manage'; // 수강 시간표 API URL

        $.ajax({
            url: url,
            method: 'GET',
            success: function (data) {
                // 요청 성공 시, 데이터를 받아서 시간표에 채우기
                var tableBody = $('#stu-course-time-TableBody'); // id가 stu-course-time-TableBody인 tbody
                tableBody.empty(); // 기존 내용을 비움

                // 시간표 데이터를 처리
                for (let hour = 1; hour <= 9; hour++) {
                    const row = $('<tr></tr>'); // 새로운 행 생성
                    row.append(`<td>${hour}교시</td>`); // 시간대 열 추가

                    // 각 요일 칼럼 초기화
                    const days = ['월요일', '화요일', '수요일', '목요일', '금요일'];
                    days.forEach(day => {
                        const course = data.find(schedule =>
                            schedule.courseDay === day && schedule.start <= hour && schedule.end >= hour
                        );
                        row.append(`<td>${course ? course.courseName : ''}</td>`); // 수업명 추가
                    });

                    tableBody.append(row); // 테이블에 추가
                }
            },
            error: function (xhr, status, error) {
                console.error('시간표 데이터를 가져오는 중 오류 발생:', error);
            },
        });
    }
});
