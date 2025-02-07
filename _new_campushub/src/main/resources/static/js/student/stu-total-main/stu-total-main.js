$(function() {
    // 페이지가 로드되면 공지사항 데이터를 가져옴
    loadNotices();

    // 공지사항 목록을 가져오는 함수
    function loadNotices() {
        var url = '/api/course';  // 공지사항 API URL (서버에서 공지사항 목록을 가져옴)

        $.ajax({
            url: url,
            method: 'GET',
            success: function(data) {
                // 데이터가 성공적으로 로드된 경우
                if (data && data.length > 0) {
                    // tbody에 내용을 추가
                    var tableBody = $('#noticeTableBody');
                    tableBody.empty();  // 기존 내용을 비우고 새로 추가

                    // 공지사항 데이터 순회하면서 각 항목을 테이블에 추가
                    $.each(data, function(index, notice) {
                        var formattedDate = new Date(notice.createdDate).toLocaleDateString('ko-KR', {
                            year: 'numeric',
                            month: 'long',
                            day: 'numeric'
                        });

                        var row = $("<tr>").append(
                            $("<td>").text(index + 1),
                            $("<td>").addClass("left").text(notice.courseName), // 'right' 클래스 추가
                            $("<td>").text(notice.division),
                            $("<td>").text(notice.credits),
                            $("<td>").text(notice.author),
                            $("<td>").text(notice.author),
                            $("<td>").text(formattedDate)
                        );

                        tableBody.append(row);
                    });
                } else {
                    $('#noticeTableBody').html("<tr><td colspan='5'>공지사항이 없습니다.</td></tr>");
                }
            },
            error: function(xhr, status, error) {
                // 에러 발생 시 메시지 표시
                console.error("공지사항을 불러오는 데 실패했습니다.", error);
                $('#noticeTableBody').html("<tr><td colspan='5'>공지사항을 불러오는 데 실패했습니다.</td></tr>");
            }
        });
    }
});