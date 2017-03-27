package com.king.baseproject.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.king.baseproject.R;
import com.king.baseproject.network.HttpUtil;
import com.king.baseproject.network.RxHelper;
import com.king.baseproject.network.RxSubscriber;
import com.king.baseproject.util.ActivityJump;
import com.king.baseproject.util.MYToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.RequestBody;

/**
 * Created by king on 2017/1/11.
 */
public class MainActivity extends BaseActivity {
    private TextView text;
    private Button btn_testnet;
    private Button btn_sign;

    private Button btn_map;

    private Button btn_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private LocationManager locationManager;
    private String locationProvider;

    private void getLoacation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if(providers.contains(LocationManager.GPS_PROVIDER)){
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return ;
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location!=null){
            //不为空,显示地理位置经纬度
            Log.e("Tag","----"+location.getLongitude());
            btn_location.setText(location.getLongitude()+"--"+location.getLatitude());
        }else {
            Log.e("Tag","----");
            MYToast.show("kong");
        }
    }

    private void initView() {
        setTitle("mainActivity");
        setRightText("right");
        setRightTextColor(R.color.black);
        btn_testnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ;
                    }
                });
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                  StatsUtil.setBtnSessuon(MainActivity.this);
                ActivityJump.JumpActivity(MainActivity.this, MapActivity.class);
            }
        });
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoacation();
            }
        });
    }

    /**
     * 电子签名
     */
