function maskUnmask() {
    var x = document.getElementById("password");
    if (x.type === "password")
        x.type = "text";
    else
        x.type = "password";
}

function reset() {
    var x = document.getElementById("password");
    x.type = "password";
}