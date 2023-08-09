package heartratevisualizer.app;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.strava.api.v3.*;
// import com.strava.api.v3.auth.*;
// import com.strava.api.v3.model.*;
// import com.strava.api.v3.api.AthletesApi;

import javastrava.api.v3.*;
import javastrava.api.v3.auth.*;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.model.TokenResponse;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.*;
import javastrava.api.v3.model.reference.StravaActivityZoneType;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.model.reference.StravaStreamType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.AuthorisationAPI;
// import javastrava.api.v3.service.*;
// import javastrava.api.v3;
import javastrava.api.v3.service.Strava;
import javastrava.util.Paging;

// import observa

@RestController  // Sier til serveren at dette er controller

public class controller {

    String access_token;
    Token token;
    Strava strava;

    @PostMapping("/getJavaToken")
    public void getJavaToken(@RequestBody CodePair code) {
        System.out.println("----" + code.getCode() + " " + code.getScope()); // Fikk code!!!! // code.code??
        // CacheAccess<Object, Object> sessionCache = JCS.getInstance("bbSessionCache");

        //  Synchronous
        // PropertyResourceBundle b = new 
        AuthorisationService service = new AuthorisationServiceImpl();
        System.out.println("Dette er service: " + service);
        token = service.tokenExchange(110728, "f8a16daf2ca0c55cafea3da9fae8e3286fca07cd", code.getCode(), AuthorisationScope.VIEW_PRIVATE);
        System.out.println("HEOHEOHEHIHEIHEHEI");
        // TokenManager
        System.out.println("------.-.-.-.-.----"+token);
        strava = new Strava(token);
        System.out.println("------.-.-.-.-.--2--"+strava); //  START FROM HERE
        // StravaAthlete athlete = strava.getAthlete(id);
        // strava.listAuthenticatedAthleteActivities(null, null)
        // return "Hei";

        //  ASynchronous
        // AuthorisationAPI auth = API.authorisationInstance();
        // System.out.println("Dette er auth: " + auth);
        // TokenResponse response = auth.tokenExchange(110728, "f8a16daf2ca0c55cafea3da9fae8e3286fca07cd", code.getCode());
        // System.out.println("Dette er respone: " + response);
        // System.out.println("HER ER ACCESS!!! " + response.getAccessToken());
        // access_token = response.getAccessToken();
        // try {
        //     API api = new API(new Token(response));
        //     System.out.println("JOHOO " + api);
        // } catch (Exception e) {
        //     System.out.println("GIkk dååårlig");
        // }
        // Token token = new Token(response);
        // System.out.println("Dette er token: " + token);

    }

    @PostMapping("/getActivities")
    public List getActivities(@RequestBody ActivitySpecs specs) {
        // API api = new API(new );
        LocalDateTime endTime = LocalDateTime.ofEpochSecond(Long.valueOf(specs.endDate), 0, ZoneOffset.UTC);
        LocalDateTime startTime = LocalDateTime.ofEpochSecond(Long.valueOf(specs.startDate), 0, ZoneOffset.UTC);
        return strava.listAuthenticatedAthleteActivities(endTime, startTime, new Paging(1, 30));
    }

    @PostMapping("/getZoneFromActivity")
    public List getZoneFromActivity(@RequestBody IDnZones idnzones) {
        // API api = new API(new );
        System.out.println("DETTE ER ID: " + idnzones.getID());
        // strava.stream
        // System.out.println("---- Hei, test HR stream:" + id + " ---: " + strava.getActivityStreams(Long.valueOf(id), StravaStreamResolutionType.HIGH, StravaStreamSeriesDownsamplingType.TIME, StravaStreamType.HEARTRATE));
        // System.out.println("__" + strava.getActivityStreams(Long.valueOf(id), StravaStreamResolutionType.HIGH, StravaStreamSeriesDownsamplingType.TIME, StravaStreamType.HEARTRATE).get(0));
        // System.out.println("TEST HER ::" + id + "__ " + strava.getActivityStreams(Long.valueOf(id)).get(0) + " ----STOPPP:D");
        // List<Float> a = strava.getActivityStreams(Long.valueOf(id)).get(0);
        // System.out.println("LENGDE ____ " + id + ", " + a.get(0).getData().size());

        // if (idnzones.getzone1End() == -1){ 
        if (token.getAthlete().getPremium() == true && idnzones.getCustomZones() == false){
            System.out.println(":.:.: " + strava.listActivityZones(Long.valueOf(idnzones.getID())) + " :,:,;");
            return strava.listActivityZones(Long.valueOf(idnzones.getID()));  
        }
        else{
            List<StravaActivityZone> list = new ArrayList<>();
            Integer[] distribution = calculateDistribution(idnzones);
            list.add(createActivityZone(distribution));
            return list;
        }
        // System.out.println(":.:.: " + strava.listActivityZones(Long.valueOf(id)) + " :,:,;");
        // return strava.listActivityZones(Long.valueOf(id));  
    }

