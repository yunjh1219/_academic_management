window.addEventListener('load', function () {

    const token = localStorage.getItem('jwtToken');

    if (token) {
        // JWT 토큰에서 payload 부분을 추출하고 디코딩하기
        const base64Url = token.split('.')[1];  // 두 번째 부분 (payload 부분)
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // URL-safe Base64 디코딩
        const decodedData = JSON.parse(atob(base64)); // payload 디코딩

        console.log('디코딩된 데이터:', decodedData);

        // 유저 번호(userNum)와 다른 정보 확인
        const userNum = decodedData.sub; // 'sub'는 일반적으로 유저 ID로 사용됩니다.
        console.log('유저넘:', userNum);

        if (userNum) {
            // 교수 정보를 가져오는 API 호출
            fetch(`/api/admin/professor/${userNum}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // JWT 토큰 추가
                },
                credentials: 'same-origin'
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === 200) {
                        // 교수 이름을 가져와서 화면에 표시
                        const professorData = data.data; // 서버에서 반환된 데이터 구조가 'data.data'인 경우
                        professorData.forEach(professor => {
                            const userInfoElement = document.createElement('div');
                            userInfoElement.innerHTML = `
                                <p>${professor.name}<span class="greeting">님, 반갑습니다</span></p>
                                <p>${professor.department}(${professor.course})</p> <!-- 수정된 부분 -->
                                <div class="button-container">
                                    <button id="editInfoBtn">정보수정</button>
                                    <button id="logoutBtn">로그아웃</button>
                                </div>
                            `;
                            document.querySelector('.login-info').appendChild(userInfoElement);
                        });
                    } else {
                        alert('교수 정보를 불러오는 데 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('교수 정보 조회 중 오류 발생:', error);
                    alert('교수 정보 조회 중 오류 발생');
                });
        } else {
            alert('유저 번호가 없습니다.');
        }
    } else {
        alert('토큰이 없습니다.');
    }







document.querySelector('.login-info').appendChild(userInfoElement);

    // 정보 변경 버튼 클릭 시
    document.getElementById('editInfoBtn').addEventListener('click', function () {
        const newName = prompt('새로운 이름을 입력하세요', storedUserInfo.name);
        const newDepartment = prompt('새로운 학과를 입력하세요', storedUserInfo.department);
        const newCourse = prompt('새로운 전공을 입력하세요', storedUserInfo.course);

        if (newName && newDepartment && newCourse) {
            const updatedUserInfo = {
                name: newName,
                department: newDepartment,
                course: newCourse
            };

            // 변경된 값 저장
            localStorage.setItem('userInfo', JSON.stringify(updatedUserInfo));

            // 화면 업데이트
            document.querySelector('.login-info').innerHTML = '';
            window.location.reload();
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
});