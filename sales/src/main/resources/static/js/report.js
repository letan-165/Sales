document.addEventListener('DOMContentLoaded', () => {
    const exportExcelBtn = document.getElementById('exportExcel');
    const exportPdfBtn = document.getElementById('exportPdf');
    const exportCsvBtn = document.getElementById('exportCsv');
    const reportTable = document.getElementById('reportTable');
  
    exportExcelBtn?.addEventListener('click', exportToExcel);
    exportPdfBtn?.addEventListener('click', exportToPdf);
    exportCsvBtn?.addEventListener('click', exportToCsv);
  
    function exportToExcel() {
      const wb = XLSX.utils.table_to_book(reportTable);
      XLSX.writeFile(wb, 'report.xlsx');
    }
  
    function exportToPdf() {
      const rows = Array.from(reportTable.querySelectorAll('tr')).map(tr => 
        Array.from(tr.children).map(td => td.innerText)
      );
  
      const docDefinition = {
        content: [
          { text: 'Báo cáo Sản phẩm Nhập hàng', style: 'header' },
          { table: { body: rows } },
        ],
        styles: {
          header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
        },
      };
  
      pdfMake.createPdf(docDefinition).download('report.pdf');
    }
  
    function exportToCsv() {
        let csvContent = '\uFEFF'; 
        const rows = reportTable.querySelectorAll('tr');
      
        rows.forEach(row => {
          const cells = Array.from(row.children).map(td => `"${td.innerText}"`); 
          csvContent += cells.join(',') + '\n';
        });
      
        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.setAttribute('download', 'report.csv');
        link.click();
      }
      
  });
const table = document.getElementById('reportTable');
const rowsPerPage = 10; 
const rows = table.querySelectorAll('tbody tr');
const pagination = document.querySelector('.pagination');
let currentPage = 1;

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
  pagination.innerHTML = ''; 

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

showPage(1);
  
