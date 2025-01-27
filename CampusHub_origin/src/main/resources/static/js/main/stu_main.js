window.addEventListener('load', function () {
    // 기본값을 localStorage에 저장
    const defaultUser = {
        name: "윤정환",
        department: "컴퓨터소프트웨어학과",
        course: "전공심화"
    };

    // localStorage에 기본값 저장 (초기 로드 시)
    localStorage.setItem('userInfo', JSON.stringify(defaultUser));

    // localStorage에서 사용자 정보를 가져오기
    const storedUserInfo = JSON.parse(localStorage.getItem('userInfo'));

    // 사용자 정보를 화면에 표시
    const userInfoElement = document.createElement('div');
    userInfoElement.innerHTML = `
        <p>${storedUserInfo.name}<span class="greeting">님, 반갑습니다</span></p>
        <p>${storedUserInfo.department}(${storedUserInfo.course})</p>
            <div class="button-container">
        <button id="editInfoBtn">정보수정</button>
        <button id="logoutButton">로그아웃</button>
        </div>
    `;
    document.querySelector('.login-info').appendChild(userInfoElement);

// 정보 변경 버튼 클릭 시 바로 페이지 이동
    document.getElementById('editInfoBtn').addEventListener('click', function () {
        window.location.href = '/mypage_info';  // 새로운 페이지로 이동 (예: '/newPage')
    });

    // 로그아웃 버튼 클릭 시
    document.getElementById("logoutButton").addEventListener("click", function() {
        const token = localStorage.getItem("jwtToken");

        if (token) {
            fetch("/api/logout", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify({}),
            })
                .then(response => response.json())
                .then(data => {
                    console.log("서버 응답:", data);  // 서버 응답을 확인
                    if (data.status) {
                        localStorage.removeItem("jwtToken");
                        window.location.href = "/";
                    } else {
                        alert("로그아웃 실패");
                    }
                })
                .catch(error => {
                    console.error("로그아웃 오류:", error);
                    alert("로그아웃에 실패했습니다.");
                });
        } else {
            alert("로그인 정보가 없습니다.");
        }
    });
});

