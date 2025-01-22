// '조회' 버튼 클릭 시 교수 목록을 불러오는 함수
document.getElementById('searchBtn').addEventListener('click', function () {
    const url = '/api/admin/professors/condition'; // 교수 목록을 가져올 URL

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('professorTableBody'); // 테이블 tbody 요소
            tableBody.innerHTML = ''; // 기존 데이터를 제거

            data.forEach((professor, index) => {
                const row = document.createElement('tr');

                row.dataset.id = professor.id; // 고유 ID 설정
                row.innerHTML = `
                    <td><input type="checkbox" class="rowCheckbox"></td>
                    <td>${index + 1}</td>
                    <td contenteditable="true" data-field="name">${professor.name}</td>
                    <td contenteditable="true" data-field="department">${professor.department}</td>
                    <td>${professor.email}</td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('교수 명단을 불러오는 중 오류가 발생했습니다.');
        });
});

// '신규' 버튼 클릭 시 새로운 행 추가
document.getElementById('newProfessorBtn').addEventListener('click', function () {
    const tableBody = document.getElementById('professorTableBody');
    const newRowId = `new+${tableBody.rows.length + 1}`;

    const newRow = document.createElement('tr');
    newRow.dataset.id = newRowId;

    newRow.innerHTML = `
        <td><input type="checkbox" class="rowCheckbox"></td>
        <td>${tableBody.rows.length + 1}</td>
        <td contenteditable="true" data-field="name">이름 입력</td>
        <td contenteditable="true" data-field="department">학과 입력</td>
        <td contenteditable="true" data-field="email">이메일 입력</td>
    `;

    tableBody.appendChild(newRow);
    alert('새로운 교수 정보를 추가했습니다. 내용을 입력하세요.');
});

// 행 선택 로직
document.getElementById('professorTableBody').addEventListener('click', function (e) {
    const row = e.target.closest('tr'); // 클릭한 요소가 속한 행 찾기
    if (row) {
        // 모든 행에서 selected 클래스 제거
        Array.from(document.querySelectorAll('#professorTableBody tr')).forEach(tr => {
            tr.classList.remove('selected');
        });

        // 클릭한 행에 selected 클래스 추가
        row.classList.add('selected');
    }
});

// '저장' 버튼 클릭 시 저장 요청 처리
document.getElementById('saveBtn').addEventListener('click', function () {
    const selectedRow = document.querySelector('#professorTableBody tr.selected');
    let url = '';
    let method = '';
    const professorData = {};

    if (selectedRow) {
        const professorId = selectedRow.dataset.id;

        professorData.name = selectedRow.querySelector('td[data-field="name"]').textContent;
        professorData.department = selectedRow.querySelector('td[data-field="department"]').textContent;
        professorData.email = selectedRow.querySelector('td[data-field="email"]').textContent;

        if (professorId.startsWith('new+')) {
            url = '/api/admin/professors';
            method = 'POST';
        } else {
            url = `/api/admin/professors/${professorId}`;
            method = 'PUT';
        }
    } else {
        alert('저장할 교수를 선택해주세요.');
        return;
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(professorData)
    })
        .then(response => {
            if (response.ok) {
                alert(method === 'POST' ? '새로운 교수 정보가 저장되었습니다.' : '교수 정보가 수정되었습니다.');
            } else {
                throw new Error('저장 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('저장하는 중 오류가 발생했습니다.');
        });
});

// '삭제' 버튼 클릭 시 선택된 교수 삭제 요청
document.getElementById('deleteBtn').addEventListener('click', function () {
    const selectedCheckboxes = document.querySelectorAll('#professorTableBody .rowCheckbox:checked');
    const professorIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.closest('tr').dataset.id);

    if (professorIds.length === 0) {
        alert('삭제할 교수를 선택해주세요.');
        return;
    }

    fetch('/api/admin/professors', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ ids: professorIds })
    })
        .then(response => {
            if (response.ok) {
                alert('선택된 교수가 성공적으로 삭제되었습니다.');
                selectedCheckboxes.forEach(checkbox => checkbox.closest('tr').remove());
            } else {
                throw new Error('삭제 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('삭제하는 중 오류가 발생했습니다.');
        });
});
