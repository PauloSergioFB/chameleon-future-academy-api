# Teste de Requisições via cUrl

# Login
curl -X POST "http://localhost:8080/api/v1/auth" \
    -H "Content-Type: application/json" \
    -d '{
        "email": "joao.andrade@example.com",
        "password": "SenhaForte123!"
    }'

###

# Refresh Token
curl -X POST "http://localhost:8080/api/v1/auth/refresh" \
    -H "Content-Type: application/json" \
    -d '{
        "refresh_token": "SEU_REFRESH_TOKEN_AQUI"
    }'

###

# Criar um novo usuário
curl -X POST "http://localhost:8080/api/v1/users" \
    -H "Content-Type: application/json" \
    -d '{
        "full_name": "Usuário de exemplo",
        "email": "example@email.com",
        "password": "123456"
    }'

###

# Atualizar usuário
curl -X PUT "http://localhost:8080/api/v1/users/1" \
    -H "Authorization: Bearer SEU_JWT_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "full_name": "Usuário de exemplo atualizado",
        "email": "example@email.com",
        "password": "654321"
    }'

###

# Remover usuário
curl -X DELETE "http://localhost:8080/api/v1/users/1" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Remover usuário
curl -X DELETE "http://localhost:8080/api/v1/users/1" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Dados do usuário logado
curl -X GET "http://localhost:8080/api/v1/users/me" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Matrículas do usuário logado
curl -X GET "http://localhost:8080/api/v1/users/me/enrollments" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Badges do usuário logado
curl -X GET "http://localhost:8080/api/v1/users/me/badges" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Perfil completo
curl -X GET "http://localhost:8080/api/v1/users/me/profile" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Lista de cursos
curl -X GET "http://localhost:8080/api/v1/courses?page=0&size=10" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Pesquisar cursos
curl -X GET "http://localhost:8080/api/v1/courses/search?query=programação" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Badges do curso
curl -X GET "http://localhost:8080/api/v1/courses/1/badges" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Tags do curso
curl -X GET "http://localhost:8080/api/v1/courses/1/tags" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Conteúdos do curso
curl -X GET "http://localhost:8080/api/v1/courses/1/contents" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Informações completas do curso
curl -X GET "http://localhost:8080/api/v1/courses/1/details" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Criar matrícula
curl -X POST "http://localhost:8080/api/v1/enrollments" \
    -H "Authorization: Bearer SEU_JWT_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "user_id": 1,
        "course_id": 6,
        "status": "in progress"
    }'

###

# Atualizar matrícula
curl -X PUT "http://localhost:8080/api/v1/enrollments/1" \
    -H "Authorization: Bearer SEU_JWT_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "user_id": 1,
        "course_id": 6,
        "progress": 1,
        "status": "in progress"
    }'

###

# Remover matrícula
curl -X DELETE "http://localhost:8080/api/v1/enrollments/1" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Listar tags
curl -X GET "http://localhost:8080/api/v1/tags" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"


# Buscar aula pelo content_id
curl -X GET "http://localhost:8080/api/v1/contents/1/lesson" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"

###

# Buscar atividade pelo content_id
curl -X GET "http://localhost:8080/api/v1/contents/3/activity" \
    -H "Authorization: Bearer SEU_JWT_TOKEN"
