

var log = {
    i:function (message) {
        if(debug)
            console.group("调式信息-->" + window.location.pathname);
            console.log(message);
            console.groupEnd();
    },
    d:function (object) {
        if(debug)
            console.group("调式信息-->" + window.location.pathname);
            console.dir(object);
            console.groupEnd();
            return false;
    }
};
