$(document).ready(function () {
  // Khởi tạo DataTables
  $("#list").DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.13.4/i18n/vi.json",
    },
    paging: true, // Bật phân trang
    searching: true, // Bật tìm kiếm
    ordering: true, // Bật sắp xếp cột
    pageLength: 10, // Số dòng mỗi trang
  });
});
