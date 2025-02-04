document.getElementById('stu-read-btn-apply').addEventListener('click', function () {
    const url = '/api/student/tuition/applystatus';
    const token = localStorage.getItem('jwtToken');  // 'jwtToken'으로 수정

    fetch(url, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // 서버 응답 처리
            alert(data.message);  // 상태 변경 성공 메시지 출력
            // 추가적으로 필요한 로직 (예: 페이지 이동, UI 업데이트 등)
        })
        .catch(error => {
            console.error('Error:', error);
            alert('복학신청하는 도중 에러가 발생했습니다.');
        });
});
