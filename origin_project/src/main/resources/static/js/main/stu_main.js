window.addEventListener('load', function () {
    const token = localStorage.getItem('jwtToken');
    const url = '/api/student';

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

            // data.data가 객체일 때 처리
            const student = data.data;  // 'data'는 객체이며, 그 안에 student 정보가 있음

            // 사용자 정보를 화면에 표시
            const userInfoElement = document.createElement('div');
            userInfoElement.innerHTML = `
                <p>${student.userName}<span class="greeting">님, 반갑습니다</span></p>
                <p>${student.deptName}</p>
                <div class="button-container">
                    <button id="editInfoBtn">정보수정</button>
                    <button id="logoutBtn">로그아웃</button>
                </div>
            `;

            // 'login-info' 클래스를 가진 div 요소를 찾고, 거기에 사용자 정보 삽입
            const loginInfoDiv = document.querySelector('.login-info');
            if (loginInfoDiv) {
                loginInfoDiv.appendChild(userInfoElement);  // 사용자 정보 div에 삽입
            } else {
                console.error('로그인 정보 div를 찾을 수 없습니다.');
            }

            // 정보 변경 버튼 클릭 시
            document.getElementById('editInfoBtn').addEventListener('click', function () {
                const newName = prompt('새로운 이름을 입력하세요', student.userName);  // 'storedUserInfo'는 현재 데이터가 없으므로 'student' 객체로 대체
                const newDepartment = prompt('새로운 학과를 입력하세요', student.deptName); // 'storedUserInfo'는 현재 데이터가 없으므로 'student' 객체로 대체
                const newCourse = prompt('새로운 전공을 입력하세요', student.course || '');  // 만약 course가 student에 포함되어 있다면

                if (newName && newDepartment && newCourse) {
                    const updatedUserInfo = {
                        name: newName,
                        department: newDepartment,
                        course: newCourse
                    };

                    // 변경된 값 저장
                    localStorage.setItem('userInfo', JSON.stringify(updatedUserInfo));

                    // 화면 업데이트
                    document.querySelector('.login-info').innerHTML = '';  // 기존 로그인 정보를 초기화
                    window.location.reload();  // 화면 새로고침
                }
            });

            // 로그아웃 버튼 클릭 시
            document.getElementById('logoutBtn').addEventListener('click', function () {
                // 로컬 스토리지에서 JWT 토큰 가져오기
                const token = localStorage.getItem('jwtToken');

                if (!token) {
                    alert('토큰이 없습니다. 로그인이 필요합니다.');
                    window.location.href = '/api/login';
                    return;
                }

                fetch('/api/logout', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `${token}` // JWT 토큰 추가
                    },
                    credentials: 'same-origin',
                })
                    .then(response => {
                        if (response.ok) {
                            // 로그아웃 성공 처리
                            alert('로그아웃 성공');

                            // 로컬 스토리지 초기화
                            localStorage.removeItem('jwtToken');
                            localStorage.removeItem('userInfo');

                            // 로그인 페이지로 리다이렉트
                            window.location.href = '/api/login';
                        } else {
                            alert('로그아웃 실패');
                        }
                    })
                    .catch(error => {
                        console.error('로그아웃 중 오류 발생:', error);
                        alert('로그아웃 중 오류 발생');
                    });
            });

        })
        .catch(error => {
            console.error('Error:', error);
            alert('학생 정보를 불러오는 중 오류가 발생했습니다.');
        });
});