//    private void Sign() {
//        String UserName = "[USERNAME]";
//        String Password = "[PASSWORD]";
//        String IntegratorKey = "[INTEGRATOR_KEY]";
//        String SignTest1File = "[PATH/TO/DOCUMENT/TEST.PDF]";
//        String recipientName = "[RECIPIENT_NAME]";
//        String recipientEmail = "[RECIPIENT_EMAIL]";
//        String BaseUrl = "https://demo.docusign.net/restapi";
//        ApiClient apiClient = new ApiClient();
//        apiClient.setBasePath(BaseUrl);
//
//        String creds = "{\"Username\":\"" +  UserName + "\",\"Password\":\"" +  Password + "\",\"IntegratorKey\":\"" +  IntegratorKey + "\"}";
//        apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);
//
//        Configuration.setDefaultApiClient(apiClient);
//        List<LoginAccount> loginAccounts = null;
//        try
//        {
//            // login call available off the AuthenticationApi
//            AuthenticationApi authApi = new AuthenticationApi();
//
//            // login has some optional parameters we can set
//            AuthenticationApi.LoginOptions loginOps = authApi.new LoginOptions();
//            loginOps.setApiPassword("true");
//            loginOps.setIncludeAccountIdGuid("true");
//            LoginInformation loginInfo = authApi.login(loginOps);
//
//            // note that a given user may be a member of multiple accounts
//           loginAccounts = loginInfo.getLoginAccounts();
//
//            MYToast.show("loginAccounts="+loginAccounts);
//            System.out.println("LoginInformation: " + loginAccounts);
//        }
//        catch (com.docusign.esign.client.ApiException ex)
//        {
//            MYToast.show("ex="+ex);
//            System.out.println("Exception: " + ex);
//        }
//
//// create a byte array that will hold our document bytes
//        byte[] fileBytes = null;
//
//        MYToast.show("currentDir=="+System.getProperty("user.dir"));
//        try
//        {
//            String currentDir = System.getProperty("user.dir");
//            // read file from a local directory
//            Path path = Paths.get(currentDir + SignTest1File);
//            fileBytes = Files.readAllBytes(path);
//        }
//        catch (IOException ioExcp)
//        {
//            // handle error
//            System.out.println("Exception: " + ioExcp);
//            return;
//        }
//
//        // create an envelope that will store the document(s), tabs(s), and recipient(s)
//        EnvelopeDefinition envDef = new EnvelopeDefinition();
//        envDef.setEmailSubject("[Java SDK] - Please sign this doc");
//
//        // add a document to the envelope
//        Document doc = new Document();
//        String base64Doc = Base64.getEncoder().encodeToString(fileBytes);
//        doc.setDocumentBase64(base64Doc);
//        doc.setName("TestFile.pdf");    // can be different from actual file name
//        doc.setDocumentId("1");
//
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(doc);
//        envDef.setDocuments(docs);
//
//        // add a recipient to sign the document, identified by name and email we used above
//        Signer signer = new Signer();
//        signer.setName(recipientName);
//        signer.setEmail(recipientEmail);
//        signer.setRecipientId("1");
//
//        // to embed the recipient you must set their |clientUserId| property!
//        signer.setClientUserId("1234");
//
//        // create a signHere tab somewhere on the document for the signer to sign
//        // default unit of measurement is pixels, can be mms, cms, inches also
//        SignHere signHere = new SignHere();
//        signHere.setDocumentId("1");
//        signHere.setPageNumber("1");
//        signHere.setRecipientId("1");
//        signHere.setXPosition("100");
//        signHere.setYPosition("150");
//
//        // can have multiple tabs, so need to add to envelope as a single element list
//        List<SignHere> signHereTabs = new ArrayList<SignHere>();
//        signHereTabs.add(signHere);
//        Tabs tabs = new Tabs();
//        tabs.setSignHereTabs(signHereTabs);
//        signer.setTabs(tabs);
//
//        // add recipients (in this case a single signer) to the envelope
//        envDef.setRecipients(new Recipients());
//        envDef.getRecipients().setSigners(new ArrayList<Signer>());
//        envDef.getRecipients().getSigners().add(signer);
//
//        // send the envelope by setting |status| to "sent". To save as a draft set to "created"
//        envDef.setStatus("sent");
//
//        // accountId is needed to create the envelope and for requesting the signer view
//        String accountId = null;
//        String envelopeId = null;
//
//        try
//        {
//            // use the |accountId| we retrieved through the Login API to create the Envelope
//            accountId = loginAccounts.get(0).getAccountId();
//
//            // instantiate a new EnvelopesApi object
//            EnvelopesApi envelopesApi = new EnvelopesApi();
//
//            // call the createEnvelope() API to send the signature request!
//            EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
//
//            // save the |envelopeId| that was generated and use in next API call
//            envelopeId = envelopeSummary.getEnvelopeId();
//
//            System.out.println("EnvelopeSummary: " + envelopeSummary);
//        }
//        catch (com.docusign.esign.client.ApiException ex)
//        {
//            System.out.println("Exception: " + ex);
//        }
//
//        // use the |accountId| we retrieved through the Login API and the |envelopeId| that was generated during envelope creation
//        accountId = loginAccounts.get(0).getAccountId();
//
//        // instantiate a new EnvelopesApi object
//        EnvelopesApi envelopesApi = new EnvelopesApi();
//
//        // set the url where you want the recipient to go once they are done signing
//        RecipientViewRequest returnUrl = new RecipientViewRequest();
//        returnUrl.setReturnUrl("https://www.docusign.com/devcenter");
//        returnUrl.setAuthenticationMethod("email");
//
//        // recipient information must match embedded recipient info we provided in step #2
//        returnUrl.setUserName(recipientName);
//        returnUrl.setEmail(recipientEmail);
//        returnUrl.setRecipientId("1");
//        returnUrl.setClientUserId("1234");
//
//        try
//        {
//            // call the CreateRecipientView API then navigate to the URL to start the signing session
//            ViewUrl recipientView = envelopesApi.createRecipientView(accountId, envelopeId, returnUrl);
//
//            System.out.println("ViewUrl: " + recipientView);
//        }
//        catch (com.docusign.esign.client.ApiException ex)
//        {
//            System.out.println("Exception: " + ex);
//        }
//
//    } // end main()
    public void getData() {
        Map<String, Object> head = new HashMap<String, Object>();
        head.put("aid", "1and6uu");
        head.put("ver", "1.0");
        head.put("ln", "cn");
        head.put("mod", "android");
        head.put("de", "2013-11-11 00:00:00");
        head.put("sync", "1");
        head.put("uuid", 0); // TODO
        head.put("chcode", "");
        head.put("cmd", 10003);
        Map<String, Object> con = new HashMap<String, Object>();
        con.put("account", "1234567");
        con.put("password", com.uns.util.MD5.MD52String("123456"));
        JSONObject json = new JSONObject();
        json.put("head", new JSONObject(head));
        json.put("con", new JSONObject(con));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toJSONString());
        HttpUtil.getRequest().queryto(body).compose(new RxHelper<Object>().io_main(MainActivity.this))
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void _onNext(Object object) {
                        MYToast.show("chenggong" + object.toString());
                        Log.e("Tag", object.toString());
                    }

                    @Override
                    public void _onError(String msg) {
                        MYToast.show("shibai" + msg);
                    }
                });
    }

    /**
     * 请求网络
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);//[0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

//    private void getData() {
//        Map<String,Object> map = new HashMap<String,Object>();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("account", "1234567");
//        jsonObject.put("password",  com.uns.util.MD5.MD52String("123456"));
//        jsonObject.put("type", 1);
//        String character = getRandomString(32).toLowerCase();
//        long timecurrentTimeMillis = System.currentTimeMillis();
//        String key = "aea9efb088aa91283767b7fd5d95f01d";
//        String askToken = "";
//        String sign = ""+ "0fbf8729dfc96b028084f573ea934298" + timecurrentTimeMillis + character + key+askToken;
//        String md5sign = "";
//        try {
//            md5sign = MD5.MD5Encode(sign);
//        } catch (NoSuchAlgorithmException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        map.put("body",jsonObject.toJSONString());
//        map.put("appid","0fbf8729dfc96b028084f573ea934298");
//        map.put("timeStamp",timecurrentTimeMillis+"");
//        map.put("character",character);
//        map.put("sign",md5sign);
//        map.put("askToken",askToken);
//        HttpUtil.getRequest().query("enterprise","login",map).compose(new RxHelper<Object>().io_main(MainActivity.this))
//                .subscribe(new RxSubscriber<Object>() {
//                    @Override
//                    public void _onNext(Object object) {
//                        MYToast.show("chenggong"+object.toString());
//                        Log.e("Tag",object.toString());
//                    }
//                    @Override
//                    public void _onError(String msg) {
//                        MYToast.show("shibai"+msg);
//                    }
//                });
//
//    }

    @Override
    public void RightOnclick() {
        super.RightOnclick();
        Toast.makeText(MainActivity.this, "sadasd", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param path 文件路径
     * @return
     */
    public static byte[] getByteArrayFrom(String path){

        byte[] result=null;

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        //创建文件
        File file=new File(path);
        FileInputStream fileInputStream=null;
        try {
            fileInputStream=new FileInputStream(file);
            int len=0;
            byte[] buffer=new byte[1024];
            while((len=fileInputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, len);
            }
            result=outputStream.toByteArray();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                    fileInputStream=null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }


        return result;
    }

}
