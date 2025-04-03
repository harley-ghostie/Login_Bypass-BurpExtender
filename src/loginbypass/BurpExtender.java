package loginbypass;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.Extension;
import burp.api.montoya.http.handler.HttpResponseHandler;
import burp.api.montoya.http.message.responses.HttpResponse;

public class BurpExtender implements Extension
{
    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName("login_bypass");

        api.http().registerHttpResponseHandler(new HttpResponseHandler()
        {
            @Override
            public void handleResponse(HttpResponse response)
            {
                String body = response.bodyToString();

                if (body.contains("localStorage.setItem('loggedIn', 'true')") ||
                    body.contains("window.isLoggedIn = true") ||
                    body.contains("window.location.href") ||
                    body.matches("(?i).*username.?=.?['\\\"]\\w+['\\\"].*password.?=.?['\\\"]\\w+['\\\"].*")) {

                    api.logging().logToOutput("🚨 POSSÍVEL BYPASS EM: " + response.initiatingRequest().url());
                }
            }
        });
    }
}
