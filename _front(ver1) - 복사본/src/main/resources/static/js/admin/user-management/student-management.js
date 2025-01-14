// 모달 열기
document.getElementById('openAddModal').onclick = function() {
    document.getElementById('studentaddModal').style.display = 'block';
}

// 모달 닫기
document.getElementById('close-modal').onclick = function() {
    document.getElementById('studentaddModal').style.display = 'none';
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    if (event.target == document.getElementById('studentaddModal')) {
        document.getElementById('studentaddModal').style.display = 'none';
    }
}