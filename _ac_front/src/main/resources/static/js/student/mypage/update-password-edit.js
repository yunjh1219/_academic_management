window.addEventListener('load', function () {
    // API에서 첫 번째 사용자 정보를 가져와서 로그인 인포에 출력하는 함수
    fetch('/api/user')
        .then(response => response.json()) // 응답을 JSON으로 파싱
        .then(data => {
            if (data.length > 0) {
                const user = data[0]; // 첫 번째 사용자만 선택
                const name = '순자';
                const department = user.department;
                const course = user.course;
                const phone = '010-9235-4977';
                const email = "orenjh@naver.com";

                const userInfoElement = document.createElement('div');
                userInfoElement.innerHTML = `
                    <!-- 기존 readonly 입력 필드 -->
        <table>
             <tr> 
                <td>
                  <span><input type="text" id="edit-email" value="" placeholder="현재 비밀번호"></span>
                  <span><input type="text" id="edit-email" value="" placeholder="신규 비밀번호"></span>
                  <span><input type="text" id="edit-email" value="" placeholder="신규 비밀번호 확인"></span>
                </td>
             </tr> 
         </table>
                    <button id="save-changes">저장</button>
                    
                         `;
                // 'user-info' div에 추가
                document.querySelector('.password-change-page').appendChild(userInfoElement);

                // '저장' 버튼 클릭 시 수정된 값을 처리하는 코드
                document.getElementById('save-changes').addEventListener('click', function () {
                    const updatedPhone = document.getElementById('edit-phone').value;
                    const updatedEmail = document.getElementById('edit-email').value;

                    // 수정된 정보 처리 (예: 서버에 전송)
                    console.log('수정된 전화번호:', updatedPhone);
                    console.log('수정된 이메일:', updatedEmail);

                    // 예시로 콘솔 출력 외에도 업데이트된 데이터를 서버로 전송
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
                            // 서버 응답에 따라 UI 업데이트 또는 알림을 표시할 수 있습니다.
                        })
                        .catch(error => {
                            console.error('서버로 데이터를 전송하는 데 실패했습니다:', error);
                        });
                });
            }
        })
        .catch(error => {
            console.error('사용자 정보를 가져오는 데 실패했습니다:', error);
        });
});