$(function () {
    // 페이지가 로드되면 학생 강의 시간표 데이터를 가져옴
    loadCourseSchedule();

    // 색상의 현재 인덱스 초기화
    let currentIndex = 0;

    // 정해진 순서대로 색상을 반환하는 함수
    function getNextColor() {
        const colors = [
            '#87CEFA', '#FFD700', '#FFB6C1', '#E0FFFF',
            '#FF69B4', '#98FB98', '#deff38', '#f56200',
            '#43fe62','#33fcff'
        ];
        const color = colors[currentIndex];
        currentIndex = (currentIndex + 1) % colors.length;
        return color;
    }

    // 학생 강의 시간표 목록을 가져오는 함수
    function loadCourseSchedule() {
        var url = '/api/student/course/calender'; // 학생 강의 시간표 API URL
        var token = localStorage.getItem('jwtToken'); // JWT 토큰을 로컬 스토리지에서 가져옴

        $.ajax({
            url: url,
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            success: function (response) {
                console.log('서버 응답:', response);

                if (response.status === 200) {
                    var tableBody = $('#stu-course-time-TableBody');
                    tableBody.empty();

                    let courseColors = {};

                    for (let hour = 1; hour <= 9; hour++) {
                        const row = $('<tr></tr>');
                        row.append(`<td><strong>${hour}교시</strong></td>`);

                        const days = ['월', '화', '수', '목', '금'];
                        days.forEach(day => {
                            const courses = response.data.filter(schedule =>
                                schedule.courseDay === day + '요일' &&
                                schedule.startPeriod <= hour &&
                                schedule.endPeriod >= hour
                            );

                            if (courses.length > 0) {
                                // 첫 번째 강의의 배경색을 td에 적용
                                if (!courseColors[courses[0].courseName]) {
                                    courseColors[courses[0].courseName] = getNextColor();
                                }
                                let bgColor = `background-color: ${courseColors[courses[0].courseName]};`;

                                let courseInfo = courses.map(course =>
                                    `<strong>강의명 : </strong><strong>${course.courseName}</strong><br>강의실 : ${course.room}`
                                ).join('<br>');

                                row.append(`<td style="${bgColor}; text-align: center; vertical-align: middle;">${courseInfo}</td>`);
                            } else {
                                row.append(`<td></td>`); // 빈 칸 채우기
                            }
                        });

                        tableBody.append(row);
                    }
                } else {
                    console.error('강의 시간표 조회 실패:', response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error('시간표 데이터를 가져오는 중 오류 발생:', error);
                console.error('응답 상태 코드:', xhr.status);
                console.error('응답 메시지:', xhr.responseText);
            },
        });
    }
});
