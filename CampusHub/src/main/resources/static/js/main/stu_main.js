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
        <button id="logoutBtn">로그아웃</button>
        </div>
    `;
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
        // localStorage 초기화
        localStorage.removeItem('userInfo');

        // 로그인 페이지로 리다이렉트
        window.location.href = '/login';  // 실제 로그인 페이지 경로로 변경
    });
});



