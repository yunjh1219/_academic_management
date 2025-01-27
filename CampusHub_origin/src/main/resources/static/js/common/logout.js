window.addEventListener('DOMContentLoaded', (event) => {
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
