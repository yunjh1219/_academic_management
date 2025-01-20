document.getElementById('prof-assign-searchBtn').addEventListener('click', function() {
    // 강좌명과 주차 선택 값 가져오기
    const courseName = document.getElementById('course-name-select').value;
    const week = document.getElementById('week-select').value;



    // URL에 필터를 쿼리 파라미터로 추가
    let url = '/api/assignments/filter';
    let params = [];

    if (courseName) {
        params.push('courseName=' + encodeURIComponent(courseName));
    }
    if (week) {
        params.push('week=' + encodeURIComponent(week));
    }

    // 강좌명과 주차가 모두 비어 있으면 파라미터 없이 전체 조회
    if (params.length > 0) {
        url += '?' + params.join('&');
    }

    // fetch로 API 호출
    fetch(url)
        .then(response => response.json())  // 응답을 JSON으로 파싱
        .then(data => {
            console.log(data);  // 데이터를 콘솔에 출력 (실제 데이터 처리 로직 추가)
            const tableBody = document.getElementById('prof-assign-search-TableBody');
            tableBody.innerHTML = '';  // 기존 데이터 제거

            data.forEach((assign, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td><input type="checkbox" class="assign-checkbox" data-id="${assign.id}"></td>
                    <td>${index + 1}</td>
                    <td>${assign.courseName}</td>
                    <td>${assign.professorName}</td>
                    <td>${assign.week}</td>
                    <td>${assign.content}</td>
                    <td>${assign.deadline}</td>
                    <td>${assign.createdDate}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('과제 목록을 불러오는 데 실패했습니다.');
        });
});

$(document).ready(function() {
    // 강좌명 목록을 가져와서 <select>에 추가
    $.ajax({
        url: '/api/assignments/course-names',
        method: 'GET',
        success: function(courseNames) {
            const $courseSelect = $('#course-name-select');  // 강좌명 select 요소
            courseNames.forEach(function(courseName) {
                $courseSelect.append(`<option value="${courseName}">${courseName}</option>`);
            });
        },
        error: function() {
            alert('강좌명을 불러오는 데 실패했습니다.');
        }
    });

    // 주차 목록을 가져와서 <select>에 추가
    $.ajax({
        url: '/api/assignments/weeks',
        method: 'GET',
        success: function(weeks) {
            const $weekSelect = $('#week-select');  // 주차 select 요소
            weeks.forEach(function(week) {
                $weekSelect.append(`<option value="${week}">${week}</option>`);
            });
        },
        error: function() {
            alert('주차를 불러오는 데 실패했습니다.');
        }
    });
});

document.getElementById('prof-assign-search-TableBody').addEventListener('click', function(event){

})