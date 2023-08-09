var customersAccessToken;
var customersRefreshToken;
var customersExpiresAt;
console.log("Starting anew")


function visMelding(navn) {
    document.write("Hallo verden for: " + navn);
}

function testCURL() {
    console.log("HeI")
    // $.get("/getJavaToken", function(data) {
    //     console.log("OLOO");
    //     console.log(data);
    // });
    const packet = {
        code: "Bollemann",
        scope: "Jolla"
    };
    $.ajax({type: "POST", 
            url: "/testCurl",
            data: JSON.stringify(packet),
            dataType: "json",
            contentType: "application/json; charset=utf-8"
    }).done(function(data){
        console.log(data);
        console.log("OLOO");
    });
    // $.ajax({type: "GET", 
    //         url: "https://www.strava.com/api/v3/athlete",
    //         headers: {"Authorization": "Bearer 5e7bc99363ea0096592121a03db59214dafd795f"}
    // }).done(function(data){
    //     console.log(data);
    //     console.log("HALLA");
    // });
};       

function CURLAuth() {
    console.log("22")
    $.ajax({type: "GET", 
            url: "http://www.strava.com/oauth/authorize?client_id=110728&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=read"
    }).done(function(data){
        console.log(data);
        console.log("HALLA");
    });
};   

function getTokens(authCode, scope) {
    // console.log("Dette er fra mainJS");

    const packet = {
        code: authCode,
        scope: scope
    };
    $.ajax({type: "POST", 
            url: "/getJavaToken",
            data: JSON.stringify(packet),
            dataType: "json",
            contentType: "application/json; charset=utf-8"
    }).done(function(data){
        // console.log(data);
        // console.log("OLOO");
    });

    // $.ajax({type: "POST", 
    // url: "https://www.strava.com/oauth/token",
    // data: {client_id: 110728, client_secret: "f8a16daf2ca0c55cafea3da9fae8e3286fca07cd", code: authCode, grant_type: "authorization_code"}
    // }).done(function(data){
    // console.log(data);
    // // customersAccessToken = data.access_token;
    // // customersRefreshToken = data.refresh_token;
    // // customersExpiresAt = data.expires_at;
    // document.cookie = "customersAccessToken="+data.access_token+";";
    // document.cookie = "customersRefreshToken="+data.refresh_token+";";
    // document.cookie = "customersExpiresAt="+data.expires_at+";";
    // console.log(customersAccessToken);
    // console.log(document.cookie);
    // console.log("halla33");
    // });
};

function nextFun() {
    location.href = 'http://www.heartratevisualizer.com/stats.html';
};

function showStart() {
    $("#idNamesOSV").html(getCookie("customersAccessToken") + "<br>" + getCookie("customersRefreshToken") + "<br>" + getCookie("customersExpiresAt"));
}

function listAct2() {
    // data: {before: , after: , per_page: 5},
    console.log("Vi prøver å liste alle aktiviteter")
    $.ajax({type: "GET", 
            url: "https://www.strava.com/api/v3/athlete/activities",
            data: {per_page: 5, "access_token": getCookie("customersAccessToken")},
            // headers: {"Authorization": getCookie("customersAccessToken")}
    }).done(function(data){
        console.log(data);
        console.log("HALLA3");
    });
};

function listAct() {
    // data: {before: , after: , per_page: 5},
    // console.log("Vi prøver å liste alle aktiviteter")
    var startDate = new Date($('#startDateSelectorID').val());
    // console.log("EKS DATO: " + startDate);
    startDate = Math.round(startDate.getTime() / 1000);
    var endDate = new Date($('#endDateSelectorID').val());
    endDate = Math.round(endDate.getTime() / 1000);

    // $.ajax({type: "GET", 
    //         url: "https://www.strava.com/api/v3/athlete/activities",
    //         data: {before: endDate, after: startDate,per_page: 30, "access_token": getCookie("customersAccessToken")},
    //         // headers: {"Authorization": getCookie("customersAccessToken")}
    // }).done(function(data){
    //     console.log(data);
    //     $("#actsID").html(data);
    //     formaterActs(data);
    //     console.log("HALLA3");
    // });

    const listActsPacket = {
        endDate: endDate,
        startDate: startDate,
        perPage: 30
    };

    $.ajax({type: "POST", 
            url: "/getActivities",
            data: JSON.stringify(listActsPacket),
            dataType: "json",
            contentType: "application/json; charset=utf-8"
    }).done(function(data){
        // console.log(data);
        // console.log("HALOEN?!");
        formaterActs(data);
    });
};

