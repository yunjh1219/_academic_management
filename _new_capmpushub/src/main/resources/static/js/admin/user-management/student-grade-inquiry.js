document.addEventListener('DOMContentLoaded', function() {
    // 모달 열기
    document.getElementById('new-register-btn').onclick = function() {
        document.getElementById('new-register-modal').style.display = 'block';
    }

    // 모달 닫기
    document.getElementById('close-modal').onclick = function() {
        document.getElementById('new-register-modal').style.display = 'none';
    }

    // 모달 외부 클릭 시 닫기
    window.onclick = function(event) {
        if (event.target == document.getElementById('new-register-modal')) {
            document.getElementById('new-register-modal').style.display = 'none';
        }
    }
});
