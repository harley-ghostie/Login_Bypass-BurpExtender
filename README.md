# 🔐 login_bypass (Burp Montoya Extension)

Extensão Burp para detectar padrões JavaScript inseguros que podem permitir bypass de autenticação.

## 🚀 Funcionalidade

- Detecta:
  - `localStorage.setItem('loggedIn', 'true')`
  - `window.isLoggedIn = true`
  - Redirecionamentos `window.location.href`
  - Credenciais hardcoded (usuário/senha)

- Output direto no **Burp > Extender > Output**

## ⚙️ Como compilar

1. Baixe o JAR da Montoya API e salve em `lib/burp-montoya-api.jar`
2. Execute:

```bash
chmod +x build_extensao.sh
./build_extensao.sh
