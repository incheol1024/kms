const jsTojavaPage = function (pagination) {
    let str = "asc";
    if (pagination.descending) str = "desc";
    return {page: pagination.page-1, size: pagination.rowsPerPage, sort: pagination.sortBy + "," + str}
};

const getJavaMaxPage = function () {
    return {page: 0, size: 9999999999}
};

const catchPromise = function (error) {
    if(typeof error.response === 'undefined')
        openError(error.message);
    else if(typeof error.response.data !== 'undefined')
        openError(error.response.data);
    else
        openError(error.response);
};

const copyObject = function (source) {
    return JSON.parse(JSON.stringify(source));
};

//add find function
if (!Array.prototype.find) {
    Object.defineProperty(Array.prototype, 'find', {
        value: function (predicate) {
            // 1. Let O be ? ToObject(this value).
            if (this == null) {
                throw new TypeError('"this" is null or not defined');
            }
            let o = Object(this);
            let len = o.length >>> 0;
            if (typeof predicate !== 'function') {
                throw new TypeError('predicate must be a function');
            }
            let thisArg = arguments[1];
            let k = 0;
            while (k < len) {
                let kValue = o[k];
                if (predicate.call(thisArg, kValue, k, o)) {
                    return kValue;
                }
                k++;
            }
            return undefined;
        },
        configurable: true,
        writable: true
    });
}