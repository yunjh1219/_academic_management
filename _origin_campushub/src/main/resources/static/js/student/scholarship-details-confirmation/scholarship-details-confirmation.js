document.getElementById('stu-ship-searchBtn').addEventListener('click',function (){

    const token = localStorage.getItem('jwtToken');
    const url = '/api/student/scholarship';

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('sholarship-search-tablebody');
            tableBody.innerHTML = '';

            data.data.forEach((ship,index) => {
                const row = document.createElement('tr');
                row.dataset.id = ship.id;
                row.innerHTML = `
                      <td>${index + 1}</td>
                      <td data-field="studentId">${ship.year}</td>
                      <td data-field="studentId1">${ship.semester}</td>
                      <td data-field="studentId2">${ship.scholarshipName}</td>
                      <td data-field="studentId3">${ship.type}</td>
                      <td data-field="studentId4">${ship.amount}</td>
                      <td data-field="studentId5">${ship.confDate}</td>
                      <td data-field="studentId6"></td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('출석 정보를 불러오는 중 오류가 발생했습니다.');
        });
})