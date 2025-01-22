window.addEventListener('load', function () {
    // API에서 첫 번째 사용자 정보를 가져와서 로그인 인포에 출력하는 함수
    fetch('/api/user')
        .then(response => response.json()) // 응답을 JSON으로 파싱
        .then(data => {
            if (data.length > 0) {
                const user = data[0]; // 첫 번째 사용자만 선택
                const userInfoElement = document.createElement('div');
                userInfoElement.innerHTML = `
                    <p>${user.name}<span class="greeting">님, 반갑습니다</span></p>
                    <p>${user.department}(${user.course})</p>
                `;
                // 'login-info' div에 추가
                document.querySelector('.login-info').appendChild(userInfoElement);
            }
        })
        .catch(error => {
            console.error('사용자 정보를 가져오는 데 실패했습니다:', error);
        });

});
