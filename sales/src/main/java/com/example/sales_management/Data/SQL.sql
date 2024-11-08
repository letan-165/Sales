INSERT INTO imports_products (importID, productID,quantity)
VALUES
    (1, 1, 2),
    (1, 2, 3),
    (2, 3, 4),
    (2, 4, 2),
    (3, 5, 1),
    (3, 6, 3),
    (4, 7, 5),
    (4, 8,1),
    (5, 9, 2),
    (5, 10, 7)

    INSERT INTO invoices (invoice_time, total_amount, status, description) VALUES
    (NOW(), 100000, 'paid', 'Invoice for order 1'),
    (NOW(), 50000, 'unpaid', 'Invoice for order 2'),
    (NOW(), 150000, 'paid', 'Invoice for order 3'),
    (NOW(), 200000, 'unpaid', 'Invoice for order 4'),
    (NOW(), 75000, 'paid', 'Invoice for order 5')
