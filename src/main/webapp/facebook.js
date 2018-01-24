$(function () {
    console.log("location.hash=" + location.hash);

    var $signInButton = $('#facebookLoginButton');
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

    function  onClickLogInRedirect() {
        console.log(">onClickLogInRedirect");
        // you can also generate your own state parameter and use it with your login request to provide CSRF protection.
        var url = "https://www.facebook.com/v2.11/dialog/oauth?client_id=" + appId + "&redirect_uri=http://localhost:8080/test/&response_type=token&scope=email";
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
});


