var customZones = false;
function customZonesFun() {
    customZones = true;
    // console.log("Hei")
    listAct();
}

var timeInZone = 0;
var totalTimeZone1 = 0;
var totalTimeZone2 = 0;
var totalTimeZone3 = 0;
var totalTimeZone4 = 0;
var totalTimeZone5 = 0;
function formaterActs(acts) {
    totalTimeZone1 = 0;
    totalTimeZone2 = 0;
    totalTimeZone3 = 0;
    totalTimeZone4 = 0;
    totalTimeZone5 = 0;
    let table = "<table id='table' class='table table-striped table-bordered'><tr><th>ID</th><th>Navn</th><th>Dato</th><th>Zones</th><th>Type</th></tr>";
    var tempLength = 0;
    for (activity of acts){
        tempLength+=1;
    };
    // console.log("-----Lengde: "+tempLength);
    // for (activity of acts){
    //     console.log(activity.name);
    // }
    const length = tempLength;
    tempLength = 0;
    var dict = {};
    for (activity of acts){
        dict[activity.id] = activity;
        tempLength+=1;
        let tempID = activity.id;
        var IDnZones = {
            id: tempID,
            customZones: customZones,
            zone1End: -1,
            zone2End: -1,
            zone3End: -1,
            zone4End: -1,
            zone5End: -1
        };
        if (customZones == true){
            IDnZones = {
                id: tempID,
                customZones: customZones,
                zone1End: $("#zone1input").val(),
                zone2End: $("#zone2input").val(),
                zone3End: $("#zone3input").val(),
                zone4End: $("#zone4input").val(),
                zone5End: $("#zone5input").val()
            };
            // console.log("TESTSONER");
            // console.log(IDnZones);
        }
         
        // console.log(IDnZones);
        
        timeInZone = 1;
        // console.log("START");
        // $.ajax({type: "POST", 
        //     url: "/getZoneFromActivity",
        //     data: JSON.stringify(tempID),
        //     dataType: "json",
        //     contentType: "application/json; charset=utf-8"
        // }).done(function(data){
        //     console.log("Yo zones:::")
        //     console.log(data);
        // });
        $.ajax({type: "POST", 
            url: "/getZoneFromActivity",
            data: JSON.stringify(IDnZones),
            dataType: "json",
            contentType: "application/json; charset=utf-8"
        }).done(function(data){
            // console.log(1111);
            customZones = false; //  For at premium folk skal kunne trykke øverste knapp.
            // console.log(length);
            // console.log(tempID);
            // console.log(data[0]);
            // console.log(data[0].distributionBuckets);
            // console.log(data[0].distributionBuckets[0]);
            // console.log(data[0].distributionBuckets[0].time);
            totalTimeZone1 += data[0].distributionBuckets[0].time;
            totalTimeZone2 += data[0].distributionBuckets[1].time;
            totalTimeZone3 += data[0].distributionBuckets[2].time;
            totalTimeZone4 += data[0].distributionBuckets[3].time; 
            totalTimeZone5 += data[0].distributionBuckets[4].time;
            timeInZone = data[0].distributionBuckets[0].time + ", "
                       + data[0].distributionBuckets[1].time + ", "
                       + data[0].distributionBuckets[2].time + ", "
                       + data[0].distributionBuckets[3].time + ", "
                       + data[0].distributionBuckets[4].time;
            // stupidTimeInZone(timeInZone);
            // console.log("HALLAballa" + timeInZone);
            table += "<tr><td>"+ dict[tempID].id +"</td>";
            table += "<td>"+ dict[tempID].name +"</td>";
            table += "<td>"+ dict[tempID].startDate +"</td>";
            table += "<td>"+ timeInZone +"</td>";
            table += "<td>"+ dict[tempID].type +"</td></tr>";
            if (tempLength >= length){
                table += "</table>";
                $("#table").html(table);
                $("#totalsID").html(totalTimeZone1 + "<br>" +
                                    totalTimeZone2 + "<br>" +
                                    totalTimeZone3 + "<br>" +
                                    totalTimeZone4 + "<br>" +
                                    totalTimeZone5);
                plotPie();
                plotPie8020();
                // $("#8020CheckBoxesFieldset").hidden = false;
                document.getElementById("8020CheckBoxesFieldset").hidden = false;
                document.getElementById("zoneInputID").hidden = false;
                // $("#listActID").hidden = "true";
            };
        });
        // console.log("STOP");
        // table += "<tr><td>"+ activity.id +"</td>";
        // table += "<td>"+ activity.name +"</td>";
        // table += "<td>"+ timeInZone +"</td>";
        // table += "<td>"+ activity.type +"</td></tr>";
    }
    // table += "</table>";
    // $("#table").html(table);
};

