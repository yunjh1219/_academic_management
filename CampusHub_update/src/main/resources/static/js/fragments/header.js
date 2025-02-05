function logout() {

    // 로컬 스토리지에서 JWT 토큰 가져오기
    const token = localStorage.getItem('jwtToken');

    fetch('/api/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`  // JWT 토큰을 Authorization 헤더에 추가
        },
        credentials: 'same-origin', // 쿠키 포함
    })
        .then(response => {
            if (response.ok) {
                // 로그아웃 성공 시 처리 (예: 로그인 페이지로 리디렉션)
                alert('로그아웃 성공');
                window.location.href = '/api/login'; // 로그인 페이지로 리디렉션
            } else {
                // 로그아웃 실패 시 처리
                alert('로그아웃 실패');
            }
        })
        .catch(error => {
            console.error('로그아웃 중 오류 발생:', error);
            alert('로그아웃 중 오류 발생');
        });
}