    public StravaActivityZone createActivityZone(Integer[] distribution) {
        StravaActivityZone a = new StravaActivityZone();
        a.setType(StravaActivityZoneType.HEARTRATE);
        List<StravaActivityZoneDistributionBucket> buckets = new ArrayList<StravaActivityZoneDistributionBucket>();
        StravaActivityZoneDistributionBucket bucket1 = new StravaActivityZoneDistributionBucket(); 
        bucket1.setMin(0);
        bucket1.setMax(130);
        bucket1.setTime(distribution[0]);
        buckets.add(bucket1);
        StravaActivityZoneDistributionBucket bucket2 = new StravaActivityZoneDistributionBucket(); 
        bucket2.setMin(130);
        bucket2.setMax(162);
        bucket2.setTime(distribution[1]);
        buckets.add(bucket2);
        StravaActivityZoneDistributionBucket bucket3 = new StravaActivityZoneDistributionBucket(); 
        bucket3.setMin(162);
        bucket3.setMax(178);
        bucket3.setTime(distribution[2]);
        buckets.add(bucket3);
        StravaActivityZoneDistributionBucket bucket4 = new StravaActivityZoneDistributionBucket(); 
        bucket4.setMin(178);
        bucket4.setMax(194);
        bucket4.setTime(distribution[3]);
        buckets.add(bucket4);
        StravaActivityZoneDistributionBucket bucket5 = new StravaActivityZoneDistributionBucket(); 
        bucket5.setMin(194);
        bucket5.setMax(-1);
        bucket5.setTime(distribution[4]);
        buckets.add(bucket5);
        a.setDistributionBuckets(buckets);
        return a;
    }

    public Integer[] calculateDistribution(IDnZones idnzones) {
        String id = idnzones.getID();
        List<StravaStream> activityStream = strava.getActivityStreams(Long.valueOf(id));
        int riktigIndeks = 6;
        for (int i = 0; i < activityStream.size(); i++) {
            if (activityStream.get(i).getType().toString().equals("heartrate")){
                System.out.println("RIKTIG INDEKS ER: " + i);
                riktigIndeks = i;
                break;
            }
            else{
                System.out.println(String.valueOf(activityStream.get(i).getType()));
            }
        }
        // StravaStream a = strava.getActivityStreams(Long.valueOf("9455718366")).get(6); // BYTT INDEX TIL FINNER HEARTRATE. TROR DET SKAL VÆRE 13504 punkter, altså fullstendig.
        StravaStream a = activityStream.get(riktigIndeks);
        // StravaStream a = strava.getActivityStreams(Long.valueOf(id)).get(6); // BYTT INDEX TIL FINNER HEARTRATE. TROR DET SKAL VÆRE 13504 punkter, altså fullstendig.
        // System.out.println("HEI " + a.getData() + "FERDIG A");
        // System.out.println(".." + a.getData().get(0) + " + " + (Float.valueOf(a.getData().get(0))+3));
        // System.out.println(Float.valueOf(a.getData().get(0))+3);
        List<Float> stream = a.getData();
        int zone1 = 0;
        int zone2 = 0;
        int zone3 = 0;
        int zone4 = 0;
        int zone5 = 0;
        for (int i = 0; i < stream.size(); i++) {
            if (stream.get(i) <= idnzones.getzone1End()) {
                zone1++;
            }
            else if (stream.get(i) > idnzones.getzone1End() && stream.get(i) <= idnzones.getzone2End()) {
                zone2++;
            }
            else if (stream.get(i) > idnzones.getzone2End() && stream.get(i) <= idnzones.getzone3End()) {
                zone3++;
            }
            else if (stream.get(i) > idnzones.getzone3End() && stream.get(i) <= idnzones.getzone4End()) {
                zone4++;
            }
            else if (stream.get(i) > idnzones.getzone4End()) {
                zone5++;
            }
        }
        System.out.println(" 1: " + zone1 + " 2: " + zone2 + " 3: " + zone3 + " 4: " + zone4 + " 5: " + zone5);
        Integer[] bb = {zone1, zone2, zone3, zone4, zone5};
        return bb;
    }

