document.getElementById('stu-atten-searchBtn').addEventListener('click', function () {
    const selectedCourse = document.getElementById('course-select').value; // 선택된 강의명

    // 선택되지 않았을 경우 경고 메시지 표시 및 함수 종료
    if (!selectedCourse) {
        alert('강의를 선택해주세요.');
        return;
    }

    const url = '/api/course'; // API 경로

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('stu-atten-TableBody');
            if (tableBody) {
                tableBody.innerHTML = ''; // 기존 데이터 초기화

                // 선택된 강의명으로 데이터 필터링
                const filteredData = data.filter(course => course.courseName === selectedCourse);

                // 필터링된 데이터를 테이블에 출력
                filteredData.forEach((course, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td data-field="index">${index + 1}</td>
                        <td data-field="courseDay">${course.schoolYear}</td> <!-- 수업연도 -->
                        <td data-field="courseName">${course.courseName}</td> <!-- 결석일자 -->
                        <td data-field="room">${course.room}</td> <!-- 강의실 -->
                    `;
                    tableBody.appendChild(row);
                });

                // 데이터가 없을 경우
                if (filteredData.length === 0) {
                    tableBody.innerHTML = `<tr><td colspan="4">선택된 강의에 해당하는 데이터가 없습니다.</td></tr>`;
                }
            } else {
                console.error('tableBody 요소를 찾을 수 없습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('데이터를 불러오는 데 실패했습니다.');
        });
});

// 서버에서 코스명 데이터를 가져와 <select>에 추가
$(function () {
    $.ajax({
        url: '/api/names', // 강의명 가져오는 API 경로
        type: 'GET',
        success: function (courseNames) {
            const $select = $('#course-select');
            courseNames.forEach(name => {
                $select.append(`<option value="${name}">${name}</option>`);
            });
        },
        error: function () {
            alert('강의 데이터를 불러오는 데 실패했습니다.');
        }
    });
});
