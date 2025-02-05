window.addEventListener('load', function () {
    // 기본값 설정
    const currentPassword = ''; // 기본값 (현재 비밀번호 입력란)
    const newPassword = ''; // 기본값 (새 비밀번호 입력란)
    const confirmNewPassword = ''; // 기본값 (새 비밀번호 확인 입력란)

    // 비밀번호 변경 폼을 표시할 요소 생성
    const userInfoElement = document.createElement('div');
    userInfoElement.innerHTML = `
        <!-- 기본 비밀번호 변경 입력 필드 -->
        <table>
            <tr> 
                <td>
                    <span><input type="password" id="edit-current-password" value="${currentPassword}" placeholder="이름"></span>
                    <span><input type="password" id="edit-new-password" value="${newPassword}" placeholder="생년월일"></span>
                      <span><input type="password" id="edit-new-password" value="${newPassword}" placeholder="학번/교번"></span>
                </td>
            </tr> 
        </table>
    <div class="button-container">
        <button class="common_btn" id="save-changes">찾기</button>
        <button class="common_btn" id="del-btn">취소</button>
    </div>
    `;

    // 'password-change-page' div에 추가
    document.querySelector('.find_account_ID').appendChild(userInfoElement);

    // '저장' 버튼 클릭 시 수정된 값을 처리하는 코드
    document.getElementById('save-changes').addEventListener('click', function () {
        const updatedCurrentPassword = document.getElementById('edit-current-password').value;
        const updatedNewPassword = document.getElementById('edit-new-password').value;
        const updatedConfirmPassword = document.getElementById('edit-confirm-password').value;

        // 수정된 정보 처리 (예: 서버에 전송)
        console.log('이름:', updatedCurrentPassword);
        console.log('생년월일:', updatedNewPassword);

        // 예시로 콘솔 출력 외에도 업데이트된 데이터를 서버로 전송
        fetch('/api/updatePassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                currentPassword: updatedCurrentPassword,
                newPassword: updatedNewPassword,
                confirmPassword: updatedConfirmPassword
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
});
