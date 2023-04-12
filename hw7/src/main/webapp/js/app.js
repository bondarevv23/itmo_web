window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.check_error = function (response, $error) {
    if (response["error"]) {
        $error.text(response["error"]);
        return true;
    }
    return false;
}

window.ajax = function (data, successFun, uri = "") {
    $.ajax({
        type: "POST",
        url: uri,
        dataType: "json",
        data: data,
        success: function (response) {
            if (response["redirect"]) {
                location.href = response["redirect"];
                return;
            }
            successFun(response);
        }
    });
}
