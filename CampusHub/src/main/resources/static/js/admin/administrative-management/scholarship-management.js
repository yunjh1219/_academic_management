// 조회
document.getElementById('admin-shipinfo-searchBtn').addEventListener('click', function () {
    const url = '/api/admin/scholarship/condition';

    const token = localStorage.getItem('jwtToken');

    fetch(url, {
        method: 'GET',
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
            console.log("서버 응답 데이터:", data);
            const tableBody = document.getElementById('admin-stushipinfo-TableBody');
            tableBody.innerHTML = '';

            data.data.forEach((scholarship, index) => {
                const row = document.createElement('tr');
                row.dataset.id = scholarship.userNum;
                row.innerHTML = `
                    <td><input type="checkbox" class="scholarship-checkbox"></td>
                    <td>${index + 1}</td>
                    <td data-field="name" style="text-align: left;">${scholarship.username}</td>
                    <td data-field="stuId">${scholarship.userNum}</td>
                    <td data-field="department">${scholarship.deptName}</td>
                    <td data-field="year">${scholarship.year ? scholarship.year.year : ''}</td>
                    <td data-field="semester">${scholarship.semester}</td>
                    <td data-field="scholarshipName">${scholarship.scholarshipName}</td>
                    <td data-field="type">${scholarship.type}</td>
                    <td data-field="amount">${scholarship.amount}</td>
                    <td data-field="confDate">${scholarship.confDate ? scholarship.confDate : ''}</td>
                    <td data-field="remarks"></td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('장학금 정보를 불러오는 중 오류가 발생했습니다.');
        });
});
