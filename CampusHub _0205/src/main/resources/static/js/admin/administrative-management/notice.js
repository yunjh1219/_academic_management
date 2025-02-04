document.getElementById('admin-notice-saveBtn').addEventListener('click',function (){

    const token = localStorage.getItem('jwtToken');
    const url = '/api/notice/create'

    // 데이터 객체 생성
    const adminNoticeData = {
        noticeTitle: '제목',
        noticeContent: '내용',
        noticeType: '학사'
    };

    console.log('요청 URL:', url);
    console.log('요청 본문:', JSON.stringify(adminNoticeData));

    fetch(url, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(adminNoticeData)
    })
        .then(response => {
            if (response.ok) {
                alert('새로운 공지사항이 성공적으로 저장되었습니다.');
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항 정보를 저장하는 중 오류가 발생했습니다.');
        });
})