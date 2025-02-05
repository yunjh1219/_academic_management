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

    // 모달 관련 코드
    const modal = document.getElementById("modal"); // 모달 요소
    const userLogo = document.getElementById("user-logo"); // 이미지 요소
    const closeBtn = document.querySelector(".close-btn"); // 닫기 버튼

    // 이미지 클릭 시 모달 열기
    userLogo.addEventListener("click", () => {
        modal.style.display = "block";
    });

    // 닫기 버튼 클릭 시 모달 닫기
    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    // 모달 바깥 영역 클릭 시 모달 닫기
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