function testCookie() {
    document.cookie = "customersAccessToken="+"11aa11"+";";
    document.cookie = "customersRefreshToken="+"22bb22"+";";
    document.cookie = "customersExpiresAt="+33333+";";
    console.log(document.cookie);
};

function getCookie(cookieName) {
    var key = cookieName + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    console.log(document.cookie);
    console.log(decodedCookie);
    console.log(ca);
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i].split("=");
        if (c[0].trim() == cookieName){
            console.log(c[1]);
            return c[1];
        }
      }
    console.log("DONE");
};

function stupidTimeInZone(a) {
    timeInZone = a;
}

function testGetHRZ() {
    $.ajax({type: "GET", 
            url: "https://www.strava.com/api/v3/activities/9468788432/zones",
            headers: {"Authorization": "Bearer "+getCookie("customersAccessToken")}
    }).done(function(data){
        console.log(data);
        console.log("HALLA");
    });
};

function plotPie(){
    const xArray = [totalTimeZone1, totalTimeZone2, totalTimeZone3, totalTimeZone4, totalTimeZone5];
    const yArray = ["Zone1","Zone2","Zone3","Zone4","Zone5"];
    // console.log("Array"+xArray);

    const data = [{
        values: xArray,
        labels: yArray,
        marker: {colors: [
            'rgb(242, 152, 152)',  
            'rgb(220, 113, 113)',
            'rgb(193, 84, 84)',
            'rgb(175, 68, 68)',
            'rgb(134, 43, 43)'
        ]},
        type: "pie"
        }];

        const layout = {title:"Zone distribution", paper_bgcolor: "rgba(33,37,41,1)", font: {
            family: "Courier New, monospace",
            size: 18,
            color: "#c15614"
            }};//, font: "rgb(193, 86, 20);"

        Plotly.newPlot("plotDIV", data, layout);
};

function plotPie8020(){
    var lowIntensity = 0;
    var highIntensity = 0;
    // for (let index = 1; index < 6; index++) {
    //     if ($('#zone'+index+'Box').is(":checked")){
    //         lowIntensity += totalTimeZone;
    //     }
    // }
    if ($('#zone1Box').is(":checked")){
        lowIntensity += totalTimeZone1;
        // console.log("CHECKED 111")
    }
    else{
        highIntensity += totalTimeZone1;
    }
    if ($('#zone2Box').is(":checked")){
        lowIntensity += totalTimeZone2;
    }
    else{
        highIntensity += totalTimeZone2;
    }
    if ($('#zone3Box').is(":checked")){
        lowIntensity += totalTimeZone3;
    }
    else{
        highIntensity += totalTimeZone3;
    }
    if ($('#zone4Box').is(":checked")){
        lowIntensity += totalTimeZone4;
    }
    else{
        highIntensity += totalTimeZone4;
    }
    if ($('#zone5Box').is(":checked")){
        lowIntensity += totalTimeZone5;
    }
    else{
        highIntensity += totalTimeZone5;
    }
    // const xArray2 = [totalTimeZone1 + totalTimeZone2, totalTimeZone3 + totalTimeZone4 + totalTimeZone5];
    const xArray2 = [lowIntensity, highIntensity];
    const yArray2 = ["Low intensity","High intensity"];
    // console.log("Array"+xArray2);

    const data = [{
        values: xArray2,
        labels: yArray2,
        type: "pie"
        }];

        const layout = {title:"Intensity distribution",paper_bgcolor: "rgba(33,37,41,1)", font: {
            family: "Courier New, monospace",
            size: 18,
            color: "#c15614"
            }};

        Plotly.newPlot("plot9020DIV", data, layout);
};