window.addEventListener('load', function () {
    // 비밀번호 변경 폼을 표시할 요소 생성
    const userInfoElement = document.createElement('div');
    userInfoElement.innerHTML = `
        <!-- 비밀번호 변경 입력 필드 -->
        <table>
            <tr>
                <td>
                  <span><input type="password" id="edit-current-password" placeholder="기존 비밀번호"></span>
                   <span><input type="password" id="edit-new-password" placeholder="신규 비밀번호"></span>
                   <span><input type="password" id="edit-confirm-password" placeholder="신규 비밀번호 확인"></span>
                </td>
            </tr>
        </table>
        <div class="button-container">
            <button class="common_btn" id="save-changes">변경</button>
            <button class="common_btn" id="cancel-btn">취소</button>
        </div>
    `;

    // 'pw_cgange' div에 추가
    document.querySelector('.pw_change').appendChild(userInfoElement);

    // '변경' 버튼 클릭 시 수정된 값을 처리하는 코드
    document.getElementById('save-changes').addEventListener('click', function () {
        const currentPassword = document.getElementById('edit-current-password').value;
        const newPassword = document.getElementById('edit-new-password').value;
        const confirmPassword = document.getElementById('edit-confirm-password').value;

        // 비밀번호 검증 로직
        if (!currentPassword || !newPassword || !confirmPassword) {
            alert('모든 필드를 입력해주세요.');
            return;
        }

        if (newPassword !== confirmPassword) {
            alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        // 수정된 정보 처리 (예: 서버에 전송)
        fetch('/api/updatePassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                currentPassword: currentPassword,
                newPassword: newPassword
            })
        })
            .then(response => response.json())
            .then(data => {
                console.log('서버 응답:', data);
                if (data.success) {
                    alert('비밀번호가 성공적으로 변경되었습니다.');
                } else {
                    alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('서버로 데이터를 전송하는 데 실패했습니다:', error);
                alert('서버 오류로 인해 비밀번호를 변경할 수 없습니다.');
            });
    });

    // '취소' 버튼 클릭 시 동작
    document.getElementById('cancel-btn').addEventListener('click', function () {
        // 입력 필드 초기화
        document.getElementById('edit-current-password').value = '';
        document.getElementById('edit-new-password').value = '';
        document.getElementById('edit-confirm-password').value = '';
        alert('비밀번호 변경이 취소되었습니다.');
    });
});
