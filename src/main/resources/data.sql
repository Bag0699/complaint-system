INSERT INTO users (
    first_name,
    last_name,
    email,
    password,
    phone,
    role,
    is_active,
    created_at,
    updated_at
) VALUES (
             'admin',
             'admin',
             'admin@admin.com',
             '$2a$12$2y5UfTYV2rXocqH5rQmlreGopJsMayP23tQx/kdgZLFtllg00VkpW', -- admin123!
             '+51999999992',
             'ADMIN',
             true,
             NOW(),
             NOW()
         );

-- Verificar creación
SELECT id, first_name, last_name, email, role, is_active
FROM users
WHERE email = 'admin@admin.com';

