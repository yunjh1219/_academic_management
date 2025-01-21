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
        if (menu.getAttribute('data-menu') === 'prof') {
            menu.classList.add('selected');
        }
    });

    sidebarItems.forEach(function(sidebarItem) {
        if (sidebarItem.classList.contains('prof')) {
            sidebarItem.style.display = 'block';  // 기본 메뉴(학생모드)만 보이기
        } else {
            sidebarItem.style.display = 'none';   // 나머지 항목 숨기기
        }
    });
});

// DOMContentLoaded 이벤트를 통해 DOM이 로드된 후 실행
document.addEventListener('DOMContentLoaded', function () {
    // 모든 depth1 항목 가져오기
    const depth1Items = document.querySelectorAll('.snb_depth1 > li > a');

    // depth1 클릭 이벤트 처리
    depth1Items.forEach(item => {
        item.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 동작 방지

            // 모든 다른 li 요소에서 active 클래스 제거
            $(".snb_depth1 li").removeClass("active");
            // 클릭된 요소에 active 클래스 추가
            $(this).parent().addClass("active");

            // 현재 클릭된 항목의 하위 depth2 가져오기
            const subMenu = this.nextElementSibling;
            if (subMenu && subMenu.classList.contains('snb_depth2')) {
                // 토글로 열고 닫기
                subMenu.style.display = subMenu.style.display === 'block' ? 'none' : 'block';
            }
        });
    });

    // 모든 depth2 항목 가져오기
    const depth2Items = document.querySelectorAll('.snb_depth2 > li > a');

    // depth2 클릭 이벤트 처리
    depth2Items.forEach(item => {
        item.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 동작 방지

            // 현재 클릭된 항목의 하위 depth3 가져오기
            const subMenu = this.nextElementSibling;
            if (subMenu && subMenu.classList.contains('snb_depth3')) {
                // 토글로 열고 닫기
                subMenu.style.display = subMenu.style.display === 'block' ? 'none' : 'block';
            }
        });
    });
    // 모든 depth3 항목 가져오기
    const depth3Items = document.querySelectorAll('.snb_depth3 > li > a');

    // depth3 클릭 이벤트 처리
    depth3Items.forEach(item => {
        item.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 동작 방지

            // 모든 다른 depth3 항목에서 active 클래스 제거
            $(".snb_depth3 li").removeClass("active");
            // 클릭된 요소에 active 클래스 추가
            $(this).parent().addClass("active");
        });
    });
});