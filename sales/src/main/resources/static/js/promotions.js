function openAddDialog() {
    // Reset các trường trong dialog thêm mới
    document.getElementById('newPromoCode').value = '';
    document.getElementById('newDiscountValue').value = '';
    document.getElementById('newStartTime').value = '';
    document.getElementById('newEndTime').value = '';
    document.getElementById('newConditions').value = '';
    document.getElementById('newUsageLimit').value = '';
    
    const addDialog = new bootstrap.Modal(document.getElementById('addDialog'));
    addDialog.show();
}

function openEditDialog(button) {
    // Lấy dữ liệu từ nút đã nhấn
    const discountID = button.getAttribute('data-discount-id');
    const discountValue = button.getAttribute('data-discount-value');
    const startTime = button.getAttribute('data-start-date');
    const endTime = button.getAttribute('data-end-date');
    const minimum = button.getAttribute('data-minimum');
    const usageLimit = button.getAttribute('data-quantity');

    // Đặt giá trị vào các trường trong dialog chỉnh sửa
    document.getElementById('editPromoCode').value = discountID;
    document.getElementById('editDiscountValue').value = discountValue;
    document.getElementById('editStartTime').value = startTime;
    document.getElementById('editEndTime').value = endTime;
    document.getElementById('editConditions').value = minimum;
    document.getElementById('editUsageLimit').value = usageLimit;

    const editDialog = new bootstrap.Modal(document.getElementById('editDialog'));
    editDialog.show();
}

function addNewPromotion() {
    // Collect form data
    const newPromoCode = document.getElementById('newPromoCode').value;
    const newDiscountValue = document.getElementById('newDiscountValue').value;
    const newStartTime = document.getElementById('newStartTime').value;
    const newEndTime = document.getElementById('newEndTime').value;
    const newConditions = document.getElementById('newConditions').value;
    const newUsageLimit = document.getElementById('newUsageLimit').value;

    // Send the data to the backend using Fetch API (or AJAX)
    fetch('/api/discounts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            discountID: newPromoCode,
            discount: newDiscountValue,
            startTime: newStartTime,
            endTime: newEndTime,
            minimum: newConditions,
            quantity: newUsageLimit
        })
    })
    .then(response => response.json())
    .then(data => {
        alert('Thêm mới thành công!');
        location.reload(); // Reload the page to update the table
    })
    .catch(error => {
        alert('Có lỗi xảy ra khi thêm mã giảm giá');
        console.error('Error:', error);
    });
}

function editPromotion() {
    // Collect form data
    const editPromoCode = document.getElementById('editPromoCode').value;
    const editDiscountValue = document.getElementById('editDiscountValue').value;
    const editStartTime = document.getElementById('editStartTime').value;
    const editEndTime = document.getElementById('editEndTime').value;
    const editConditions = document.getElementById('editConditions').value;
    const editUsageLimit = document.getElementById('editUsageLimit').value;

    // Send the updated data to the backend using Fetch API (or AJAX)
    fetch(`/api/discounts/${editPromoCode}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            discount: editDiscountValue,
            startTime: editStartTime,
            endTime: editEndTime,
            minimum: editConditions,
            quantity: editUsageLimit
        })
    })
    .then(response => response.json())
    .then(data => {
        alert('Chỉnh sửa thành công!');
        location.reload(); // Reload the page to update the table
    })
    .catch(error => {
        alert('Có lỗi xảy ra khi chỉnh sửa mã giảm giá');
        console.error('Error:', error);
    });
}

function deleteDiscount(discountID) {
    // Ask for confirmation before deleting
    if (confirm('Bạn có chắc chắn muốn xóa mã giảm giá này?')) {
        fetch(`/api/discounts/${discountID}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Xóa thành công!');
                location.reload(); // Reload the page to update the table
            } else {
                alert('Có lỗi xảy ra khi xóa mã giảm giá');
            }
        })
        .catch(error => {
            alert('Có lỗi xảy ra khi xóa mã giảm giá');
            console.error('Error:', error);
        });
    }
}