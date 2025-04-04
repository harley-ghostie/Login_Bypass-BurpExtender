package loginbypass;

import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence;
import burp.api.montoya.scanner.audit.issues.AuditIssueDefinition;
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity;
import burp.api.montoya.collaborator.Interaction;

import java.util.Collections;
import java.util.List;

public class CustomAuditIssue implements AuditIssue {

    private final String name;
    private final String url;
    private final String detail;
    private final String remediation;
    private final List<HttpRequestResponse> httpMessages;
    private final AuditIssueSeverity severity;
    private final AuditIssueConfidence confidence;

    public CustomAuditIssue(
            String name,
            String url,
            String detail,
            String remediation,
            List<HttpRequestResponse> httpMessages,
            AuditIssueSeverity severity,
            AuditIssueConfidence confidence
    ) {
        this.name = name;
        this.url = url;
        this.detail = detail;
        this.remediation = remediation;
        this.httpMessages = httpMessages;
        this.severity = severity;
        this.confidence = confidence;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public AuditIssueDefinition definition() {
        return new AuditIssueDefinition() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String background() {
                return "Autenticação client-side é inerentemente insegura. "
                        + "Trechos de código JavaScript que manipulam login ou tokens podem ser utilizados para contornar autenticação.";
            }

            @Override
            public String remediation() {
                return remediation;
            }

            @Override
            public int typeIndex() {
                return 0;
            }

            @Override
            public AuditIssueSeverity typicalSeverity() {
                return severity;
            }
        };
    }

    @Override
    public AuditIssueSeverity severity() {
        return severity;
    }

    @Override
    public AuditIssueConfidence confidence() {
        return confidence;
    }

    @Override
    public String detail() {
        return detail;
    }

    @Override
    public String remediation() {
        return remediation;
    }

    @Override
    public List<HttpRequestResponse> requestResponses() {
        return httpMessages;
    }

    @Override
    public HttpService httpService() {
        return httpMessages.get(0).request().httpService();
    }

    @Override
    public String baseUrl() {
        return httpMessages.get(0).request().url().toString();
    }

    @Override
    public List<Interaction> collaboratorInteractions() {
        return Collections.emptyList();
    }
}

