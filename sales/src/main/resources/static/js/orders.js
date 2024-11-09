document.addEventListener("DOMContentLoaded", function () {
    loadOrders();
});

function loadOrders() {
    fetch("/api/orders")
        .then(response => response.json())
        .then(data => {
            const orderList = document.getElementById("orderList");
            orderList.innerHTML = "";
            data.forEach(order => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${order.orderID}</td>
                    <td>${order.productName}</td>
                    <td>${order.orderDate}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="openStatusDialog('${order.orderID}', '${order.status}', '${order.paymentStatus}')">Sửa</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteOrder('${order.orderID}')">Xóa</button>
                    </td>
                `;
                orderList.appendChild(row);
            });
        })
        .catch(error => console.error("Error loading orders:", error));
}

function openDialog(mode, orderID = '', customerName = '', productName = '', status = '', paymentStatus = '', orderDate = '') {
    const modalTitle = document.getElementById("dialogLabel");
    const orderIDInput = document.getElementById("orderID");
    const productNameInput = document.getElementById("productName");
    const orderDateInput = document.getElementById("orderDate");

    if (mode === 'add') {
        modalTitle.textContent = "Thêm đơn hàng";
        orderIDInput.value = '';
        productNameInput.value = '';
        orderDateInput.value = '';
    } else if (mode === 'edit') {
        modalTitle.textContent = "Sửa đơn hàng";
        orderIDInput.value = orderID;
        productNameInput.value = productName;
        orderDateInput.value = orderDate;
    }

    const modal = new bootstrap.Modal(document.getElementById("dialog"));
    modal.show();
}

function addOrder() {
    const newOrder = {
        productName: document.getElementById("productName").value,
        orderDate: document.getElementById("orderDate").value
    };

    fetch("/api/orders", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(newOrder)
    })
    .then(response => response.json())
    .then(data => {
        loadOrders();
        bootstrap.Modal.getInstance(document.getElementById("dialog")).hide();
    })
    .catch(error => console.error("Error adding order:", error));
}

function openStatusDialog(orderID, status, paymentStatus) {
    document.getElementById("orderID").value = orderID;
    document.getElementById("productName").value = status;
    document.getElementById("orderDate").value = paymentStatus;
    const modal = new bootstrap.Modal(document.getElementById("dialog"));
    modal.show();
}

function updateOrderStatus() {
    const orderID = document.getElementById("orderID").value;
    const updatedOrder = {
        productName: document.getElementById("productName").value,
        orderDate: document.getElementById("orderDate").value
    };

    fetch(`/api/orders/${orderID}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedOrder)
    })
    .then(response => response.json())
    .then(data => {
        loadOrders();
        bootstrap.Modal.getInstance(document.getElementById("dialog")).hide();
    })
    .catch(error => console.error("Error updating order status:", error));
}

function deleteOrder(orderID) {
    if (!confirm("Bạn có chắc chắn muốn xóa đơn hàng này?")) return;

    fetch(`/api/orders/${orderID}`, {
        method: "DELETE"
    })
    .then(response => {
        if (response.ok) {
            loadOrders();
        } else {
            console.error("Error deleting order:", response.statusText);
        }
    })
    .catch(error => console.error("Error deleting order:", error));
}