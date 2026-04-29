# 🔐 login_bypass (Burp Montoya Extension)

![Status](https://img.shields.io/badge/STATUS-EM%20DESENVOLVIMENTO-brightgreen?style=for-the-badge)
> [!NOTE]
> Projeto em desenvolvimento.
>
> Este repositório reúne scripts de apoio para validação técnica e triagem de segurança. Os scripts podem passar por ajustes, refatoração e melhorias de precisão conforme novos cenários forem testados.

Extensão Burp para detectar padrões JavaScript inseguros que podem permitir bypass de autenticação.

Versão de API Montoya
## 🚀 Funcionalidade

- Detecta:
  - localStorage.setItem('loggedIn', 'true')
  - window.isLoggedIn = true
  - Redirecionamentos window.location.href
  - Credenciais hardcoded (usuário/senha)

- Output direto no **Burp > Extender > Output**

## 📥 Clone este repositório
```bash
git clone https://github.com/harley-ghostie/Login_Bypass-BurpExtender
```
```bash
cd Login_Bypass-BurpExtender
```

## 🛠️ Como compilar

### 1. Clone a Montoya API (obrigatório)

```bash
git clone https://github.com/PortSwigger/burp-extensions-montoya-api.git
```
2. Copie o JAR da API para sua pasta local

```bash
cp burp-extensions-montoya-api/lib/burp-montoya-api.jar lib/
```
Ou crie a pasta se ainda não existir: mkdir -p lib

### 3. Compile a extensão com o script
```bash
chmod +x build_extensao.sh
./build_extensao.sh
```
O script vai gerar o arquivo login_bypass_montoya.jar, pronto para ser carregado no Burp Suite.

## 🔌 Como instalar no Burp Suite
Abra o Burp

Vá em Extender > Extensions > Add

Tipo: Java

Arquivo: selecione login_bypass_montoya.jar

Veja os logs em Extender > Output


