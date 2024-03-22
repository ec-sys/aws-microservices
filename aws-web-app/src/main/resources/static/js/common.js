function Util () {}

Util.isUndefined = function (obj) {
    if(obj == undefined || obj == null) return  true;
    return false;
};

Util.trim = function (strObj) {
    if(Util.isUndefined(strObj)) return "";
    return strObj.trim();
};

Util.getAPIUrl = function (pathName) {
    return CONST_API_SERVER + pathName;
};

Util.getUaaAPIUrl = function (pathName) {
    return CONST_API_SERVER + "api/uaa/" + pathName;
};

Util.showLoading = function () {
}

Util.hideLoading = function () {
}