class AppMenu extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
            <button id="menuButton" class="menu-button" onclick="toggleMenu()">≡</button>
            <div class="menu">
                <div id="sideMenu" class="side-menu">
                    <div class="side-menu-header">
                        <span class="menu-title">メニュー</span>
                    </div>
                <div class="side-menu-body">
                    <button class="btn btn-primary" onclick="location.href='itiran.html'">
                        管理薬品一覧
                    </button>
                    <button class="btn btn-success" onclick="location.href='tamesitamesi.html'">
                        使用記録一覧
                    </button>
                    <button class="btn btn-secondary" onclick="location.href='QR.html'">
                        実習室一覧
                    </button>
                    <button class="btn btn-warning" onclick="location.href='Remainingstock.html'">
                        マイページ
                    </button>
                    <button class="btn btn-danger" onclick="location.href='delete.html'">
                        QRコード読み取り
                    </button>
                    ----------------------------
                    管理者専用
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        管理者ページ
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        新規薬品登録
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        利用ユーザー一覧
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        QRコード再発行
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        使用記録一覧
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        容量・在庫一覧
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        薬品瓶抹消
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        管理ユーザー申請一覧
                    </button>
                    <button class="btn btn-info" onclick="location.href='deleterireki.html'">
                        ホームに戻る
                    </button>
                </div>
            </div>
        </div>
        `;
    }
}
customElements.define('app-menu', AppMenu);

function toggleMenu() {
    const main = document.getElementById("maingamen");
    const menu = document.getElementById("sideMenu");
    const button = document.getElementById("menuButton");


    main.classList.toggle("menu-open")
    const isOpen = menu.classList.toggle("open");

    if (isOpen) {
        button.textContent = "×";
        button.style.color = "#333";
    } else {
        button.textContent = "≡";
        button.style.color = "white";
    }
}