    @PostMapping("/testCurl")
    public void testCurl() {
        System.out.println("STARTE HER");   //  9389417647
        // return strava.getAthlete(token.getAthlete().getId()).
        // System.out.println("TEST HER ::" + "9389417647" + "__ " + strava.getActivityStreams(Long.valueOf("9389417647")).get(5).getData().size() + " ----STOPPPP");
        // strava.getActivityStreams(Long.valueOf("9455718366")).indexOf("heartrate");
        List<StravaStream> activityStream = strava.getActivityStreams(Long.valueOf("9455718366"));
        System.out.println("HAILOSANN " + activityStream);
        int riktigIndeks = 6;
        for (int i = 0; i < activityStream.size(); i++) {
            System.out.println("----" + activityStream.get(i).getType().toString());
            if (activityStream.get(i).getType().toString().equals("heartrate")){
                System.out.println("RIKTIG INDEKS ER: " + i);
                riktigIndeks = i;
                break;
            }
            else{
                System.out.println(String.valueOf(activityStream.get(i).getType()));
            }
        }
        // StravaStream a = strava.getActivityStreams(Long.valueOf("9455718366")).get(6); // BYTT INDEX TIL FINNER HEARTRATE. TROR DET SKAL VÆRE 13504 punkter, altså fullstendig.
        StravaStream a = activityStream.get(riktigIndeks);
        System.out.println("HEI " + a.getData() + "FERDIG A");
        System.out.println(".." + a.getData().get(0) + " + " + (Float.valueOf(a.getData().get(0))+3));
        System.out.println(Float.valueOf(a.getData().get(0))+3);
        List<Float> stream = a.getData();
        int zone1 = 0;
        int zone2 = 0;
        int zone3 = 0;
        int zone4 = 0;
        int zone5 = 0;
        for (int i = 0; i < stream.size(); i++) {
            if (stream.get(i) <= 130) {
                zone1++;
            }
            else if (stream.get(i) > 130 && stream.get(i) <= 162) {
                zone2++;
            }
            else if (stream.get(i) > 162 && stream.get(i) <= 178) {
                zone3++;
            }
            else if (stream.get(i) > 178 && stream.get(i) <= 194) {
                zone4++;
            }
            else if (stream.get(i) > 194) {
                zone5++;
            }
        }
        System.out.println(" 1: " + zone1 + " 2: " + zone2 + " 3: " + zone3 + " 4: " + zone4 + " 5: " + zone5);
        Integer[] bb = {1, 2, 3};
        // List<Float> a = strava.getActivityStreams(Long.valueOf(id)).get(0);
        // System.out.println("LENGDE ____ " + id + ", " + a.get(0).getData().size());
        // strava.listActivityZones(Long.valueOf("9389417647"));  
        System.out.println("SLUTTE HER");
    }


    @GetMapping("/helloo")     //  http://localhost:8080/helloo
    public String helloo() {

        return "Hello bolla;)";
    }

    @GetMapping("/error")
    public String error() {

        return "Hva prøver du på nå??";
    }

    @GetMapping("/navn")
    public String navn(String name) {
        return "Halla " + name;
    }

    @GetMapping("/getStudent")
    public String getStudent() {
        return "Dette er en student: Einar";
    }
    
    @PostMapping("/setStudent")
    public String setStudent(@RequestBody String student) {  //  Bruker PostMan. Skriver noe i body, denne funksjonen henter det.
        System.out.println(student);
        return "Hei " + student + ", Object received.";
    }

    // @GetMapping("/testCURL")
    // public String testCURL() {
    //     return "Dette er ein test:D";
    // }
}
