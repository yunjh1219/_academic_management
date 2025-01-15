document.getElementById('searchBtn').addEventListener('click', function() {
    const url = '/admin-potal/notices';

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('noticeTableBody');
            tableBody.innerHTML = ''; // 기존 데이터 제거

            data.forEach(notice => {
                const row = document.createElement('tr');
                const formattedDate = new Date(notice.createdAt).toLocaleDateString('ko-KR', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                });

                row.innerHTML = `
                    <td>${notice.id}</td>
                    <td>${notice.title}</td>
                    <td>${notice.author}</td>
                    <td>${formattedDate}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항을 불러오는 중 오류가 발생했습니다.');
        });
});

document.getElementById('noticeTableBody').addEventListener('click', function(event) {
    if (event.target.tagName === 'TD') {
        const row = event.target.parentElement;
        const cells = row.getElementsByTagName('td');
        const noticeId = cells[0].textContent;

        fetch(`/admin-potal/notices/${noticeId}`)
            .then(response => response.json())
            .then(notice => {
                // 데이터 폼에 채우기
                document.getElementById('title').value = notice.title;
                document.getElementById('content').value = notice.content;
                document.getElementById('file').value = '';
                document.getElementById('createdate').value = new Date(notice.createdAt).toLocaleDateString();
                document.getElementById('updatedate').value = new Date().toLocaleDateString();
                document.getElementById('author').value = notice.author;

                // **실시간으로 테이블 업데이트**
                document.getElementById('title').addEventListener('input', function() {
                    cells[1].textContent = this.value;
                });
                document.getElementById('content').addEventListener('input', function() {
                    cells[2].textContent = this.value;
                });
            })
            .catch(error => {
                console.error('Error:', error);
                alert('공지사항 정보를 불러오는 중 오류가 발생했습니다.');
            });
    }
});

// 저장 버튼 클릭 시 DB로 전송
document.getElementById('saveBtn').addEventListener('click', function() {
    const noticeId = document.getElementById('noticeTableBody').querySelector('tr').children[0].textContent;
    const updatedNotice = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value,
        author: document.getElementById('author').value
    };

    fetch(`/admin-potal/notices/${noticeId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedNotice)
    })
        .then(response => {
            if (response.ok) {
                alert('공지사항이 성공적으로 저장되었습니다.');
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항을 저장하는 중 오류가 발생했습니다.');
        });
});
