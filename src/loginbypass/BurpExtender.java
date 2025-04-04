package loginbypass;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity;
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class BurpExtender implements BurpExtension {
    @Override
    public void initialize(MontoyaApi api) {
        api.extension().setName("Login Bypass Scanner");

        api.scanner().registerScanCheck(new ScanCheck() {
            @Override
            public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {
                HttpRequest request = baseRequestResponse.request();
                HttpResponse response = baseRequestResponse.response();

                if (response == null || request == null) return AuditResult.auditResult();

                String url = request.url().toString();
                String body = response.bodyToString();
                String snippet = null;

                // Padrões JavaScript suspeitos de autenticação client-side
                Pattern[] patterns = new Pattern[] {
                    Pattern.compile("window\\.location\\.href ?= ?['\"](/\\w+\\.html)['\"]", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("localStorage\\.setItem\\(['\"]loggedIn['\"], ?['\"]true['\"]\\)", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("window\\.isLoggedIn ?= ?true", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("fetch\\([\"'](/auth/login)[\"']", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("if ?\\(username === [\"']\\w+[\"'] && password === [\"']\\w+[\"']\\)", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("const users ?= ?\\[.*?\\]", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("document\\.querySelector\\(.*?\\)\\.onclick", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("document\\.getElementById\\(.*?\\)\\.onclick", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("getCookie\\([\"']login[\"']\\)", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("setCookie\\([\"']login[\"']", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("document\\.cookie ?= ?[\"']login=.*?[\"']", Pattern.CASE_INSENSITIVE)
                };

                for (Pattern pattern : patterns) {
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        snippet = matcher.group();
                        break;
                    }
                }

                if (snippet != null) {
                    AuditIssue issue = new CustomAuditIssue(
                        "Login Bypass Possível",
                        url,
                        "Trecho JavaScript encontrado: " + snippet +
                        "\n\n💡 *PoC sugerida:* No console do navegador, cole:\n\n" +
                        (snippet.contains("getCookie") || snippet.contains("setCookie") || snippet.contains("document.cookie")
                            ? "document.cookie = 'login=admin'; location.reload();"
                            : snippet) +
                        "\n\n⚠️ Se houver redirecionamento ou acesso ao painel, a autenticação está ocorrendo apenas no lado cliente.",
                        "A autenticação parece estar ocorrendo no cliente (CWE-602).\nMais info: https://cwe.mitre.org/data/definitions/602.html",
                        List.of(baseRequestResponse),
                        AuditIssueSeverity.HIGH,
                        AuditIssueConfidence.FIRM
                    );

                    return AuditResult.auditResult(List.of(issue));
                }

                return AuditResult.auditResult();
            }

            @Override
            public AuditResult activeAudit(HttpRequestResponse baseRequestResponse, AuditInsertionPoint auditInsertionPoint) {
                return AuditResult.auditResult(); // Active scan será implementado depois
            }

            @Override
            public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
                return ConsolidationAction.KEEP_BOTH;
            }
        });
    }
}
