package com.example.khalil.pixidustwebapi.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Khalil on 1/22/2018.
 */

public class RestClient {
        private ArrayList<NameValuePair> params;
        private ArrayList <NameValuePair> headers;
        private ArrayList <String> jParams;
        private String url;

        private int responseCode;
        private String message;

        private String response;

        public String getResponse() {
            return response;
        }
        public String getErrorMessage() {
            return message;
        }
        public int getResponseCode() {
            return responseCode;
        }

        public RestClient(String url)
        {
            this.url = url;
            params = new ArrayList<NameValuePair>();
            headers = new ArrayList<NameValuePair>();
            jParams = new ArrayList<String>();
        }
        public void AddParam(String name, String value)
        {
            params.add(new BasicNameValuePair(name, value));
        }
        public void AddParam(String value)
        {
            jParams.add(value);
        }
        public void AddHeader(String name, String value)
        {
            headers.add(new BasicNameValuePair(name, value));
        }
        public static enum RequestMethod {GET,POST}
        public ExecuteResult Execute(RequestMethod method) throws Exception
        {
            ExecuteResult exeResult = null;
            try
            {
                switch(method)
                {
                    case GET:
                    {
                        //add parameters
                        String combinedParams = "";
                        if(!params.isEmpty()){
                            combinedParams += "?";
                            for(NameValuePair p : params)
                            {
                                String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");
                                if(combinedParams.length() > 1)
                                {
                                    combinedParams  +=  "&" + paramString;
                                }
                                else
                                {
                                    combinedParams += paramString;
                                }
                            }
                        }

                        HttpGet request = new HttpGet(url + combinedParams);

                        //add headers
                        for(NameValuePair h : headers)
                        {
                            request.addHeader(h.getName(), h.getValue());
                        }

                        exeResult = executeRequest(request, url);
                        break;
                    }
                    case POST:
                    {
                        HttpPost request = new HttpPost(url);

                        //add headers
                        for(NameValuePair h : headers)
                        {
                            request.addHeader(h.getName(), h.getValue());
                        }

                        if(!params.isEmpty()){
                            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        }

                        if(!jParams.isEmpty()){
                            for(int index=0; index < jParams.size(); index ++){
                                String strData = jParams.get(index).toString();

                                if(strData.contains("\r"))
                                    strData = strData.replace("\r", "\\u000d");
                                if(strData.contains("\n"))
                                    strData = strData.replace("\n", "\\u000a");

                                request.setEntity(new StringEntity(strData));

                            }
                        }

                        exeResult =  executeRequest(request, url);
                        break;
                    }
                }
            }
            catch (Exception e) {
                ////ApplicationLogging.Log(e);
            }

            return  exeResult;
        }
        private ExecuteResult executeRequest(HttpUriRequest request, String url)
        {

           HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 1500000000);
            HttpConnectionParams.setSoTimeout(httpParameters, 500000000);

            HttpClient client = new DefaultHttpClient(httpParameters);

            HttpResponse httpResponse;
            ExecuteResult exeResult = new ExecuteResult();

            try
            {
                // request.add_field("User-Agent", "My User Agent Dawg");
                httpResponse = client.execute(request);
                httpResponse.setHeader("Cache-Control", "no-cache, no-store");
                responseCode = httpResponse.getStatusLine().getStatusCode();
                exeResult.ResponseCode = responseCode;

                message = httpResponse.getStatusLine().getReasonPhrase();
                exeResult.Message = message;
                HttpEntity entity = httpResponse.getEntity();
                exeResult.ResponseMessage = EntityUtils.toString(entity);
            }
            catch (Exception e) {
                if(e.getMessage() == null || e.getMessage().equals(""))
                    exeResult.ErrorMessage = "TimeOut";//ApplicationLogging.getStackTrace(e);
                else
                    exeResult.ErrorMessage = e.getMessage();
                client.getConnectionManager().shutdown();
            }
            return exeResult;
        }
    }
