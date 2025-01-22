// 학생 데이터
const student = {
    id: 1,
    userNum: "111",
    password: "123",
    userName: "학생1",
    birthday: "2003-01-01T00:00:00",
    email: "student1@example.com",
    phone: "010-1234-5678",
    address: "서울시 강남구",
    dept: { id: 101, name: "컴퓨터공학과" },
    grade: "FRESHMAN",
    role: "STUDENT",
    type: "STUDENT",
    status: "ENROLLED",
    refreshToken: null,
};

// 관리자 데이터
const admin = {
    id: 2,
    userNum: "222",
    password: "123",
    userName: "관리자1",
    birthday: "1985-07-15T00:00:00",
    email: "admin@example.com",
    phone: "010-9876-5432",
    address: "서울시 종로구",
    dept: null,
    grade: null,
    role: "ADMIN",
    type: "ADMIN",
    status: "ACTIVE",
    refreshToken: null,
};

// 교수 데이터
const professor = {
    id: 3,
    userNum: "333",
    password: "123",
    userName: "교수1",
    birthday: "1975-05-22T00:00:00",
    email: "prof@example.com",
    phone: "010-5555-6666",
    address: "서울시 서초구",
    dept: { id: 102, name: "전자공학과" },
    grade: null,
    role: "PROFESSOR",
    type: "PROFESSOR",
    status: "ACTIVE",
    refreshToken: null,
};

// 로컬 스토리지에 저장
localStorage.setItem("student", JSON.stringify(student));
localStorage.setItem("admin", JSON.stringify(admin));
localStorage.setItem("professor", JSON.stringify(professor));

function handleLogin(event) {
    event.preventDefault(); // 폼 기본 제출 동작 중단

    // 폼 데이터 가져오기
    const type = document.querySelector('input[name="type"]:checked')?.value;
    const userNum = document.getElementById("userNum").value;
    const password = document.getElementById("password").value;

    if (!type || !userNum || !password) {
        alert("모든 필드를 입력해주세요.");
        return;
    }

    // 저장된 데이터 가져오기
    const userData = JSON.parse(localStorage.getItem(type));
    if (!userData) {
        alert("존재하지 않는 사용자 유형입니다.");
        return;
    }

    // 사용자 정보 검증
    if (userData.userNum === userNum && userData.password === password) {
        alert(`${userData.userName}님, 로그인 성공!`);
        localStorage.setItem("loginData", JSON.stringify({ type, userNum }));

        // 타입에 따른 리다이렉트
        switch (type) {
            case "student":
                window.location.href = "/stu-main";
                break;
            case "professor":
                window.location.href = "/prof-main";
                break;
            case "admin":
                window.location.href = "/admin-dashboard";
                break;
            default:
                alert("잘못된 로그인 유형입니다.");
        }
    } else {
        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}
