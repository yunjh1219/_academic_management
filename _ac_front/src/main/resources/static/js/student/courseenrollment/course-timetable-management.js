$(function() {
    // 페이지가 로드되면 수강 시간표 데이터를 가져옴
    loadCourseSchedule();

    // 수강 시간표 목록을 가져오는 함수
    function loadCourseSchedule() {
        var url = '/api/course-schedule/manage'; // 수강 시간표 API URL

        $.ajax({
            url: url, // API URL
            method: 'GET', // GET 요청
            success: function(data) {
                // 요청 성공 시, 데이터를 받아서 시간표에 채우기
                var tableBody = $('table tbody');
                tableBody.empty(); // 기존의 내용을 비움

                // 시간표 데이터로 테이블 채우기
                $.each(data, function(index, schedule) {
                    var row = $('<tr></tr>');
                    row.append('<td>' + schedule.start + '교시</td>'); // 시작 교시

                    // 각 요일에 맞는 강의명을 채운다
                    row.append('<td>' + (schedule.courseDay === '월' ? schedule.courseName : '') + '</td>');
                    row.append('<td>' + (schedule.courseDay === '화' ? schedule.courseName : '') + '</td>');
                    row.append('<td>' + (schedule.courseDay === '수' ? schedule.courseName : '') + '</td>');
                    row.append('<td>' + (schedule.courseDay === '목' ? schedule.courseName : '') + '</td>');
                    row.append('<td>' + (schedule.courseDay === '금' ? schedule.courseName : '') + '</td>');
                    row.append('<td>' + (schedule.courseDay === '토' ? schedule.courseName : '') + '</td>');
                    row.append('<td>' + (schedule.courseDay === '일' ? schedule.courseName : '') + '</td>');

                    tableBody.append(row); // 행 추가
                });
            },
            error: function(xhr, status, error) {
                console.error('시간표 데이터를 가져오는 중 오류 발생:', error);
            }
        });
    }
});
