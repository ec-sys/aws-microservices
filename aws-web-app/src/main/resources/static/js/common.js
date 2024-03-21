function isUndefined(obj) {
    if(obj == undefined || obj == null) return  true;
    return false;
}
function trim(strObj) {
    if(isUndefined(strObj)) return "";
    return strObj.trim();
}