$(function () {
    console.log("readyFB");
   
    console.log("location.hash=" + location.hash);

    var $signInButton = $('#facebookLogInRedirect');
    var $signOutButton = $('#facebookLogOutRedirectButton');
    var $authenticatedControls = $('div.authenticated');
    var $userEmailSpan = $authenticatedControls.find(".userEmail");
    var appId = $('input#appId').val();
    $signInButton.click(onClickLogInRedirect);
    $signOutButton.click(onClickLogOutRedirect);


    checkHash();

    function checkHash() {
        console.log(">checkHash");
        var response = checkAndProcessHash();
        if (response) {
            signIntoBackEnd(response.access_token);
        }
    }
    function checkAndProcessHash() {
        console.log(">checkAndProcessHash");
        var hash = location.hash;
        if (!hash)
            return;
        hash = hash.substring(1);
        var hashValues = {};
        var params = hash.split('&');
        params.forEach(function (val) {
            var pair = val.split('=');
            hashValues[pair[0]] = pair[1];
        })
        console.log("<checkHash: " + JSON.stringify(hashValues));
        return hashValues;
    }
    // in general if you're building a web app, it is best to add the token as a session variable to identify that browser session with a particular person
    function signIntoBackEnd(accesToken) {
        console.log(">signIntoBackEnd: " + accesToken);
        $.ajax({
            method: "POST",
            url: "api/test",
            data: accesToken,
            dataType: "json"
        })
                .done(function (data) {
                    console.log(">received data from server: " + JSON.stringify(data));
                    onSignIntoBackEnd(data.email);
                }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log("error: " + textStatus);
        });
    }
    function fbAsyncInit() {
        console.log(">fbAsyncInit");
        FB.init({
            appId: appId,
            cookie: true, // enable cookies to allow the server to access 
            // the session
            xfbml: true, // parse social plugins on this page
            version: 'v2.8' // use graph api version 2.8
        });

        // Now that we've initialized the JavaScript SDK, we call 
        // FB.getLoginStatus().  This function gets the state of the
        // person visiting this page and can return one of three states to
        // the callback you provide.  They can be:
        //
        // 1. Logged into your app ('connected')
        // 2. Logged into Facebook, but not your app ('not_authorized')
        // 3. Not logged into Facebook and can't tell if they are logged into
        //    your app or not.
        //
        // These three cases are handled in the callback function.

        checkLoginState(); // Knowing which of the these three states the user is in is one of the first things your application needs to know on page load.
    }

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
    function checkLoginState() {
        FB.getLoginStatus(function (response) {
            statusChangeCallback(response);
        }, true); //  true to force a roundtrip to Facebook - effectively refreshing the cache of the response object.
    }

    // This is called with the results from from FB.getLoginStatus().
    function statusChangeCallback(response) {
        // The response object is returned with a status field that lets the
        // app know the current login status of the person.
        // Full docs on the response object can be found in the documentation
        // for FB.getLoginStatus().
        console.log('>statusChangeCallback response=' + JSON.stringify(response));
        if (response.status === 'connected') {

            // the user is logged in and has authenticated your
            // app, and response.authResponse supplies
            // the user's ID, a valid access token, a signed
            // request, and the time the access token 
            // and signed request each expire
            var uid = response.authResponse.userID;
            var accessToken = response.authResponse.accessToken;
            testAPI();
        } else if (response.status === 'not_authorized') {
            // the user is logged in to Facebook, 
            // but has not authenticated your app
        } else {
            // the user isn't logged in to Facebook.
        }

    }
    function  logOut() {
        console.log(">logOut");
        FB.logout(function (response) {
            // Person is now logged out
            console.log(">loggedOut");
        });
    }
// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.
    function testAPI() {
        console.log('>testAPI');
        FB.api('/me?fields=email', function (response) {
            console.log(JSON.stringify(response));
            $('#status').text('Thanks for logging in, ' + response.email);
        });
    }

    function  logIn() {
        console.log(">logIn");
        FB.login(function (response) { // Calling FB.login() results in the JS SDK attempting to open a popup window. 
            // Handle the response object, like in statusChangeCallback() in our demo
            // code.
            console.log(">logIn returned");
            statusChangeCallback(response);
        }, {scope: 'email'});
    }

    function  onClickLogInRedirect() {
        console.log(">onClickLogInRedirect");
        // you can also generate your own state parameter and use it with your login request to provide CSRF protection.
        var url = "https://www.facebook.com/v2.11/dialog/oauth?client_id=" + appId + "&redirect_uri=http://localhost:8080/test/&state={st=state123abc,ds=123456789}&&response_type=token&scope=email";
        console.log(">logInRedirect url=" + url);
        window.location.assign(url);
    }

    function onClickLogOutRedirect() {
        console.log('>onClickLogOutRedirect');
        //You can log people out of your app by undoing whatever login status indicator you added, for example deleting the session that indicates a person is logged in.
        $.ajax({
            method: "POST",
            url: "api/test/logout",
        })
                .done(function (data) {
                    console.log(">received data from server: " + JSON.stringify(data));
                    showSignedOutUserControls();
                }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log("error: " + textStatus);
        });
    }

    function showSignedInUserControls(email) {
        console.log('>showSignedInUserControls: email=' + email);
        $signInButton.hide();
        $userEmailSpan.text(email);
        $authenticatedControls.show();
    }
    function onSignIntoBackEnd(email) {
        console.log('>onSignIntoBackEnd: email=' + email);
        showSignedInUserControls(email);
    }

    function showSignedOutUserControls() {
        console.log('>showSignedOutUserControls');
        $signInButton.show();
        $authenticatedControls.hide();
    }

    fbAsyncInit();
    $('#facebookLogOutButton').click(logOut);
    $('div.facebookLoginButton').click(logIn);

});

 
















