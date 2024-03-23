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

Util.getLoginAuth = function () {
    let data = localStorage.getItem(CONST_LOGIN_STORAGE);
    if(!Util.isUndefined(data)) {
        let login = JSON.parse(data);
        return {
            userId: login.userId,
            idToken: login.token.idToken,
            accessToken: login.token.accessToken
        }
    }
    return undefined;
}
Util.getUserProfile = function () {
    let data = localStorage.getItem(CONST_LOGIN_STORAGE);
    if(!Util.isUndefined(data)) {
        let login = JSON.parse(data);
        return {
            userId: login.userId,
            avatar: login.avatar,
            firstName: login.firstName,
            lastName: login.lastName,
            fullName: login.firstName + " " + login.lastName
        }
    }
}
