POST /api/auth/register
Content-Type: application/json
{
    name: String
    email: String
    password: String
}
response:
{
    token: String
    body: User
}

POST /api/auth/login
Content-Type: application/json
{
    name: String (не обов'язково)
    email: String
    password: String
}
response:
{
    token: String
    body: User
}
GET /api/auth/user
Authorization: Bearer +token
response:
{
    body: User
}