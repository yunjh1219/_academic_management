
// 신규
// 신규 버튼 클릭 시 새로운 행 추가
document.getElementById('prof-assign-reg-newBtn').addEventListener('click', function() {

    // 로컬 스토리지에서 JWT 토큰 가져오기
    const token = localStorage.getItem('jwtToken');
    const url = "/api/professor/course";

    // 강의명 정보 가져오기
    fetch(url, {
        method: 'GET',  // GET 방식으로 요청
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`  // JWT 토큰을 Authorization 헤더에 추가
        }
    })
        .then(response => response.json())  // 응답을 JSON 형식으로 파싱
        .then(data => {
            console.log('받은 데이터:', data);  // 받아온 데이터를 콘솔에 출력하여 확인
            const courseNameSelect = document.getElementById('prof_assig_reg_courseName');
            courseNameSelect.innerHTML = ''; // 셀렉트 박스 초기화

            // 기본 옵션 유지
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = '강의명을 선택하세요';
            defaultOption.disabled = true;  // 선택 불가능한 옵션으로 설정
            defaultOption.selected = true;  // 기본 선택되도록 설정
            courseNameSelect.appendChild(defaultOption);

            // data.data가 배열일 경우 처리
            const courses = data.data || [];  // data가 배열 형태로 존재하는지 확인
            courses.forEach(course => {
                const option = document.createElement('option');
                option.value = course.courseName;  // 강의 ID를 value로 설정
                option.textContent = course.courseName;  // 강의명 표시
                courseNameSelect.appendChild(option);  // 셀렉트 박스에 옵션 추가
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });

    // 주차 정보 동적으로 추가
    const weekSelect = document.getElementById('prof_assig_reg_week');
    weekSelect.innerHTML = ''; // 주차 셀렉트 박스 초기화

    // 1주차부터 16주차까지 반복하여 셀렉트 옵션 추가
    for (let i = 1; i <= 16; i++) {
        const option = document.createElement('option');
        option.value = `${i}주차`;  // value로 "1주차", "2주차" 등 설정
        option.textContent = `${i}주차`;  // 표시되는 텍스트도 "1주차", "2주차" 등 설정
        weekSelect.appendChild(option);  // 셀렉트 박스에 옵션 추가
    }


    // 폼 초기화 (입력된 데이터 초기화)
    document.getElementById('prof_assig_reg_courseName').value = '';
    document.getElementById('prof_assig_reg_week').value = '';
    document.getElementById('prof_assig_reg_assginExplain').value = '';
    document.getElementById('prof_assig_reg_limitDate').value = '';


    alert('새로운 장학 정보를 추가했습니다. 정보를 입력해주세요.');

    const tableBody = document.getElementById('prof-assign-search-TableBody');
    const new_prof_assign_reg_Id = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성 (현재 행의 개수를 기반으로)
    const displayId = new_prof_assign_reg_Id.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = new_prof_assign_reg_Id;  // 고유한 data-id 부여

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    // 입력된 폼 데이터 가져오기
    const courseName = document.getElementById('prof_assig_reg_courseName').value;
    const week = document.getElementById('prof_assig_reg_week').value;
    const Explain = document.getElementById('prof_assig_reg_assginExplain').value;
    const limitDate = document.getElementById('prof_assig_reg_limitDate').value;



    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="prof-assign-search-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="courseName">${courseName}</td> 
        <td data-field="week">${week}</td> 
        <td data-field="Explain">${Explain}</td> 
        <td data-field="limitDate">${limitDate}</td> 
    `;


    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#prof-assign-search-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('prof-assig-reg-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTime_prof_assign_reg_Editing(newRow);
});

// 실시간으로 수정 내용 반영
function addRealTime_prof_assign_reg_Editing(row) {
    const prof_assig_reg_courseNameCell = row.querySelector('td[data-field="courseName"]');
    const prof_assig_reg_weekCell = row.querySelector('td[data-field="week"]');
    const prof_assig_reg_limitDateCell = row.querySelector('td[data-field="limitDate"]');



    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('prof_assig_reg_courseName').addEventListener('input', function() {
        if (prof_assig_reg_courseNameCell) {
            prof_assig_reg_courseNameCell.textContent = this.value;  // 이름 수정 반영
        }
    });

    document.getElementById('prof_assig_reg_week').addEventListener('input', function() {
        if (prof_assig_reg_weekCell) {
            prof_assig_reg_weekCell.textContent = this.value;
        }
    });




    document.getElementById('prof_assig_reg_limitDate').addEventListener('input', function() {
        if (prof_assig_reg_limitDateCell) {
            prof_assig_reg_limitDateCell.textContent = this.value;
        }
    });


}


//저장버튼
document.getElementById('prof-assign-reg-saveBtn').addEventListener('click', function() {
    const courseName = document.getElementById('prof_assig_reg_courseName').value;  // 강의명
    const week = document.getElementById('prof_assig_reg_week').value;  // 주차
    const assginExplain = document.getElementById('prof_assig_reg_assginExplain').value;  // 학과
    const limitDate = document.getElementById('prof_assig_reg_limitDate').value;  // 학년도


    // 선택된 행이 있는지 확인 (수정일 경우 ID가 존재)
    const selectedRow = document.querySelector('#prof-assign-search-TableBody tr.selected');  // 선택된 행 찾기
    let url = '';
    let method = '';
    let shipinfoIdInRow = null;

    if (selectedRow) {
        // 선택된 행에서 ID 가져오기
        shipinfoIdInRow = selectedRow.dataset.id;  // 선택된 학생의 ID

        if (shipinfoIdInRow.startsWith('new+')) {
            // 신규 학생 (new+로 시작하는 ID)
            url = '/api/professor/assignment/create';
            method = 'POST';  // 신규 추가: POST 요청
        }
    } else {
        // 선택된 행이 없으면 신규로 처리
        url = '/api/professor/assignment/create';
        method = 'POST';  // 신규 추가: POST 요청
    }

    // 데이터 객체 생성
    const prof_assign_reg_Data = {
        courseName: courseName,  //강의명
        week: week,  //주차
        assignExplain: assginExplain, //과제설명
        limitDate: limitDate, //과제기한
    };

    // 로컬 스토리지에서 JWT 토큰 가져오기
    const token = localStorage.getItem('jwtToken');
    console.log("date:",prof_assign_reg_Data);

    // 요청 보내기
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`  // JWT 토큰을 Authorization 헤더에 추가
        },
        body: JSON.stringify(prof_assign_reg_Data)
    })
        .then(response => {
            if (response.ok) {
                if (method === 'PUT') {
                    alert('과제 정보가 성공적으로 수정되었습니다.');
                } else {
                    alert('새로운 과제 정보가 성공적으로 저장되었습니다.');
                }
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('과제 정보를 저장하는 중 오류가 발생했습니다.');
        });
});

