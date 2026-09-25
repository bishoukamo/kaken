function filterMedicine(){

    const medicine = document.getElementById("medicineFilter").value;
    const stock = document.getElementById("stockFilter").value;
    const start = document.getElementById("startDate").value;
    const end = document.getElementById("endDate").value;
    const keyword = document.getElementById("searchInput").value.toLowerCase();
    

    let rows = document.querySelectorAll("#medicineTable tr");

    rows.forEach(function(row){

        let medicineName = row.cells[1].textContent;
        let registerDate = row.cells[2].textContent;
        let stockState = row.cells[5].textContent;

        let show = true;

        // キーワード検索
        if (keyword !== "" &&
            !medicineName.toLowerCase().includes(keyword)) {
            show = false;
        }

        // 薬品名
        if (medicine !== "" && medicineName !== medicine) {
            show = false;
        }

        // 状態
        if (stock !== "" && stockState !== stock) {
            show = false;
        }

        // 開始日
        if (start !== "" && registerDate < start) {
            show = false;
        }

        // 終了日
        if (end !== "" && registerDate > end) {
            show = false;
        }

        row.style.display = show ? "" : "none";

    });
}