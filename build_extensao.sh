#!/bin/bash

BURP_JAR="lib/burp-montoya-api.jar"
SRC_DIR="src/loginbypass"
CLASS_NAME="BurpExtender.java"

if [[ ! -f "$BURP_JAR" ]]; then
    echo "❌ Montoya API JAR não encontrado em: $BURP_JAR"
    echo "➡️ Baixe em: https://portswigger.net/burp/documentation/montoya"
    exit 1
fi

if [[ ! -f "$SRC_DIR/$CLASS_NAME" ]]; then
    echo "❌ Arquivo $CLASS_NAME não encontrado em $SRC_DIR"
    exit 1
fi

mkdir -p out

javac -cp "$BURP_JAR" -d out "$SRC_DIR/$CLASS_NAME"
if [[ $? -ne 0 ]]; then
    echo "❌ Erro na compilação."
    exit 1
fi

cd out
jar cf ../login_bypass_montoya.jar loginbypass/*.class
cd ..

echo "✅ login_bypass_montoya.jar criado com sucesso!"
