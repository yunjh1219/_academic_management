window.addEventListener('load', function () {

    const token = localStorage.getItem('jwtToken');
    const url = '/api/student';

    fetch(url,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${token}`  // JWT 토큰을 Authorization 헤더에 추가
        },
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
            const user = data.data;  // 'data'는 객체이며, 그 안에 student 정보가 있음

            const userInfoElement = document.createElement('div');
            userInfoElement.innerHTML = `
                <table>
                    <tr>
                        <td>
                            <span id="name">${user.userName}</span>
                            <span id="department">${user.detpName}</span>
                            <span id="phone">${user.phone}</span>
                            <span id="email">${user.email}</span>
                        </td>
                    </tr>

                    <tr> 
                        <td>
                            <span>변경할 전화번호</span>
                            <span>
                                <input type="text" id="edit-phone" value="">
                            </span>
                        </td>
                    </tr> 

                    <tr> 
                        <td>
                            <span>변경할 이메일</span>
                            <span>
                                <input type="text" id="edit-email" value="">
                            </span>
                        </td>
                    </tr> 
                </table>
                <div class="button-container">
                    <button class="common_btn" id="save-changes">변경</button>
                    <button class="common_btn" id="cancel-btn">취소</button>
                </div>
            `;

            document.querySelector('.info_change').appendChild(userInfoElement);

            // 저장 버튼 이벤트 처리
            document.getElementById('save-changes').addEventListener('click', function () {
                const updatedPhone = document.getElementById('edit-phone').value;
                const updatedEmail = document.getElementById('edit-email').value;

                fetch('/api/updateUser', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        phone: updatedPhone,
                        email: updatedEmail
                    })
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('서버에서 응답:', data);
                    })
                    .catch(error => {
                        console.error('서버로 데이터를 전송하는 데 실패했습니다:', error);
                    });
            });

            // 취소 버튼 이벤트 처리
            document.getElementById('cancel-btn').addEventListener('click', function () {
                const token = localStorage.getItem('jwtToken');
                if (token) {
                    const payload = JSON.parse(atob(token.split('.')[1]));
                    const userType = payload.type;

                    if (userType === 'STUDENT') {
                        window.location.href = "/stu_main"; // 학생 대시보드
                    } else if (userType === 'PROFESSOR') {
                        window.location.href = "/prof_main"; // 교수 대시보드
                    } else {
                        alert('인증 시간이 만료되었습니다. 재로그인 해주시길 바랍니다.');
                        window.location.href = "/login";
                    }
                } else {
                    alert('토큰 정보가 없습니다.');
                }
            });
        })
        .catch(error => {
            console.error('사용자 정보를 가져오는 데 실패했습니다:', error);
        });
});
