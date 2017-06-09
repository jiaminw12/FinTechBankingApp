package finapp.publicstatic.com.fintechbankapp;

public class WebServiceAddress {

    private static final String BASE_URL = "http://fintechapp.azurewebsites.net/";

    public String getBaseUrl(String page){
        return BASE_URL + page + ".php";
    }


}
