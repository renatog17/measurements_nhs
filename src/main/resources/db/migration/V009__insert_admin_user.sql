INSERT INTO users (
    name,
    email,
    password,
    role,
    active,
    created_at,
    updated_at
) VALUES (
             'Admin',
             'admin@admin.com',
             '$2a$10$ugynKXvGgbnXow2n6ucyGuglnLOy9rdDpC/xX2M5EYKY0homuaOTW',
             'ADMIN',
             true,
             NOW(),
             NOW()
         );