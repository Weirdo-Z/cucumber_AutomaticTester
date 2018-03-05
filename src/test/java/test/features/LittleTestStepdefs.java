package test.features;

import com.alibaba.fastjson.JSONObject;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import http.SimpleHttpClient;
import org.junit.Assert;
import org.junit.runner.RunWith;
import utils.Utils;

import java.io.File;

/**
 * Created by Zoey on 2018/03/05
 */
public class LittleTestStepdefs {
    private String url = "";
    private int hours = 0;
    private String result = "";
    @Given("^baseUrl is \"([^\"]*)\"$")
    public void baseuriIs(String arg0) throws Throwable {
        this.url = arg0;
    }
    @Given("^trustStore is \"([^\"]*)\" trustStorePassword is \"([^\"]*)\"$")
    public void truststoreIsTrustStorePasswordIs(String arg0, String arg1) throws Throwable {
        if(!"".equals(arg0)){
            System.setProperty("javax.net.ssl.trustStore",arg0);
            if(!"".equals(arg1)){
                System.setProperty("javax.net.ssl.trustStorePassword",arg1);
            }
        }
    }
    @Given("^expected run hours \"([^\"]*)\"$")
    public void expectedRunHours(String arg0) throws Throwable {
        if(!"".equals(arg0)) {
            this.hours = Integer.parseInt(arg0);
        }
    }

    @Then("^send event \"([^\"]*)\"$")
    public void sendEvent(String arg0) throws Throwable {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(arg0).toURI());
        JSONObject json = Utils.toJSONObject(file);
        this.result = new SimpleHttpClient(url).doPost(json.toJSONString(), "text/json", "utf-8");
    }

    @Then("^check response \"([^\"]*)\"$")
    public void checkResponse(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^check status \"([^\"]*)\"$")
    public void checkStatus(String arg0) throws Throwable {
    }

}
