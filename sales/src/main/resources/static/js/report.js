document.addEventListener('DOMContentLoaded', () => {
  const exportExcelBtn = document.getElementById('exportExcel');
  const exportPdfBtn = document.getElementById('exportPdf');
  const exportCsvBtn = document.getElementById('exportCsv');
  
  const reportTable = document.getElementById('reportTable');
  const orderReportTable = document.getElementById('orderReportTable');
  
  exportExcelBtn?.addEventListener('click', () => exportToExcel(reportTable, orderReportTable,invoiceReportTable));
  exportPdfBtn?.addEventListener('click', () => exportToPdf(reportTable, orderReportTable, invoiceReportTable));
  exportCsvBtn?.addEventListener('click', () => exportToCsv(reportTable, orderReportTable, invoiceReportTable));
  
  // Phân trang
  const rowsPerPage = 10;
  const pagination = document.querySelector('.pagination');
  let currentPage = 1;

  // Chọn bảng cần phân trang
  const table = reportTable || orderReportTable||invoiceReportTable;
  const rows = table.querySelectorAll('tbody tr');

  function showPage(page) {
    currentPage = page;

    rows.forEach((row, index) => {
      row.style.display =
        index >= (page - 1) * rowsPerPage && index < page * rowsPerPage
          ? ''
          : 'none';
    });

    updatePagination();
  }

  function updatePagination() {
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    pagination.innerHTML = ''; // Clear old pagination

    for (let i = 1; i <= totalPages; i++) {
      const pageItem = document.createElement('li');
      pageItem.className = 'page-item' + (i === currentPage ? ' active' : '');
      const pageLink = document.createElement('a');
      pageLink.className = 'page-link';
      pageLink.textContent = i;
      pageLink.href = '#';
      pageLink.addEventListener('click', (e) => {
        e.preventDefault();
        showPage(i);
      });
      pageItem.appendChild(pageLink);
      pagination.appendChild(pageItem);
    }
  }

  showPage(1); // Mặc định hiển thị trang 1

  // Hàm xuất Excel
  function exportToExcel(reportTable, orderReportTable,invoiceReportTable) {
    const table = reportTable || orderReportTable||invoiceReportTable; 
    const wb = XLSX.utils.table_to_book(table);
    XLSX.writeFile(wb, 'report.xlsx');
  }


  function exportToPdf(reportTable, orderReportTable, invoiceReportTable) {
    const table = reportTable || orderReportTable||invoiceReportTable; 
    const rows = Array.from(table.querySelectorAll('tr')).map(tr => 
      Array.from(tr.children).map(td => td.innerText)
    );

    const docDefinition = {
      content: [
        { text: 'Báo cáo', style: 'header' },
        { table: { body: rows } },
      ],
      styles: {
        header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
      },
    };

    pdfMake.createPdf(docDefinition).download('report.pdf');
  }

 
  function exportToCsv(reportTable, orderReportTable, invoiceReportTable) {
    const table = reportTable || orderReportTable||invoiceReportTable; 
    let csvContent = '\uFEFF'; 
    const rows = table.querySelectorAll('tr');

    rows.forEach(row => {
      const cells = Array.from(row.children).map(td => `"${td.innerText.replace(/"/g, '""')}"`);
      csvContent += cells.join(',') + '\n';
    });

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.setAttribute('download', 'report.csv');
    link.click();
  }
});
