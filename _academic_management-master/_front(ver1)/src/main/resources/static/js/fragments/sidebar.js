document.addEventListener('DOMContentLoaded', function() {
    const menuItems = document.querySelectorAll('.menu-item');
    const sidebarItems = document.querySelectorAll('.snb_depth1 > li');

    // 메뉴 클릭 시 동적으로 selectedMenu 업데이트
    menuItems.forEach(function(item) {
        item.addEventListener('click', function(event) {
            const selectedMenu = item.getAttribute('data-menu');

            // 모든 메뉴 항목에서 selected 클래스를 제거하고, 클릭된 항목에만 추가
            menuItems.forEach(function(menu) {
                menu.classList.remove('selected');
            });
            item.classList.add('selected');

            // 사이드바 메뉴 항목 표시/숨기기
            sidebarItems.forEach(function(sidebarItem) {
                if (sidebarItem.classList.contains(selectedMenu)) {
                    sidebarItem.style.display = 'block';  // 클릭된 메뉴 항목 보이기
                } else {
                    sidebarItem.style.display = 'none';   // 나머지 항목 숨기기
                }
            });
        });
    });

    // 기본 메뉴(학생모드) 표시
    menuItems.forEach(function(menu) {
        if (menu.getAttribute('data-menu') === 'student') {
            menu.classList.add('selected');
        }
    });

    sidebarItems.forEach(function(sidebarItem) {
        if (sidebarItem.classList.contains('student')) {
            sidebarItem.style.display = 'block';  // 기본 메뉴(학생모드)만 보이기
        } else {
            sidebarItem.style.display = 'none';   // 나머지 항목 숨기기
        }
    });
});