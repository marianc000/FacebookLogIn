$(function () {
    console.log("readyFB");
    function fbAsyncInit() {
        console.log(">fbAsyncInit");
        FB.init({
            appId: '407938939637716',
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
        });
    }
    fbAsyncInit();
    $('#facebookLogOutButton').click(logOut);
    $('div.facebookLoginButton').click(logIn);
});











