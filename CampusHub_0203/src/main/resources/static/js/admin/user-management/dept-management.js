//신규
document.getElementById('admin-deptfinfo-newBtn').addEventListener('click',function (){

    //폼 초기화 (입력된 데이터 초기화)
    document.getElementById('admin_deptfinfo_deptname').value = '';

    alert('새로운 학과 정보를 입력해주세요');

    const tableBody = document.getElementById('admin-deptfinfo-TableBody');
    const newdeptId = `new+${tableBody.rows.length + 1}`;   // 행 순서대로 ID 생성 (현재 행의 개수를 기반으로)
    const displayId = newdeptId.replace('new+', ''); // 'new+'를 빈 문자열로 대체
    const newRow = document.createElement('tr');   // 새로운 행(tr) 생성
    newRow.dataset.id = newdeptId;  // 고유한 data-id 부여

    // 현재 날짜를 기본값으로 설정 (작성일자)
    const today = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });

    const deptName = document.getElementById('admin_deptfinfo_deptname').value;

    // 새로운 행의 HTML 구조 설정
    newRow.innerHTML = `
        <td><input type="checkbox" class="student-checkbox"></td>
        <td>${displayId}</td> <!-- 신규 ID에서 'new+' 부분 제거하여 표시 -->
        <td style="text-align: left;" data-field="deptname">${deptName}</td> <!-- 이름 -->
    `;

    // 기존에 선택된 행의 'selected' 제거
    const previouslySelectedRow = document.querySelector('#admin-deptfinfo-TableBody tr.selected');
    if (previouslySelectedRow) {
        previouslySelectedRow.classList.remove('selected');
    }

    // 새로운 행을 추가하고 자동으로 선택 상태로 만듦
    tableBody.appendChild(newRow);
    newRow.classList.add('prof-row', 'selected');

    // 실시간 반영 이벤트 추가
    addRealTimeProfEditing(newRow);
});

// 실시간으로 수정 내용 반영
function addRealTimeProfEditing(row) {
    const admin_deptfinfo_deptnameCell = row.querySelector('td[data-field="deptname"]');


    // 폼 필드에 실시간으로 입력되는 값 반영
    document.getElementById('admin_deptfinfo_deptname').addEventListener('input', function() {
        if (admin_deptfinfo_deptnameCell) {
            admin_deptfinfo_deptnameCell.textContent = this.value;  // 이름 수정 반영
        }
    });
}

//저장

