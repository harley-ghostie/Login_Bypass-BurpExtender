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

## 🧩 4. Baixe a Montoya API

Se ainda não tem, baixe aqui:  
🔗 [https://portswigger.net/burp/documentation/montoya](https://portswigger.net/burp/documentation/montoya)

Ou clone o repo:  
```bash
git clone https://github.com/PortSwigger/burp-extensions-montoya-api.git
