function handleLogin(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 방지

    // 폼 데이터 수집
    const userNum = document.getElementById('userNum').value;
    const password = document.getElementById('password').value;
    const userType = document.querySelector('input[name="type"]:checked').value; // 선택된 라디오 버튼 값

    const loginData = {
        userNum: userNum,
        password: password,
        type: userType
    };

    // 로그인 요청 (AJAX)
    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData) // JSON 형식으로 서버로 데이터 전송
    })
        .then(response => {
            // 응답 헤더에서 JWT 토큰 추출
            const token = response.headers.get('Authorization');  // "Bearer <token>" 형태로 가져옴

            if (token) {
                // 로컬 스토리지에 JWT 토큰 저장
                localStorage.setItem('jwtToken', token.replace('Bearer ', ''));
            }

            return response.json();  // JSON 응답 본문 반환
        })
        .then(data => {
            console.log('서버 응답:', data); // 응답 데이터를 콘솔에 출력

            if (data.status === 200) {
                // 로그인 성공 시
                alert("로그인 성공!");

                // 이후 페이지 리다이렉트
                if (userType === 'STUDENT') {
                    window.location.href = "/stu_main"; // 학생 대시보드
                } else if (userType === 'PROFESSOR') {
                    window.location.href = "/prof_main"; // 교수 대시보드
                } else if (userType === 'ADMIN') {
                    window.location.href = "/admin_total"; // 관리자 대시보드
                } else {
                    window.location.href = "/dashboard"; // 기본 대시보드
                }
            } else {
                alert("로그인 실패! 아이디와 비밀번호를 확인해주세요.");
            }
        })
        .catch(error => {
            console.error('로그인 중 오류 발생:', error);
            alert('서버와의 연결에 실패했습니다. 나중에 다시 시도해주세요.');
        });
}
