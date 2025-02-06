// 탭 클릭 시 활성화 함수
document.getElementById('student-tab').addEventListener('click', function() {
    setActiveTab('student');
});
document.getElementById('professor-tab').addEventListener('click', function() {
    setActiveTab('professor');
});
document.getElementById('admin-tab').addEventListener('click', function() {
    setActiveTab('admin');
});

// 탭을 활성화하는 함수
function setActiveTab(type) {
    const tabs = document.querySelectorAll('.login-form-tab');
    tabs.forEach(tab => tab.classList.remove('active')); // 기존의 active 클래스 제거

    // 선택된 탭에 active 클래스 추가
    if (type === 'student') {
        document.getElementById('student-tab').classList.add('active');
    } else if (type === 'professor') {
        document.getElementById('professor-tab').classList.add('active');
    } else if (type === 'admin') {
        document.getElementById('admin-tab').classList.add('active');
    }
}
